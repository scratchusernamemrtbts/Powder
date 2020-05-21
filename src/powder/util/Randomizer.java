package powder.util;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static int nextInt(int n) {
        return ThreadLocalRandom.current().nextInt(n);
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
