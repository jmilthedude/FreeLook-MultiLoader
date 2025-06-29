package net.ninjadev.freelook;

import net.fabricmc.api.ClientModInitializer;

public class FreeLookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FreeLookCommon.init();
    }
}
