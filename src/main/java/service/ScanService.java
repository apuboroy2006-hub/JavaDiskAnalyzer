package service;
import model.ScanResult;
import scanner.ScanTask;
import java.nio.file.Path;
import java.util.function.Consumer;
public class ScanService {
    public ScanResult scan(Path root,Consumer<Path> progress){return new ScanTask().execute(root,progress);}
}
