package utils;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
public final class SystemUtils {
    private SystemUtils(){}
    public static List<Path> drives(){
        List<Path> r=new ArrayList<>();
        FileSystems.getDefault().getRootDirectories().forEach(r::add);
        return r;
    }
}
