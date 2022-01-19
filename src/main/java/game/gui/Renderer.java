package game.gui;

import game.world.WorldMap;
import javafx.application.Application;

public class Renderer implements Runnable {
    private final int REFRESH_TIME = 200;

    private WorldMap worldMap;
    private Application app;

    private boolean isRunning = false;
    private boolean isOpened = true;

    public Renderer(WorldMap worldMap, Application app){
        this.worldMap = worldMap;
        this.app = app;
    }

    @Override
    public void run() {
        if(this.isOpened){
            if(this.isRunning){
                System.out.println("Biegnę");

                try {
                    Thread.sleep(REFRESH_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                run();

            }
        }
    }

    public void addObserver(App application) {
        this.app = application;
    }

    public void setIsRunning(boolean state){
        this.isRunning = state;
    }

    public void setIsOpened(boolean state){
        this.isOpened = state;
    }
}
