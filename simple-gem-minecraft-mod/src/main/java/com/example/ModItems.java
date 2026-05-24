package com.example;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FIRE_CRYSTAL = registerItem(
            "fire_crystal",
            new FireCrystalItem(new Item.Settings())
    );

    public static final Item STEALTH_CRYSTAL = registerItem(
            "stealth_crystal",
            new StealthCrystalItem(new Item.Settings())
    );

    public static final Item WATER_CRYSTAL = registerItem(
            "water_crystal",
            new WaterCrystalItem(new Item.Settings())
    );

    public static final Item FLY_CRYSTAL = registerItem(
            "fly_crystal",
            new FlyCrystalItem(new Item.Settings())
    );

    public static final Item HELL_CRYSTAL = registerItem(
            "hell_crystal",
            new HellCrystalItem(new Item.Settings())
    );

    public static final Item ORBITAL_STRIKE_CRYSTAL = registerItem(
            "orbital_strike_crystal",
            new OrbitalStrikeCrystalItem(new Item.Settings())
    );

    public static final Item STRENGTH_CRYSTAL = registerItem(
            "strength_crystal",
            new StrengthCrystalItem(new Item.Settings())
    );

    public static final Item VOID_CRYSTAL = registerItem(
            "void_crystal",
            new VoidCrystalItem(new Item.Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of("crystalmod", name),
                item
        );
    }

    public static void registerModItems() {
    }
}