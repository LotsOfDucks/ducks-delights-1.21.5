package lod.ducksdelights.entity.custom;

import lod.ducksdelights.block.custom.DemonCoreBlock;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.entity.damage.ModDamageTypes;
import lod.ducksdelights.sound.ModSounds;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class DemonCoreBlockEntity extends BlockEntity {
    private boolean powered;
    private boolean waterlogged;
    public int ticks;
    public int range = 24;

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
        blockEntity.powered = world.isReceivingRedstonePower(pos);
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
        blockEntity.powered = world.isReceivingRedstonePower(pos);
        blockEntity.waterlogged = world.getBlockState(pos).get(DemonCoreBlock.WATERLOGGED);
        if (blockEntity.powered) {
            int j = blockEntity.range;
            int k = pos.getX();
            int l = pos.getY();
            int m = pos.getZ();
            Box box = (new Box(k, l, m, k + 1, l + 1, m + 1)).expand(j + 4).stretch(0.0, 0.0, 0.0);
            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isMobOrPlayer);
            if (!list.isEmpty()) {
                irradiateEntities(world, list, pos, j);
            }
            long s = world.getTime();
            if (s % 40L == 0L) {
                if (!blockEntity.waterlogged) {
                    world.playSound(null, pos, ModSounds.DEMON_CORE_AMBIENT, SoundCategory.BLOCKS, 10.0F, 1.0F);
                } else {
                    world.playSound(null, pos, ModSounds.DEMON_CORE_AMBIENT, SoundCategory.BLOCKS, 5.0F, 0.5F);
                }
            }
        }
    }

    private static void irradiateEntities(World world, List<LivingEntity> list, BlockPos pos, Integer val) {
        for (LivingEntity livingEntity : list) {
            if (pos.toCenterPos().isInRange(livingEntity.getPos(), val) && !livingEntity.isInCreativeMode() && livingEntity.isAlive()) {
                getRaycastSides(livingEntity, pos);
                float q = calculateReceivedDamage(world, livingEntity, pos, val);
                if (q == 1) {
                    if (livingEntity.isPlayer() && livingEntity.age <= 200) {
                        return;
                    } else {
                        applyDamage(world, pos, livingEntity);
                    }
                }
            }
        }
    }

    private static void getRaycastSides(LivingEntity livingEntity, BlockPos blockPos) {
        Position entityPositionFeet = livingEntity.getPos();
        Position entityPositionEyes = livingEntity.getEyePos();
        double blockCenterPosX = blockPos.toCenterPos().getX();
        double blockCenterPosY = blockPos.toCenterPos().getY();
        double blockCenterPosZ = blockPos.toCenterPos().getZ();
        if (entityPositionEyes.getX() > blockCenterPosX) {
            Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
        } else {
            Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
        }
        if (entityPositionEyes.getY() > blockCenterPosY) {
            Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.51).getY(), blockPos.toCenterPos().getZ());
        } else {
            Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
        }
        if (entityPositionEyes.getZ() > blockCenterPosZ) {
            Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
        } else {
            Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
        }
        if (entityPositionFeet.getX() > blockCenterPosX) {
            Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
        } else {
            Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
        }
        if (entityPositionFeet.getY() > blockCenterPosY) {
            Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.51).getY(), blockPos.toCenterPos().getZ());
        } else {
            Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
        }
        if (entityPositionFeet.getZ() > blockCenterPosZ) {
            Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
        } else {
            Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
        }
    }

    public static float calculateReceivedDamage(World world, LivingEntity livingEntity, BlockPos blockPos, Integer val) {
        long a = (long) (Math.clamp(Math.floor(Math.abs(blockPos.toCenterPos().distanceTo(livingEntity.getPos().add(-1)))), 1, val));
        long s = world.getTime();
        if (s % a == 0L) {
            Position entityPositionFeet = livingEntity.getPos();
            Position entityPositionEyes = livingEntity.getEyePos();
            double blockCenterPosX = blockPos.toCenterPos().getX();
            double blockCenterPosY = blockPos.toCenterPos().getY();
            double blockCenterPosZ = blockPos.toCenterPos().getZ();
            if (entityPositionEyes.getX() > blockCenterPosX) {
                Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
            } else {
                Vec3d eyeVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
            }
            if (entityPositionEyes.getY() > blockCenterPosY) {
                Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.51).getY(), blockPos.toCenterPos().getZ());
            } else {
                Vec3d eyeVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
            }
            if (entityPositionEyes.getZ() > blockCenterPosZ) {
                Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
            } else {
                Vec3d eyeVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
            }
            if (entityPositionFeet.getX() > blockCenterPosX) {
                Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
            } else {
                Vec3d footVectorX = new Vec3d(blockPos.toCenterPos().add(-0.51).getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().getZ());
            }
            if (entityPositionFeet.getY() > blockCenterPosY) {
                Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(0.51).getY(), blockPos.toCenterPos().getZ());
            } else {
                Vec3d footVectorY = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().add(-0.51).getY(), blockPos.toCenterPos().getZ());
            }
            if (entityPositionFeet.getZ() > blockCenterPosZ) {
                Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(0.51).getZ());
            } else {
                Vec3d footVectorZ = new Vec3d(blockPos.toCenterPos().getX(), blockPos.toCenterPos().getY(), blockPos.toCenterPos().add(-0.51).getZ());
            }
            int i = 0;
            int j = 0;
            boolean eyesX = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorX, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            boolean eyesY = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorY, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            boolean eyesZ = livingEntity.getWorld().raycast(new RaycastContext(eyeVectorZ, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            boolean footX = livingEntity.getWorld().raycast(new RaycastContext(footVectorX, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            boolean footY = livingEntity.getWorld().raycast(new RaycastContext(footVectorY, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            boolean footZ = livingEntity.getWorld().raycast(new RaycastContext(footVectorZ, livingEntity.getEyePos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
            if (!eyesX || !eyesY || !eyesZ || !footX || !footY || !footZ) {
                ++i;
            }
            ++j;
            return (float) i / (float) j;
        }
        return 0.0F;
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
