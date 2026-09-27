package service;

import java.nio.file.Path;
import java.util.List;

public class SearchService {

    private final FileService files = new FileService();

    public List<Path> search(Path root, String query) {

        if (root == null || query == null || query.isBlank()) {
            return List.of();
        }

        return files.search(root, query);
    }
}