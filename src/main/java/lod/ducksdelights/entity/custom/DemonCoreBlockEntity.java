package lod.ducksdelights.entity.custom;

import lod.ducksdelights.block.custom.DemonCoreBlock;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.entity.damage.ModDamageTypes;
import lod.ducksdelights.sound.ModSounds;
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

    public static float calculateReceivedDamage(World world, Vec3d pos1, Vec3d pos2, Vec3d pos3, Vec3d pos4, Vec3d pos5, Vec3d pos6, LivingEntity livingEntity, BlockPos pos, Integer val) {
        long a = (long) (Math.clamp(Math.ceil(Math.abs(pos.toCenterPos().distanceTo(livingEntity.getPos()))), 1, val));
        long s = world.getTime();
        if (s % a == 0L) {
            Box box = livingEntity.getBoundingBox();
            double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
            double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
            double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
            double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
            double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
            if (!(d < 0.0) && !(e < 0.0) && !(f < 0.0)) {
                int i = 0;
                int j = 0;

                for (double k = 0.0; k <= 1.0; k += d) {
                    for (double l = 0.0; l <= 1.0; l += e) {
                        for (double m = 0.0; m <= 1.0; m += f) {
                            double n = MathHelper.lerp(k, box.minX, box.maxX);
                            double o = MathHelper.lerp(l, box.minY, box.maxY);
                            double p = MathHelper.lerp(m, box.minZ, box.maxZ);
                            Vec3d vec3d = new Vec3d(n + g, o, p + h);
                            Vec3d vec3d2 = new Vec3d(n + g, o + (0.9 * (box.maxY - box.minY)), p + h);
                            boolean bl = livingEntity.getWorld().raycast(new RaycastContext(pos1, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl1 = livingEntity.getWorld().raycast(new RaycastContext(pos2, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl2 = livingEntity.getWorld().raycast(new RaycastContext(pos3, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl3 = livingEntity.getWorld().raycast(new RaycastContext(pos4, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl4 = livingEntity.getWorld().raycast(new RaycastContext(pos5, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl5 = livingEntity.getWorld().raycast(new RaycastContext(pos6, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl6 = livingEntity.getWorld().raycast(new RaycastContext(pos1, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl7 = livingEntity.getWorld().raycast(new RaycastContext(pos2, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl8 = livingEntity.getWorld().raycast(new RaycastContext(pos3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl9 = livingEntity.getWorld().raycast(new RaycastContext(pos4, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl10 = livingEntity.getWorld().raycast(new RaycastContext(pos5, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            boolean bl11 = livingEntity.getWorld().raycast(new RaycastContext(pos6, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, livingEntity)).getType() == HitResult.Type.BLOCK;
                            if (!bl || !bl1 || !bl2 || !bl3 || !bl4 || !bl5 || !bl6 || !bl7 || !bl8 || !bl9 || !bl10 || !bl11) {
                                ++i;
                            }
                            ++j;
                        }
                    }
                }
                return (float) i / (float) j;
            } else {
                return 0.0F;
            }
        }
        return 0.0F;
    }

    private static void irradiateEntities(World world, List<LivingEntity> list, BlockPos pos, Integer val) {
        for (LivingEntity livingEntity : list) {
            if (pos.toCenterPos().isInRange(livingEntity.getPos(), val) && !livingEntity.isInCreativeMode() && livingEntity.getHealth() > 0) {
                Vec3d vec3d = new Vec3d(pos.toCenterPos().add(0.51).getX(), pos.toCenterPos().getY(), pos.toCenterPos().getZ());
                Vec3d vec3d1 = new Vec3d(pos.toCenterPos().add(-0.51).getX(), pos.toCenterPos().getY(), pos.toCenterPos().getZ());
                Vec3d vec3d2 = new Vec3d(pos.toCenterPos().getX(), pos.toCenterPos().add(0.51).getY(), pos.toCenterPos().getZ());
                Vec3d vec3d3 = new Vec3d(pos.toCenterPos().getX(), pos.toCenterPos().add(-0.51).getY(), pos.toCenterPos().getZ());
                Vec3d vec3d4 = new Vec3d(pos.toCenterPos().getX(), pos.toCenterPos().getY(), pos.toCenterPos().add(0.51).getZ());
                Vec3d vec3d5 = new Vec3d(pos.toCenterPos().getX(), pos.toCenterPos().getY(), pos.toCenterPos().add(-0.51).getZ());
                float q = calculateReceivedDamage(world, vec3d, vec3d1, vec3d2, vec3d3, vec3d4, vec3d5, livingEntity, pos, val);
                if (q == 1) {
                    if (livingEntity.isPlayer() && livingEntity.age <= 200 && !livingEntity.isInCreativeMode()) {
                        return;
                    } else {
                        applyDamage(world, pos, livingEntity);
                    }
                }
            }
        }
    }

    public static void applyDamage(World world, BlockPos pos, LivingEntity livingEntity) {
        long a = (long) (Math.clamp(Math.ceil(Math.abs(pos.toCenterPos().distanceTo(livingEntity.getPos()))), 1, 20));
        long s = world.getTime();
        if (s % a == 0L) {
            DamageSource damageSource = new DamageSource(
                    world.getRegistryManager()
                            .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                            .getEntry(ModDamageTypes.FISSION.getValue()).get()
            );
            livingEntity.damage((ServerWorld) world, damageSource, 1);
        }
    }
}
