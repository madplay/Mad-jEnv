package com.madplay.jenv.component;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.madplay.jenv.MadJenvHelper;
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

    public void initComponent() {
        File jenvFile = MadJenvHelper.getJenvFile();

        if (isWindows()) {
            jenvFile = new File("c:/jenv");
        }

        if (jenvFile.exists()) {
            setUpJdkVersion(jenvFile);
        }
    }

    private void setUpJdkVersion(File jenvFile) {
        File javaHome = new File(jenvFile, MadJenvHelper.VERSION_DIRECTORY);
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
        return file -> file.getName().equals(MadJenvHelper.JENV_CURRENT_VERSION)
                && !installedVersions.contains(file.getName());
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    @NotNull
    public String getComponentName() {
        return APPLICATION_COMPONENT_NAME;
    }
}