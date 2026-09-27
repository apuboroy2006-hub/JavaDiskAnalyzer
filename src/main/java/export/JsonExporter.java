package export;

import java.nio.file.Files;
import java.nio.file.Path;

import model.FolderInfo;

public class JsonExporter {

    public void export(FolderInfo root, Path target) throws Exception {
        Files.writeString(target, json(root));
    }

    private String json(FolderInfo f) {

        StringBuilder b = new StringBuilder();

        b.append("{");

        b.append("\"name\":\"")
         .append(escape(f.getName()))
         .append("\",");

        b.append("\"path\":\"")
         .append(escape(f.getPath().toString()))
         .append("\",");

        b.append("\"size\":")
         .append(f.getTotalSize())
         .append(",");

        b.append("\"files\":")
         .append(f.getFileCount())
         .append(",");

        b.append("\"folders\":")
         .append(f.getFolderCount())
         .append(",");

        b.append("\"children\":[");

        for (int i = 0; i < f.getChildren().size(); i++) {

            if (i > 0) {
                b.append(",");
            }

            b.append(json(f.getChildren().get(i)));
        }

        b.append("]");

        b.append("}");

        return b.toString();
    }

    private String escape(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}