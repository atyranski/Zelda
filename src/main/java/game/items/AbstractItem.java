package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

public class AbstractItem implements IMapItem{
    protected int x;
    protected int y;
    protected String key;
    protected String name;
    protected Image image;

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

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
