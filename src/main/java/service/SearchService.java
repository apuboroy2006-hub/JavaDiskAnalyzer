package service;
import java.nio.file.Path;
import java.util.List;
public class SearchService {
    private final FileService files=new FileService();
    public List<Path> search(Path root,String query){
        return query==null||query.isBlank()?List.of():files.search(root,query);
    }
}
