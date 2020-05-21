package powder.util;

import java.awt.*;

public class ColorRandomizer {
    private static int clamp(int c) {
        if (c > 255) return 255;
        return Math.max(c, 0);
    }

    private static Color makeColor(int r, int g, int b) {
        return new Color(
                clamp(r),
                clamp(g),
                clamp(b)
        );
    }

    public static Color randomize(Color color, int red, int green, int blue) {
        return makeColor(
                color.getRed() + randomNumber(red),
                color.getGreen() + randomNumber(green),
                color.getBlue() + randomNumber(blue)
        );
    }

    public static Color uniform(Color color, int random) {
        random = randomNumber(random);
        return makeColor(
                color.getRed() + random,
                color.getGreen() + random,
                color.getBlue() + random
        );
    }

    private static int randomNumber(int n) {
        if (n == 0) return 0;
        int r = Randomizer.nextInt(n);
        return r - r/2;
    }
}
