package game.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    final private int WINDOW_WIDTH = 1000;
    final private int WINDOW_HEIGHT = 600;

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

            VBox container = new VBox();
            container.setPadding(new Insets(10, 20, 10, 20));

            Scene scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }
}
