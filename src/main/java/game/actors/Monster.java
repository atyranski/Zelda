package game.actors;

import game.utils.Directions;
import game.utils.Vector2D;
import game.world.WorldMap;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Monster implements IMapElement{
    private Image image;
    private String key;
    private Directions orientation;
    private HashMap<Directions, Image> orientationImages = new HashMap<>();
    private int x;
    private int y;
    private int monsterType;
    private int health;
    private WorldMap worldMap;

    public Monster(int x, int y, WorldMap worldMap, String key) throws FileNotFoundException {
        this.identifyMonster(key);
        this.initializeImages();
        this.orientation = Directions.DOWN;
        this.image = orientationImages.get(this.orientation);
        this.key = key;
        this.worldMap = worldMap;
        this.x = x;
        this.y = y;
    }

//    Getters
    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(x, y);
    }


//    Setters
    public void takeDamage(int damage) {
        this.health -= damage;
    }


//    Initialize methods
    private void identifyMonster(String key){
        this.monsterType = Integer.parseInt(key.substring(key.length()-1));

        switch (monsterType) {
            case 1:
                this.health = 8;
                break;
            case 2:
                this.health = 10;
                break;
            case 3:
                this.health = 12;
                break;
            case 4:
                this.health = 14;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    private void initializeImages() throws FileNotFoundException {
        this.orientationImages.put(Directions.UP, new Image(new FileInputStream("src/main/resources/map/monsters/monster"+ monsterType +"_back.png")));
        this.orientationImages.put(Directions.DOWN, new Image(new FileInputStream("src/main/resources/map/monsters/monster"+ monsterType +".png")));
        this.orientationImages.put(Directions.RIGHT, new Image(new FileInputStream("src/main/resources/map/monsters/monster"+ monsterType +"_right.png")));
        this.orientationImages.put(Directions.LEFT, new Image(new FileInputStream("src/main/resources/map/monsters/monster"+ monsterType +"_left.png")));
    }
}
