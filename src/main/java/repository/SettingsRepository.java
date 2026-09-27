package repository;
import java.util.prefs.Preferences;
public class SettingsRepository {
    private final Preferences p=Preferences.userRoot().node("JavaDiskAnalyzer");
    public String get(String k,String d){return p.get(k,d);}
    public void set(String k,String v){p.put(k,v);}
}
