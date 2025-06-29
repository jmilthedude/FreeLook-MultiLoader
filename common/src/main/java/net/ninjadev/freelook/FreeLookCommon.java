package net.ninjadev.freelook;

import net.ninjadev.freelook.init.ModConfigs;
import net.ninjadev.freelook.platform.Services;

public class FreeLookCommon {
    public static void init() {
        Constants.LOG.info("Is mod loaded: {}", Services.PLATFORM.isModLoaded("freelook"));
        if (Services.PLATFORM.isModLoaded("freelook")) {
            Constants.LOG.info("Hello from FreeLook");
            ModConfigs.register();
        }
    }
}
