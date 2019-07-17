package com.madplay.jenv.component;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author kimtaeng
 * Created on 2019. 4. 12
 */
public class MadJenvAppComponent implements ApplicationComponent {
    private final String APPLICATION_COMPONENT_NAME = "MadJenvAppComponent";
    private final String VERSION_DIRECTORY = "versions";
    private final String JENV_FILE_EXTENSION = ".jenv";
    private final String JENV_CURRENT_VERSION = "current";

    public void initComponent() {
        registerJenvrc();
        File jenvFile = new File(new File(System.getProperty("user.home")), JENV_FILE_EXTENSION);

        if (SystemUtils.IS_OS_WINDOWS) {
            jenvFile = new File("c:/jenv");
        }

        if (jenvFile.exists()) {
            setUpJdkVersion(jenvFile);
        }
    }

    private void setUpJdkVersion(File jenvFile) {
        File javaHome = new File(jenvFile, VERSION_DIRECTORY);
        if (javaHome.exists()) {
            File[] jdkVersions = javaHome.listFiles();
            if (jdkVersions != null) {
                List<Sdk> javaSdkList = ProjectJdkTable.getInstance().getSdksOfType(JavaSdk.getInstance());
                List<String> installedVersions = javaSdkList.stream()
                        .map(Sdk::getName).collect(Collectors.toList());

                Optional<File> currentVersion = Arrays.stream(jdkVersions)
                        .filter(findCurrentVersion(installedVersions))
                        .findFirst();

                if (currentVersion.isPresent()) {
                    File versionFile = currentVersion.get();
                    VirtualFile sdkHome = ApplicationManager.getApplication()
                            .runWriteAction((Computable<VirtualFile>) () -> LocalFileSystem.getInstance()
                                    .refreshAndFindFileByPath(versionFile.getAbsolutePath()));

                    if (sdkHome != null) {
                        final Sdk newSdk = SdkConfigurationUtil.setupSdk(new Sdk[]{}, sdkHome,
                                JavaSdk.getInstance(), true, null, versionFile.getName());

                        if (newSdk != null) {
                            SdkConfigurationUtil.addSdk(newSdk);
                        }
                    }
                }
            }
        }
    }

    private Predicate<File> findCurrentVersion(List<String> installedVersions) {
        return file -> file.getName().equals(JENV_CURRENT_VERSION) && !installedVersions.contains(file.getName());
    }

    private void registerJenvrc() {
        boolean registered = false;
        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        FileType propertiesFileType = fileTypeManager.getFileTypeByExtension(".properties");
        List<FileNameMatcher> associations = fileTypeManager.getAssociations(propertiesFileType);
        for (FileNameMatcher association : associations) {
            if (association.getPresentableString().equals("jenvrc")) {
                registered = true;
                break;
            }
        }
        if (!registered) {
            fileTypeManager.associatePattern(propertiesFileType, "jenvrc");
        }
    }

    @NotNull
    public String getComponentName() {
        return APPLICATION_COMPONENT_NAME;
    }
}