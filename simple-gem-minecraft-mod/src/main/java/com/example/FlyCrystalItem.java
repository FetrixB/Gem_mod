package com.example;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.nbt.NbtCompound;

public class FlyCrystalItem extends Item {
    private static final int FLY_DURATION = 600; // 30 seconds
    private static final int COOLDOWN = 1200; // 60 seconds

    public FlyCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Check cooldown
            if (user.getItemCooldownManager().isCoolingDown(this)) {
                return TypedActionResult.fail(itemStack);
            }

            // Enable creative flight
            user.getAbilities().allowFlying = true;
            user.getAbilities().flying = true;
            user.sendAbilitiesUpdate();

            // Set cooldown
            user.getItemCooldownManager().set(this, COOLDOWN);

            // Schedule disabling flight after 30 seconds
            world.getServer().execute(() -> {
                if (!user.isRemoved()) {
                    user.getAbilities().allowFlying = false;
                    user.getAbilities().flying = false;
                    user.sendAbilitiesUpdate();
                }
            });

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_ENDER_DRAGON_FLAP,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
