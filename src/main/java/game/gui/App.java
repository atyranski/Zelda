package game.gui;

import game.actors.Player;
import game.world.Area;
import game.actors.IMapElement;
import game.world.WorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private VBox vbox_mainScreen;

    @Override
    public void init() throws Exception {
        super.init();
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

//            ---------------TEMP-------------------
//            ImageView iv = null;
//            try {
//                iv = new ImageView(new Image(new FileInputStream("src/main/resources/map/characters/old_man.png")));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            };
//            iv.setFitWidth(AREA_SIZE);
//            iv.setFitHeight(AREA_SIZE);
//            gp_mapElements.add(iv, 13,12);
//            ---------------TEMP-------------------

//            Create world map
            try {
                worldMap = new WorldMap(MAP_WIDTH, MAP_HEIGHT);
                generateMapBackground(gp_mapBackground, worldMap);
                generateMapElements(gp_mapElements, worldMap);

            } catch (IOException e) {
                e.printStackTrace();
            }

//            Create main screen
            ImageView imageView = new ImageView(new Image("mainScreenLogo.png"));
            vbox_mainScreen = new VBox(imageView);
            vbox_mainScreen.setStyle("-fx-background-color: white;");
            vbox_mainScreen.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            vbox_mainScreen.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            vbox_mainScreen.setAlignment(Pos.CENTER);

//            Create stack pane
            sp_map = new StackPane();
            sp_map.getChildren().add(gp_mapBackground);
            sp_map.getChildren().add(gp_mapElements);
            sp_map.getChildren().add(vbox_mainScreen);

//            Create main container
            VBox container = new VBox(sp_map);

            Scene scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);
//            scene.addEventHandler(KeyEvent.ANY, (key) -> worldMap.onKeyPress(key));
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                System.out.println("[App] - pressed " + key.getCode());
            });
            scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
                System.out.println("[App] - released " + key.getCode());
            });

            primaryStage.setScene(scene);
            primaryStage.show();
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
            System.out.println(mapElements.get(key));

            ImageView actor = new ImageView(mapElements.get(key).getImage());
            actor.setFitWidth(AREA_SIZE);
            actor.setFitHeight(AREA_SIZE);

            gp.add(actor, mapElements.get(key).getX()+1, mapElements.get(key).getY()+1);
        }
    }

}
