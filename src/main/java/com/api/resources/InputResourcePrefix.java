package com.api.resources;

/**
 * @author qxv5892
 */
public enum InputResourcePrefix {

	/** Pseudo URL prefix for loading from the class path: "classpath:" */
	CLASSPATH_URL_PREFIX("classpath:"),
	/** URL prefix for loading from the file system: "file:" */
	FILE_URL_PREFIX("file:"),
	/** URL prefix for loading from a jar file: "jar:" */
	JAR_URL_PREFIX("jar:"),
	/** URL prefix for loading from a war file : "war:" */
	WAR_URL_PREFIX("war:"),
	/** URL protocol for a file in the file system: "file" */
	URL_PROTOCOL_FILE("file"),
	/** URL protocol for an entry from a jar file: "jar" */
	URL_PROTOCOL_JAR("jar"),

	/** URL protocol for an entry from a war file: "war" */
	URL_PROTOCOL_WAR("war"),

	/** URL protocol for an entry from a zip file: "zip" */
	URL_PROTOCOL_ZIP("zip");

	private String prefix;

	InputResourcePrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}
}
