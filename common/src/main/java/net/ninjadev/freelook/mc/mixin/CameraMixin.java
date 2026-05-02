package net.ninjadev.freelook.mc.mixin;

import net.minecraft.client.Camera;
import net.ninjadev.freelook.mc.event.CameraEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow protected void setRotation(float yaw, float pitch) {}

    @Redirect(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", ordinal = 1))
    public void freelook$redirectSetRotation(Camera instance, float yaw, float pitch) {
        CameraEvents.onCameraUpdate();
        if (CameraEvents.shouldUpdate()) {
            this.setRotation(CameraEvents.getYaw(), CameraEvents.getPitch());
        } else {
            this.setRotation(yaw, pitch);
        }
    }
}
