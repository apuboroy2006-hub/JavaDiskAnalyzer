package scanner;
import model.FolderInfo;
import model.ScanStatistics;
import java.nio.file.*;
import java.util.Comparator;
import java.util.function.Consumer;

public class DirectoryWalker {
    private final ScanStatistics stats;
    public DirectoryWalker(ScanStatistics stats){this.stats=stats;}

    public FolderInfo build(Path dir, Consumer<Path> progress){
        FolderInfo info=new FolderInfo(dir.getFileName()==null?dir.toString():dir.getFileName().toString(),dir);
        try(var s=Files.list(dir)){
            s.sorted(Comparator.comparing(p->p.getFileName().toString().toLowerCase())).forEach(p->{
                try{
                    if(Files.isDirectory(p)){
                        FolderInfo child=build(p,progress);
                        info.addChild(child); info.addSize(child.getTotalSize());
                        info.addFolder(); stats.folder();
                    }else if(Files.isRegularFile(p)){
                        long size=Files.size(p);
                        info.addSize(size); info.addFile(); stats.file(size);
                        progress.accept(p);
                    }
                }catch(Exception e){stats.error();}
            });
        }catch(Exception e){stats.error();}
        return info;
    }
}
