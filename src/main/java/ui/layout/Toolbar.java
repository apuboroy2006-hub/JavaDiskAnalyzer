package ui.layout;
import javax.swing.*;
import java.awt.*;
import ui.components.*;
public class Toolbar extends JPanel {
    public final DriveSelector drives=new DriveSelector();
    public final SearchBox search=new SearchBox();
    public final JButton select=new JButton("Select Folder"),scan=new JButton("Scan"),stop=new JButton("Stop"),export=new JButton("Export");
    public Toolbar(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("Drive:"));add(drives);add(select);add(scan);add(stop);add(export);
        add(new JLabel("Search:"));add(search);
    }
}
