package net.ninjadev.freelook;

import net.ninjadev.freelook.init.ModConfigs;
import net.ninjadev.freelook.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {
    public static void init() {
        if (Services.PLATFORM.isModLoaded("freelook")) {
            Constants.LOG.info("Hello from FreeLook");
            ModConfigs.register();
        }
    }
}
