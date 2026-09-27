package ui.dialogs;
import javax.swing.*;
import app.AppConfig;
public final class AboutDialog {
    private AboutDialog(){}
    public static void show(java.awt.Component p){
        JOptionPane.showMessageDialog(p,AppConfig.APP_NAME+"\nVersion "+AppConfig.VERSION,"About",JOptionPane.INFORMATION_MESSAGE);
    }
}
