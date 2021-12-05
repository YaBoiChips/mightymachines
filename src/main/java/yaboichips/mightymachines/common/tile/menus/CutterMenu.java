package yaboichips.mightymachines.common.tile.menus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import yaboichips.mightymachines.core.MMContainers;

public class CutterMenu extends RecipeBookMenu {

    public final Container container;
    protected final Level level;


    public CutterMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv, new SimpleContainer(2));
    }

    public CutterMenu(int windowId, Inventory playerInv, Container inventory) {
        super(MMContainers.CUTTER, windowId);
        checkContainerSize(inventory, 2);
        this.container = inventory;
        inventory.startOpen(playerInv.player);
        level = playerInv.player.level;

        this.addSlot(new Slot(inventory, 0, 42, 33));
        this.addSlot(new ResultSlot(playerInv.player, inventory, 1, 114, 33));

        // Main Inventory
        int startX = 8;
        int startY = 84;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }
        // Hotbar
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInv, column, startX + (column * slotSizePlus2), 142));
        }
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

    @Override
    public void fillCraftSlotsStackedContents(StackedContents contents) {
        if (this.container instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible) this.container).fillStackedContents(contents);
        }
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe recipe) {
        return recipe.matches(this.container, this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 1;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public boolean shouldMoveToInventory(int slot) {
        return slot != 1;
    }

    public static class ResultSlot extends Slot {
        private final Player player;
        private int removeCount;

        public ResultSlot(Player play, Container container, int p_39544_, int p_39545_, int p_39546_) {
            super(container, p_39544_, p_39545_, p_39546_);
            this.player = play;
        }

        public boolean mayPlace(ItemStack p_39553_) {
            return false;
        }

        public ItemStack remove(int p_39548_) {
            if (this.hasItem()) {
                this.removeCount += Math.min(p_39548_, this.getItem().getCount());
            }

            return super.remove(p_39548_);
        }

        public void onTake(Player p_150563_, ItemStack p_150564_) {
            super.onTake(p_150563_, p_150564_);
        }

        protected void onQuickCraft(ItemStack p_39555_, int p_39556_) {
            this.removeCount += p_39556_;
        }
    }
}
