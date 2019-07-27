package com.madplay.jenv;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author kimtaeng
 * Created on 2019. 6. 12
 */
public class MadDialogWrapper extends DialogWrapper {
    public MadDialogWrapper() {
        super(true);
        init();
        setTitle("Select JDK Version.");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        Sdk[] allJdks = ProjectJdkTable.getInstance().getAllJdks();
        List<String> versionList = Arrays.stream(allJdks)
                .map(Sdk::getName).map(Object::toString).collect(Collectors.toList());
        ComboBox nameBox = new ComboBox(versionList.toArray());

        int currentVersionIndex = getCurrentVersionIndex(versionList);
        nameBox.setSelectedIndex(currentVersionIndex);
        MadJenvHelper.setSelectedJavaVersion(versionList.get(currentVersionIndex));

        nameBox.addActionListener(event -> {
            ComboBox comboBox = (ComboBox) event.getSource();
            String selectedVersion = (String) comboBox.getSelectedItem();
            MadJenvHelper.setSelectedJavaVersion(selectedVersion);
        });
        dialogPanel.add(nameBox, BorderLayout.CENTER);

        return dialogPanel;
    }

    private int getCurrentVersionIndex(List<String> versionList) {
        OptionalInt index = IntStream.range(0, versionList.size())
                .filter(idx -> versionList.get(idx).equals(MadJenvHelper.getCurrentJavaVersion()))
                .findFirst();

        if(index.isPresent()) {
            return index.getAsInt();
        }
        return 0;
    }


}