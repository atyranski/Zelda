package game.world;

public class WorldMap {
    private Area[][] map;
    private int width;
    private int heigh;

    public WorldMap(int width, int height){
        this.width = width;
        this.heigh = height;
        this.map = new Area[width][height];
    }

    public boolean addArea(Area area) {
        if(area.getX() > 9 || area.getX() < 0 || area.getY() > 9 || area.getY() < 0)
            return false;

        this.map[area.getX()][area.getY()] = area;
        return true;
    }

    public boolean buildMap(){
        for(int i=0; i<this.width; i++){
            for(int j=0; j<this.heigh; j++){

            }
        }

        return true;
    }
}
