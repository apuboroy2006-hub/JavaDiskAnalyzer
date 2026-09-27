package scanner;

import model.FolderInfo;
import model.ScanResult;
import model.ScanStatistics;

import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class ScanTask {

    public ScanResult execute(
            Path root,
            Consumer<Path> progress,
            BooleanSupplier cancelled) {

        ScanStatistics stats = new ScanStatistics();

        FolderInfo folder = new FolderScanner().scan(
                root,
                stats,
                progress,
                cancelled
        );

        return new ScanResult(
                folder,
                stats.files(),
                stats.folders(),
                stats.bytes(),
                stats.errors(),
                stats.skipped()
        );
    }
}