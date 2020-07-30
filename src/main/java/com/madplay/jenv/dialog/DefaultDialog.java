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
	public DefaultDialog(DialogMessage dialogMessage) {
		super(dialogMessage);
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
		return new HashMap<>() {
			{
				put("guideLabel", new JLabel(dialogMessage.getDescription()));
			}
		};
	}

	@Override
	protected void doOKAction() {
		try {
			if(dialogMessage == DialogMessage.NOT_INSTALLED_JENV) {
				Desktop.getDesktop().browse(new URI(JenvConstants.JENV_INSTALL_URL.getName()));
			} else {
				this.close(OK_EXIT_CODE);
			}
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