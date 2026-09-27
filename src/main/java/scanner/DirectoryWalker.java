package scanner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import model.FolderInfo;
import model.ScanStatistics;

public class DirectoryWalker {

    private final ScanStatistics stats;

    public DirectoryWalker(ScanStatistics stats) {
        this.stats = stats;
    }

    public FolderInfo build(
            Path dir,
            Consumer<Path> progress,
            BooleanSupplier cancelled) {

        // Stop pressed?
        if (cancelled.getAsBoolean()) {
            return new FolderInfo(
                dir.getFileName() == null
                    ? dir.toString()
                    : dir.getFileName().toString(),
                dir
            );
        }

        FolderInfo info = new FolderInfo(
            dir.getFileName() == null
                ? dir.toString()
                : dir.getFileName().toString(),
            dir
        );

        try (var s = Files.list(dir)) {

            s.sorted(
                Comparator.comparing(
                    p -> p.getFileName()
                         .toString()
                         .toLowerCase()
                )
            ).forEach(p -> {

                // Stop immediately before processing next item
                if (cancelled.getAsBoolean()) {
                    return;
                }

                try {

                    if (Files.isDirectory(p)) {

                        // Check again before entering subfolder
                        if (cancelled.getAsBoolean()) {
                            return;
                        }

                        FolderInfo child = build(
                            p,
                            progress,
                            cancelled
                        );

                        if (cancelled.getAsBoolean()) {
                            return;
                        }

                        info.addChild(child);
                        info.addSize(
                            child.getTotalSize()
                        );

                        info.addFolder();
                        stats.folder();

                    } else if (Files.isRegularFile(p)) {

                        if (cancelled.getAsBoolean()) {
                            return;
                        }

                        long size = Files.size(p);

                        info.addSize(size);
                        info.addFile();

                        stats.file(size);

                        progress.accept(p);
                    }

                } catch (Exception e) {

                    if (!cancelled.getAsBoolean()) {
                        stats.error();
                    }
                }
            });

        } catch (Exception e) {

            if (!cancelled.getAsBoolean()) {
                stats.error();
            }
        }

        return info;
    }
}