package ui.components;
import javax.swing.*;
import java.nio.file.Path;
import service.DriveService;
public class DriveSelector extends JComboBox<Path> {
    public DriveSelector(){for(Path p:new DriveService().drives())addItem(p);}
}
