package net.ninjadev.freelook.mixin;

import net.minecraft.client.Minecraft;
import net.ninjadev.freelook.event.CameraEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onClientTick(CallbackInfo ci) {
        CameraEvents.onClientTick();
    }
}
