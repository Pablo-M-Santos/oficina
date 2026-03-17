<script setup lang="ts">
import { h, resolveComponent } from "vue";
import { upperFirst } from "scule";
import type { TableColumn } from "@nuxt/ui";
import { useClipboard } from "@vueuse/core";
import type { Column } from "@tanstack/vue-table";
import Table from "~/components/Table.vue";

const UButton = resolveComponent("UButton");
const UCheckbox = resolveComponent("UCheckbox");
const UBadge = resolveComponent("UBadge");
const UDropdownMenu = resolveComponent("UDropdownMenu");

const toast = useToast();
const { copy } = useClipboard();

type Payment = {
  id: string;
  date: string;
  status: "paid" | "failed" | "refunded";
  email: string;
  amount: number;
};

const data = ref<Payment[]>([
  {
    id: "4600",
    date: "2024-03-11T15:30:00",
    status: "paid",
    email: "james.anderson@example.com",
    amount: 594,
  },
  {
    id: "4599",
    date: "2024-03-11T10:10:00",
    status: "failed",
    email: "mia.white@example.com",
    amount: 276,
  },
  {
    id: "4598",
    date: "2024-03-11T08:50:00",
    status: "refunded",
    email: "william.brown@example.com",
    amount: 315,
  },
  {
    id: "4597",
    date: "2024-03-10T19:45:00",
    status: "paid",
    email: "emma.davis@example.com",
    amount: 529,
  },
  {
    id: "4596",
    date: "2024-03-10T15:55:00",
    status: "paid",
    email: "ethan.harris@example.com",
    amount: 639,
  },
  {
    id: "4595",
    date: "2024-03-10T13:40:00",
    status: "refunded",
    email: "ava.thomas@example.com",
    amount: 428,
  },
  {
    id: "4594",
    date: "2024-03-10T09:15:00",
    status: "paid",
    email: "michael.wilson@example.com",
    amount: 683,
  },
  {
    id: "4593",
    date: "2024-03-09T20:25:00",
    status: "failed",
    email: "olivia.taylor@example.com",
    amount: 947,
  },
  {
    id: "4592",
    date: "2024-03-09T18:45:00",
    status: "paid",
    email: "benjamin.jackson@example.com",
    amount: 851,
  },
  {
    id: "4591",
    date: "2024-03-09T16:05:00",
    status: "paid",
    email: "sophia.miller@example.com",
    amount: 762,
  },
  {
    id: "4590",
    date: "2024-03-09T14:20:00",
    status: "paid",
    email: "noah.clark@example.com",
    amount: 573,
  },
  {
    id: "4589",
    date: "2024-03-09T11:35:00",
    status: "failed",
    email: "isabella.lee@example.com",
    amount: 389,
  },
  {
    id: "4588",
    date: "2024-03-08T22:50:00",
    status: "refunded",
    email: "liam.walker@example.com",
    amount: 701,
  },
  {
    id: "4587",
    date: "2024-03-08T20:15:00",
    status: "paid",
    email: "charlotte.hall@example.com",
    amount: 856,
  },
  {
    id: "4586",
    date: "2024-03-08T17:40:00",
    status: "paid",
    email: "mason.young@example.com",
    amount: 492,
  },
  {
    id: "4585",
    date: "2024-03-08T14:55:00",
    status: "failed",
    email: "amelia.king@example.com",
    amount: 637,
  },
  {
    id: "4584",
    date: "2024-03-08T12:30:00",
    status: "paid",
    email: "elijah.wright@example.com",
    amount: 784,
  },
  {
    id: "4583",
    date: "2024-03-08T09:45:00",
    status: "refunded",
    email: "harper.scott@example.com",
    amount: 345,
  },
  {
    id: "4582",
    date: "2024-03-07T23:10:00",
    status: "paid",
    email: "evelyn.green@example.com",
    amount: 918,
  },
  {
    id: "4581",
    date: "2024-03-07T20:25:00",
    status: "paid",
    email: "logan.baker@example.com",
    amount: 567,
  },
]);

