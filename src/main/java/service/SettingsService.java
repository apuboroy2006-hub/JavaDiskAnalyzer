package service;
import java.util.prefs.Preferences;
public class SettingsService {
    private final Preferences p=Preferences.userRoot().node("JavaDiskAnalyzer");
    public boolean darkMode(){return p.getBoolean("darkMode",false);}
    public void darkMode(boolean v){p.putBoolean("darkMode",v);}
}
