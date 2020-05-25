package powder;

import java.io.InputStream;
import java.net.URL;

public class FileLoader {
    public static InputStream getStream(String name) {
        return FileLoader.class.getClassLoader().getResourceAsStream(name);
    }

    public static URL getURL(String name) {
        return FileLoader.class.getClassLoader().getResource(name);
    }
}
