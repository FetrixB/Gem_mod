package com.example;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup CRYSTALS_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of("crystalmod", "crystals"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.FIRE_CRYSTAL))
                    .displayName(Text.literal("Crystals"))
                    .entries((context, entries) -> {
                        entries.add(ModItems.FIRE_CRYSTAL);
                        entries.add(ModItems.STEALTH_CRYSTAL);
                        entries.add(ModItems.WATER_CRYSTAL);
                        entries.add(ModItems.FLY_CRYSTAL);
                    })
                    .build()
    );

    public static void registerItemGroups() {
    }
}