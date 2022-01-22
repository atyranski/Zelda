package game.world;

import game.actors.*;
import game.gui.App;
import game.items.*;
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
    private HashMap<String, IMapItem> mapItems = new HashMap<>();

    private int width;
    private int heigh;
    private int currentDungeon;


    public WorldMap(int width, int height, App app) throws IOException {
        this.app = app;
        this.width = width;
        this.heigh = height;

        buildMap();
        buildMapObjects();
        buildItems();
        changeDungeon(0);
        app.updateObjects();
    }


    public void onKeyPress(KeyEvent key){
        switch (key.getCode()){
            case UP -> turnPlayer(Directions.UP);
            case DOWN -> turnPlayer(Directions.DOWN);
            case RIGHT -> turnPlayer(Directions.RIGHT);
            case LEFT -> turnPlayer(Directions.LEFT);
            case Z -> playerAttackStart();
            case C -> playerSwitchItem();
        };
        this.app.updateObjects();
        this.app.updateInterface();
//        System.out.println(mapElements.get("player").getImage());
    }

    public void onKeyUp(KeyEvent key){
        switch (key.getCode()){
            case Z -> playerAttackStop();
        };
    }

    public Dungeon getCurrentDungeon(){
        return dungeons[currentDungeon];
    }

    public HashMap<String, IMapElement> getCurrentMapElements(){
        return this.mapElements;
    }

    public HashMap<String, IMapItem> getMapItems() {
        return this.mapItems;
    }

    public Vector2D getWorldSize(){
        return new Vector2D(width, heigh);
    }

    public Player getPlayer(){
        Player player = (Player) mapElements.get("player");
        return player;
    }

    public void changeDungeon(int dungeonNumber){
        this.currentDungeon = dungeonNumber;
        Player player = null;

//        IMapElements
        if(mapElements.containsKey("player")) player = (Player) mapElements.get("player");

        ArrayList<IMapElement> actors = dungeons[currentDungeon].getActors();
        mapElements = new HashMap<>();

        for(IMapElement actor: actors){
            mapElements.put(actor.getKey(), actor);
        }

        if (player != null) mapElements.put("player", player);

//        IMapItems
        ArrayList<IMapItem> items = dungeons[currentDungeon].getItems();
        mapItems = new HashMap<>();

        for(IMapItem item: items){
            mapItems.put(item.getName(), item);
        }

        this.app.updateBackground();
    }

    private boolean turnPlayer(Directions direction){
        Player player = (Player) mapElements.get("player");
        String picked = "";

        if(player.getOrientation() == direction) {
            if(player.move(dungeons[currentDungeon])){
                for(String key: mapItems.keySet()){
                    IMapItem item = mapItems.get(key);
                    if(item.getPosition().equals(player.getPosition())){
                        switch (item.getKey()){
                            case "S":
                                player.addToEquipment(item);
                                break;
                            case "B":
                                player.addToEquipment(item);
                                break;
                            case "R":
                                if(item.getName().equals("greenRupee")) player.addgreenRupee();
                                else player.addBlueRupee();
                                break;
                            case "P":
                                if(item.getName().equals("potion")) player.heal(2);
                                else player.restoreFullHealth();
                                break;
                        }
                        picked = key;

                    }
                }

                if(!picked.equals("")) {
                    dungeons[currentDungeon].removeItem(mapItems.get(picked));
                    mapItems.remove(picked);
                }
            }

        } else player.turn(direction);

        return true;
    }

    public boolean canMoveTo(Vector2D position){
        Boolean result = true;
        for(String key: mapElements.keySet()){
            if(mapElements.get(key).getPosition().equals(position)) result = false;
        }

        return result;
    }

    private boolean playerAttackStart(){
        Player player = (Player) mapElements.get("player");

        player.attackStart();
        app.updateObjects();

        return true;
    }

    private boolean playerAttackStop(){
        Player player = (Player) mapElements.get("player");
        String killed = "";

        if(player.attackStop()){
            Vector2D hitPosition = switch (player.getOrientation()){
                case UP -> player.getPosition().add(new Vector2D(0, -1));
                case DOWN -> player.getPosition().add(new Vector2D(0, 1));
                case RIGHT -> player.getPosition().add(new Vector2D(1, 0));
                case LEFT -> player.getPosition().add(new Vector2D(-1, 0));
            };

            for(String key: mapElements.keySet()){
                IMapElement element = mapElements.get(key);
                if(element.getPosition().equals(hitPosition) && element.getClass().equals(Monster.class)){
                    Monster monster = (Monster) element;
                    monster.takeDamage(player.getStrength());

                    if(monster.getHealth() <= 0) killed = key;
                }
            }

            if (!killed.equals("")) {
                dungeons[currentDungeon].removeActor(mapElements.get(killed));
                mapElements.remove(killed);
            }
        }

        app.updateObjects();

        return true;
    }

    private boolean playerSwitchItem(){
        Player player = (Player) mapElements.get("player");
        player.takeNextItem();

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
//            System.out.println(line);
            String[] row = line.split(" ");

            String role = row[0];
            int x = Integer.parseInt(String.valueOf(row[1]));
            int y = Integer.parseInt(String.valueOf(row[2]));
            String key = row[3];
            int dungeonIndex = Integer.parseInt(String.valueOf(row[4]));

            IMapElement element = switch (role){
                case "P" -> new Player(x, y, this, key);
                case "C" -> new Merchant(x, y, key);
                case "M" -> new Monster(x, y,this, key);
                default -> throw new IllegalStateException("[WorldMap/buildMapObjects] Unexpected value: " + line);
            };

//            mapElements.put(key, element);
            dungeons[dungeonIndex].addActor(element);
            line = bf.readLine();
        }
        bf.close();

        return true;
    }

    private boolean buildItems() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/map/items.txt"));
        String line = bf.readLine();

        while (line != null){
            String[] row = line.split(" ");

            String key = row[0];
            String name = row[1];
            int x = Integer.parseInt(String.valueOf(row[2]));
            int y = Integer.parseInt(String.valueOf(row[3]));
            int dungeonIndex = Integer.parseInt(String.valueOf(row[4]));
            int parameter = Integer.parseInt(String.valueOf(row[5]));

            IMapItem element = switch (key){
                case "S" -> new Sword(x, y, key, name, parameter);
                case "B" -> new Bomb(x, y, key, name);
                case "P" -> new Potion(x, y, key, name, parameter);
                case "R" -> new Rupee(x, y, key, name, parameter);
                default -> throw new IllegalStateException("[WorldMap/buildItems] Unexpected value: " + line);
            };

            dungeons[dungeonIndex].addItem(element);
            line = bf.readLine();
        }
        bf.close();

        return true;
    }

}
