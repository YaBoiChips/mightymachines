package yaboichips.mightymachines.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import yaboichips.mightymachines.common.blocks.GeneratorBlock;
import yaboichips.mightymachines.common.tile.menus.GeneratorMenu;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.core.MMItems;
import yaboichips.mightymachines.util.BlockEntityPacketHandler;

import javax.annotation.Nonnull;

public class GeneratorTE extends RandomizableContainerBlockEntity implements IEnergyStorage, BlockEntityPacketHandler {
    public GeneratorTE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.GENERATOR, pos, state);
    }

    private NonNullList<ItemStack> contents = NonNullList.withSize(1, ItemStack.EMPTY);
    private final IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
    protected int numPlayersUsing;

    public int fuel;
    public int cooldown;
    public int energy;

    //Energy
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 10;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return 10000;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
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
        return new TranslatableComponent("container.generator_container");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new GeneratorMenu(id, player, this);
    }

    @Override
    public int getContainerSize() {
        return 1;
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
        if (block instanceof GeneratorBlock) {
            this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
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
        nbtTag.putInt("Fuel", this.getFuel());
        nbtTag.putInt("Cooldown", this.getCooldown());
        nbtTag.putInt("Energy", this.getEnergy());
        return nbtTag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(getBlockPos(), -1, getUpdateTag());
    }

    @Override
    public void handleClientPacketNoTypeCheck(ClientboundBlockEntityDataPacket packet) {
        load(packet.getTag());
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("Fuel", this.getFuel());
        compound.putInt("Cooldown", this.getCooldown());
        compound.putInt("Energy", this.getEnergy());
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.setFuel(compound.getInt("Fuel"));
        this.setCooldown(compound.getInt("Cooldown"));
        this.setEnergy(compound.getInt("Energy"));
    }

    //Controllers
    public static void tick(Level world, BlockPos p_155109_, BlockState state, GeneratorTE tile) {
        generateEnergy(world, tile);
        fuelGenerator(world, tile);
    }

    public static void generateEnergy(Level world, GeneratorTE tile){
        if (world.isClientSide) return;
        if (tile.hasFuel()){
            if(tile.getEnergy() < tile.getMaxEnergyStored()) {
                tile.setEnergy(tile.getEnergy() + 10);
                int i = world.getRandom().nextInt(16);
                world.playLocalSound(tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1, 1, true);
                if (i == 10) {
                    tile.setFuel(tile.getFuel() - 1);
                }
            }
        }
    }

    public static void fuelGenerator(Level world, GeneratorTE tile){
        if (world.isClientSide) return;
        ItemStack fuelItem = tile.getItem(0);
        if (fuelItem.getItem() == MMItems.ENERGETIC_DUST && !tile.hasFuel()){
            if(tile.getEnergy() < tile.getMaxEnergyStored()) {
                tile.setFuel(10);
                fuelItem.shrink(1);
            }
        }
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    //getters/setters
    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean hasFuel(){
        return getFuel() > 0;
    }
}