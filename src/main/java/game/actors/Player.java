package game.actors;

import game.world.Directions;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Player implements IMapElement{
    private Image image;
    private Directions orientation;
    private HashMap<Directions, Image> orientationImages = new HashMap<>();
    private int x;
    private int y;

    public Player(int x, int y) throws FileNotFoundException {
        this.initializeImages();
        this.orientation = Directions.DOWN;
        this.image = orientationImages.get(this.orientation);
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

    private void initializeImages() throws FileNotFoundException {
        this.orientationImages.put(Directions.UP, new Image(new FileInputStream("src/main/resources/map/player/player_back.png")));
        this.orientationImages.put(Directions.DOWN, new Image(new FileInputStream("src/main/resources/map/player/player.png")));
        this.orientationImages.put(Directions.RIGHT, new Image(new FileInputStream("src/main/resources/map/player/player_right.png")));
        this.orientationImages.put(Directions.LEFT, new Image(new FileInputStream("src/main/resources/map/player/player_left.png")));
    }

    public void updateOrientation(Directions direction){
        this.image = this.orientationImages.get(direction);
    }
}
