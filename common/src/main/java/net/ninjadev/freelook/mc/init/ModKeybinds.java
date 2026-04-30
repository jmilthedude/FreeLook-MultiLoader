package net.ninjadev.freelook.mc.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(ResourceLocation.parse("freelook:category"));
    public static KeyMapping keyFreeLook = new KeyMapping("freelook.key.use", GLFW.GLFW_KEY_LEFT_ALT, CATEGORY);
    public static KeyMapping keyToggleMode = new KeyMapping("freelook.key.toggle", GLFW.GLFW_KEY_RIGHT_ALT, CATEGORY);

    public static KeyMapping[] register(KeyMapping[] KeyBindings) {
        return ArrayUtils.addAll(KeyBindings, keyFreeLook, keyToggleMode);
    }
}
