package com.madplay.jenv.listener;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.madplay.jenv.service.MadJenvService;

/**
 * @author madplay
 */
public class ProjectListener implements ProjectManagerListener {
	@Override
	public void projectOpened(@NotNull Project project) {
		MadJenvService service = ServiceManager.getService(MadJenvService.class);
		service.initProject(project);
	}

	@Override
	public void projectClosed(@NotNull Project project) {
		// nothing to do
	}
}
