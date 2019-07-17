package com.madplay.jenv.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.madplay.jenv.MadDialogWrapper;

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
            Sdk jdk = ProjectJdkTable.getInstance().findJdk(MadDialogWrapper.selectedJdk);
            Project project = e.getData(DataKeys.PROJECT);
            SdkConfigurationUtil.setDirectoryProjectSdk(project, jdk);
        }
    }
}