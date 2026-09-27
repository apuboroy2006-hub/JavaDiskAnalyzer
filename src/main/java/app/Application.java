package app;
import ui.MainWindow;
import javax.swing.UIManager;
public class Application {
    public void start() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        new MainWindow().setVisible(true);
    }
}
