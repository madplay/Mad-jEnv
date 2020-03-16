package com.madplay.jenv.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.madplay.jenv.config.MadJenvState;
import com.madplay.jenv.dialog.DefaultDialog;
import com.madplay.jenv.dialog.MadJenvDialog;
import com.madplay.jenv.service.MadJenvStateService;

/**
 * @author madplay
 */
public class MadJenvAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		MadJenvState state = MadJenvStateService.getInstance().getState();
		state.setProject(event.getData(CommonDataKeys.PROJECT));

		if (state.isJenvInstalled()) {
			new MadJenvDialog().show();
		} else {
			new DefaultDialog().show();
		}
	}
}