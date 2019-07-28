package com.madplay.jenv;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Kimtaeng
 * Created on 2019. 7. 16
 */
public class DefaultDialog extends DialogWrapper {
    public DefaultDialog() {
        super(true);
        init();
        setTitle("You have to install jEnv :D");
        setResizable(false);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        String message = "<html>Can't find jEnv :( <br/>" +
                "If you click OK button, go to jEev installation guide.</html>";
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setPreferredSize(new Dimension(350, 80));
        JLabel label = new JLabel(message);
        dialogPanel.add(label, BorderLayout.CENTER);
        return dialogPanel;
    }
}