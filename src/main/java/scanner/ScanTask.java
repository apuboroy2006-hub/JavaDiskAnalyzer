package scanner;
import model.*;
import java.nio.file.Path;
import java.util.function.Consumer;
public class ScanTask {
    public ScanResult execute(Path root,Consumer<Path> progress){
        ScanStatistics s=new ScanStatistics();
        FolderInfo f=new FolderScanner().scan(root,s,progress);
        return new ScanResult(f,s.files(),s.folders(),s.bytes(),s.errors());
    }
}
