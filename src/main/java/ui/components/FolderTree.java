package ui.components;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.*;
import model.FolderInfo;
public class FolderTree extends JPanel {
    private final DefaultMutableTreeNode root=new DefaultMutableTreeNode("No scan");
    private final JTree tree=new JTree(root);
    private final Map<DefaultMutableTreeNode,FolderInfo> data=new HashMap<>();
    public FolderTree(){super(new BorderLayout());add(new JScrollPane(tree));}
    public void setRoot(FolderInfo f){
        root.removeAllChildren();data.clear();root.setUserObject(f.getName());data.put(root,f);addChildren(root,f);
        ((DefaultTreeModel)tree.getModel()).reload();tree.expandRow(0);
    }
    private void addChildren(DefaultMutableTreeNode n,FolderInfo f){
        for(FolderInfo c:f.getChildren()){
            DefaultMutableTreeNode x=new DefaultMutableTreeNode(c.getName());data.put(x,c);n.add(x);addChildren(x,c);
        }
    }
    public FolderInfo selectedFolder(){
        TreePath p=tree.getSelectionPath();return p==null?null:data.get((DefaultMutableTreeNode)p.getLastPathComponent());
    }
    public JTree tree(){return tree;}
}
