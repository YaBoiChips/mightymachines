package yaboichips.mightymachines.common.tile;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import yaboichips.mightymachines.common.blocks.FarmerBlock;
import yaboichips.mightymachines.common.tile.menus.FarmerMenu;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.mixin.ClientboundBlockEntityDataPacketAccess;
import yaboichips.mightymachines.util.BlockEntityPacketHandler;
import yaboichips.mightymachines.util.MachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FarmerTE extends EnergeticTileEntity implements BlockEntityPacketHandler, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{54};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final IItemHandlerModifiable items = createHandler();
    private MachineEnergyStorage energyStorage = new MachineEnergyStorage(getMaxEnergyStored(), getMaxTransfer());
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energyStorage);
    public static final int MAX_ENERGY = 10000;
    public int work;

    protected int numPlayersUsing;
    private NonNullList<ItemStack> contents = NonNullList.withSize(54, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);


    public FarmerTE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.FARMER, pos, state);
    }

    //Controllers
    public static void tick(Level world, BlockPos pos, BlockState state, FarmerTE tile) {
        if (tile.getEnergy() >= tile.getMaxEnergyStored()){
            tile.setEnergy(tile.getMaxEnergyStored());
        }
        world.sendBlockUpdated(tile.getBlockPos(), world.getBlockState(tile.getBlockPos()), world.getBlockState(tile.getBlockPos()), 2);
        harvest(world, pos, tile);
        suckItems(world, pos, tile);
        System.out.println(tile.energyStorage.getEnergyStored());
    }

    public static void harvest(Level world, BlockPos pos, FarmerTE tile) {
        if (world.isClientSide) return;
        if (tile.getEnergy() > 0) {
            for (BlockPos block : BlockPos.betweenClosed(pos.getX() - 2, pos.getY(), pos.getZ() - 2, pos.getX() + 2, pos.getY(), pos.getZ() + 2)) {
                if (tile.getWork() <= 0) {
                    BlockState crop = world.getBlockState(block);
                    if (crop.getBlock() instanceof CropBlock) {
                        if (((CropBlock) crop.getBlock()).isMaxAge(crop)) {
                            tile.setEnergy(tile.getEnergy() - 100);
                            world.destroyBlock(block, true);
                            world.setBlock(block, crop.getBlock().defaultBlockState(), 1);
                            tile.setWork(100);
                        }
                    }
                    if (crop == Blocks.PUMPKIN.defaultBlockState() || crop == Blocks.MELON.defaultBlockState()) {
                        tile.setEnergy(tile.getEnergy() - 100);
                        world.destroyBlock(block, true);
                        tile.setWork(100);
                    }
                }
            }
            tile.setWork(tile.getWork() - 1);
            tile.setChanged();
        }
    }

    public static void suckItems(Level world, BlockPos pos, FarmerTE tile) {
        AABB aabb = new AABB(pos).inflate(4);
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, aabb);
        if (tile.getWork() <= 0) {
            for (ItemEntity itemEntity : itemEntities) {
                tile.setEnergy(tile.getEnergy() - 3);
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
                    tile.setChanged();
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
        return new TranslatableComponent("container.farmer_container");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new FarmerMenu(id, player, this, dataAccess);
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
        if (block instanceof FarmerBlock) {
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
        if (capability == CapabilityEnergy.ENERGY) {
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
        saving(nbtTag);
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loading(tag);
        super.handleUpdateTag(tag);
    }

    @Override
    public void handleClientPacketNoTypeCheck(ClientboundBlockEntityDataPacket packet) {
        load(packet.getTag());
    }


    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saving(tag);
        return ClientboundBlockEntityDataPacketAccess.create(getBlockPos(), MMBlockEntities.FARMER, tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        loading(tag);
        super.onDataPacket(net, pkt);
    }

    public void saving(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("Work", this.getWork());
        compound.putInt("Energy", this.energyStorage.getEnergyStored());
        ContainerHelper.saveAllItems(compound, this.contents);
    }

    private void loading(CompoundTag nbt) {
        this.contents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.setWork(nbt.getInt("Work"));
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.contents);
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("Work", this.getWork());
        compound.putInt("Energy", this.energyStorage.getEnergyStored());
        ContainerHelper.saveAllItems(compound, this.contents);
    }


    @Override
    public void load(CompoundTag compound) {
        loading(compound);
        if(compound.contains("Energy", Tag.TAG_INT)) {
            this.energyStorage = new MachineEnergyStorage(getMaxEnergyStored(), getMaxTransfer(), compound.getInt("Energy"));
        }
        super.load(compound);
    }



    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index == 0) {
                return FarmerTE.this.energyStorage.getEnergyStored();
            }
            return 0;
        }
        @Override
        public void set(int index, int value) {
            if (index == 0) {
                FarmerTE.this.energyStorage.setEnergyStored(value);
            }
        }
        @Override
        public int getCount() {
            return 1;
        }
    };

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
        return maxReceive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public void setEnergyStored(int energy) {
        this.energyStorage.setEnergyStored(energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return MAX_ENERGY;
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
        return this.energyStorage.getEnergyStored();
    }

    public void setEnergy(int energy) {
        this.energyStorage.setEnergyStored(energy);

    }
}
