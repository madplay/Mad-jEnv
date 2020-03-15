package com.madplay.jenv.dialog;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.madplay.jenv.constant.JenvConstants;

/**
 * @author madplay
 */
public class DefaultDialog extends AbstractDialogWrapper {
	public DefaultDialog() {
		setTitle("You have to install jEnv :D");
	}

	@Override
	protected void checkJenvConfig() {
	}

	@Override
	protected void updateJenvConfig() {
	}

	@Override
	protected Map<String, JComponent> makeComponents() {
		String message = "<html>Can't find jEnv :( <br/>" +
			"If you click OK button, go to jEev installation guide.</html>";
		new HashMap<>() {
			{
				put("guideLabel", new JLabel(message));
			}
		};
		return null;
	}

	@Override
	protected void doOKAction() {
		try {
			Desktop.getDesktop().browse(new URI(JenvConstants.JENV_INSTALL_URL.getName()));
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	@Override
	protected void attachComponents() {
		componentMap.forEach((name, component) ->
			panel.add(component, BorderLayout.CENTER));

	}
}