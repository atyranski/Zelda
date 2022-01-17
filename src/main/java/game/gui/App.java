package game.gui;

import game.world.Area;
import game.world.WorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App extends Application {
//    Number of tiles in the proper dimension
    final private int MAP_WIDTH = 25;
    final private int MAP_HEIGHT = 15;
    final private int AREA_SIZE = 40;
//    Size of program window
    final private int WINDOW_WIDTH = MAP_WIDTH * AREA_SIZE;
    final private int WINDOW_HEIGHT = MAP_HEIGHT * AREA_SIZE;

    private WorldMap worldMap;
    private GridPane gp_map;
    private GridPane gp_mapElements;

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
            gp_map = new GridPane();
            gp_map.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            gp_map.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);

            gp_mapElements = new GridPane();
            gp_mapElements.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            gp_mapElements.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);

//            Create world map
            try {
                worldMap = new WorldMap(MAP_WIDTH, MAP_HEIGHT);
                generateObjects(gp_map, gp_mapElements, worldMap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            VBox container = new VBox(gp_map);

            Scene scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    public void generateObjects(GridPane gp, GridPane gp_e, WorldMap wp) throws FileNotFoundException {
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
}
