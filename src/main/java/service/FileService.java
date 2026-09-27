package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class FileService {

    public List<Path> search(Path root, String query) {

        List<Path> results = new ArrayList<>();

        if (root == null || query == null || query.isBlank()) {
            return results;
        }

        String searchText = query.trim().toLowerCase(Locale.ROOT);

        try (Stream<Path> stream = Files.walk(root)) {

            stream
                .filter(Files::exists)
                .filter(path -> matches(path, searchText))
                .limit(5000)
                .forEach(results::add);

        } catch (Exception ignored) {
            // Ignore inaccessible files/folders
        }

        return results;
    }

    private boolean matches(Path path, String query) {

        // 1. File/folder name
        Path fileName = path.getFileName();

        if (fileName != null &&
            fileName.toString().toLowerCase(Locale.ROOT).contains(query)) {
            return true;
        }

        // 2. Full path
        String fullPath = path.toString().toLowerCase(Locale.ROOT);

        if (fullPath.contains(query)) {
            return true;
        }

        // 3. File extension
        if (Files.isRegularFile(path)) {

            String name = fileName == null
                    ? ""
                    : fileName.toString().toLowerCase(Locale.ROOT);

            int dot = name.lastIndexOf('.');

            if (dot > 0 && dot < name.length() - 1) {

                String extension = name.substring(dot + 1);

                if (extension.equals(query)) {
                    return true;
                }

                if (("." + extension).equals(query)) {
                    return true;
                }
            }
        }

        return false;
    }
}