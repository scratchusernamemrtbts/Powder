package powder;

import powder.position.Position;
import powder.tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final List<Tile> checkActive = new ArrayList<>();
    private boolean updatesSuppressed = true;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];

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
    }

    private static void shuffleArray(Tile[] array, int size) {
        Random random = ThreadLocalRandom.current();
        while (size-- > 0) {
            int r = random.nextInt(size + 1);
            Tile tmp = array[r];
            array[r] = array[size];
            array[size] = tmp;
        }
    }

    public void update() {
        updatesSuppressed = false;
        for (Tile t : checkActive) {
            t.setActive(true);
        }
        checkActive.clear();

        Tile[] updatingTiles = new Tile[width * height];
        int totalTiles = 0;
        for (int x = 0; x < width; x++) {
            for (Tile tile : tiles[x]) {
                if (tile.isActive()) {
                    updatingTiles[totalTiles] = tile;
                    totalTiles++;
                }
            }
        }
        shuffleArray(updatingTiles, totalTiles);

        for (int i = 0; i < totalTiles; i++) {
            updatingTiles[i].update();
        }
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
        return getTile(position.getX(), position.getY());
    }

    public Tile getTile(int x, int y) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > getWidth() - 1) x = getWidth() - 1;
        if (y > getHeight() - 1) y = getHeight() - 1;
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
