package powder.position;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Position relative(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position down() {
        return relative(0, -1);
    }

    public Position up() {
        return relative(0, 1);
    }

    public Position left() {
        return relative(-1, 0);
    }

    public Position right() {
        return relative(1, 0);
    }

    public Position direction(Direction direction) {
        switch (direction) {
            case Up:
                return up();
            case Down:
                return down();
            case Left:
                return left();
            case Right:
                return right();
        }
        throw new RuntimeException("powder.position.Direction is invalid.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
