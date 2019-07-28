package com.madplay.jenv.component;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.madplay.jenv.MadJenvHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author kimtaeng
 * Created on 2019. 4. 12
 */
public class MadJenvProjectComponent implements ProjectComponent {
    private final String PROJECT_COMPONENT_NAME = "MadJenvProjectComponent";
    private Project project;

    public MadJenvProjectComponent(Project project) {
        this.project = project;
    }

    public String getComponentName() {
        return PROJECT_COMPONENT_NAME;
    }

    public void projectOpened() {
        File projectJenvFile = new File(project.getBasePath() + File.separator + MadJenvHelper.VERSION_FILE);

        MadJenvHelper.setProjectJenvFile(projectJenvFile);
        if (projectJenvFile != null && projectJenvFile.exists()) {

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(projectJenvFile)))) {
                String jdkVersion = br.readLine();
                MadJenvHelper.setCurrentJavaVersion(jdkVersion);
                Sdk jdk = ProjectJdkTable.getInstance().findJdk(jdkVersion);
                if (jdk != null) {
                    SdkConfigurationUtil.setDirectoryProjectSdk(project, jdk);
                }
            } catch (Exception e) {
                // @todo
                System.err.println(e);
            }
        } else {
            // not exists project's local jEnv file(.java-version).
        }
    }

    public void initComponent() {
        File jenvFile = MadJenvHelper.getJenvFile();

        if (MadJenvHelper.isWindows()) {
            jenvFile = new File("c:/jenv");
        }

        if (jenvFile != null && jenvFile.exists()) {
            setUpJdkVersion(jenvFile);
            MadJenvHelper.setIsJenvInstalled(true);
        }
    }

    private void setUpJdkVersion(File jenvFile) {
        File javaHome = new File(jenvFile, MadJenvHelper.VERSION_DIRECTORY);
        if (javaHome != null && javaHome.exists()) {
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
}