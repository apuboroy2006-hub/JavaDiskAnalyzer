package scanner;
import model.*;
import java.nio.file.Path;
import java.util.function.Consumer;
public class FolderScanner {
    public FolderInfo scan(Path root,ScanStatistics stats,Consumer<Path> progress){
        return new DirectoryWalker(stats).build(root,progress);
    }
}
