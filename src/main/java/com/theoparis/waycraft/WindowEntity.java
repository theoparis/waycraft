package com.theoparis.waycraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WindowEntity extends AbstractDecorationEntity {
    public WindowEntity(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Box calculateBoundingBox(BlockPos pos, Direction side) {
        return new Box(new Vec3d(pos), new Vec3d(pos).add(1, 1, 1));
    }

    @Override
    public void onPlace() {

    }

    @Override
    public void onBreak(ServerWorld world, @Nullable Entity breaker) {

    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }
}
