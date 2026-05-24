package com.example;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class StealthCrystalItem extends Item {

    public StealthCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            user.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.SPEED, 300, 2)
            );

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.2F
            );
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
