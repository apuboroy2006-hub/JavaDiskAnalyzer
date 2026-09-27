package scanner;

import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import model.FolderInfo;
import model.ScanStatistics;

public class FolderScanner {

    public FolderInfo scan(
            Path root,
            ScanStatistics stats,
            Consumer<Path> progress,
            BooleanSupplier cancelled) {

        return new DirectoryWalker(stats)
                .build(root, progress, cancelled);
    }
}