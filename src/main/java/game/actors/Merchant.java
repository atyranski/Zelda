package game.actors;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Merchant implements IMapElement {
    private Image image;
    private String key;
    private int x;
    private int y;

    public Merchant(int x, int y, String key) throws FileNotFoundException {
        this.image = new Image(new FileInputStream("src/main/resources/map/characters/old_man.png"));
        this.key = key;
        this.x = x;
        this.y = y;
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
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
