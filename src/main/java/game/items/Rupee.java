package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Rupee implements IMapItem{
    private int x;
    private int y;
    private String key;
    private String name;
    private Image image;

    public Rupee(int x, int y, String key, String name, int parameter) throws FileNotFoundException {
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
        if (name.equals("blueRupee")) image = new Image(new FileInputStream("src/main/resources/map/gui/blue_rupee.png"));
        else image = new Image(new FileInputStream("src/main/resources/map/gui/green_rupee.png"));
    }
}
