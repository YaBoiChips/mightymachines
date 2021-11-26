package yaboichips.mightymachines.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.util.BlockEntityPacketHandler;

public class GeneratorTE extends RandomizableContainerBlockEntity implements IEnergyStorage, BlockEntityPacketHandler {
    public GeneratorTE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.GENERATOR, pos, state);
    }

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
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_59625_) {

    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
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
    }

    public static void generateEnergy(Level world, BlockPos p_155109_, BlockState state, GeneratorTE tile){

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

}
