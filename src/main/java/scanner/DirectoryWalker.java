package scanner;

import java.nio.file.AccessDeniedException;
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

        FolderInfo info = new FolderInfo(
                dir.getFileName() == null
                        ? dir.toString()
                        : dir.getFileName().toString(),
                dir
        );

        if (cancelled.getAsBoolean()) {
            return info;
        }

        try (var stream = Files.list(dir)) {

            stream.sorted(
                    Comparator.comparing(
                            p -> p.getFileName()
                                    .toString()
                                    .toLowerCase()
                    )
            ).forEach(p -> {

                if (cancelled.getAsBoolean()) {
                    return;
                }

                try {

                    if (Files.isDirectory(p)) {

                        FolderInfo child = build(
                                p,
                                progress,
                                cancelled
                        );

                        if (cancelled.getAsBoolean()) {
                            return;
                        }

                        info.addChild(child);
                        info.addSize(child.getTotalSize());
                        info.addFolder();
                        stats.folder();

                    } else if (Files.isRegularFile(p)) {

                        long size = Files.size(p);

                        info.addSize(size);
                        info.addFile();

                        stats.file(size);

                        progress.accept(p);

                    } else {

                        // Could not determine file or directory
                        if (!cancelled.getAsBoolean()) {
                            stats.addSkipped();
                        }
                    }

                } catch (AccessDeniedException e) {

                    // Permission denied = skipped
                    if (!cancelled.getAsBoolean()) {
                        stats.addSkipped();
                    }

                } catch (SecurityException e) {

                    // Security restriction = skipped
                    if (!cancelled.getAsBoolean()) {
                        stats.addSkipped();
                    }

                } catch (Exception e) {

                    // Unexpected filesystem problem = real error
                    if (!cancelled.getAsBoolean()) {
                        stats.error();
                    }
                }
            });

        } catch (AccessDeniedException e) {

            // Cannot enter directory
            if (!cancelled.getAsBoolean()) {
                stats.addSkipped();
            }

        } catch (SecurityException e) {

            // Security restriction
            if (!cancelled.getAsBoolean()) {
                stats.addSkipped();
            }

        } catch (Exception e) {

            // Unexpected directory error
            if (!cancelled.getAsBoolean()) {
                stats.error();
            }
        }

        return info;
    }
}