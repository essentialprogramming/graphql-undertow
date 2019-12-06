package com.api.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ResourceUtils {

	private static final Logger LOG = Logger.getLogger(ResourceUtils.class.getName());

	private ResourceUtils() {
		throw new IllegalAccessError("Instantiation prohibted");
	}

	public static ClassLoader getClassLoader() {
		ClassLoader loader;
		try {
			loader = Thread.currentThread().getContextClassLoader();
		} catch (SecurityException ex) {
			// No thread context class loader -> use class loader of this class.
			LOG.log(Level.FINE, "No thread context class loader -> use class loader of this class", ex);
			loader = ResourceUtils.class.getClassLoader();
			if (loader == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					loader = ClassLoader.getSystemClassLoader();
				} catch (SecurityException e) {
					// Cannot access system ClassLoader
					LOG.log(Level.FINE, "Cannot access system ClassLoader", e);
				}
			}
		}

		return loader;
	}
}
