package com.madplay.jenv.constant;

/**
 * @author madplay
 */
public enum DialogMessage {
	NOT_INSTALLED_JENV("You have to install jEnv :D",
		"<html>Can't find jEnv :( <br/>If you click OK button, go to jEev installation guide.</html>"),
	NOT_INSTALLED_JAVA("You have to install java :D", "Please install java in your path");

	String title;
	String description;

	DialogMessage(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
