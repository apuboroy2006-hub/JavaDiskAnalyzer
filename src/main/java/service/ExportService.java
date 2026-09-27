package service;
import export.*;
import model.FolderInfo;
import java.nio.file.Path;
public class ExportService {
    public void csv(FolderInfo f,Path p)throws Exception{new CsvExporter().export(f,p);}
    public void json(FolderInfo f,Path p)throws Exception{new JsonExporter().export(f,p);}
    public void html(FolderInfo f,Path p)throws Exception{new HtmlExporter().export(f,p);}
}
