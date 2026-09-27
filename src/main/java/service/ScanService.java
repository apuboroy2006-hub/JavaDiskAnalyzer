package service;

import java.nio.file.Path;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import model.ScanResult;
import scanner.ScanTask;

public class ScanService {

    public ScanResult scan(
            Path root,
            Consumer<Path> progress,
            BooleanSupplier cancelled) {

        return new ScanTask().execute(
                root,
                progress,
                cancelled
        );
    }
}