const columns: TableColumn<Payment>[] = [
  {
    id: "select",
    header: ({ table }) =>
      h(UCheckbox, {
        modelValue: table.getIsSomePageRowsSelected()
          ? "indeterminate"
          : table.getIsAllPageRowsSelected(),
        "onUpdate:modelValue": (value: boolean | "indeterminate") =>
          table.toggleAllPageRowsSelected(!!value),
        "aria-label": "Select all",
      }),
    cell: ({ row }) =>
      h(UCheckbox, {
        modelValue: row.getIsSelected(),
        "onUpdate:modelValue": (value: boolean | "indeterminate") =>
          row.toggleSelected(!!value),
        "aria-label": "Select row",
      }),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "id",
    header: ({ column }) => getSimpleHeader(column, "ID"),
    cell: ({ row }) => `#${row.getValue("id")}`,
  },
  {
    accessorKey: "date",
    header: ({ column }) => getSimpleHeader(column, "Date"),
    cell: ({ row }) => {
      return new Date(row.getValue("date")).toLocaleString("en-US", {
        day: "numeric",
        month: "short",
        hour: "2-digit",
        minute: "2-digit",
        hour12: false,
      });
    },
  },
  {
    accessorKey: "status",
    filterFn: (row, columnId, filterValue: string[]) => {
      if (!filterValue?.length) return true;
      return filterValue.includes(row.getValue(columnId));
    },

    cell: ({ row }) => {
      const status = row.getValue("status") as "paid" | "failed" | "refunded";

      const configMap = {
        paid: {
          label: "Pago",
          dot: "bg-green-500",
          bg: "bg-green-100",
          border: "border border-green-200",
          text: "text-green-700",
        },
        failed: {
          label: "Falhou",
          dot: "bg-red-500",
          bg: "bg-red-100",
          border: "border border-red-200",
          text: "text-red-700",
        },
        refunded: {
          label: "Reembolsado",
          dot: "bg-gray-400",
          bg: "bg-gray-100",
          border: "border border-gray-200",
          text: "text-gray-700",
        },
      };

      const config = configMap[status];

      return h(
        "div",
        {
          class: [
            "inline-flex items-center gap-2",

        
            "px-2.5 py-1 rounded-full px-3",

            config.bg,
            config.border,
          ],
        },
        [
          h("span", {
            class: `w-2 h-2 rounded-full ${config.dot}`,
          }),
          h(
            "span",
            {
              class: `text-sm font-medium ${config.text}`,
            },
            config.label,
          ),
        ],
      );
    },
  },
  {
    accessorKey: "email",
    header: ({ column }) => getSimpleHeader(column, "Email"),
  },
  {
    accessorKey: "amount",
    header: ({ column }) => getSimpleHeader(column, "Amount"),
    meta: {
      class: {
        th: "text-right",
        td: "text-right font-medium",
      },
    },
    cell: ({ row }) => {
      const amount = Number.parseFloat(row.getValue("amount"));
      return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "EUR",
      }).format(amount);
    },
  },
  {
    id: "actions",
    enableHiding: false,
    meta: {
      class: {
        td: "text-right",
      },
    },
    cell: ({ row }) => {
      const items = [
        {
          type: "label",
          label: "Actions",
        },
        {
          label: "Copy payment ID",
          onSelect() {
            copy(row.original.id);

            toast.add({
              title: "Payment ID copied to clipboard!",
              color: "success",
              icon: "i-lucide-circle-check",
            });
          },
        },
        {
          label: row.getIsExpanded() ? "Collapse" : "Expand",
          onSelect() {
            row.toggleExpanded();
          },
        },
        {
          type: "separator",
        },
        {
          label: "View customer",
        },
        {
          label: "View payment details",
        },
      ];

      return h(
        UDropdownMenu,
        {
          content: {
            align: "end",
          },
          items,
          "aria-label": "Actions dropdown",
        },
        () =>
          h(UButton, {
            icon: "i-lucide-ellipsis-vertical",
            color: "neutral",
            variant: "ghost",
            "aria-label": "Actions dropdown",
          }),
      );
    },
  },
];


function getSimpleHeader(column: Column<any>, label: string) {
  const isSorted = column.getIsSorted();

  return h(UButton, {
    label,
    variant: "ghost",
    class: [
      "w-full justify-start",
      "text-[#737373] font-semibold",
      "bg-transparent hover:bg-transparent",
      "focus:bg-transparent focus-visible:bg-transparent",
      "ring-0 focus:ring-0 shadow-none",
      "active:bg-transparent",
      "rounded-none",
    ],
    icon: isSorted
      ? isSorted === "asc"
        ? "i-lucide-arrow-up-narrow-wide"
        : "i-lucide-arrow-down-wide-narrow"
      : "i-lucide-arrow-up-down",
    onClick: () =>
      column.toggleSorting(column.getIsSorted() === "asc"),
  });
}


</script>

<template>
  <div class="min-h-screen bg-[#FAFAFA] p-15">
    <Table :data="data" :columns="columns" />
  </div>
</template>

<style>
:deep(th) {
  background-color: #f9fafb;
  color: #111827;
  font-weight: 600;
}
</style>
