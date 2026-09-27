package export;

import java.nio.file.Files;
import java.nio.file.Path;

import model.FolderInfo;
import utils.SizeFormatter;

public class HtmlExporter {

    public void export(FolderInfo root, Path target) throws Exception {

        StringBuilder b = new StringBuilder();

        b.append("<html>");
        b.append("<head>");
        b.append("<meta charset=\"UTF-8\">");
        b.append("<title>Disk Report</title>");

        b.append("<style>");
        b.append("body{font-family:Arial;margin:30px;}");
        b.append("table{border-collapse:collapse;width:100%;}");
        b.append("td,th{padding:8px;border:1px solid #ddd;}");
        b.append("</style>");

        b.append("</head>");
        b.append("<body>");

        b.append("<h1>Java Disk Analyzer</h1>");

        b.append("<table>");
        b.append("<tr>");
        b.append("<th>Folder</th>");
        b.append("<th>Size</th>");
        b.append("<th>Files</th>");
        b.append("<th>Folders</th>");
        b.append("</tr>");

        write(b, root);

        b.append("</table>");
        b.append("</body>");
        b.append("</html>");

        Files.writeString(target, b.toString());
    }

    private void write(StringBuilder b, FolderInfo f) {

        b.append("<tr>");

        b.append("<td>")
         .append(escape(f.getPath().toString()))
         .append("</td>");

        b.append("<td>")
         .append(SizeFormatter.format(f.getTotalSize()))
         .append("</td>");

        b.append("<td>")
         .append(f.getFileCount())
         .append("</td>");

        b.append("<td>")
         .append(f.getFolderCount())
         .append("</td>");

        b.append("</tr>");

        for (FolderInfo child : f.getChildren()) {
            write(b, child);
        }
    }

    private String escape(String value) {

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}