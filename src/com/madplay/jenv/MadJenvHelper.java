package com.madplay.jenv;

import java.io.File;

/**
 * @author Kimtaeng
 * Created on 2019. 6. 23
 */
public class MadJenvHelper {
    public static final String VERSION_DIRECTORY = "versions";
    public static final String VERSION_FILE = ".java-version";
    public static final String JENV_CURRENT_VERSION = "current";

    private static String currentVersion;
    private static String selectedVersion;

    private static final String JENV_FILE_EXTENSION = ".jenv";
    private static final File JENV_FILE = new File(new File(System.getProperty("user.home")),
            JENV_FILE_EXTENSION);

    public static File getJenvFile() {
        return JENV_FILE;
    }

    public static void setCurrentJavaVersion(String version) {
        currentVersion = version;
    }

    public static String getCurrentJavaVersion() {
        return currentVersion;
    }

    public static void setSelectedJavaVersion(String version) {
        selectedVersion = version;
    }

    public static String getSelectedJavaVersion() {
        return selectedVersion;
    }
}
