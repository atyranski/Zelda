package game.gui;

import game.actors.Player;
import game.items.IMapItem;
import game.world.Area;
import game.actors.IMapElement;
import game.world.WorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class App extends Application {
//    Number of tiles in the proper dimension
    final private int MAP_WIDTH = 25;
    final private int MAP_HEIGHT = 15;
    final private int AREA_SIZE = 40;

//    Size of program window
    final private int WINDOW_WIDTH = MAP_WIDTH * AREA_SIZE;
    final private int WINDOW_HEIGHT = MAP_HEIGHT * AREA_SIZE;

    private StackPane sp_map;
    private WorldMap worldMap;
    private GridPane gp_mapBackground;
    private GridPane gp_mapElements;
    private GridPane gp_interface;
    private VBox vbox_mainScreen;
    private Scene scene;

//    private Renderer renderer;
//    private Thread thread;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop(){
        System.out.println("[App | stop] - Link says: ima goin home//");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(() -> {
            primaryStage.setTitle("The Legend of Zelda: Trying to pass the semester");
            primaryStage.getIcons().add(new Image("icon.png"));
            primaryStage.setResizable(false);

//            Create map grid
            gp_mapBackground = new GridPane();
            gp_mapBackground.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            gp_mapBackground.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);

            gp_mapElements = new GridPane();
            gp_mapElements.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            gp_mapElements.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            generateBoundaries(gp_mapElements);

//            Create interface grid
            gp_interface = new GridPane();
            gp_interface.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            gp_interface.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            generateInterfaceWireframe(gp_interface);

//            Create world map
            try {
                worldMap = new WorldMap(MAP_WIDTH, MAP_HEIGHT, this);
                generateMapBackground(gp_mapBackground, worldMap);
                generateMapElements(gp_mapElements, worldMap);
                generateInterface(gp_interface, worldMap);

            } catch (IOException e) {
                e.printStackTrace();
            }

//            Create main screen
            Button bt_start = new Button("Start");
            bt_start.setMinSize(160, 60);
            bt_start.setMaxSize(160, 60);
            bt_start.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            bt_start.setOnAction(actionEvent ->  {
                this.sp_map.getChildren().remove(vbox_mainScreen);

//                this.scene.addEventHandler(KeyEvent.ANY, (key) -> worldMap.onKeyPress(key));

                this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
//                    System.out.println("[App | start] - pressed " + key.getCode());
                    worldMap.onKeyPress(key);
                    updateObjects();
                });
                this.scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
//                    System.out.println("[App | start] - released " + key.getCode());
                    worldMap.onKeyUp(key);
                });
            });

            ImageView imageView = new ImageView(new Image("mainScreenLogo.png"));

            vbox_mainScreen = new VBox(imageView, bt_start);
            vbox_mainScreen.setMargin(bt_start, new Insets(20));
            vbox_mainScreen.setStyle("-fx-background-color: white;");
            vbox_mainScreen.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            vbox_mainScreen.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            vbox_mainScreen.setAlignment(Pos.CENTER);

//            Create stack pane
            sp_map = new StackPane();
            sp_map.getChildren().add(gp_mapBackground);
            sp_map.getChildren().add(gp_mapElements);
            sp_map.getChildren().add(gp_interface);
            sp_map.getChildren().add(vbox_mainScreen);

//            Create main container
            VBox container = new VBox(sp_map);

