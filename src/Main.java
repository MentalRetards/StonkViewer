import java.nio.file.FileSystemException;

public class Main {
    public static Window window;
    public static FileManager fileManager;
    public static void main(String[] args) {
        fileManager = new FileManager();
        window = new Window("Stonks Viewer");
        window.addLabelCentered("Stonks Viewer", new Bound(500, -10, 0, 0), 30);
        ButtonGroupID buttonGroup = window.addRadio("May", new Bound(200, 200, 200, 40));
        window.addRadio("June", new Bound(200, 240, 200, 40), buttonGroup);
        window.addRadio("July", new Bound(200, 160, 200, 40), buttonGroup);
        window.setSize(1000, 700);
        window.init();
    }
    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

}