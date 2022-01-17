package game.world;

import java.util.ArrayList;

public class Area implements IMapElement{
    ArrayList<IMapElement> elements = new ArrayList<>();
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

    public ArrayList<IMapElement> getElements() {
        return elements;
    }
}
