package net.ninjadev.freelook.mc.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.ninjadev.freelook.init.ModConfigs;

public class FreeLookState {
    private float yaw;
    private float pitch;
    private float prevYaw;
    private float prevPitch;
    private final float originalYaw;
    private final float originalPitch;

    private double prevMouseX;
    private double prevMouseY;

    private float lerpStartYaw;
    private float lerpStartPitch;
    private long lerpStartTime;

    public FreeLookState(Minecraft mc) {
        originalYaw = mc.player.getYRot();
        originalPitch = mc.player.getXRot();
        yaw = prevYaw = originalYaw;
        pitch = prevPitch = originalPitch;
        prevMouseX = mc.mouseHandler.xpos();
        prevMouseY = mc.mouseHandler.ypos();
    }

    public void updateMouseInput(Minecraft mc) {
        double mouseX = mc.mouseHandler.xpos();
        double mouseY = mc.mouseHandler.ypos();
        double dx = (prevMouseX - mouseX) * getSensitivity(mc) * 0.15;
        double dy = (prevMouseY - mouseY) * getSensitivity(mc) * 0.15;
        prevMouseX = mouseX;
        prevMouseY = mouseY;

        yaw = prevYaw - (float) dx;
        if (ModConfigs.FREELOOK.shouldClamp()) {
            yaw = Mth.clamp(yaw, originalYaw - 100.0f, originalYaw + 100.0f);
        }

        if (mc.options.invertMouseY().get()) {
            pitch = prevPitch + (float) dy;
        } else {
            pitch = prevPitch - (float) dy;
        }
        pitch = Mth.clamp(pitch, -90.0f, 90.0f);

        if (mc.options.getCameraType().isMirrored()) {
            yaw -= 180;
            pitch = -pitch;
        }

        prevYaw = yaw;
        prevPitch = pitch;
    }

    public void startInterpolation() {
        lerpStartYaw = yaw;
        lerpStartPitch = pitch;
        lerpStartTime = System.currentTimeMillis();
    }

    public boolean computeInterpolation(double durationSeconds) {
        float t = Math.min((float) (System.currentTimeMillis() - lerpStartTime) / (float) (durationSeconds * 1000.0), 1.0f);
        yaw = Mth.lerp(t, lerpStartYaw, originalYaw);
        pitch = Mth.lerp(t, lerpStartPitch, originalPitch);
        return t >= 1.0f;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getOriginalYaw() {
        return originalYaw;
    }

    public float getOriginalPitch() {
        return originalPitch;
    }

    private static double getSensitivity(Minecraft mc) {
        return Math.pow(mc.options.sensitivity().get() * 0.6 + 0.2, 3.0) * 8.0;
    }
}
