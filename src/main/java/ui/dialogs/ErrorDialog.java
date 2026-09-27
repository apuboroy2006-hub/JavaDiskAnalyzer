package ui.dialogs;
import javax.swing.*;
public final class ErrorDialog {
    private ErrorDialog(){}
    public static void show(java.awt.Component p,String m){JOptionPane.showMessageDialog(p,m,"Error",JOptionPane.ERROR_MESSAGE);}
}
