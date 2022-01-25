package game.items;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sword extends AbstractItem {
    private int strength;

    public Sword(int x, int y, String key, String name, int strength) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;
        this.strength = strength;

        this.initializeImage();
    }

    public int getStrength() {
        return strength;
    }

    private void initializeImage() throws FileNotFoundException {
        if (name.equals("woodenSword")) image = new Image(new FileInputStream("src/main/resources/map/gui/woodenSword.png"));
        else image = new Image(new FileInputStream("src/main/resources/map/gui/whiteSword.png"));
    }
}
