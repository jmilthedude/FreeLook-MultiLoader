package net.ninjadev.freelook;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class FreeLookNeoforge {
    public FreeLookNeoforge(IEventBus eventBus) {
        FreeLookCommon.init();
    }
}
