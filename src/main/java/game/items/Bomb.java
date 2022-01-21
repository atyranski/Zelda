package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bomb implements IMapItem{
    private int x;
    private int y;
    private String key;
    private String name;
    private Image image;

    public Bomb(int x, int y, String key, String name) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;
        this.image = new Image(new FileInputStream("src/main/resources/map/gui/bomb.png"));
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(x,y);
    }

}
