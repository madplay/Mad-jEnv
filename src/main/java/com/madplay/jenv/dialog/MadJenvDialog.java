package com.madplay.jenv.dialog;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JComponent;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.madplay.jenv.MadJenvHelper;
import com.madplay.jenv.config.MadJenvState;
import com.madplay.jenv.service.MadJenvService;
import com.madplay.jenv.service.MadJenvStateService;

/**
 * @author madplay
 */
public class MadJenvDialog extends AbstractDialogWrapper {

	private ComboBox<String> comboBox;

	public MadJenvDialog() {
		setTitle("Select JDK Version.");
	}

	@Override
	protected void checkJenvConfig() {
		MadJenvState state = Objects.requireNonNull(MadJenvStateService.getInstance().getState());
		Integer position = MadJenvHelper.getCurrentVersionPosition(state.getFormattedJavaVersion());
		comboBox.setSelectedIndex(position == null ? 0 : position.intValue());
	}

	@Override
	protected void updateJenvConfig() {
		String selectedVersion = String.valueOf(comboBox.getSelectedItem());
		MadJenvState state = Objects.requireNonNull(MadJenvStateService.getInstance().getState());
		state.setCurrentJavaVersion(selectedVersion);
		MadJenvService service = ServiceManager.getService(MadJenvService.class);
		service.changeJenvVersion();
		this.close(DialogWrapper.OK_EXIT_CODE);
	}

	@Override
	protected Map<String, JComponent> makeComponents() {
		List<String> versionList = MadJenvHelper.getAllJdkVersionList();
		comboBox = new ComboBox(versionList.toArray());
		return new HashMap<>() {
			{
				put("versionBox", comboBox);
			}
		};
	}

	@Override
	protected void attachComponents() {
		componentMap.forEach((name, component) ->
			panel.add(component, BorderLayout.CENTER));
	}
}
