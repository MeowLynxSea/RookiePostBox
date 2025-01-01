package com.cuzz.rookiepostbox.menu.confirm_menu.usage;

import com.cuzz.rookiepostbox.menu.common.ItemBuilder;
import com.cuzz.rookiepostbox.menu.confirm_menu.ConfirmMenu;
import nl.odalitadevelopments.menus.items.DisplayItem;
import nl.odalitadevelopments.menus.items.UpdatableItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

final class ConfirmMenuUsage {

    // Simple confirm menu
    public void openSimple(JavaPlugin plugin, Player player) {
        ConfirmMenu.builder()
                .title("Confirm")
                .response((response) -> {
                    if (!response) {
                        // Cancelled
                        return;
                    }

                    // Confirmed
                })
                .closeAfter(false)
                .buttonSize(ConfirmMenu.ButtonSize.SMALL)
                .confirmItem(
                        DisplayItem.of(ItemBuilder.of(Material.GREEN_WOOL)
                                .displayName("&aConfirm")
                                .lore(
                                        "&fClick to confirm"
                                )
                                .build()
                        )
                )
                .open(plugin, player);
    }

    // Advanced confirm menu with updatable title and confirm item
    public void openAdvanced(JavaPlugin plugin, Player player) {
        ConfirmMenu.builder()
                .title((contents) -> { // Updates the title every second
                    int delay = contents.cache(ConfirmMenu.DELAY_CACHE_ID, -1);
                    if (delay <= 0) return "Confirm";

                    return "Confirm in " + delay + "s";
                }, true)
                .response((response) -> {
                    if (!response) {
                        // Cancelled
                        return;
                    }

                    // Confirmed
                })
                .closeAfter(false)
                .readableTimeDelay(5)
                .buttonSize(ConfirmMenu.ButtonSize.SMALL)
                .confirmItem((contents) -> { // Updates the item every second
                    int delay = contents.cache(ConfirmMenu.DELAY_CACHE_ID, 5);

                    return UpdatableItem.of(() -> ItemBuilder.of(Material.GREEN_WOOL)
                            .displayName("&aConfirm")
                            .lore((lore) -> {
                                if (delay > 0) {
                                    lore.add("&7Can confirm in " + delay + "s");
                                    return lore;
                                }

                                lore.add("&fClick to confirm");
                                return lore;
                            })
                            .build());
                })
                .open(plugin, player);
    }
}