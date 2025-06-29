package net.ninjadev.freelook.config;

import com.google.gson.annotations.Expose;

public class FreeLookConfig extends BaseConfig {

    @Expose private ConfigOption.BooleanValue clampView;
    @Expose private ConfigOption.BooleanValue interpolate;
    @Expose private ConfigOption.DoubleValue interpolateSpeed;

    @Override
    public String getName() {
        return "FreeLook";
    }

    @Override
    protected void reset() {
        clampView = new ConfigOption.BooleanValue(true, "Clamp your head rotation to your shoulders. As you would expect in real life.");
        interpolate = new ConfigOption.BooleanValue(true, "Smooth the camera returning to original direction. Instead of snapping back instantly.");
        interpolateSpeed = new ConfigOption.DoubleValue(0.2D, "The time in seconds to move your view back to the original position.");
    }

    public boolean shouldClamp() {
        return this.clampView.getValue();
    }

    public boolean shouldInterpolate() {
        return this.interpolate.getValue();
    }

    public double getInterpolateSpeed() {
        return this.interpolateSpeed.getValue();
    }

}
