package game.world;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorldMap {
    final private int DUNGEONS_AMOUNT = 10;

    private Dungeon[] dungeons = new Dungeon[DUNGEONS_AMOUNT];
    private int width;
    private int heigh;
    private int currentDungeon = 0;

    public WorldMap(int width, int height) throws IOException {
        this.width = width;
        this.heigh = height;

        buildMap();
    }

//    public boolean addArea(Area area) {
//        if(area.getX() > 9 || area.getX() < 0 || area.getY() > 9 || area.getY() < 0)
//            return false;
//
//        this.map[area.getX()][area.getY()] = area;
//        return true;
//    }

    public boolean buildMap() throws IOException {
        List<String> templates = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/map/templates.txt"));
        String line = bf.readLine();
        int i = 0;

        while (line != null){
            if(line.charAt(0) == '.') {
                String[] template = templates.toArray(new String[0]);
                templates = new ArrayList<>();
                dungeons[i] = new Dungeon(width, heigh, template);
                i++;
            } else templates.add(line);

            line = bf.readLine();
        }
        bf.close();

        return true;
    }

    public Dungeon getCurrentDungeon(){
        return dungeons[currentDungeon];
    }
}
