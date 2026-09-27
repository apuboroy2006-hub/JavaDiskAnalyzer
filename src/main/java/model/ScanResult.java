package model;
public record ScanResult(FolderInfo root,long files,long folders,long bytes,long errors) {}
