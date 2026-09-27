package ui.components;
import javax.swing.*;
import java.awt.*;
public class ProgressPanel extends JPanel {
    public final JProgressBar bar=new JProgressBar();
    public final JLabel status=new JLabel("Ready");
    public ProgressPanel(){setLayout(new BorderLayout(8,0));add(status,BorderLayout.WEST);add(bar,BorderLayout.CENTER);}
}
