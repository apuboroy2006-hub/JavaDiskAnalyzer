package ui.components;
import javax.swing.*;
import java.awt.*;
import utils.SizeFormatter;
public class DiskUsagePanel extends JPanel {
    private final JLabel label=new JLabel("No scan");
    public DiskUsagePanel(){setLayout(new BorderLayout());add(label);}
    public void update(long bytes,long files,long folders){
        label.setText("Size: "+SizeFormatter.format(bytes)+"   Files: "+files+"   Folders: "+folders);
    }
}
