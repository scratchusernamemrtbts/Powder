package powder;

import powder.position.Position;
import powder.tiles.SandTile;
import powder.tiles.WaterTile;
import powder.ui.BasicConsoleInterface;
import powder.ui.Interface;
import powder.ui.WindowInterface;

public class Powder {
    private final World world;
    private final Interface ui;

    public Powder() {
        world = new World(200, 100);
        ui = new WindowInterface(this);

//        world = new World(40, 40);
//        ui = new BasicConsoleInterface(this);
//        for (int x = 20; x < 30; x++) {
//            for (int y = 10; y < 30; y++) {
//                world.setTile(new Position(x, y), new SandTile(world));
//            }
//        }
//        for (int x = 20; x < 30; x++) {
//            for (int y = 31; y < 39; y++) {
//                world.setTile(new Position(x, y), new WaterTile(world));
//            }
//        }
    }

    public void start() {
        ui.start();

        while (true) {
            try {
                update();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            if (!ui.isTurbo()) {
                try {
                    Thread.sleep(14);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update() {
        ui.update();
        if (!ui.isPaused()) {
            world.update();
        }
        ui.render();
    }

    public World getWorld() {
        return world;
    }

    public Interface getUi() {
        return ui;
    }
}
