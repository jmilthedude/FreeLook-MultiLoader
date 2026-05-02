package net.ninjadev.freelook.mc.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.ninjadev.freelook.init.ModConfigs;
import net.ninjadev.freelook.mc.init.ModKeybinds;

public class CameraEvents {
    private static FreeLookState state = null;
    private static boolean interpolating = false;
    private static boolean isToggled = false;

    public static void onClientTick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (ModKeybinds.keyToggleMode.consumeClick()) {
            isToggled = !isToggled;
            player.sendOverlayMessage(Component.literal("FreeLook Toggle: " + isToggled));
        }
    }

    public static void onCameraUpdate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (mc.options.getCameraType().isMirrored()) return;

        if (ModKeybinds.keyFreeLook.isDown() || isToggled) {
            if (state == null) {
                state = new FreeLookState(mc);
                return;
            }
            state.updateMouseInput(mc);
            lockPlayerRotation(mc.player);
        } else if (interpolating) {
            lockPlayerRotation(mc.player);
            if (state.computeInterpolation(ModConfigs.FREELOOK.getInterpolateSpeed())) {
                deactivate();
            }
        } else if (state != null) {
            if (ModConfigs.FREELOOK.shouldInterpolate()) {
                state.startInterpolation();
                interpolating = true;
            } else {
                deactivate();
            }
        }
    }

    private static void deactivate() {
        state = null;
        interpolating = false;
    }

    private static void lockPlayerRotation(LocalPlayer player) {
        player.setYRot(state.getOriginalYaw());
        player.yBodyRot = state.getOriginalYaw();
        player.yHeadRot = state.getYaw();
        player.setXRot(state.getPitch());
    }

    public static boolean shouldUpdate() {
        return state != null;
    }

    public static float getYaw() {
        return state != null ? state.getYaw() : 0;
    }

    public static float getPitch() {
        return state != null ? state.getPitch() : 0;
    }
}
