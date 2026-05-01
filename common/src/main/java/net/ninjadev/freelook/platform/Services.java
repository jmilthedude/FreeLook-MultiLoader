package net.ninjadev.freelook.platform;

import net.ninjadev.freelook.Constants;
import net.ninjadev.freelook.platform.services.IPlatformHelper;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        Iterator<T> iterator = ServiceLoader.load(clazz).iterator();
        while (iterator.hasNext()) {
            try {
                T service = iterator.next();
                Constants.LOG.debug("Loaded {} for service {}", service, clazz);
                return service;
            } catch (ServiceConfigurationError e) {
                Constants.LOG.debug("Skipping unavailable service provider for {}", clazz);
            }
        }
        throw new NullPointerException("Failed to load service for " + clazz.getName());
    }
}
