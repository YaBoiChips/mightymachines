package yaboichips.mightymachines.core;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.tile.menus.CutterMenu;
import yaboichips.mightymachines.common.tile.menus.GeneratorMenu;
import yaboichips.mightymachines.common.tile.menus.SmasherMenu;

import java.util.ArrayList;
import java.util.List;

public class MMContainers {
    public static List<MenuType<?>> containers = new ArrayList<>();

    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> builder) {
        MenuType<T> containerType = new MenuType<>(builder);
        containerType.setRegistryName(MightyMachines.createResource(id));
        containers.add(containerType);
        return containerType;
    }    public static final MenuType<GeneratorMenu> GENERATOR = register("generator", GeneratorMenu::new);

    public static void init() {
    }    public static final MenuType<SmasherMenu> SMASHER = register("smasher", SmasherMenu::new);
    public static final MenuType<CutterMenu> CUTTER = register("cutter", CutterMenu::new);





}
