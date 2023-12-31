import java.io.File;

public class ProfileFileManager {
    private final File directory;
    private String profileName;
    private final byte id;
    public ProfileFileManager(File directory, byte id) {
        FileHandler.createAndTestDir(directory);
        this.directory = directory;
        profileName = directory.getName();
        this.id = id;
    }
    public byte getId() {
        return this.id;
    }
    public String getName() {
        return this.profileName;
    }
    public File getDirectory() {
        return directory;
    }
}
