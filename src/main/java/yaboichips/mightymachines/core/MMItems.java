package yaboichips.mightymachines.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.items.Jetpack;
import yaboichips.mightymachines.common.items.guns.AmberGun;
import yaboichips.mightymachines.common.items.guns.OpalGun;
import yaboichips.mightymachines.common.items.guns.PyriteGun;
import yaboichips.mightymachines.common.items.guns.RubyGun;

import java.util.ArrayList;
import java.util.List;

public class MMItems {
    public static final CreativeModeTab TAB = new CreativeModeTab(MightyMachines.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMItems.THALLIUM_DUST);
        }
    };
    public static List<Item> items = new ArrayList<>();
    public static final Item RUBY_ORE = createBlockItem(MMBlocks.RUBY_ORE, new Item.Properties().tab(TAB));
    public static final Item SAPPHIRE_ORE = createBlockItem(MMBlocks.SAPPHIRE_ORE, new Item.Properties().tab(TAB));
    public static final Item TOPAZ_ORE = createBlockItem(MMBlocks.TOPAZ_ORE, new Item.Properties().tab(TAB));
    public static final Item OPAL_ORE = createBlockItem(MMBlocks.OPAL_ORE, new Item.Properties().tab(TAB));
    public static final Item AMBER_ORE = createBlockItem(MMBlocks.AMBER_ORE, new Item.Properties().tab(TAB));
    public static final Item ONYX_ORE = createBlockItem(MMBlocks.ONYX_ORE, new Item.Properties().tab(TAB));
    public static final Item PYRITE_ORE = createBlockItem(MMBlocks.PYRITE_ORE, new Item.Properties().tab(TAB));
    public static final Item THALLIUM_ORE = createBlockItem(MMBlocks.THALLIUM_ORE, new Item.Properties().tab(TAB));
    public static final Item BAUXITE_ORE = createBlockItem(MMBlocks.BAUXITE_ORE, new Item.Properties().tab(TAB));
    public static final Item TIN_ORE = createBlockItem(MMBlocks.TIN_ORE, new Item.Properties().tab(TAB));

    public static final Item RUBY = createItem(new Item(new Item.Properties().tab(TAB)), "ruby");
    public static final Item AMETHYST = createItem(new Item(new Item.Properties().tab(TAB)), "amethyst");
    public static final Item SAPPHIRE = createItem(new Item(new Item.Properties().tab(TAB)), "sapphire");
    public static final Item TOPAZ = createItem(new Item(new Item.Properties().tab(TAB)), "topaz");
    public static final Item OPAL = createItem(new Item(new Item.Properties().tab(TAB)), "opal");
    public static final Item AMBER = createItem(new Item(new Item.Properties().tab(TAB)), "amber");
    public static final Item ONYX = createItem(new Item(new Item.Properties().tab(TAB)), "onyx");
    public static final Item PYRITE = createItem(new Item(new Item.Properties().tab(TAB)), "pyrite");
    public static final Item THALLIUM_INGOT = createItem(new Item(new Item.Properties().tab(TAB)), "thallium_ingot");
    public static final Item ALUMINIUM_INGOT = createItem(new Item(new Item.Properties().tab(TAB)), "aluminium_ingot");
    public static final Item TIN_INGOT = createItem(new Item(new Item.Properties().tab(TAB)), "tin_ingot");
    public static final Item BRONZE_INGOT = createItem(new Item(new Item.Properties().tab(TAB)), "bronze_ingot");
    public static final Item THALLIUM_DUST = createItem(new Item(new Item.Properties().tab(TAB)), "thallium_dust");
    public static final Item ENERGETIC_DUST = createItem(new Item(new Item.Properties().tab(TAB)), "energetic_dust");

    public static final Item THALLIUM_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "thallium_plate");
    public static final Item ALUMINIUM_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "aluminium_plate");
    public static final Item TIN_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "tin_plate");
    public static final Item BRONZE_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "bronze_plate");
    public static final Item IRON_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "iron_plate");
    public static final Item GOLD_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "gold_plate");
    public static final Item COPPER_PLATE = createItem(new Item(new Item.Properties().tab(TAB)), "copper_plate");

    public static final Item THALLIUM_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "thallium_gear");
    public static final Item ALUMINIUM_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "aluminium_gear");
    public static final Item TIN_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "tin_gear");
    public static final Item BRONZE_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "bronze_gear");
    public static final Item IRON_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "iron_gear");
    public static final Item GOLD_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "gold_gear");
    public static final Item COPPER_GEAR = createItem(new Item(new Item.Properties().tab(TAB)), "copper_gear");

    public static final Item RUBY_GUN = createItem(new RubyGun(new Item.Properties().tab(TAB)), "ruby_gun");
    public static final Item OPAL_GUN = createItem(new OpalGun(new Item.Properties().tab(TAB)), "opal_gun");
    public static final Item PYRITE_GUN = createItem(new PyriteGun(new Item.Properties().tab(TAB)), "pyrite_gun");
    public static final Item AMBER_GUN = createItem(new AmberGun(new Item.Properties().tab(TAB)), "amber_gun");



    public static final Item CRANK = createItem(new Item(new Item.Properties().tab(TAB)), "crank");

    public static final Item JETPACK = createItem(new Jetpack(ArmorMaterials.IRON, EquipmentSlot.CHEST, new Item.Properties().tab(TAB)), "jetpack");

    public static final Item GENERATOR = createBlockItem(MMBlocks.GENERATOR, new Item.Properties().tab(TAB));
    public static final Item MANUAL_SMASHER = createBlockItem(MMBlocks.MANUAL_SMASHER, new Item.Properties().tab(TAB));
    public static final Item SMASHER = createBlockItem(MMBlocks.SMASHER, new Item.Properties().tab(TAB));
    public static final Item CUTTER = createBlockItem(MMBlocks.CUTTER, new Item.Properties().tab(TAB));

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