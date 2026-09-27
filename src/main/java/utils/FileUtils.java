package utils;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.*;
public final class FileUtils {
    private FileUtils(){}
    public static String extension(Path p){
        String n=p.getFileName()==null?"":p.getFileName().toString();
        int i=n.lastIndexOf('.');
        return i>0?n.substring(i+1).toLowerCase():"";
    }
    public static void open(Path p)throws IOException{
        if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(p.toFile());
    }
    public static void reveal(Path p)throws IOException{
        if(System.getProperty("os.name").toLowerCase().contains("win"))
            new ProcessBuilder("explorer.exe","/select,"+p.toAbsolutePath()).start();
        else open(p.getParent()==null?p:p.getParent());
    }
}
