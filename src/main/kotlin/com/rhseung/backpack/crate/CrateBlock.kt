package com.rhseung.backpack.crate

import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class CrateBlock(settings: Settings) : BlockWithEntity(settings) {
    init {
        this.defaultState = this.stateManager.defaultState
            .with(FACING, Direction.NORTH)
            .with(OPEN, false);
    }

    companion object {
        val CODEC = createCodec(::CrateBlock);
        val FACING = Properties.FACING;
        val OPEN = Properties.OPEN;
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult
    ): ActionResult {
        if (world is ServerWorld) {
            val blockEntity = world.getBlockEntity(pos);
            if (blockEntity is CrateBlockEntity)
                player.openHandledScreen(blockEntity);
        }

        return ActionResult.SUCCESS;
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return CODEC;
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL;
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return CrateBlockEntity(pos, state);
    }

    override fun hasComparatorOutput(state: BlockState): Boolean {
        return false;
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val blockEntity = world.getBlockEntity(pos);
        if (blockEntity is CrateBlockEntity)
            blockEntity.tick();
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING, OPEN);
    }

    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return this.defaultState.with(FACING, ctx.playerLookDirection.opposite);
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        ItemScatterer.onStateReplaced(state, newState, world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}