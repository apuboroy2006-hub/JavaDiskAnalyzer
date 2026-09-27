package worker;
import javax.swing.SwingWorker;
import model.FolderInfo;
import service.ExportService;
import java.nio.file.Path;
public class ExportWorker extends SwingWorker<Void,Void> {
    private final FolderInfo root; private final Path target; private final String type;
    public ExportWorker(FolderInfo r,Path t,String ty){root=r;target=t;type=ty;}
    protected Void doInBackground()throws Exception{
        ExportService s=new ExportService();
        switch(type){case "CSV"->s.csv(root,target);case "JSON"->s.json(root,target);case "HTML"->s.html(root,target);}
        return null;
    }
}
