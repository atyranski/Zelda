package game.world;

import game.actors.IMapElement;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Area {
    private int x;
    private int y;
    private char tileType;

    public Area(int x, int y, char tileType){
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getTileType() {
        return tileType;
    }

}
