package ui.layout;
import javax.swing.*;
import java.awt.*;
public class Sidebar extends JPanel {
    public Sidebar(JComponent tree){super(new BorderLayout());setPreferredSize(new Dimension(280,0));add(tree);}
}
