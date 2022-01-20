package game.world;

import game.actors.IMapElement;

import java.util.ArrayList;

public class Dungeon {
    private Area[][] dungeon;
    private int width;
    private int heigh;
    private int[] connections;
    private ArrayList<IMapElement> actors = new ArrayList<>();

    public Dungeon(int width, int height, String[] template, int[] connections){
        this.width = width;
        this.heigh = height;
        this.dungeon = new Area[width][height];
        this.connections = connections;

        generateDungeon(template);
    }

    private void generateDungeon(String[] template){
        for (int y=0; y<heigh; y++) {
            for(int x=0; x<width; x++){
                dungeon[x][y] = new Area(x, y, template[y].charAt(x));
            }
        }
//        for(int x=0; x<width; x++){
//            for(int y=0; y<heigh; y++){
//                System.out.print(template[x].charAt(y));
//            }
//            System.out.println("\n");
//        }
    }

    public Area[][] getAreas() {
        return dungeon;
    }

    public ArrayList<IMapElement> getActors() {
        return actors;
    }

    public void addActor(IMapElement actor) {
        this.actors.add(actor);
    }

    public int[] getConnections() {
        return connections;
    }
}
