package yaboichips.mightymachines.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import yaboichips.mightymachines.MightyMachines;

import java.util.ArrayList;
import java.util.List;

public class MMItems {
    public static List<Item> items = new ArrayList<>();

    public static final CreativeModeTab TAB = new CreativeModeTab(MightyMachines.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.IRON_INGOT);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tab_item_search.png");
        }
    };

    public static Item createItem(Item item, String id) {
        return createItem(item, MightyMachines.createResource(id));
    }

    public static Item createBlockItem(Block block, Item.Properties props) {
        return createItem(new BlockItem(block, props), Registry.BLOCK.getKey(block));
    }

    public static Item createItem(Item item, ResourceLocation id) {
        if (id != null && !id.equals(new ResourceLocation("minecraft:air"))) {
            item.setRegistryName(id);

            items.add(item);

            return item;
        } else return null;
    }

    public static void init() {
    }
}
