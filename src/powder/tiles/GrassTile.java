package powder.tiles;

import powder.World;
import powder.position.Position;
import powder.util.ColorRandomizer;
import powder.util.Randomizer;

import javax.security.auth.login.FailedLoginException;
import java.awt.*;

public class GrassTile extends PowderTile {
    private int dirtNeighbors = 0;
    private int failures = 0;

    public GrassTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(0, 156, 0), 30));
    }

    @Override
    public double getFlammability() {
        return 0.1;
    }

    private boolean isNearSky(Position position) {
        for (int i = 0; i < 5; i++) {
            Position needle = position.relative(0, i);
            if (needle.getY() >= getWorld().getHeight() - 1) {
                return false;
            }
            if (!getWorld().getTile(needle).blocksSunlight()) {
                return true;
            }
        }
        return false;
    }

    private void checkNeighbor(Position position) {
        Tile tile = getWorld().getTile(position);
        if (tile instanceof DirtTile) {
            if (Randomizer.nextDouble() < 0.1) {
                if (isNearSky(position)) {
                    getWorld().setTile(position, new GrassTile(getWorld()));
                    if (getWorld().getTile(position.up()).isAir()) {
                        if (Randomizer.nextDouble() < 0.2) {
                            getWorld().setTile(position.up(), new TallGrassTile(getWorld(), Randomizer.nextInt(2) + 2));
                        }
                    }
                }
            }
            dirtNeighbors++;
        }
    }

    @Override
    public void update() {
        super.update();

        if (!isNearSky(getPosition())) {
            getWorld().setTile(getPosition(), new DirtTile(getWorld()));
            return;
        }

        dirtNeighbors = 0;
        checkNeighbor(getPosition().up());
        checkNeighbor(getPosition().down());
        checkNeighbor(getPosition().left());
        checkNeighbor(getPosition().right());

        if (dirtNeighbors == 0) {
            failures++;
        } else {
            failures = 0;
        }

        setActive(isActive() || failures < 20);
    }
}
