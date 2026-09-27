package export;

import java.nio.file.Files;
import java.nio.file.Path;

import model.FolderInfo;

public class CsvExporter {

    public void export(FolderInfo root, Path target) throws Exception {

        StringBuilder b = new StringBuilder();

        b.append("Name,Path,Size,Files,Folders\n");

        write(b, root);

        Files.writeString(target, b.toString());
    }

    private void write(StringBuilder b, FolderInfo f) {

        b.append("\"")
         .append(escape(f.getName()))
         .append("\",\"")
         .append(escape(f.getPath().toString()))
         .append("\",")
         .append(f.getTotalSize())
         .append(",")
         .append(f.getFileCount())
         .append(",")
         .append(f.getFolderCount())
         .append("\n");

        for (FolderInfo child : f.getChildren()) {
            write(b, child);
        }
    }

    private String escape(String value) {
        return value.replace("\"", "\"\"");
    }
}