package com.madplay.jenv;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;

import com.intellij.openapi.projectRoots.ProjectJdkTable;

/**
 * @author madplay
 */
public class MadJenvHelper {

	public static boolean isWindows() {
		return StringUtils.contains(System.getProperty("os.name").toLowerCase(), "win");
	}

	public static List<String> getAllJdkVersionList() {
		return Arrays.stream(ProjectJdkTable.getInstance().getAllJdks())
			.map(sdk -> sdk.getName())
			.collect(Collectors.toList());
	}

	public static Integer getCurrentVersionPosition(String currentVersion) {
		List<String> versionList = getAllJdkVersionList();
		OptionalInt index = IntStream.range(0, versionList.size())
			.filter(idx -> StringUtils.equals(versionList.get(idx), (currentVersion)))
			.findFirst();

		if (index.isPresent()) {
			return index.getAsInt();
		}
		return null;
	}
}
