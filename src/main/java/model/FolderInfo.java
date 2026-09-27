package model;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class FolderInfo {
    private final String name;
    private final Path path;
    private long totalSize, fileCount, folderCount;
    private final List<FolderInfo> children = new ArrayList<>();
    public FolderInfo(String name, Path path) { this.name=name; this.path=path; }
    public String getName(){return name;} public Path getPath(){return path;}
    public long getTotalSize(){return totalSize;} public long getFileCount(){return fileCount;}
    public long getFolderCount(){return folderCount;} public List<FolderInfo> getChildren(){return children;}
    public void addSize(long n){totalSize+=n;} public void addFile(){fileCount++;}
    public void addFolder(){folderCount++;} public void addChild(FolderInfo f){children.add(f);}
}
