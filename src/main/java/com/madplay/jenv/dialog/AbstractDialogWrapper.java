package com.madplay.jenv.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.ui.DialogWrapper;
import com.madplay.jenv.constant.DialogMessage;

/**
 * @author madplay
 */
public abstract class AbstractDialogWrapper extends DialogWrapper {
	protected static int WIDTH = 300;
	private static final String DEFAULT_TITLE = "Welcome to Mad-jEnv";

	protected JPanel panel;
	protected Map<String, JComponent> componentMap;
	protected DialogMessage dialogMessage;

	public AbstractDialogWrapper(DialogMessage dialogMessage) {
		super(true);
		this.dialogMessage = dialogMessage;
		setTitle(DEFAULT_TITLE);
		setResizable(false);
		componentMap = makeComponents();
		panel = new JPanel(new BorderLayout());
		checkJenvConfig();
		init();
	}

	protected abstract void checkJenvConfig();

	protected abstract void updateJenvConfig();

	protected abstract Map<String, JComponent> makeComponents();

	protected abstract void attachComponents();

	@Override
	protected void doOKAction() {
		updateJenvConfig();
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		attachComponents();
		panel.setPreferredSize(new Dimension(WIDTH, panel.getHeight()));
		return panel;
	}
}
