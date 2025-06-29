package net.ninjadev.freelook.config;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Map;

public class ConfigOption<T> {
    @Expose protected T value;
    @Expose protected String comment;

    public ConfigOption(){}
    public ConfigOption(T value, String comment) {
        this.value = value;
        this.comment = comment;
    }
    public T getValue() {
        return value;
    }

    public String getComment() {
        return this.comment;
    }

    public static class IntValue extends ConfigOption<Integer> {
        public IntValue(Integer value, String comment) {
            super(value, comment);
        }
    }

    public static class DoubleValue extends ConfigOption<Double> {
        public DoubleValue(Double value, String comment) {
            super(value, comment);
        }
    }

    public static class StringValue extends ConfigOption<String> {
        public StringValue(String value, String comment) {
            super(value, comment);
        }
    }

    public static class BooleanValue extends ConfigOption<Boolean> {
        public BooleanValue(Boolean value, String comment) {
            super(value, comment);
        }
    }

    public static class ListValue<V> extends ConfigOption<List<V>> {
        public ListValue(List<V> value, String comment) {
            super(value, comment);
        }
    }

    public static class MapValue<K, V> extends ConfigOption<Map<K, V>> {
        public MapValue(Map<K, V> value, String comment) {
            super(value, comment);
        }
    }
}