//            Create scene
            scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> {
//                if(renderer != null) renderer.setIsOpened(false);
            });
        });
    }

    private void generateBoundaries(GridPane gp){
        Label boundary;
        boundary = new Label("");
        boundary.setMinSize(0, 0);
        boundary.setMaxSize(0, 0);
        boundary.setStyle("-fx-background-color: red;");
        gp.add(boundary, 0, 0);

        for (int i=1; i<MAP_WIDTH+1; i++){
            boundary = new Label("");
            boundary.setMinSize(AREA_SIZE, 0);
            boundary.setMaxSize(AREA_SIZE, 0);
            boundary.setStyle("-fx-background-color: red;");
            gp.add(boundary, i, 0);
        }

        for (int i=1; i<MAP_HEIGHT+1; i++){
            boundary = new Label("");
            boundary.setMinSize(0, AREA_SIZE);
            boundary.setMaxSize(0, AREA_SIZE);
            boundary.setStyle("-fx-background-color: red;");
            gp.add(boundary, 0, i);
        }
    }

    public void generateMapBackground(GridPane gp, WorldMap wp) throws FileNotFoundException {
        Area[][] areas = wp.getCurrentDungeon().getAreas();
        Image image;

        for(int y=0; y<MAP_HEIGHT; y++ ){
            for(int x=0; x<MAP_WIDTH; x++){
//                System.out.println(areas[x][y].getTileType());

                image = switch (areas[x][y].getTileType()){
//                    Basic tiles
                    case 'G' -> new Image(new FileInputStream("src/main/resources/map/tiles/grass_tile.png"));
                    case 'P' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_tile.png"));
                    case 'W' -> new Image(new FileInputStream("src/main/resources/map/tiles/water.png"));
                    case 'T' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_tile.png"));
                    case 'S' -> new Image(new FileInputStream("src/main/resources/map/tiles/stone_tile.png"));
                    case 'D' -> new Image(new FileInputStream("src/main/resources/map/tiles/dungeon_brick.png"));
//                    Path & grass corners
                    case 'a' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_corner_grass_tile_1_a.png"));
                    case 'b' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_corner_grass_tile_2_b.png"));
                    case 'c' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_corner_grass_tile_3_c.png"));
                    case 'd' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_corner_grass_tile_4_d.png"));
//                    Path & grass cornerbends
                    case 'e' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_cornerBend_grass_tile_1_e.png"));
                    case 'f' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_cornerBend_grass_tile_2_f.png"));
                    case 'g' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_cornerBend_grass_tile_3_g.png"));
                    case 'h' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_cornerBend_grass_tile_4_h.png"));
//                    Path & grass sides
                    case 'i' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_side_grass_tile_1_i.png"));
                    case 'j' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_side_grass_tile_2_j.png"));
                    case 'k' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_side_grass_tile_3_k.png"));
                    case 'l' -> new Image(new FileInputStream("src/main/resources/map/tiles/path_side_grass_tile_4_l.png"));
//                    Water & grass corners
                    case 'm' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_corner_grass_tile_1_m.png"));
                    case 'n' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_corner_grass_tile_2_n.png"));
                    case 'o' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_corner_grass_tile_3_o.png"));
                    case 'p' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_corner_grass_tile_4_p.png"));
//                    Water & grass cornerbends
                    case 'r' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_cornerBend_grass_tile_1_r.png"));
                    case 's' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_cornerBend_grass_tile_2_s.png"));
                    case 't' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_cornerBend_grass_tile_3_t.png"));
                    case 'u' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_cornerBend_grass_tile_4_u.png"));
//                    Water & grass sides
                    case 'v' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_side_grass_tile_1_v.png"));
                    case 'w' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_side_grass_tile_2_w.png"));
                    case 'x' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_side_grass_tile_3_x.png"));
                    case 'y' -> new Image(new FileInputStream("src/main/resources/map/tiles/water_side_grass_tile_4_y.png"));
//                    Bush grass
                    case 'z' -> new Image(new FileInputStream("src/main/resources/map/tiles/bush_grass_tile.png"));
                    default -> throw new IllegalStateException("Unexpected value: " + areas[x][y].getTileType());
                };

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(AREA_SIZE);
                imageView.setFitHeight(AREA_SIZE);

                gp.add(imageView, x, y);
            }
        }
    }

    public void generateMapElements(GridPane gp, WorldMap wp) throws FileNotFoundException{
        HashMap<String, IMapElement> mapElements = wp.getCurrentMapElements();

        for(String key: mapElements.keySet()){
            ImageView actor = new ImageView(mapElements.get(key).getImage());

            if(key.equals("player")){
                Player player = (Player) mapElements.get(key);

                if(player.isAttacking()) {
                    switch (player.getOrientation()){
                        case UP:
                            actor.setFitWidth(AREA_SIZE);
                            actor.setFitHeight(2*AREA_SIZE);
                            gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY(), 1, 2);
                            break;

                        case DOWN:
                            actor.setFitWidth(AREA_SIZE);
                            actor.setFitHeight(2*AREA_SIZE);
                            gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY()+1, 1, 2);
                            break;

                        case LEFT:
                            actor.setFitWidth(2*AREA_SIZE);
                            actor.setFitHeight(AREA_SIZE);
                            gp.add(actor, mapElements.get(key).getX(), mapElements.get(key).getY()+1, 2, 1);
                            break;

                        case RIGHT:
                            actor.setFitWidth(2*AREA_SIZE);
                            actor.setFitHeight(AREA_SIZE);
                            gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY()+1, 2, 1);
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + key);
                    }


                } else {
                    actor.setFitWidth(AREA_SIZE);
                    actor.setFitHeight(AREA_SIZE);
                    gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY()+1);

                }
            } else {
                actor.setFitWidth(AREA_SIZE);
                actor.setFitHeight(AREA_SIZE);
                gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY()+1);

            }


        }

        HashMap<String, IMapItem> mapItems = wp.getMapItems();

        for(String key: mapItems.keySet()){
            ImageView item = new ImageView(mapItems.get(key).getImage());

            item.setFitWidth(AREA_SIZE);
            item.setFitHeight(AREA_SIZE);
            gp.add(item, mapItems.get(key).getX()+1, mapItems.get(key).getY()+1);
        }
    }

    public void generateInterface(GridPane gp, WorldMap wp) throws FileNotFoundException{
//        Create Healthbar
        Player player = wp.getPlayer();
        ArrayList<ImageView> listHealth = new ArrayList<>();
        int health = player.getHealth();

        for(int i=0; i< (player.getMaxHealt()/2); i++) {
            ImageView empty = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/heart_empty.png")));
            empty.setFitHeight(AREA_SIZE);
            empty.setFitWidth(AREA_SIZE);
            listHealth.add(empty);
        }

        if (player.getHealth() % 2 == 1){
            ImageView half = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/heart_half.png")));
            half.setFitHeight(AREA_SIZE);
            half.setFitWidth(AREA_SIZE);
            listHealth.add(half);
            health -= 1;
        }

        for (int i=0; i< (health/2); i++) {
            ImageView full = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/heart_full.png")));
            full.setFitHeight(AREA_SIZE);
            full.setFitWidth(AREA_SIZE);
            listHealth.add(full);
        }

        int n = listHealth.size();

        HBox hbox_healthBar = new HBox();
        if(player.getMaxHealt() == 10) hbox_healthBar = new HBox(listHealth.get(n-1), listHealth.get(n-2), listHealth.get(n-3), listHealth.get(n-4), listHealth.get(n-5));
        else if (player.getMaxHealt() == 12) hbox_healthBar = new HBox(listHealth.get(n-1), listHealth.get(n-2), listHealth.get(n-3), listHealth.get(n-4), listHealth.get(n-5), listHealth.get(n-6));

        hbox_healthBar.setStyle("-fx-background-color: #171717;");
        gp.add(hbox_healthBar,4,2 );

//        Create EQ
        ImageView iv_greenRupee = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/green_rupee.png")));
        ImageView iv_blueRupee = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/blue_rupee.png")));
        ArrayList<ImageView> listItems = new ArrayList<>();

        for(int i=0; i< 3; i++) {
            ImageView empty = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/inventory_empty.png")));
            empty.setFitHeight(2*AREA_SIZE);
            empty.setFitWidth(2*AREA_SIZE);
            listItems.add(empty);
        }

        for (int i=0; i< player.getEquipment().size(); i++) {
            ImageView item = new ImageView(new Image(new FileInputStream("src/main/resources/map/gui/inventory_" + player.getEquipment().get(i).getName() + ".png")));
            item.setFitHeight(2*AREA_SIZE);
            item.setFitWidth(2*AREA_SIZE);
            listItems.add(item);
        }

        iv_greenRupee.setFitWidth(AREA_SIZE);
        iv_greenRupee.setFitHeight(AREA_SIZE);
        iv_blueRupee.setFitWidth(AREA_SIZE);
        iv_blueRupee.setFitHeight(AREA_SIZE);

        Label l_greenAmount = new Label(String.valueOf(player.getGreenRupee()));
        Label l_blueAmount = new Label(String.valueOf(player.getBlueRupee()));

        l_greenAmount.setMinSize(35,26);
        l_greenAmount.setMaxSize(35,26);
        l_blueAmount.setMinSize(35,26);
        l_blueAmount.setMaxSize(35,26);

        l_greenAmount.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        l_blueAmount.setFont(Font.font("Verdana", FontWeight.BOLD, 22));

        l_greenAmount.setTextFill(Color.color(0.92,0.92,0.92));
        l_blueAmount.setTextFill(Color.color(0.92,0.92,0.92));

        HBox hbox_greenRupee = new HBox(iv_greenRupee, l_greenAmount);
        HBox hbox_blueRupee = new HBox(iv_blueRupee, l_blueAmount);

        hbox_greenRupee.setMargin(l_greenAmount, new Insets(5, 5, 0, 0));
        hbox_blueRupee.setMargin(l_blueAmount, new Insets(5, 5, 0, 0));

        int m = listItems.size();
        HBox hBox_items = new HBox(listItems.get(m-1), listItems.get(m-2), listItems.get(m-3));
        VBox vbox_credits = new VBox(hbox_greenRupee, hbox_blueRupee);


        HBox hbox_equipment = new HBox(vbox_credits,hBox_items);
        hbox_equipment.setStyle("-fx-background-color: #171717;");
        gp.add(hbox_equipment,1,4 );
    }

    private void generateInterfaceWireframe(GridPane gp){
//        center top left
        Label boundary = new Label("");
        boundary.setMinSize(0, 0);
        boundary.setMaxSize(0, 0);
        boundary.setStyle("-fx-background-color: red;");
        gp.add(boundary, 0, 0);

        int[] widthParts = new int[]{ 8*AREA_SIZE, AREA_SIZE, 9*AREA_SIZE, 6*AREA_SIZE, AREA_SIZE };
        int[] heightParts = new int[]{ AREA_SIZE, AREA_SIZE, 11*AREA_SIZE, 2*AREA_SIZE};

//        System.out.println(Arrays.toString(widthParts));
//        System.out.println(Arrays.toString(heightParts));

        for (int j=0; j<4; j++){
            for(int i=0; i<widthParts.length; i++){
                boundary = new Label("");
                boundary.setMinSize(widthParts[i], 0);
                boundary.setMaxSize(widthParts[i], 0);
                boundary.setStyle("-fx-background-color: red;");
                gp.add(boundary, i+1, 0);
            }
        }

        for (int j=0; j<4; j++){
            for(int i=0; i<heightParts.length; i++){
                boundary = new Label("");
                boundary.setMinSize(0, heightParts[i]);
                boundary.setMaxSize(0, heightParts[i]);
                boundary.setStyle("-fx-background-color: red;");
                gp.add(boundary, 0, i+1);
            }
        }

    }

    public void updateBackground(){
        Platform.runLater(() -> {
            gp_mapBackground.getChildren().clear();
            try {
                generateMapBackground(gp_mapBackground, worldMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateObjects(){
        Platform.runLater(() -> {
            gp_mapElements.getChildren().clear();
            generateBoundaries(gp_mapElements);
            try {
                generateMapElements(gp_mapElements, worldMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateInterface(){
        Platform.runLater(() -> {
            gp_interface.getChildren().clear();
            generateInterfaceWireframe(gp_interface);
            try {
                generateInterface(gp_interface, worldMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


}
