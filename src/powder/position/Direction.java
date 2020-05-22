package powder.position;

import powder.util.Randomizer;

public enum Direction {
    Up,
    Down,
    Left,
    Right;

    public static Direction random() {
        switch (Randomizer.nextInt(4)) {
            case 0: return Direction.Up;
            case 1: return Direction.Down;
            case 2: return Direction.Left;
            case 3: return Direction.Right;
        }
        throw new RuntimeException("Unknown random");
    }
}
