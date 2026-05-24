package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.LightningEntity;
import net.minecraft.entity.projectile.TNTEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class OrbitalStrikeCrystalItem extends Item {

    public OrbitalStrikeCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {

            BlockPos center = user.getBlockPos();

            // MASSIVE CENTER EXPLOSION
            world.createExplosion(
                    null,
                    center.getX(),
                    center.getY(),
                    center.getZ(),
                    12.0F,
                    World.ExplosionSourceType.TNT
            );

            // LIGHTNING RAIN
            for (int i = 0; i < 40; i++) {

                int offsetX = world.random.nextBetween(-25, 25);
                int offsetZ = world.random.nextBetween(-25, 25);

                BlockPos strikePos = center.add(offsetX, 0, offsetZ);

                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);

                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(
                            strikePos.getX(),
                            strikePos.getY(),
                            strikePos.getZ()
                    );

                    world.spawnEntity(lightning);
                }
            }

            // TNT CIRCLES / LAYERS
            for (int radius = 5; radius <= 25; radius += 5) {

                for (int angle = 0; angle < 360; angle += 15) {

                    double rad = Math.toRadians(angle);

                    int x = center.getX() + (int)(Math.cos(rad) * radius);
                    int z = center.getZ() + (int)(Math.sin(rad) * radius);

                    TNTEntity tnt = new TNTEntity(
                            world,
                            x,
                            center.getY(),
                            z,
                            user
                    );

                    tnt.setFuse(40 + radius);

                    world.spawnEntity(tnt);
                }
            }

            // THUNDER SOUND
            world.playSound(
                    null,
                    center,
                    SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                    SoundCategory.MASTER,
                    10.0F,
                    0.5F
            );

            // COOLDOWN
            user.getItemCooldownManager().set(this, 20 * 30);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}