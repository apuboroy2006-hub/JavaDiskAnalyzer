package model;
public class ScanStatistics {
    private long files, folders, bytes, errors;
    public synchronized void file(long size){files++;bytes+=size;}
    public synchronized void folder(){folders++;}
    public synchronized void error(){errors++;}
    public synchronized long files(){return files;}
    public synchronized long folders(){return folders;}
    public synchronized long bytes(){return bytes;}
    public synchronized long errors(){return errors;}
}
