package ui.dialogs;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
public final class ExportDialog {
    private ExportDialog(){}
    public static Path choose(Component p){
        JFileChooser c=new JFileChooser();
        return c.showSaveDialog(p)==JFileChooser.APPROVE_OPTION?c.getSelectedFile().toPath():null;
    }
}
