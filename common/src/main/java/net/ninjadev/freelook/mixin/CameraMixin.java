package net.ninjadev.freelook.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.ninjadev.freelook.event.CameraEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow protected void setRotation(float yaw, float pitch) {}

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V"))
    public void setRotation(Camera instance, float yaw, float pitch) {
        if (Minecraft.getInstance().options.getCameraType().isMirrored()) {
            this.setRotation(yaw, pitch);
        } else if (CameraEvents.shouldUpdate()) {
            CameraEvents.onCameraUpdate(instance);
        } else {
            this.setRotation(yaw, pitch);
        }
    }
}
