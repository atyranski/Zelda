package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Potion extends AbstractItem{
    public Potion(int x, int y, String key, String name) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;

        this.initializeImage();
    }

    private void initializeImage() throws FileNotFoundException {
        if (name.equals("potionFull")) image = new Image(new FileInputStream("src/main/resources/map/gui/heart_pickup.png"));
        else image = new Image(new FileInputStream("src/main/resources/map/gui/potion.png"));
    }
}
