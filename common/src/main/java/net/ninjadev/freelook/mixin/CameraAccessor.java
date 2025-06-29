package net.ninjadev.freelook.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Invoker(value = "setRotation")
    void callSetRotation(float yaw, float pitch);
}
