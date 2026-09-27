package ui.layout;
import javax.swing.*;
import java.awt.*;
import ui.components.*;
public class MainLayout extends JPanel {
    public final Toolbar toolbar=new Toolbar();
    public final FolderTree tree=new FolderTree();
    public final FileTable table=new FileTable();
    public final ProgressPanel progress=new ProgressPanel();
    public final DiskUsagePanel usage=new DiskUsagePanel();
    public MainLayout(){
        super(new BorderLayout(5,5));add(toolbar,BorderLayout.NORTH);
        JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new Sidebar(tree),table);
        split.setDividerLocation(280);add(split,BorderLayout.CENTER);
        JPanel bottom=new JPanel(new BorderLayout());bottom.add(progress,BorderLayout.NORTH);bottom.add(usage,BorderLayout.SOUTH);
        add(bottom,BorderLayout.SOUTH);
    }
}
