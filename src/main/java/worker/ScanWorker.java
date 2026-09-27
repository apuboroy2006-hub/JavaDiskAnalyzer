package worker;
import javax.swing.SwingWorker;
import model.ScanResult;
import service.ScanService;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
public class ScanWorker extends SwingWorker<ScanResult,Path> {
    private final Path root; private final ScanService service; private final Consumer<Path> consumer;
    public ScanWorker(Path root,ScanService service,Consumer<Path> consumer){
        this.root=root;this.service=service;this.consumer=consumer;
    }
    protected ScanResult doInBackground(){
        return service.scan(root,p->{if(!isCancelled())publish(p);});
    }
    protected void process(List<Path> chunks){
        if(!chunks.isEmpty())consumer.accept(chunks.get(chunks.size()-1));
    }
}
