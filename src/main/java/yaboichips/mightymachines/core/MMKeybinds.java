package yaboichips.mightymachines.core;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class MMKeybinds {
    public static final String GROUP_ID = "Mighty Machines";
    public static KeyMapping JETPACK_UP = new KeyMapping("Jetpack Up", GLFW.GLFW_KEY_SPACE, GROUP_ID);

    public static void register() {
        ClientRegistry.registerKeyBinding(JETPACK_UP);
    }
}
