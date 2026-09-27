package worker;
import javax.swing.SwingWorker;
import service.SearchService;
import java.nio.file.Path;
import java.util.List;
public class SearchWorker extends SwingWorker<List<Path>,Void> {
    private final Path root; private final String query; private final SearchService service;
    public SearchWorker(Path r,String q,SearchService s){root=r;query=q;service=s;}
    protected List<Path> doInBackground(){return service.search(root,query);}
}
