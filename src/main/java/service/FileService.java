package service;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
public class FileService {
    public List<Path> search(Path root,String query){
        List<Path> out=new ArrayList<>();
        try(Stream<Path> s=Files.walk(root)){
            s.filter(p->p.getFileName()!=null)
             .filter(p->p.getFileName().toString().toLowerCase().contains(query.toLowerCase()))
             .limit(5000).forEach(out::add);
        }catch(Exception ignored){}
        return out;
    }
}
