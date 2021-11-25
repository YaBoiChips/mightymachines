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

    public static final Item RUBY_ORE = createBlockItem(MMBlocks.RUBY_ORE, new Item.Properties().tab(TAB));
    public static final Item AMETHYST_ORE = createBlockItem(MMBlocks.AMETHYST_ORE, new Item.Properties().tab(TAB));
    public static final Item SAPPHIRE_ORE = createBlockItem(MMBlocks.SAPPHIRE_ORE, new Item.Properties().tab(TAB));
    public static final Item TOPAZ_ORE = createBlockItem(MMBlocks.TOPAZ_ORE, new Item.Properties().tab(TAB));
    public static final Item OPAL_ORE = createBlockItem(MMBlocks.OPAL_ORE, new Item.Properties().tab(TAB));
    public static final Item AMBER_ORE = createBlockItem(MMBlocks.AMBER_ORE, new Item.Properties().tab(TAB));
    public static final Item ONYX_ORE = createBlockItem(MMBlocks.ONYX_ORE, new Item.Properties().tab(TAB));
    public static final Item PYRITE_ORE = createBlockItem(MMBlocks.PYRITE_ORE, new Item.Properties().tab(TAB));

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
