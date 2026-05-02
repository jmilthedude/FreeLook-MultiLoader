package net.ninjadev.freelook;

import net.ninjadev.freelook.mc.event.CameraEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        CameraEvents.onCameraUpdate();
        if (CameraEvents.shouldUpdate()) {
            event.setYaw(CameraEvents.getYaw());
            event.setPitch(CameraEvents.getPitch());
        }
    }
}
