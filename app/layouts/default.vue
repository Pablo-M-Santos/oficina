<script setup lang="ts">
import type { NavigationMenuItem } from "@nuxt/ui";

const navItems = ref<NavigationMenuItem[]>([
  {
    label: "Dashboard",
    icon: "i-lucide-layout-dashboard",
    to: "/",
  },
  {
    label: "Ordens de Serviço",
    icon: "i-lucide-clipboard-list",
    to: "/ordens",
  },
  {
    label: "Clientes",
    icon: "i-lucide-users",
    to: "/clientes",
  },
  {
    label: "Veículos",
    icon: "i-lucide-car",
    to: "/veiculos",
  },
  {
    label: "Mecânicos",
    icon: "i-lucide-wrench",
    to: "/mecanicos",
  },
  {
    label: "Financeiro",
    icon: "i-lucide-wallet",
    to: "/financeiro",
  },
  {
    label: "Relatórios",
    icon: "i-lucide-bar-chart-2",
    to: "/relatorios",
  },
]);

const bottomItems = ref<NavigationMenuItem[]>([
  {
    label: "Configurações",
    icon: "i-lucide-settings",
    to: "/configuracoes",
  },
]);

const notifications = ref(3);
</script>

<template>
  <div class="flex h-screen">
    <aside
      class="flex flex-col w-60 h-full bg-[#171717] border-r border-[var(--ui-border)]"
    >
      <div class="flex h-20 items-center gap-3 px-4">
        <UIcon
          name="i-lucide-wrench"
          class="w-8 h-8 text-[var(--ui-primary)] shrink-0"
        />
        <span class="text-xl font-bold tracking-tight">AutoOS</span>
      </div>

      <nav class="flex-1 px-2 py-6 overflow-y-auto">
        <UNavigationMenu
          orientation="vertical"
          :items="navItems"
          :ui="{ list: 'space-y-4' }"
          class="w-full"
        />
      </nav>

      <div class="px-2 p-3 border-t border-[var(--ui-border)]">
        <UNavigationMenu
          orientation="vertical"
          :items="bottomItems"
          class="w-full"
        />
      </div>
    </aside>

    <main class="flex flex-1 flex-col overflow-auto bg-white">
      <header
        class="flex h-20 items-center justify-end border-b border-[var(--ui-border)]"
      >
        <div class="flex items-center gap-4 pr-4">
          <button
            type="button"
            class="relative flex h-10 w-10 items-center justify-center rounded-full border border-black bg-white text-black transition-colors hover:bg-black hover:text-white"
          >
            <UIcon
              name="i-lucide-bell"
              class="h-5 w-5 text-current transition-colors"
            />

      
            <span
              v-if="notifications > 0"
                class="absolute top-0 right-0 translate-x-1/4 -translate-y-1/4 flex h-5 w-5 items-center justify-center rounded-full bg-red-500 text-xs font-bold text-white"
            >
              {{ notifications }}
            </span>
          </button>

          <div
            class="flex items-center gap-3 rounded-xl border border-gray-200 bg-[#171717] px-3 py-2"
          >
            <div class="flex flex-col text-right leading-tight">
              <span class="text-sm font-semibold text-white">
                Usuário Admin
              </span>
              <span class="text-xs text-white">Gerente da Oficina</span>
            </div>

            <UAvatar
              src="https://i.pravatar.cc/80?img=12"
              alt="Foto do perfil"
              size="lg"
            />
          </div>
        </div>
      </header>

      <div class="flex-1 min-h-0">
        <slot />
      </div>
    </main>
  </div>
</template>
