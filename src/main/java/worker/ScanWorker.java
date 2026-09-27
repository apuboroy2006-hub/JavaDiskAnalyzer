package worker;

import javax.swing.SwingWorker;
import model.ScanResult;
import service.ScanService;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public class ScanWorker extends SwingWorker<ScanResult, Path> {

    private final Path root;
    private final ScanService service;
    private final Consumer<Path> consumer;

    public ScanWorker(Path root, ScanService service, Consumer<Path> consumer) {
        this.root = root;
        this.service = service;
        this.consumer = consumer;
    }

    @Override
    protected ScanResult doInBackground() {

        return service.scan(
            root,
            p -> {
                if (isCancelled()) {
                    throw new CancellationException();
                }

                publish(p);
            },
            this::isCancelled
        );
    }

    @Override
    protected void process(List<Path> chunks) {

        if (!isCancelled() && !chunks.isEmpty()) {
            consumer.accept(
                chunks.get(chunks.size() - 1)
            );
        }
    }

    public static class CancellationException extends RuntimeException {
    }
}