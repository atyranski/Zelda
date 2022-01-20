package game.actors;

import game.utils.Directions;
import game.utils.Vector2D;
import game.world.Dungeon;
import game.world.WorldMap;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Player implements IMapElement{
    private Image image;
    private String key;
    private Directions orientation;
    private HashMap<Directions, Image> orientationImages = new HashMap<>();
    private int x;
    private int y;
    private WorldMap worldMap;
    private int maxHealt = 10;
    private int health = maxHealt;

    public Player(int x, int y, WorldMap worldMap, String key) throws FileNotFoundException {
        this.initializeImages();
        this.orientation = Directions.DOWN;
        this.image = orientationImages.get(this.orientation);
        this.key = key;
        this.worldMap = worldMap;
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

    public Directions getOrientation() {
        return orientation;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealt() {
        return maxHealt;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealt(int maxHealt) {
        this.maxHealt = maxHealt;
    }

    private void initializeImages() throws FileNotFoundException {
        this.orientationImages.put(Directions.UP, new Image(new FileInputStream("src/main/resources/map/player/player_back.png")));
        this.orientationImages.put(Directions.DOWN, new Image(new FileInputStream("src/main/resources/map/player/player.png")));
        this.orientationImages.put(Directions.RIGHT, new Image(new FileInputStream("src/main/resources/map/player/player_right.png")));
        this.orientationImages.put(Directions.LEFT, new Image(new FileInputStream("src/main/resources/map/player/player_left.png")));
    }

    public void turn(Directions direction){
        this.orientation = direction;
        this.image = this.orientationImages.get(orientation);
    }

    public boolean move(Dungeon dungeon){
        Vector2D shift = switch (orientation){
            case UP -> new Vector2D(0,-1);
            case DOWN -> new Vector2D(0,1);
            case RIGHT -> new Vector2D(1,0);
            case LEFT -> new Vector2D(-1,0);
        };

        Vector2D worldSize = worldMap.getWorldSize();
        if(x + shift.getX() < 0){
//            [3]
            worldMap.changeDungeon(dungeon.getConnections()[3]);
            x = worldSize.getX()-1;
            y = (int) Math.floor(worldSize.getY()/2);
            return true;

        } else if(x + shift.getX() >= worldSize.getX()){
//            [1]
            worldMap.changeDungeon(dungeon.getConnections()[1]);
            x = 0;
            y = (int) Math.floor(worldSize.getY()/2);
            return true;

        } else if(y + shift.getY() < 0){
//            [0]
            worldMap.changeDungeon(dungeon.getConnections()[0]);
            x = (int) Math.floor(worldSize.getX()/2);
            y = worldSize.getY()-1;
            return true;

        } else if(y + shift.getY() >= worldSize.getY()){
//            [2]
            worldMap.changeDungeon(dungeon.getConnections()[2]);
            x = (int) Math.floor(worldSize.getX()/2);
            y = 0;
            return true;

        }

        if(dungeon.getAreas()[x + shift.getX()][y + shift.getY()].getTileType() == 'P'){
            x += shift.getX();
            y += shift.getY();
            return true;
        }

        return false;
    }
}
