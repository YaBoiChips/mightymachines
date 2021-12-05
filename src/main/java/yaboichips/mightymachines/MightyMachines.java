package yaboichips.mightymachines;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yaboichips.mightymachines.common.tile.screens.CutterScreen;
import yaboichips.mightymachines.common.tile.screens.GeneratorScreen;
import yaboichips.mightymachines.common.tile.screens.SmasherScreen;
import yaboichips.mightymachines.common.world.OreGenerator;
import yaboichips.mightymachines.core.*;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mightymachines")
public class MightyMachines {

    public static final String MOD_ID = "mightymachines";
    private static final Logger LOGGER = LogManager.getLogger();

    public MightyMachines() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static @Nonnull
    ResourceLocation createResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MenuScreens.register(MMContainers.GENERATOR, GeneratorScreen::new);
        MenuScreens.register(MMContainers.SMASHER, SmasherScreen::new);
        MenuScreens.register(MMContainers.CUTTER, CutterScreen::new);
        MMKeybinds.register();
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        OreGenerator.registerOre();
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("mightymachines", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info("MM: HELLO from Register Blocks");
            MMBlocks.init();
            MMBlocks.blocks.forEach(block -> event.getRegistry().register(block));
            MMBlocks.blocks.clear();
            MMBlocks.blocks = null;
            LOGGER.info("MM: Blocks egistered");
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            LOGGER.info("MM: HELLO from Register Items");
            MMItems.init();
            MMItems.items.forEach(item -> event.getRegistry().register(item));
            MMItems.items.clear();
            MMItems.items = null;
            LOGGER.info("MM: Items registered");
        }

        @SubscribeEvent
        public static void onTileRegistry(final RegistryEvent.Register<BlockEntityType<?>> event) {
            LOGGER.info("MM: HELLO from Register Block Entities");
            MMBlockEntities.init();
            MMBlockEntities.blockentity.forEach(blockentity -> event.getRegistry().register(blockentity));
            MMBlockEntities.blockentity.clear();
            MMBlockEntities.blockentity = null;
            LOGGER.info("MM: Block Entities registered");
        }

        @SubscribeEvent
        public static void onMenuRegistry(final RegistryEvent.Register<MenuType<?>> event) {
            LOGGER.info("MM: HELLO from Register Menus");
            MMContainers.init();
            MMContainers.containers.forEach(item -> event.getRegistry().register(item));
            MMContainers.containers.clear();
            MMContainers.containers = null;
            LOGGER.info("MM: Menus Registered");
        }

        @SubscribeEvent
        public static void onRecipeRegistry(final RegistryEvent.Register<RecipeSerializer<?>> event) {
            LOGGER.info("MM: HELLO from Register Recipes");
            MMRecipeSerializers.init();
            MMRecipeSerializers.serializers.forEach(serializer -> event.getRegistry().register(serializer));
            MMRecipeSerializers.serializers.clear();
            MMRecipeSerializers.serializers = null;
            LOGGER.info("MM: Recipes Registered");
        }
    }
}