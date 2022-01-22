package yaboichips.mightymachines.core;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.tile.menus.*;

import java.util.ArrayList;
import java.util.List;

public class MMContainers {
    public static List<MenuType<?>> containers = new ArrayList<>();

    public static final MenuType<GeneratorMenu> GENERATOR = register("generator", GeneratorMenu::new);
    public static final MenuType<ManualSmasherMenu> MANUAL_SMASHER = register("manual_smasher", ManualSmasherMenu::new);
    public static final MenuType<CutterMenu> CUTTER = register("cutter", CutterMenu::new);
    public static final MenuType<QuarryMenu> QUARRY = register("quarry", QuarryMenu::new);
    public static final MenuType<FarmerMenu> FARMER = register("farmer", FarmerMenu::new);


    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> builder) {
        MenuType<T> containerType = new MenuType<>(builder);
        containerType.setRegistryName(MightyMachines.createResource(id));
        containers.add(containerType);
        return containerType;
    }

    public static void init() {
    }
}