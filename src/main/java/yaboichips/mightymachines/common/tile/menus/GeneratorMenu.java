package yaboichips.mightymachines.common.tile.menus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import yaboichips.mightymachines.core.MMContainers;

public class GeneratorMenu extends AbstractContainerMenu {

    public final Container inventory;
    private final ContainerData fuelTime;

    public GeneratorMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv, new SimpleContainer(1), new SimpleContainerData(1));
    }
    public GeneratorMenu(int windowId, Inventory playerInv, Container inventory, ContainerData fuelTime) {
        super(MMContainers.GENERATOR, windowId);
        this.fuelTime = fuelTime;
        this.inventory = inventory;

        this.addSlot(new Slot(inventory, 1, 79, 58));

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

        this.addDataSlots(fuelTime);
    }
    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public ContainerData getFuelTime() {
        return fuelTime;
    }
}
