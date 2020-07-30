package com.madplay.jenv.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.madplay.jenv.MadJenvHelper;
import com.madplay.jenv.config.MadJenvState;
import com.madplay.jenv.constant.JenvConstants;

/**
 * @author madplay
 */
public class MadJenvService {

	public void initProject(Project project) {
		File jenvFile = new File(System.getProperty("user.home") + File.separator
			+ JenvConstants.JENV_FILE_EXTENSION.getName());

		if (MadJenvHelper.isWindows()) {
			jenvFile = new File("c:/jenv");
		}

		MadJenvState state = Objects.requireNonNull(MadJenvStateService.getInstance().getState());

		if (jenvFile != null && jenvFile.exists()) {
			state.setJenvInstalled(true);
		}

		if (CollectionUtils.isNotEmpty(MadJenvHelper.getAllJdkVersionList())) {
			state.setJavaInstalled(true);
		}

		File projectJenvFile = new File(project.getBasePath() + File.separator + JenvConstants.VERSION_FILE.getName());

		if (projectJenvFile != null && projectJenvFile.exists()) {
			state.setProjectJenvExists(true);
		}

		state.setProjectJenvFilePath(projectJenvFile.getPath());

		try {
			Path path = Paths.get(projectJenvFile.getPath());
			String jdkVersion = Files.readAllLines(path).get(0);

			state.setCurrentJavaVersion(jdkVersion);
			Sdk jdk = ProjectJdkTable.getInstance().findJdk(state.getFormattedJavaVersion());
			if (jdk != null) {
				SdkConfigurationUtil.setDirectoryProjectSdk(project, jdk);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public void changeJenvVersion() {
		MadJenvState state = Objects.requireNonNull(MadJenvStateService.getInstance().getState());
		Sdk jdk = ProjectJdkTable.getInstance().findJdk(state.getFormattedJavaVersion());
		SdkConfigurationUtil.setDirectoryProjectSdk(state.getProject(), jdk);

		File jenvFile = new File(state.getProjectJenvFilePath());
		if (jenvFile.exists()) {
			try {
				Path path = Paths.get(jenvFile.getPath());
				Files.write(path, state.getJenvJavaVersion().getBytes());
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
