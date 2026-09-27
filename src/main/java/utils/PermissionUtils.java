package utils;
import java.nio.file.Files;
import java.nio.file.Path;
public final class PermissionUtils {
    private PermissionUtils(){}
    public static boolean readable(Path p){return Files.isReadable(p);}
}
