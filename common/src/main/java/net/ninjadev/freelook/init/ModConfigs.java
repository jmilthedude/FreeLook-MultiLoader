package net.ninjadev.freelook.init;

import net.ninjadev.freelook.Constants;
import net.ninjadev.freelook.config.FreeLookConfig;

public class ModConfigs {
    public static FreeLookConfig FREELOOK;

    public static void register() {
        Constants.LOG.info("registerConfigs()");
        FREELOOK = new FreeLookConfig().readConfig();
    }
}
