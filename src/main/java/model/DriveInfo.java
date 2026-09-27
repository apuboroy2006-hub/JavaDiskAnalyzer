package model;
import java.nio.file.Path;
public record DriveInfo(Path path,long totalSpace,long freeSpace) {
    public long usedSpace(){return Math.max(0,totalSpace-freeSpace);}
}
