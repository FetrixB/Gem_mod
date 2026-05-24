package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class VoidCrystalItem extends Item {

    public VoidCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Summon 3 endermen around the player
            for (int i = 0; i < 3; i++) {
                EndermanEntity enderman = EntityType.ENDERMAN.create(world);
                if (enderman != null) {
                    double angle = (i / 3.0) * 2 * Math.PI;
                    double distance = 2.0;
                    double x = user.getX() + Math.cos(angle) * distance;
                    double z = user.getZ() + Math.sin(angle) * distance;
                    
                    enderman.setPosition(x, user.getY(), z);
                    
                    // Set enderman as angry/aggressive
                    enderman.setTarget(null);
                    
                    world.spawnEntity(enderman);
                }
            }

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
