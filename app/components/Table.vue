<script setup lang="ts">
import { h, resolveComponent } from "vue";
import type { TableColumn } from "@nuxt/ui";
import { upperFirst } from "scule";
import { getPaginationRowModel } from "@tanstack/vue-table";

import type { SortingState, PaginationState } from "@tanstack/vue-table";
import { ref } from "vue";

const UButton = resolveComponent("UButton");

interface Pagination {
  pageIndex: number;
  pageSize: number;
}

interface Props<T> {
  data: T[];
  columns: TableColumn<T>[];
}

const props = defineProps<Props<any>>();

const globalFilter = ref("");
const sorting = ref<SortingState>([]);

const table = useTemplateRef("table");
const statusOptions = ["paid", "failed", "refunded"];
const baseThClass = "bg-[#FAFAFA] text-[#737373]";

const columnsWithStyle = computed(() =>
  props.columns.map((col) => ({
    ...col,
    meta: {
      ...col.meta,
      class: {
        ...col.meta?.class,
        th: `${baseThClass} ${col.meta?.class?.th || ""}`,
      },
    },
  })),
);

const pagination = ref<PaginationState>({
  pageIndex: 0,
  pageSize: 8,
});

const paginatedData = computed(() => {
  const start = pagination.value.pageIndex * pagination.value.pageSize;
  const end = start + pagination.value.pageSize;
  return props.data.slice(start, end);
});
</script>

<template>
  <div class="flex items-center justify-end gap-3 px-4 py-3.5 w-full">
    <UInput
      :model-value="
        table?.tableApi?.getColumn('email')?.getFilterValue() as string
      "
      placeholder="Pesquisar..."
      icon="i-lucide-search"
      size="lg"
      variant="none"
      :ui="{
        base: 'bg-white dark:bg-white text-gray-600 border border-gray-200 focus:border-gray-400 gap-2',
      }"
      class="w-72 min-w-[12ch] rounded-[12px] shadow-sm"
      @update:model-value="
        table?.tableApi?.getColumn('email')?.setFilterValue($event)
      "
    />

    <div class="flex items-center gap-3">
      <!-- Status Dropdown -->
      <UDropdownMenu
        :items="
          statusOptions.map((status) => ({
            label: upperFirst(status),
            type: 'checkbox',
            checked: (
              (table?.tableApi
                ?.getColumn('status')
                ?.getFilterValue() as string[]) ?? []
            ).includes(status),
            onUpdateChecked(checked: boolean) {
              const column = table?.tableApi?.getColumn('status');
              const current = (column?.getFilterValue() as string[]) ?? [];

              if (checked) {
                column?.setFilterValue([...current, status]);
              } else {
                column?.setFilterValue(current.filter((s) => s !== status));
              }
            },
            onSelect(e: Event) {
              e.preventDefault();
            },
          }))
        "
      >
        <UButton
          label="Status"
          trailing-icon="i-lucide-chevron-down"
          variant="plain"
          :ui="{
            base: 'bg-white text-[#171717] justify-between',
            rounded: 'rounded-[15px]',
            ring: 'ring-1 ring-gray-200 focus-visible:ring-0',
            color: {
              white: {
                outline:
                  'bg-white text-[#171717] ring-gray-200 hover:bg-white hover:text-[#171717]',
              },
            },
          }"
          class="min-w-[140px] font-normal !ring-1 !ring-gray-200"
        />
      </UDropdownMenu>

      <!-- Columns Dropdown -->
      <UDropdownMenu
        :items="
          table?.tableApi
            ?.getAllColumns()
            .filter((column) => column.getCanHide())
            .map((column) => ({
              label: upperFirst(column.id),
              type: 'checkbox',
              checked: column.getIsVisible(),
              onUpdateChecked(checked: boolean) {
                table?.tableApi
                  ?.getColumn(column.id)
                  ?.toggleVisibility(!!checked);
              },
              onSelect(e: Event) {
                e.preventDefault();
              },
            }))
        "
        :content="{ align: 'end' }"
      >
        <UButton
          label="Columns"
          trailing-icon="i-lucide-chevron-down"
          variant="plain"
          :ui="{
            base: 'bg-white text-[#171717] justify-between',
            rounded: 'rounded-[15px]',
            ring: 'ring-1 ring-gray-200',
            color: {
              white: {
                outline: 'ring-gray-200 text-[#171717]',
              },
            },
          }"
          class="min-w-[140px] font-normal !ring-1 !ring-gray-200"
        />
      </UDropdownMenu>
    </div>
  </div>
  <div
    class="flex flex-col border border-gray-200 rounded-xl shadow-sm bg-white"
  >
    <div
      class="flex flex-col border border-gray-200 rounded-xl shadow-sm bg-white overflow-hidden"
    >
      <UTable
        ref="table"
        :data="props.data"
        :columns="columnsWithStyle"
        v-model:sorting="sorting"
        v-model:global-filter="globalFilter"
        v-model:pagination="pagination"
        :pagination-options="{ getPaginationRowModel: getPaginationRowModel() }"
        :ui="{
          root: 'relative',
          base: 'min-w-full divide-y divide-gray-200',
          thead: 'bg-[#FAFAFA] border-b border-gray-200 dark:border-gray-200', // Adicionado dark:
          th: 'px-6 py-4 text-left text-[#737373] font-semibold text-xs uppercase tracking-wider border-b !border-gray-200 dark:!border-gray-200',
          td: 'px-6 py-5 whitespace-nowrap text-[#171717] font-medium text-sm ',
          tr: 'hover:bg-gray-50/50 transition-colors border-b border-gray-200 last:border-0',
        }"
      >
        <template #expanded="{ row }">
          <pre>{{ row.original }}</pre>
        </template>

        <template #status-data="{ row }">
          <UBadge
            size="sm"
            variant="subtle"
            class="rounded-full px-3 py-1 font-semibold"
            :color="
              row.original.status === 'Concluído'
                ? 'success'
                : row.original.status === 'Em Andamento'
                  ? 'primary'
                  : 'warning'
            "
          >
            {{ row.original.status }}
          </UBadge>
        </template>
      </UTable>
      <div
        class="px-6 py-4 border-t border-gray-100 text-sm text-gray-500 font-medium"
      >
        {{ table?.tableApi?.getFilteredSelectedRowModel().rows.length || 0 }} de
        {{ table?.tableApi?.getFilteredRowModel().rows.length || 0 }}
        selecionados
      </div>
    </div>
  </div>
  <div class="flex justify-end pt-2">
    <UPagination
      :page="(table?.tableApi?.getState().pagination.pageIndex || 0) + 1"
      :items-per-page="table?.tableApi?.getState().pagination.pageSize"
      :total="table?.tableApi?.getFilteredRowModel().rows.length"
      @update:page="(p) => table?.tableApi?.setPageIndex(p - 1)"
      class="bg-white border border-gray-200 rounded-xl shadow-sm px-4 py-2"
    />
  </div>
</template>
