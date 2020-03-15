package com.madplay.jenv.constant;

/**
 * @author madplay
 */
public enum JenvConstants {
	VERSION_FILE(".java-version"),
	JENV_FILE_EXTENSION(".jenv"),
	JENV_INSTALL_URL("https://www.jenv.be");

	private String name;

	JenvConstants(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}