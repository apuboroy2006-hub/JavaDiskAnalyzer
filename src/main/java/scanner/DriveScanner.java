package scanner;
import model.DriveInfo;
import java.nio.file.*;
public class DriveScanner {
    public DriveInfo scan(Path root)throws Exception{
        FileStore s=Files.getFileStore(root);
        return new DriveInfo(root,s.getTotalSpace(),s.getUsableSpace());
    }
}
