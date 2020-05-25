package powder;

import powder.tiles.*;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class TileDatabase {
    private static final World dummyWorld = new World(1, 1);

    public static class TileInfo {
        private final String name;
        private final String key;
        private final Constructor<? extends Tile> constructor;
        private final Color color;

        TileInfo(String name, String key, Class<? extends Tile> tileClass) throws NoSuchMethodException {
            this.name = name;
            this.key = key;
            this.constructor = tileClass.getDeclaredConstructor(World.class);
            this.color = construct(dummyWorld).getColor();
        }

        public String getName() {
            return name;
        }

        public Tile construct(World world) {
            try {
                return constructor.newInstance(world);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public Color getColor() {
            return color;
        }

        public String getKey() {
            return key;
        }
    }

    public static List<TileInfo> tiles = new ArrayList<>();

    static {
        add("Air", "a", AirTile.class);
        add("Sand", "s", SandTile.class);
        add("Water", "w", WaterTile.class);
        add("Bedrock", "b", BedrockTile.class);
        add("Fuse", "e", FuseTile.class);
        add("Gunpowder", "u", GunpowderTile.class);
        add("Fire", "f", FireTile.class);
        add("Dirt", "d", DirtTile.class);
        add("Grass", "g", GrassTile.class);
        add("Gravel", "r", GravelTile.class);
        add("Oil", "o", OilTile.class);
        add("Mercury", "m", MercuryTile.class);
        add("Blue Fire", "l", BlueFireTile.class);
        add("Methane",  "h", MethaneTile.class);
        add("Steam", "t", SteamTile.class);
        add("Bleach", "y", BleachTile.class);
        add("Virus", "v", VirusTile.class);
    }

    private static void add(String name, String key, Class<? extends Tile> tileClass) {
        try {
            tiles.add(new TileInfo(name, key, tileClass));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
