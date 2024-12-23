package com.rhseung.backpack.crate

import com.rhseung.backpack.init.ModBlockEntityTypes
import net.minecraft.block.BlockState
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.block.entity.ViewerCountManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrateBlockEntity(
    pos: BlockPos,
    state: BlockState
): LootableContainerBlockEntity(ModBlockEntityTypes.CRATE, pos, state) {
    private var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(27, ItemStack.EMPTY);

    private val stateManager = object : ViewerCountManager() {
        override fun onContainerOpen(world: World, pos: BlockPos, state: BlockState) {
            this@CrateBlockEntity.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
            this@CrateBlockEntity.setOpen(state, true);
        }

        override fun onContainerClose(world: World, pos: BlockPos, state: BlockState) {
            this@CrateBlockEntity.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
            this@CrateBlockEntity.setOpen(state, false);
        }

        override fun onViewerCountUpdate(
            world: World,
            pos: BlockPos,
            state: BlockState,
            oldViewerCount: Int,
            newViewerCount: Int
        ) {}

        override fun isPlayerViewing(player: PlayerEntity): Boolean {
            val currentScreenHandler = player.currentScreenHandler;
            return if (currentScreenHandler is GenericContainerScreenHandler)
                currentScreenHandler.inventory == this@CrateBlockEntity;
            else
                false;
        }
    }

    fun getItems() = inventory;

    override fun writeNbt(nbt: NbtCompound, registries: RegistryWrapper.WrapperLookup) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, registries);
    }

    override fun readNbt(nbt: NbtCompound, registries: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registries);
        inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registries);
    }

    override fun onOpen(player: PlayerEntity) {
        if (!this.removed && !player.isSpectator)
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.cachedState);
    }

    override fun onClose(player: PlayerEntity) {
        if (!this.removed && !player.isSpectator)
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.cachedState);
    }

    fun tick() {
        if (!this.removed)
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.cachedState);
    }

    fun setOpen(state: BlockState, open: Boolean) {
        this.world?.setBlockState(this.getPos(), state.with(CrateBlock.Companion.OPEN, open), 3);
    }

    fun playSound(state: BlockState, soundEvent: SoundEvent) {
        val vec3i = state.get(CrateBlock.Companion.FACING).vector;
        val d = pos.x.toDouble() + 0.5 + vec3i.x.toDouble() / 2.0;
        val e = pos.y.toDouble() + 0.5 + vec3i.y.toDouble() / 2.0;
        val f = pos.z.toDouble() + 0.5 + vec3i.z.toDouble() / 2.0;
        this.world?.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, (this.world!!.random.nextFloat().times(0.1f)) + 0.9f);
    }

    override fun size(): Int {
        return inventory.size;
    }

    override fun getDisplayName(): Text {
        return Text.literal("Crate");
    }

    override fun getContainerName(): Text {
        return Text.literal("Crate Container");
    }

    override fun getHeldStacks(): DefaultedList<ItemStack> {
        return this.inventory;
    }

    override fun setHeldStacks(inventory: DefaultedList<ItemStack>) {
        this.inventory = inventory;
    }

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }
}