package model;

public class ScanStatistics {

    private long files;
    private long folders;
    private long bytes;
    private long errors;
    private long skipped;

    public synchronized void file(long size) {
        files++;
        bytes += size;
    }

    public synchronized void folder() {
        folders++;
    }

    public synchronized void error() {
        errors++;
    }

    public synchronized void addSkipped() {
        skipped++;
    }

    public long files() {
        return files;
    }

    public long folders() {
        return folders;
    }

    public long bytes() {
        return bytes;
    }

    public long errors() {
        return errors;
    }

    public long skipped() {
        return skipped;
    }
}