package com.madplay.jenv.config;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.annotations.Transient;

/**
 * @author madplay
 */
public class MadJenvState {
	@Transient
	private Project project;
	private String projectJenvFilePath;
	private String currentJavaVersion;
	private boolean isJenvInstalled;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectJenvFilePath() {
		return projectJenvFilePath;
	}

	public void setProjectJenvFilePath(String projectJenvFilePath) {
		this.projectJenvFilePath = projectJenvFilePath;
	}

	public String getCurrentJavaVersion() {
		return currentJavaVersion;
	}

	public void setCurrentJavaVersion(String currentJavaVersion) {
		this.currentJavaVersion = currentJavaVersion;
	}

	public boolean isJenvInstalled() {
		return isJenvInstalled;
	}

	public void setJenvInstalled(boolean jenvInstalled) {
		isJenvInstalled = jenvInstalled;
	}

	public String getFormattedJavaVersion() {
		if (StringUtils.isBlank(currentJavaVersion)) {
			return StringUtils.EMPTY;
		}

		double parsed = Double.parseDouble(currentJavaVersion);

		if (parsed >= 10.0) {
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(false);
			return format.format(parsed);
		}
		return currentJavaVersion;
	}

	public String getJenvJavaVersion() {
		double parsed = Double.parseDouble(currentJavaVersion);
		if (parsed >= 10.0) {
			parsed += 0.0;
		}
		return Double.toString(parsed);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		MadJenvState that = (MadJenvState)o;

		return new EqualsBuilder()
			.append(isJenvInstalled, that.isJenvInstalled)
			.append(projectJenvFilePath, that.projectJenvFilePath)
			.append(currentJavaVersion, that.currentJavaVersion)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(projectJenvFilePath)
			.append(currentJavaVersion)
			.append(isJenvInstalled)
			.toHashCode();
	}
}
