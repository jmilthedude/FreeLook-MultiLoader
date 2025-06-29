package net.ninjadev.freelook.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.ninjadev.freelook.init.ModKeybinds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Options.class)
public class OptionsMixin {
    @Mutable @Shadow @Final public KeyMapping[] keyMappings;
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void registerKeymappings(Minecraft minecraft, File gameDir, CallbackInfo ci) {
        keyMappings = ModKeybinds.register(keyMappings);
    }
}
