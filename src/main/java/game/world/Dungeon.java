package game.world;

import java.util.Arrays;

public class Dungeon {
    private Area[][] dungeon;
    private int width;
    private int heigh;
    private int[] connections = new int[4];

    public Dungeon(int width, int height, String[] template, int[] connections){
        this.width = width;
        this.heigh = height;
        this.dungeon = new Area[width][height];
        this.connections = connections;
        System.out.println(Arrays.toString(connections));

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

    public int[] getConnections() {
        return connections;
    }
}
