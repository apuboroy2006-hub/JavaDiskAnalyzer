package ui.dialogs;
import javax.swing.*;
import service.SettingsService;
public final class SettingsDialog {
    private SettingsDialog(){}
    public static void show(java.awt.Component p){
        SettingsService s=new SettingsService();
        JCheckBox c=new JCheckBox("Dark mode",s.darkMode());
        if(JOptionPane.showConfirmDialog(p,c,"Settings",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)s.darkMode(c.isSelected());
    }
}
