package ui.theme;
import javax.swing.*;
public final class ThemeManager {
    private ThemeManager(){}
    public static void apply(boolean dark){
        try{UIManager.setLookAndFeel(dark?"javax.swing.plaf.nimbus.NimbusLookAndFeel":UIManager.getSystemLookAndFeelClassName());}
        catch(Exception ignored){}
    }
}
