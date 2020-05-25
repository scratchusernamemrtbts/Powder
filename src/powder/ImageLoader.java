package powder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageLoader {
    public static Image loadImage(String path) {
        try {
            return ImageIO.read(FileLoader.getStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Icon loadIcon(String path) {
        return new ImageIcon(FileLoader.getURL(path));
    }
}
