package game.items;

import game.actors.Player;

public class Equipment {
    private int greenRupee = 0;
    private int blueRupee = 0;
    private int potion = 0;
    private Sword sword = null;
    private boolean shield = false;
    private int bomb = 0;

    private Player player;

    public Equipment(Player player){
        this.player = player;
    }

//    Getters
    public int getGreenRupee() {
        return greenRupee;
    }

    public int getBlueRupee() {
        return blueRupee;
    }

    public int getPotion() {
        return potion;
    }

    public Sword getSword() {
        return sword;
    }

    public int getBomb() {
        return bomb;
    }

    public boolean isShield() {
        return shield;
    }

    //    Setters
    public void addGreenRupee(int amount) {
        this.greenRupee += amount;
    }

    public void addBlueRupee(int amount) {
        this.blueRupee += amount;
    }

    public void addPotion(int amount) {
        this.potion += amount;
    }

    public boolean setSword(Sword sword) {
        if(this.sword == null) this.sword = sword;
        else if(this.sword.getName().equals("woodenSword") && sword.getName().equals("whiteSword")) this.sword = sword;
        else if(this.sword.getName().equals("whiteSword") && sword.getName().equals("woodenSword")) return false;
        else if(this.sword.getName().equals("whiteSword") && sword.getName().equals("whiteSword")) return false;
        else throw new IllegalStateException("Unexpected value: " + sword.getName());

        this.player.setStrength(this.sword.getStrength());

        return true;
    }

    public void addBomb(int amount) {
        this.bomb += amount;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }
}