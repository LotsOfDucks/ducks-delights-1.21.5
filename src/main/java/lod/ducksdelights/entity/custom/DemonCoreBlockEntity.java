package lod.ducksdelights.entity.custom;

import lod.ducksdelights.block.custom.DemonCoreBlock;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.entity.damage.ModDamageTypes;
import lod.ducksdelights.sound.ModSounds;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import javax.swing.text.html.BlockView;
import java.util.List;

public class DemonCoreBlockEntity extends BlockEntity {
    private boolean powered;
    private boolean waterlogged;
    public int ticks;
    public int range = 20;

    public DemonCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.DEMON_CORE, pos, state);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return this.createComponentlessNbt(registries);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, DemonCoreBlockEntity blockEntity) {
        ++blockEntity.ticks;
        blockEntity.powered = world.getBlockState(pos).get(DemonCoreBlock.POWERED);
        blockEntity.waterlogged = world.getBlockState(pos).get(DemonCoreBlock.WATERLOGGED);
        if (blockEntity.waterlogged && blockEntity.powered) {
            double d = pos.toCenterPos().getX();
            double e = pos.toCenterPos().getY();
            double g = pos.toCenterPos().getZ();
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + (0.5 *Math.random()), e + 0.02, g + (0.5 *Math.random()), Math.random(), 0.02, Math.random());
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + (-0.5 *Math.random()), e + 0.02, g + (-0.5 *Math.random()), (-1 *Math.random()), 0.02, (-1 * Math.random()));
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + (-0.5 *Math.random()), e + 0.02, g + (0.5 *Math.random()), (-1 *Math.random()), 0.02, Math.random());
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + (0.5 *Math.random()), e + 0.02, g + (-0.5 *Math.random()), Math.random(), 0.02, (-1 * Math.random()));
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, DemonCoreBlockEntity blockEntity) {
        ++blockEntity.ticks;
        blockEntity.powered = world.getBlockState(pos).get(DemonCoreBlock.POWERED);
        blockEntity.waterlogged = world.getBlockState(pos).get(DemonCoreBlock.WATERLOGGED);
        if (blockEntity.powered) {
            int range = blockEntity.range;
            int boxX = pos.getX();
            int boxY = pos.getY();
            int boxZ = pos.getZ();
            Box box = (new Box(boxX, boxY, boxZ, boxX + 1, boxY + 1, boxZ + 1)).expand(range + 4);
            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isMobOrPlayer);
            if (!list.isEmpty()) {
                irradiateEntities(world, list, pos, range);
            }
            if (world.getTime() % 40L == 0L) {
                if (!blockEntity.waterlogged) {
                    world.playSound(null, pos, ModSounds.DEMON_CORE_AMBIENT, SoundCategory.BLOCKS, 4.0F, 1.0F);
                } else {
                    world.playSound(null, pos, ModSounds.DEMON_CORE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 0.5F);
                }
            }
        }
    }

    private static void irradiateEntities(World world, List<LivingEntity> list, BlockPos pos, Integer val) {
        for (LivingEntity livingEntity : list) {
            if (pos.toCenterPos().isInRange(livingEntity.getPos(), val) && !livingEntity.isInCreativeMode() && livingEntity.isAlive()) {
                if (haslos(world, livingEntity, pos)) {
                    if (livingEntity.isPlayer() && livingEntity.age <= 200) {
                        return;
                    } else {
                        applyDamage(world, pos, livingEntity);
                    }
                }
            }
        }
    }

    private static boolean haslos(World world, LivingEntity livingEntity, BlockPos blockPos) {
        Vec3d entityPositionFeet = livingEntity.getPos();
        Vec3d entityPositionEyes = livingEntity.getEyePos();
        double blockCenterPosX = blockPos.toCenterPos().getX();
        double blockCenterPosY = blockPos.toCenterPos().getY();
        double blockCenterPosZ = blockPos.toCenterPos().getZ();
        long entityDistance = (long) (Math.clamp(Math.ceil(Math.abs(blockPos.toCenterPos().distanceTo(livingEntity.getPos().add(-1)))), 1, 20));
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            entityDistance = entityDistance * 2;
        }
        if (world.getTime() % entityDistance == 0L) {
            if (entityPositionEyes.getX() > blockCenterPosX) {
                Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
                boolean eyesX = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorX, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesX) {
                    return true;
                }
            } else {
                Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
                boolean eyesX = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorX, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesX) {
                    return true;
                }
            }
            if (entityPositionEyes.getY() > blockCenterPosY) {
                Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.49).getY(), blockPos.toCenterPos().getZ());
                boolean eyesY = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorY, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesY) {
                    return true;
                }
            } else {
                Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
                boolean eyesY = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorY, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesY) {
                    return true;
                }
            }
            if (entityPositionEyes.getZ() > blockCenterPosZ) {
                Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
                boolean eyesZ = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorZ, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesZ) {
                    return true;
                }
            } else {
                Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
                boolean eyesZ = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorZ, entityPositionEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!eyesZ) {
                    return true;
                }
            }
            if (entityPositionFeet.getX() > blockCenterPosX) {
                Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
                boolean footX = livingEntity.getWorld().raycast(new RaycastContext(footVectorX, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footX) {
                    return true;
                }
            } else {
                Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
                boolean footX = livingEntity.getWorld().raycast(new RaycastContext(footVectorX, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footX) {
                    return true;
                }
            }
            if (entityPositionFeet.getY() > blockCenterPosY) {
                Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.49).getY(), blockPos.toCenterPos().getZ());
                boolean footY = livingEntity.getWorld().raycast(new RaycastContext(footVectorY, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footY) {
                    return true;
                }
            } else {
                Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
                boolean footY = livingEntity.getWorld().raycast(new RaycastContext(footVectorY, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footY) {
                    return true;
                }
            }
            if (entityPositionFeet.getZ() > blockCenterPosZ) {
                Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
                boolean footZ = livingEntity.getWorld().raycast(new RaycastContext(footVectorZ, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footZ) {
                    return true;
                }
            } else {
                Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
                boolean footZ = livingEntity.getWorld().raycast(new RaycastContext(footVectorZ, entityPositionFeet, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                if (!footZ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void applyDamage(World world, BlockPos pos, LivingEntity livingEntity) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(ModDamageTypes.FISSION.getValue()).get()
        );
        livingEntity.damage((ServerWorld) world, damageSource, 1);
    }
}
