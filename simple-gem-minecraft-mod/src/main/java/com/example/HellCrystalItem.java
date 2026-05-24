package com.example;

import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;

public class HellCrystalItem extends Item {

    public HellCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {

            SmallFireballEntity fireball = new SmallFireballEntity(
                    world,
                    user,
                    user.getRotationVec(1.0F).x,
                    user.getRotationVec(1.0F).y,
                    user.getRotationVec(1.0F).z
            );

            fireball.setPosition(
                    user.getX(),
                    user.getEyeY(),
                    user.getZ()
            );

            world.spawnEntity(fireball);

            // Create bigger explosion effect at player position
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.createExplosion(user, user.getX(), user.getY(), user.getZ(), 2.0F, Explosion.DestructionType.BREAK);

                // Transform ground to netherrack in a radius
                BlockPos center = user.getBlockPos().down();
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos pos = center.add(x, 0, z);
                        if (world.getBlockState(pos).getMaterial().isReplaceable()) {
                            world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
                        }
                    }
                }
            }

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ITEM_FIRECHARGE_USE,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
