package com.madplay.jenv.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.madplay.jenv.MadDialogWrapper;
import com.madplay.jenv.MadJenvHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author kimtaeng
 * Created on 2019. 4. 12
 */
public class MadJenvAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MadDialogWrapper dialogWrapper = new MadDialogWrapper();
        dialogWrapper.show();

        if (dialogWrapper.isOK()) {
            String selectedVersion = MadJenvHelper.getSelectedJavaVersion();
            Sdk jdk = ProjectJdkTable.getInstance().findJdk(selectedVersion);
            Project project = e.getData(DataKeys.PROJECT);
            changeJenvVersion();
            SdkConfigurationUtil.setDirectoryProjectSdk(project, jdk);
            MadJenvHelper.setCurrentJavaVersion(selectedVersion);
        }
    }

    private void changeJenvVersion() {
        File jenvFile = MadJenvHelper.getProjectJenvFile();
        try (FileWriter fileWriter = new FileWriter(jenvFile)) {
            fileWriter.write(MadJenvHelper.getSelectedJenvVersion());
        } catch (IOException e) {
            // @todo
            System.err.println(e);
        }
    }
}