package game.actors;

import javafx.scene.image.Image;

public interface IMapElement {
    String key = null;

    public Image getImage();

    public String getKey();

    public int getX();

    public int getY();


}
