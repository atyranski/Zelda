package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Shield extends AbstractItem{
    public Shield(int x, int y, String key, String name) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;
        this.image = new Image(new FileInputStream("src/main/resources/map/gui/shield.png"));
    }
}
