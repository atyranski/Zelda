package game.items;

import game.utils.Vector2D;
import javafx.scene.image.Image;

public interface IMapItem {

    public Image getImage();

    public int getX();

    public int getY();

    public String getKey();

    public String getName();

    public Vector2D getPosition();

}
