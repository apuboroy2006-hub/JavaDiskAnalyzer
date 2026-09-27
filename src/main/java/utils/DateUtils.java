package utils;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
public final class DateUtils {
    private static final DateTimeFormatter F=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateUtils(){}
    public static String format(FileTime t){return t==null?"":F.format(t.toInstant().atZone(ZoneId.systemDefault()));}
}
