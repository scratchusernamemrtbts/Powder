package powder.ui;

import powder.TileDatabase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectTileAction implements ActionListener {
    private final TileDatabase.TileInfo tileInfo;
    private final WindowInterface windowInterface;

    public SelectTileAction(TileDatabase.TileInfo tileInfo, WindowInterface windowInterface) {
        this.tileInfo = tileInfo;
        this.windowInterface = windowInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        windowInterface.setCurrentTileInfo(tileInfo);
    }

    public TileDatabase.TileInfo getTileInfo() {
        return tileInfo;
    }
}
