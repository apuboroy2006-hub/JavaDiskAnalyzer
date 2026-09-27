package service;
import java.nio.file.Path;
import java.util.List;
import scanner.DriveScanner;
import utils.SystemUtils;
public class DriveService {
    public List<Path> drives(){return SystemUtils.drives();}
    public model.DriveInfo info(Path p)throws Exception{return new DriveScanner().scan(p);}
}
