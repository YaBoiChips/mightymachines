package yaboichips.mightymachines.common.tile;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import yaboichips.mightymachines.common.blocks.QuarryBlock;
import yaboichips.mightymachines.common.tile.menus.QuarryMenu;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.util.BlockEntityPacketHandler;
import yaboichips.mightymachines.util.MachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class QuarryTE extends EnergeticTileEntity implements BlockEntityPacketHandler, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};
    private final IItemHandlerModifiable items = createHandler();
    private final MachineEnergyStorage energyStorage = new MachineEnergyStorage(getMaxEnergyStored(), getMaxTransfer());
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energyStorage);
    public int work;

    protected int numPlayersUsing;
    private NonNullList<ItemStack> contents = NonNullList.withSize(54, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);


    public QuarryTE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.QUARRY, pos, state);
    }

    //Controllers
    public static void tick(Level world, BlockPos pos, BlockState state, QuarryTE tile) {
        mine(world, pos, tile);
        suckItems(world, pos, tile);
    }

    public static void mine(Level world, BlockPos pos, QuarryTE tile) {
        if (world.isClientSide) return;
        if (tile.getEnergy() > 0) {
            for (BlockPos block : BlockPos.betweenClosed(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, 1, pos.getZ() + 2)) {
                if (tile.getWork() <= 0) {
                    BlockState state = world.getBlockState(block);
                    if (state.getMaterial() != Material.AIR) {
                        world.destroyBlock(block, true);
                        tile.setWork(120);
                    }
                }
            }
            tile.setWork(tile.getWork() - 1);
        }
    }

    public static void suckItems(Level world, BlockPos pos, QuarryTE tile) {
        AABB aabb = new AABB(pos).inflate(4);
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, aabb);
        if (tile.getWork() <= 0) {
            for (ItemEntity itemEntity : itemEntities) {
                final ItemStack pickupStack = itemEntity.getItem().copy().split(1);
                for (int slot = 0; slot < tile.items.getSlots(); slot++) {
                    if (tile.items.isItemValid(slot, pickupStack) && tile.items.insertItem(slot, pickupStack, true).getCount() != pickupStack.getCount()) {
                        ItemStack actualPickupStack = itemEntity.getItem().split(1);
                        ItemStack remaining = tile.items.insertItem(slot, actualPickupStack, false);
                        if (!remaining.isEmpty()) {
                            final ItemEntity item = new ItemEntity(EntityType.ITEM, world);
                            item.setItem(remaining);
                            item.setPos(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
                            item.lifespan = remaining.getEntityLifespan(world);
                            world.addFreshEntity(item);
                        }
                    }
                }
            }
        }
    }

    //Container
    @Override
    public NonNullList<ItemStack> getItems() {
        return this.contents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.contents = itemsIn;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.quarry_container");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new QuarryMenu(id, player, this);
    }

    @Override
    public int getContainerSize() {
        return 54;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof QuarryBlock) {
            this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @Override
    public void addEnergy(int energy) {
        this.energyStorage.setEnergyStored(getEnergyStored() + energy);
    }


    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nonnull Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        if (capability == CapabilityEnergy.ENERGY){
            return energyHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return super.getCapability(cap);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }

    //nbt
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbtTag = super.getUpdateTag();
        return nbtTag;
    }

    @Override
    public void handleClientPacketNoTypeCheck(ClientboundBlockEntityDataPacket packet) {
        load(packet.getTag());
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("Work", this.getWork());
        compound.putInt("Energy", this.getEnergy());
        CompoundTag compoundtag = new CompoundTag();
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.contents);
        }
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.setWork(compound.getInt("Work"));
        this.setEnergy(compound.getInt("Energy"));
        super.load(compound);
        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.contents);
        }
    }

    //Getters/Setters
    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(i, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 10;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return getEnergy();
    }

    @Override
    public void setEnergyStored(int energy) {
        this.energyStorage.setEnergyStored(energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return 10000;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }

    public void setEnergy(int energy) {
        this.energyStorage.setEnergyStored(energy);
    }
}