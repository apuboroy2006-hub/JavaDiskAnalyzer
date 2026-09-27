package repository;
import model.ScanResult;
import java.util.*;
public class ScanRepository {
    private final List<ScanResult> history=new ArrayList<>();
    public void save(ScanResult r){history.add(r);}
    public List<ScanResult> history(){return List.copyOf(history);}
}
