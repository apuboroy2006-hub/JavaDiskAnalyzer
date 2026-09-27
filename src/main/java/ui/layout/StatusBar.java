package ui.layout;
import javax.swing.*;
import java.awt.*;
public class StatusBar extends JPanel {
    public final JLabel label=new JLabel("Ready");
    public StatusBar(){setLayout(new BorderLayout());add(label,BorderLayout.WEST);}
}
