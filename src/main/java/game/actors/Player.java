package game.actors;

import game.items.IMapItem;
import game.items.Sword;
import game.utils.Directions;
import game.utils.Vector2D;
import game.world.Dungeon;
import game.world.WorldMap;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

public class Player implements IMapElement{
    private Image image;
    private String key;
    private Directions orientation;
    private HashMap<Directions, Image> orientationImages = new HashMap<>();
    private HashMap<Directions, Image> attackImages = new HashMap<>();
    private int x;
    private int y;
    private WorldMap worldMap;
    private int maxHealt = 12;
    private int health = 3;
    private int strength = 0;
    private ArrayList<IMapItem> equipment = new ArrayList<>();
    private int eqFreeSlot = 0;
    private int currentItem = 0;
    private int greenRupee = 0;
    private int blueRupee = 0;
    private boolean isAttacking = false;

    public Player(int x, int y, WorldMap worldMap, String key) throws FileNotFoundException {
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
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public Vector2D getPosition(){
        return new Vector2D(x, y);
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

    public int getStrength() {
        return strength;
    }

    public boolean isAttacking(){
        return this.isAttacking;
    }

    public int getGreenRupee() {
        return greenRupee;
    }

    public int getBlueRupee() {
        return blueRupee;
    }

    public ArrayList<IMapItem> getEquipment() {
        return equipment;
    }

    public int getCurrentItem() {
        return currentItem;
    }

//    Setters
    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealt(int maxHealt) {
        this.maxHealt = maxHealt;
    }


//    Initialize methods
    private void initializeImages() throws FileNotFoundException {
    this.orientationImages.put(Directions.UP, new Image(new FileInputStream("src/main/resources/map/player/player_back.png")));
    this.orientationImages.put(Directions.DOWN, new Image(new FileInputStream("src/main/resources/map/player/player.png")));
    this.orientationImages.put(Directions.RIGHT, new Image(new FileInputStream("src/main/resources/map/player/player_right.png")));
    this.orientationImages.put(Directions.LEFT, new Image(new FileInputStream("src/main/resources/map/player/player_left.png")));

    this.attackImages.put(Directions.UP, new Image(new FileInputStream("src/main/resources/map/player/player_attack_back.png")));
    this.attackImages.put(Directions.DOWN, new Image(new FileInputStream("src/main/resources/map/player/player_attack.png")));
    this.attackImages.put(Directions.RIGHT, new Image(new FileInputStream("src/main/resources/map/player/player_attack_right.png")));
    this.attackImages.put(Directions.LEFT, new Image(new FileInputStream("src/main/resources/map/player/player_attack_left.png")));
}


//    Players actions
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

        if (!worldMap.canMoveTo(this.getPosition().add(shift))) return false;

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

    public void attackStart(){
//        System.out.println("[Player | attackStart] - Attack Start");
        if(strength > 0){
            this.image = this.attackImages.get(orientation);
            this.isAttacking = true;
        }
    }

    public boolean attackStop(){
//        System.out.println("[Player | attackStop] - Attack Stop");

        if(strength > 0){
            this.image = this.orientationImages.get(orientation);
            this.isAttacking = false;
            return true;
        }

        return false;
    }

    public void addToEquipment(IMapItem item) {
        if(this.equipment.size() > 0) this.currentItem = Math.min(this.currentItem + 1, 2);
        this.equipment.add(0, item);
        this.activateCurrentItem();
    }

    public void takeNextItem(){
        this.currentItem = switch (this.currentItem){
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + this.currentItem);
        };

        this.activateCurrentItem();
    }

    private void activateCurrentItem(){
        if(currentItem < equipment.size()){
            switch (equipment.get(currentItem).getKey()){
                case "S":
                    Sword sword = (Sword) equipment.get(currentItem);
                    this.strength = sword.getStrength();
                    break;
                case "B":
                    this.strength = 0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + equipment.get(currentItem).getKey());
            }
        } else this.strength = 0;
    }

    public void addgreenRupee() {
        this.greenRupee += 1;
    }

    public void addBlueRupee() {
        this.blueRupee += 1;
    }

    public void restoreFullHealth(){
        this.health = this.maxHealt;
    }

    public void heal(int amount){
        this.health = Math.min(this.maxHealt, this.health + amount);
    }
}
