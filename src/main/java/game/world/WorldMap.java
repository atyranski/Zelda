package game.world;

import game.actors.IMapElement;
import game.actors.Merchant;
import game.actors.Player;
import game.gui.App;
import game.utils.Directions;
import game.utils.Vector2D;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WorldMap {
    final private int DUNGEONS_AMOUNT = 10;

    private App app;

    private Dungeon[] dungeons = new Dungeon[DUNGEONS_AMOUNT];
//    private ArrayList<IMapElement> mapElements = new ArrayList<>();
    private HashMap<String, IMapElement> mapElements = new HashMap<>();

    private int width;
    private int heigh;
    private int currentDungeon = 0;


    public WorldMap(int width, int height, App app) throws IOException {
        this.app = app;
        this.width = width;
        this.heigh = height;

        buildMap();
        buildMapObjects();
    }

    public void onKeyPress(KeyEvent key){
        switch (key.getCode()){
            case UP -> turnPlayer(Directions.UP);
            case DOWN -> turnPlayer(Directions.DOWN);
            case RIGHT -> turnPlayer(Directions.RIGHT);
            case LEFT -> turnPlayer(Directions.LEFT);
        }
        this.app.updateObjects();
//        System.out.println(mapElements.get("player").getImage());
    }

    public Dungeon getCurrentDungeon(){
        return dungeons[currentDungeon];
    }

    public HashMap<String, IMapElement> getCurrentMapElements(){
        return this.mapElements;
    }

    public Vector2D getWorldSize(){
        return new Vector2D(width, heigh);
    }

    public void changeDungeon(int dungeonNumber){
        this.currentDungeon = dungeonNumber;
        this.app.updateBackground();
    }

    private boolean turnPlayer(Directions direction){
        Player player = (Player) mapElements.get("player");

        if(player.getOrientation() == direction) player.move(dungeons[currentDungeon]);
        else player.turn(direction);

        return true;
    }

    private boolean buildMap() throws IOException {
        List<String> templates = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/map/templates.txt"));
        String line = bf.readLine();
        int i = 0;

        while (line != null){
            if(line.charAt(0) == '.') {
                String[] template = templates.toArray(new String[0]);
                templates = new ArrayList<>();
                line = line.substring(3);
                int[] connections = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                dungeons[i] = new Dungeon(width, heigh, template,connections);
                i++;
            } else templates.add(line);

            line = bf.readLine();
        }
        bf.close();

        return true;
    }

    private boolean buildMapObjects() throws  IOException{
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
                case "P" -> new Player(x, y, this);
                case "M" -> new Merchant(x, y);
                default -> throw new IllegalStateException("[WorldMap/buildMapObjects] Unexpected value: " + line.charAt(0));
            };

            mapElements.put(key, element);
            line = bf.readLine();
        }
        bf.close();

        return true;
    }
}
