package scanner;
import model.FileInfo;
import utils.FileUtils;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
public class FileScanner {
    public FileInfo read(Path p)throws Exception{
        BasicFileAttributes a=Files.readAttributes(p,BasicFileAttributes.class);
        return new FileInfo(p.getFileName().toString(),p,a.isRegularFile()?a.size():0,
                a.lastModifiedTime(),FileUtils.extension(p),a.isDirectory());
    }
}
