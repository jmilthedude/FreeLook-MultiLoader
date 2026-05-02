package net.ninjadev.freelook;

import net.minecraft.client.Camera;
import net.ninjadev.freelook.mc.event.CameraEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Camera camera = event.getCamera();
        CameraEvents.onCameraUpdate(camera);
        if (CameraEvents.shouldUpdate()) {
            event.setYaw(CameraEvents.getYaw());
            event.setPitch(CameraEvents.getPitch());
        }
    }
}
