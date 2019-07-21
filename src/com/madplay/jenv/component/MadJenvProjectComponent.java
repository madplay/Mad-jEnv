package com.madplay.jenv.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.madplay.jenv.MadJenvHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
            }
        }
    }
}