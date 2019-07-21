package com.madplay.jenv;

import java.io.File;
import java.text.DecimalFormat;

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
    private static File PROJECT_JENV_FILE;

    public static File getJenvFile() {
        return JENV_FILE;
    }

    public static void setProjectJenvFile(File projectJenvFile) {
        PROJECT_JENV_FILE = projectJenvFile;
    }

    public static File getProjectJenvFile() {
        return PROJECT_JENV_FILE;
    }

    public static void setCurrentJavaVersion(String version) {
        currentVersion = version;

        double parsed = Double.parseDouble(version);
        if (parsed >= 10.0) {
            DecimalFormat format = new DecimalFormat();
            format.setDecimalSeparatorAlwaysShown(false);
            currentVersion = format.format(parsed);
        }
    }

    public static String getCurrentJavaVersion() {
        return currentVersion;
    }

    public static void setSelectedJavaVersion(String version) {
        selectedVersion = version;
    }

    public static String getSelectedJenvVersion() {
        double parsed = Double.parseDouble(selectedVersion);
        if (parsed >= 10.0) {
            parsed += 0.0;
        }
        return Double.toString(parsed);
    }

    public static String getSelectedJavaVersion() {
        return selectedVersion;
    }
}
