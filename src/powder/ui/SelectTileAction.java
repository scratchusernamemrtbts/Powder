package powder.ui;

import powder.TileDatabase;

import java.awt.*;
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
        if (tileInfo.getColor().getRed() + tileInfo.getColor().getGreen() + tileInfo.getColor().getBlue() > 512) {
            windowInterface.tilesMenu.setForeground(new Color(0, 0, 0));
        } else {
            windowInterface.tilesMenu.setForeground(tileInfo.getColor());
        }
    }
}
