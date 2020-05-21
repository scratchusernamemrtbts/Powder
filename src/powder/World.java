package powder;

import powder.position.Position;
import powder.tiles.*;
import powder.util.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    private final int width = 200;
    private final int height = 100;

    private boolean updatesSuppressed = true;

    private final Tile[][] tiles = new Tile[width][height];
    private final List<Tile> checkActive = new ArrayList<>();

    public World() {
//        Init air powder.tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setTile(new Position(x, y), new AirTile(this));
            }
        }
//        Add floor, ceiling
        for (int x = 0; x < width; x++) {
            setTile(new Position(x, 0), new BedrockTile(this));
            setTile(new Position(x, getHeight() - 1), new BedrockTile(this));
        }
//        Add walls
        for (int y = 0; y < height; y++) {
            setTile(new Position(0, y), new BedrockTile(this));
            setTile(new Position(getWidth() - 1, y), new BedrockTile(this));
        }
//        setTile(new powder.position.Position(3, 1), new powder.powder.tiles.SolidTile(this));
//        Some sand
//        setTile(new powder.position.Position(2, 7), new powder.powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(3, 4), new powder.powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(3, 5), new powder.powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(3, 6), new powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(3, 7), new powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(4, 4), new powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(4, 5), new powder.tiles.SandTile(this));
//        setTile(new powder.position.Position(5, 8), new powder.tiles.SandTile(this));

//        for (int x = 20; x < 30; x++) {
//            for (int y = 10; y < 70; y++) {
//                setTile(new Position(x, y), new SandTile(this));
//            }
//        }

//        for (int x = 35; x < 55; x++) {
//            for (int y = 10; y < 70; y++) {
//                setTile(new powder.position.Position(x, y), new powder.powder.tiles.WaterTile(this));
//            }
//        }
//        setTile(new powder.position.Position(2, 5), new powder.tiles.SandTile(this));
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(Tile[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Tile a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void update() {
        updatesSuppressed = false;
        for (Tile t : checkActive) {
            t.setActive(true);
        }
        checkActive.clear();

//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                powder.powder.tiles.Tile t = tilesSnapshot[x][y];
//                t.update();
//            }
//        }

//        for (int x = 0; x < width; x++) {
//            for (int y = height - 1; y >= 0; y--) {
//                powder.tiles.Tile t = tilesSnapshot[x][y];
//                t.update();
//            }
//        }

        Tile[] tiles2 = new Tile[width * height];
        int i = 0;
        for (int x = 0; x < width; x++) {
            for (Tile tile : tiles[x]) {
                tiles2[i] = tile;
                i++;
            }
        }
        shuffleArray(tiles2);

        for (Tile t : tiles2) {
            if (t.isActive()) {
                t.update();
            }
        }

//        if (Randomizer.nextBoolean()) {
//            for (int x = 0; x < width; x += 2) {
//                for (int y = 0; y < height; y += 2) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//            for (int x = 0; x < width; x += 2) {
//                for (int y = 1; y < height; y += 2) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//            for (int x = 1; x < width; x += 2) {
//                for (int y = 0; y < height; y += 2) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//            for (int x = 1; x < width; x += 2) {
//                for (int y = 1; y < height; y += 2) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//        } else {
//            for (int x = 1; x < width; x += 2) {
//                for (int y = 0; y < height; y++) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//            for (int x = 0; x < width; x += 2) {
//                for (int y = 0; y < height; y++) {
//                    Tile t = tilesSnapshot[x][y];
//                    if (t.isActive()) {
//                        t.update();
//                    }
//                }
//            }
//        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void swapTiles(Position original, Position other) {
        if (original.equals(other)) {
            return;
        }
        Tile tempTile = getTile(original);
        getTile(other).setActive(true);
        setTile(original, getTile(other));
        setTile(other, tempTile);
    }

    public void setTile(Position position, Tile t) {
        if (t != null) {
            t.setPosition(position);
        }
        tiles[position.getX()][position.getY()] = t;
        tileUpdate(position);
    }

    public Tile getTile(Position position) {
        return tiles[position.getX()][position.getY()];
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void tileUpdate(Position position) {
        if (updatesSuppressed) {
            return;
        }
        checkActive.add(getTile(position));
        checkActive.add(getTile(position.up()));
        checkActive.add(getTile(position.down()));
        checkActive.add(getTile(position.left()));
        checkActive.add(getTile(position.right()));
    }
}
