package yaboichips.mightymachines.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.tile.menus.GeneratorMenu;
import yaboichips.mightymachines.mixin.MenuTypeAccess;

import java.util.ArrayList;
import java.util.List;

public class MMContainers {
    public static List<MenuType<?>> containers = new ArrayList<>();

    public static final MenuType<GeneratorMenu> GENERATOR = register("generator", GeneratorMenu::new);


    private static <T extends AbstractContainerMenu> MenuType<T> register(String key, MenuType.MenuSupplier<T> builder) {
        MenuType<T> containerType = MenuTypeAccess.create(builder);
        Registry.register(Registry.MENU, new ResourceLocation(MightyMachines.MOD_ID, key), containerType);
        containers.add(containerType);
        return containerType;
    }

    public static void init(){
    }
}
