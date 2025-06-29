package net.ninjadev.freelook.init;

import net.minecraft.client.KeyMapping;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static KeyMapping keyFreeLook = new KeyMapping("freelook.key.use", GLFW.GLFW_KEY_LEFT_ALT, "freelook.category");
    public static KeyMapping keyToggleMode = new KeyMapping("freelook.key.toggle", GLFW.GLFW_KEY_RIGHT_ALT, "freelook.category");

    public static KeyMapping[] register(KeyMapping[] KeyBindings) {
        return ArrayUtils.addAll(KeyBindings, keyFreeLook, keyToggleMode);
    }
}
