package service;
import model.FolderInfo;
import java.util.Comparator;
import java.util.List;
public class SortService {
    public void sortBySize(List<FolderInfo> list){
        list.sort(Comparator.comparingLong(FolderInfo::getTotalSize).reversed());
    }
}
