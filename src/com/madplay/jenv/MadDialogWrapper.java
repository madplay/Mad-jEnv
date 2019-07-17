package com.madplay.jenv;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author kimtaeng
 * Created on 2019. 6. 12
 */
public class MadDialogWrapper extends DialogWrapper {
    public static String selectedJdk = "";

    public MadDialogWrapper() {
        super(true);
        init();
        setTitle("Select JDK Version.");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        Sdk[] allJdks = ProjectJdkTable.getInstance().getAllJdks();
        Object[] nameList = Arrays.stream(allJdks)
                .map(Sdk::getName).collect(Collectors.toList()).toArray();
        ComboBox nameBox = new ComboBox(nameList);
        nameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboBox comboBox = (ComboBox) e.getSource();
                String selectedVersion = (String) comboBox.getSelectedItem();
                selectedJdk = selectedVersion;
            }
        });
        dialogPanel.add(nameBox, BorderLayout.CENTER);

        return dialogPanel;
    }


}