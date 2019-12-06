package com.api.resources;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Resource implementation for reading from a file.
 */
public class FileInputResource implements InputResource {
	/**
	 * File to read.
	 */
	private String fileName;
	private URL file;

	/**
	 * Constructs the resource.
	 * @param aFileName File to read.
	 * @throws FileNotFoundException 
	 */
	public FileInputResource(String aFileName) throws IOException {
		fileName = aFileName;
		file = InputResource.getURL(fileName);
	}

	@Override
	public InputStream getInputStream()
			throws IOException {
		return new BufferedInputStream(file.openStream());
	}

	public URL getFile() {
		return file;
	}
}
