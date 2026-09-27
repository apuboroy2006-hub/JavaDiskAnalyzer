package ui.components;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.FolderInfo;
import utils.SizeFormatter;
import java.awt.*;
import java.nio.file.Path;
public class FileTable extends JPanel {
    private final DefaultTableModel model=new DefaultTableModel(
        new Object[]{"Name","Path","Size","Files","Folders"},0){
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table=new JTable(model);
    public FileTable(){super(new BorderLayout());table.setAutoCreateRowSorter(true);add(new JScrollPane(table));}
    public void showFolder(FolderInfo f){
        model.setRowCount(0);
        for(FolderInfo c:f.getChildren())
            model.addRow(new Object[]{c.getName(),c.getPath(),SizeFormatter.format(c.getTotalSize()),c.getFileCount(),c.getFolderCount()});
    }
    public Path selectedPath(){
        int r=table.getSelectedRow(); if(r<0)return null;
        return Path.of(model.getValueAt(table.convertRowIndexToModel(r),1).toString());
    }
    public JTable table(){return table;}
}
