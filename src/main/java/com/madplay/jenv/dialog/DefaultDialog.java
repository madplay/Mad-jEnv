package com.madplay.jenv.dialog;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.madplay.jenv.constant.DialogMessage;
import com.madplay.jenv.constant.JenvConstants;

/**
 * @author madplay
 */
public class DefaultDialog extends AbstractDialogWrapper {
	private DialogMessage dialogMessage;

	public DefaultDialog(DialogMessage message) {
		this.dialogMessage = message;
		setTitle(dialogMessage.getTitle());
	}

	@Override
	protected void checkJenvConfig() {
	}

	@Override
	protected void updateJenvConfig() {
	}

	@Override
	protected Map<String, JComponent> makeComponents() {
		new HashMap<>() {
			{
				put("guideLabel", new JLabel(dialogMessage.getDescription()));
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