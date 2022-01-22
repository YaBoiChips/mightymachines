package yaboichips.mightymachines.common.tile.menus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yaboichips.mightymachines.core.MMContainers;

public class FarmerMenu extends AbstractContainerMenu {

    public final Container container;
    public final ContainerData energy;
    protected final Level level;

    public FarmerMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv, new SimpleContainer(54), new SimpleContainerData(1));
    }

    public FarmerMenu(int windowId, Inventory playerInv, Container inventory, ContainerData energy) {
        super(MMContainers.FARMER, windowId);
        checkContainerSize(inventory, 54);
        this.energy = energy;
        this.container = inventory;
        inventory.startOpen(playerInv.player);
        level = playerInv.player.level;
        int i = (6 - 4) * 18;

        for(int j = 0; j < 6; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new ResultSlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 161 + i));
        }
    }

    public int getEnergy(){
        return this.energy.get(0);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public ItemStack quickMoveStack(Player p_39651_, int p_39652_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39652_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39652_ < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    public void removed(Player playerIn) {
        super.removed(playerIn);
    }

    public static class ResultSlot extends Slot {
        public ResultSlot(Container container, int x, int y, int z) {
            super(container, x, y, z);
        }

        @Override
        public boolean mayPlace(ItemStack p_40231_) {
            return false;
        }
    }
}