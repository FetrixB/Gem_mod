package com.example;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class WaterCrystalItem extends Item {

    public WaterCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            user.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 600, 0)
            );

            // Shoot water bullets
            SnowballEntity waterBullet = new SnowballEntity(world, user);
            waterBullet.setVelocity(user.getRotationVec(1.0F).multiply(1.5));
            world.spawnEntity(waterBullet);

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ITEM_BUCKET_FILL,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
