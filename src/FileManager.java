import java.io.File;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static File mainPath;
    List<ProfileFileManager> profiles = new ArrayList<>();
    private ProfileFileManager selectedProfile;
    private File config;
    private List<String> configStr;
    public FileManager() {
        mainPath = new File(System.getenv("APPDATA") + "\\.StonkViewer");
        FileHandler.createAndTestDir(mainPath);
        initProfiles();
        config = FileHandler.createFile(mainPath, "config.cfg");
        initConfig(config);
    }
    public ProfileFileManager getSelectedProfile() {
        return this.selectedProfile;
    }

    public void saveToConfig(Field field, String value) {
        FileHandler.replaceFileLine(config, field.getField() + ":" + value, field.getLine());
        configStr = FileHandler.readFromFile(config);
    }
    public String findFieldFromConfig(String field) {
        for (String str : configStr) {
            String[] split = str.split(":");
            if (split[0].equals(field.toLowerCase())) return split[1];
        }
        return null;
    }
    public List<ProfileFileManager> getProfiles() {
        return profiles;
    }
    public void selectProfile(String name) {
        selectProfile(getProfileFromName(name));
    }
    public void selectProfile(ProfileFileManager profile) {
        if (profile == null) profile = profiles.get(0);
        this.selectedProfile = profile;
    }
    public void initConfig(File config) {

        configStr = FileHandler.readFromFile(config);
        String str = findFieldFromConfig(Field.PROFILE.getField());
        if (str == null) {
            saveToConfig(Field.PROFILE, profiles.get(0).getName());
            str = profiles.get(0).getName();
        }
        selectProfile(str);
    }
    public ProfileFileManager createNewProfile(String name) {
        name = name.toLowerCase();
        if (FileHandler.lookForFile(mainPath, name) != null) return null;
        ProfileFileManager profile = new ProfileFileManager(FileHandler.createDir(mainPath, name), getUnusedID());
        profiles.add(profile);
        return profile;
    }
    public ProfileFileManager getProfileByID(byte id) {
        for (ProfileFileManager profile : profiles) {
            if (profile.getId() == id) return profile;
        }
        return null;
    }
    public byte getUnusedID() {
        byte id = 0;
        for (; getProfileByID(id) != null; id ++);
        return id;
    }
    public ProfileFileManager getProfileFromName(String name) {
        for (ProfileFileManager profile : profiles) {
            if (profile.getName().equalsIgnoreCase(name)) return profile;
        }
        return null;
    }
    public void initProfiles() {
        byte count = 0;
        for (File file : mainPath.getAbsoluteFile().listFiles()) {
            if (!file.isDirectory()) continue;
            profiles.add(new ProfileFileManager(file, count));
            count ++;
        }
        if (profiles.isEmpty())
            profiles.add(new ProfileFileManager(FileHandler.createDir(mainPath, "user"), (byte) 0));
    }
}
