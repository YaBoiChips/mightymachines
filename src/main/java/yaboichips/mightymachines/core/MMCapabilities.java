package yaboichips.mightymachines.core;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.capabilities.IPackerCapability;

@Mod.EventBusSubscriber(modid = MightyMachines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MMCapabilities {

    public static final Capability<IPackerCapability> PACKER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IPackerCapability.class);
    }
}
