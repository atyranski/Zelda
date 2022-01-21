package game.items;

import game.actors.IMapElement;
import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sword implements IMapItem {
    private int x;
    private int y;
    private String key;
    private String name;
    private int strength;
    private Image image;

    public Sword(int x, int y, String key, String name, int strength) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;
        this.strength = strength;

        this.initializeImage();
    }

    public Image getImage() {
        return this.image;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(x,y);
    }

    private void initializeImage() throws FileNotFoundException {
        if (name.equals("woodenSword")) image = new Image(new FileInputStream("src/main/resources/map/gui/woodenSword.png"));
        else image = new Image(new FileInputStream("src/main/resources/map/gui/whiteSword.png"));
    }
}
