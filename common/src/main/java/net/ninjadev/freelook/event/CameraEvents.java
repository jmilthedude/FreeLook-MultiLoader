package net.ninjadev.freelook.event;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.ninjadev.freelook.Constants;
import net.ninjadev.freelook.init.ModConfigs;
import net.ninjadev.freelook.init.ModKeybinds;
import net.ninjadev.freelook.mixin.CameraAccessor;

public class CameraEvents {
    private static Minecraft minecraft;
    private static LocalPlayer player;

    private static float yaw;
    private static float pitch;
    private static float prevYaw;
    private static float prevPitch;
    private static float originalYaw;
    private static float originalPitch;

    private static double mouseDX;
    private static double mouseDY;
    private static double prevMouseX;
    private static double prevMouseY;

    private static long lerpStart = 0;
    private static long lerpTimeElapsed = 0;

    private static State state = State.INACTIVE;
    private static boolean isToggled = false;

    public static void onClientTick() {
        if (ModKeybinds.keyToggleMode.consumeClick()) {
            isToggled = !isToggled;
            getPlayer().sendSystemMessage(Component.literal("FreeLook Toggle: " + isToggled));
        }
    }

    public static void onCameraUpdate(Camera camera) {
        if (getMinecraft().options.getCameraType().isMirrored()) return;

        if (ModKeybinds.keyFreeLook.isDown() || isToggled) {
            Constants.LOG.info("FreeLook camera active");
            if (state == State.INACTIVE) {
                reset();
                setup();
                state = State.ACTIVE;
                return;
            }

            updateMouseInput();
            updateCameraRotation(camera);
            lockPlayerRotation();

        } else if (state == State.INTERPOLATING) {
            lockPlayerRotation();
            interpolate(camera);
        } else {
            if (state == State.ACTIVE) {
                if (ModConfigs.FREELOOK.shouldInterpolate()) {
                    ((CameraAccessor) camera).callSetRotation(yaw, pitch);
                    startInterpolation();
                    state = State.INTERPOLATING;
                } else {
                    reset();
                }
            }
        }
    }

    private static void startInterpolation() {
        lerpStart = System.currentTimeMillis();
    }

    private static void setup() {
        Constants.LOG.info("Setting up FreeLook camera...");
        originalYaw = getPlayer().getYRot();
        originalPitch = getPlayer().getXRot();
        yaw = prevYaw = originalYaw;
        pitch = prevPitch = originalPitch;
        prevMouseX = getMinecraft().mouseHandler.xpos();
        prevMouseY = getMinecraft().mouseHandler.ypos();
    }

    private static void updateCameraRotation(Camera camera) {
        double dx = mouseDX * getSensitivity() * 0.15D;
        double dy = mouseDY * getSensitivity() * 0.15D;

        yaw = prevYaw - (float) dx;
        if (ModConfigs.FREELOOK.shouldClamp()) {
            yaw = Mth.clamp(yaw, (originalYaw - 100.0F), (originalYaw + 100.0F));
        }

        if (getMinecraft().options.invertYMouse().get()) {
            pitch = prevPitch + (float) dy;
        } else {
            pitch = prevPitch - (float) dy;
        }
        pitch = Mth.clamp(pitch, -90.0F, 90.0F);

        if (getMinecraft().options.getCameraType().isMirrored()) {
            yaw -= 180;
            pitch = -pitch;
        }

        ((CameraAccessor) camera).callSetRotation(yaw, pitch);

        prevYaw = yaw;
        prevPitch = pitch;
    }

    private static void interpolate(Camera camera) {
        double duration = ModConfigs.FREELOOK.getInterpolateSpeed() * 1000f;
        float delta = (System.currentTimeMillis() - lerpStart) - lerpTimeElapsed;
        delta /= (float) duration;

        float percentCompleted = (float) lerpTimeElapsed / (float) duration;
        float interpolatedYaw = lerp(yaw, originalYaw, percentCompleted * 10f * delta);
        float interpolatedPitch = lerp(pitch, originalPitch, percentCompleted * 10f * delta);

        ((CameraAccessor) camera).callSetRotation(yaw, pitch);
        yaw = interpolatedYaw;
        pitch = interpolatedPitch;

        lerpTimeElapsed = (System.currentTimeMillis() - lerpStart);
        if (lerpTimeElapsed >= duration) {
            reset();
        }
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static void reset() {
        lerpTimeElapsed = 0;
        yaw = 0;
        pitch = 0;
        prevYaw = 0;
        prevPitch = 0;
        mouseDX = 0;
        mouseDY = 0;
        prevMouseX = 0;
        prevMouseY = 0;
        player = null;
        minecraft = null;
        state = State.INACTIVE;
    }

    private static void lockPlayerRotation() {
        getPlayer().setYRot(originalYaw);
        getPlayer().yBodyRot = originalYaw;
        getPlayer().yHeadRot = yaw;
        getPlayer().setXRot(originalPitch);
    }

    private static void updateMouseInput() {
        mouseDX = prevMouseX - getMinecraft().mouseHandler.xpos();
        mouseDY = prevMouseY - getMinecraft().mouseHandler.ypos();

        prevMouseX = getMinecraft().mouseHandler.xpos();
        prevMouseY = getMinecraft().mouseHandler.ypos();
    }

    private static double getSensitivity() {
        return Math.pow(getMinecraft().options.sensitivity().get() * 0.6D + 0.2D, 3.0D) * 8.0D;
    }

    private static LocalPlayer getPlayer() {
        if (player == null) player = getMinecraft().player;
        return player;
    }

    private static Minecraft getMinecraft() {
        if (minecraft == null) minecraft = Minecraft.getInstance();
        return minecraft;
    }

    public static boolean shouldUpdate() {
        return ModKeybinds.keyFreeLook.isDown() || CameraEvents.isToggled || state != State.INACTIVE;
    }

    public enum State {
        INACTIVE, ACTIVE, INTERPOLATING
    }
}
