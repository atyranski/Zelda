package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Potion implements IMapItem{
    private int x;
    private int y;
    private String key;
    private String name;
    private Image image;

    public Potion(int x, int y, String key, String name, int parameter) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.key = key;
        this.name = name;

        this.initializeImage();
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

    private void initializeImage() throws FileNotFoundException {
        if (name.equals("potionFull")) image = new Image(new FileInputStream("src/main/resources/map/gui/potion_full.png"));
        else image = new Image(new FileInputStream("src/main/resources/map/gui/potion.png"));
    }
}
