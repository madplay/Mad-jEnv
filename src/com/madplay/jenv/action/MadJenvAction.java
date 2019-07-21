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
            Sdk jdk = ProjectJdkTable.getInstance().findJdk(MadJenvHelper.getSelectedJavaVersion());
            Project project = e.getData(DataKeys.PROJECT);
            changeJenvVersion();
            SdkConfigurationUtil.setDirectoryProjectSdk(project, jdk);
        }
    }

    private void changeJenvVersion() {
        File jenvFile = MadJenvHelper.getJenvFile();
        try (FileWriter fileWriter = new FileWriter(jenvFile)) {
            fileWriter.write(MadJenvHelper.getSelectedJavaVersion());
        } catch (IOException e) {
            e.printStackTrace();
            // @todo
        }

    }
}