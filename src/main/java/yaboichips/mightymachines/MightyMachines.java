package yaboichips.mightymachines;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yaboichips.mightymachines.common.capabilities.IPackerCapability;
import yaboichips.mightymachines.common.capabilities.PackerCap;
import yaboichips.mightymachines.common.tile.screens.FarmerScreen;
import yaboichips.mightymachines.common.tile.screens.GeneratorScreen;
import yaboichips.mightymachines.common.tile.screens.MachineBuilderScreen;
import yaboichips.mightymachines.common.world.OreGenerator;
import yaboichips.mightymachines.core.*;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@Mod(MightyMachines.MOD_ID)
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
        MenuScreens.register(MMContainers.BUILDER, MachineBuilderScreen::new);
        MenuScreens.register(MMContainers.FARMER, FarmerScreen::new);
        MMKeybinds.register();
    }

    @SubscribeEvent
    public void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            System.out.println("pog");
            IPackerCapability packer = new PackerCap(player);
            if (MMBoolMap.hasPacker.get(player.getUUID()) != null) {
                if (MMBoolMap.hasPacker.get(player.getUUID())) {
                    event.addCapability(new ResourceLocation(MOD_ID, "packer"),
                            new ICapabilityProvider() {
                                @NotNull
                                @Override
                                public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                                    if (cap == MMCapabilities.PACKER_CAPABILITY) {
                                        return LazyOptional.of(() -> (T) packer);
                                    }
                                    return LazyOptional.empty();
                                }
                            });
                }
            }
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        OreGenerator.registerOre();
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


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info("MM: HELLO from Register Blocks");
            MMBlocks.init();
            MMBlocks.blocks.forEach(block -> event.getRegistry().register(block));
            MMBlocks.blocks.clear();
            MMBlocks.blocks = null;
            LOGGER.info("MM: Blocks registered");
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