package powder.tiles;

import powder.World;
import powder.position.Direction;
import powder.util.Randomizer;

import java.awt.*;

public class VirusTile extends SolidTile {
    private int infected = 0;
    private int failures = 0;

    public VirusTile(World world) {
        super(world);
        setColor(new Color(255, 0, 255));
    }

    @Override
    public void update() {
        if (failures % 10 == 0) {
            if (getWorld().getTile(getPosition().up()) instanceof VirusTile &&
                    getWorld().getTile(getPosition().down()) instanceof VirusTile &&
                    getWorld().getTile(getPosition().left()) instanceof VirusTile &&
                    getWorld().getTile(getPosition().right()) instanceof VirusTile
            ) {
                setActive(false);
                return;
            }
        }

        if (Randomizer.nextDouble() < 0.1) {
            return;
        }

        Tile candidate = getWorld().getTile(getPosition().direction(Direction.random()));
        if (!(candidate instanceof PowderTile)) {
            failures++;
            return;
        }

        getWorld().setTile(candidate.getPosition(), new VirusTile(getWorld()));
        infected++;
    }
}
