package model;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
public record FileInfo(String name, Path path, long size, FileTime modified,
                       String extension, boolean directory) {}
