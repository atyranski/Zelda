package game.world;

import game.actors.IMapElement;
import game.actors.Merchant;
import game.actors.Player;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldMap {
    final private int DUNGEONS_AMOUNT = 10;

    private Dungeon[] dungeons = new Dungeon[DUNGEONS_AMOUNT];
//    private ArrayList<IMapElement> mapElements = new ArrayList<>();
    private HashMap<String, IMapElement> mapElements = new HashMap<>();

    private int width;
    private int heigh;
    private int currentDungeon = 0;


    public WorldMap(int width, int height) throws IOException {
        this.width = width;
        this.heigh = height;

        buildMap();
        buildMapObjects();
    }

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

    public boolean buildMapObjects() throws  IOException{
        BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/map/mapElements.txt"));
        String line = bf.readLine();

        while (line != null){
            System.out.println(line);
            String[] row = line.split(" ");

            String role = row[0];
            int x = Integer.parseInt(String.valueOf(row[1]));
            int y = Integer.parseInt(String.valueOf(row[2]));
            String key = row[3];

            IMapElement element = switch (role){
                case "P" -> new Player(x, y);
                case "M" -> new Merchant(x, y);
                default -> throw new IllegalStateException("[WorldMap/buildMapObjects] Unexpected value: " + line.charAt(0));
            };

            mapElements.put(key, element);
            line = bf.readLine();
        }
        bf.close();

        return true;
    }

    public void onKeyPress(KeyEvent key){
        switch (key.getCode()){
            case UP -> movePlayer(Directions.UP);
            case DOWN -> movePlayer(Directions.DOWN);
            case RIGHT -> movePlayer(Directions.RIGHT);
            case LEFT -> movePlayer(Directions.LEFT);
        }
        System.out.println(mapElements.get("player").getImage());
    }

    public Dungeon getCurrentDungeon(){
        return dungeons[currentDungeon];
    }

    public HashMap<String, IMapElement> getCurrentMapElements(){
        return this.mapElements;
    }

    private boolean movePlayer(Directions direction){
        Player player = (Player) mapElements.get("player");
        player.updateOrientation(direction);
        return true;
    }
}
