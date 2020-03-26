package com.codecool.java.roguelikegame.beings;

public class Player extends Being {

    private int level;

    public Player(int yPosition, int xPosition) {
        super.strength = 5;
        super.defence = 5;
        super.hp = 50;
        super.agility = 5;
        super.yPosition = yPosition;
        super.xPosition = xPosition;
        this.level = 0;
        super.icon = new Icon(level).getPlayerIcon();
    }

    public void levelUp() {
        super.strength += super.strength;
        super.defence += super.defence;
        super.hp += super.hp;
        super.agility += (int) super.agility / 4;
        super.yPosition = 3;
        super.xPosition = 3;
        Icon newIcon = new Icon(++level);
        super.icon = newIcon.getPlayerIcon();
    }

    @Override
    public String[][] getIcon() {
        return super.icon;
    }
}
