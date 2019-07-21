package com.madplay.jenv.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.madplay.jenv.MadJenvHelper;

import java.io.BufferedReader;
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
        VirtualFile projectBaseDir = project.getBaseDir();
        VirtualFile jenvrcFile = projectBaseDir.findChild(MadJenvHelper.VERSION_FILE);
        if (jenvrcFile != null && jenvrcFile.exists()) {
            try {
                String jdkVersion = new BufferedReader(new InputStreamReader(jenvrcFile.getInputStream())).readLine();
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

    public void projectClosed() {
        // @todo
    }
}