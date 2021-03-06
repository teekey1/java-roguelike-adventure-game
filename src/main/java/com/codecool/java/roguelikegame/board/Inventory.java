package com.codecool.java.roguelikegame.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.codecool.java.roguelikegame.beings.Being;
import com.codecool.java.roguelikegame.beings.Icon;
import com.codecool.java.roguelikegame.beings.Item;

import helpers.UI;

public class Inventory {

    Scanner input = new Scanner(System.in);
    List<Item> itemsList = new ArrayList<>();
    Icon arrowIcon = new Icon();
    int makerIndex = 0;
    final int COLUMN_WIDTH = 15;
    final int POTION_HIGHT = 2;
    final int ARROW_HIGHT = 9;
    final int DESCRIPTION_HIGHT = 14;
    final int PRINTING_START = 4;
    final int ACTIONS_HIGHT = 27;
    final int PLAYER_STATS_Y = 23;
    final int PLAYER_STATS_X = 125;

    public Inventory() {
    }

    public Being inventoryScreen(Being player) {
        boolean isRunning = true;
        while (isRunning) {
            printBoardWithPotions(player);
            switch (input.next().toLowerCase()) {
                case "a":                           // move left
                    if (makerIndex > 0) {
                        makerIndex--;
                    }
                    break;
                case "d":                           // move right
                    if (makerIndex < 9) {
                        makerIndex++;
                    }
                    break;
                case "c":                           // use potion, change player's stats, print message on screen
                    player = usePotion(player);
                    try {
						Thread.sleep(3000);
                    } catch (InterruptedException e) {}
                    break;
                case "q":                           // quit from inventory
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }
        return player;
    }

    private void printBoardWithPotions(Being player) {
        UI.clearScreen();
        Board board = new Board("src/main/resources/inventory.txt");
        board.printBoard();
        int x = PRINTING_START;
        for (Item item : itemsList) {
            board.printOnBoard(item.getIcon(), POTION_HIGHT, x);
            x += COLUMN_WIDTH;
        }
        board.printOnBoard(arrowIcon.getArrowIcon(), ARROW_HIGHT, PRINTING_START + COLUMN_WIDTH * makerIndex);
        printPotionDescription(board);
        printPlayerOnBoard(board, player);
        UI.moveCursor(40, 0);
    }

    public void addItem(Item item) {
        itemsList.add(item);
    }
 
    public void printPotionDescription(Board board){
        String[][] description = new String[11][1];
        for (int i = 0; i< description.length; i++) {
            description[i][0] = " ";
        }
        description[0][0] = "POTION:";

        try {
            Item item = itemsList.get(makerIndex);
            getProperPowerName(description, item);
            board.printOnBoard(description, DESCRIPTION_HIGHT, PRINTING_START);
            board.printOnBoard(item.getIcon(), DESCRIPTION_HIGHT + 3, PRINTING_START);

        } catch (IndexOutOfBoundsException e) {}

        printInstructions(board, description);
        
    }

    private void printInstructions(Board board, String[][] description) {
        description[0][0] = "Actions:";
        description[2][0] = "a - move left";
        description[4][0] = "d - move right";
        description[6][0] = "c - chose potion";
        description[8][0] = "q - quit";
        description[9][0] = " ";
        description[10][0] = "Enter what you want to do and press \"ENTER\"";
        board.printOnBoard(description, ACTIONS_HIGHT, PRINTING_START);
    }

    private void getProperPowerName(String[][] description, Item item) {
        switch (item.getType()) {
            case HP:
                description[9][0] = "+ 5 HP";
                break;
            case STRENGTH:
                description[9][0] = "+ 5 STRENGTH";
                break;
            case AGILITY:
                description[9][0] = "+ 5 AGILITY";
                break;
            case DEFENCE:
                description[9][0] = "+ 5 DEFENCE";
                break;
            default:
                description[9][0] = "+ 5 HP";
                break;
        }
    }

    private Being usePotion(Being player) {
        try {
            Item item = itemsList.get(makerIndex);
            switch (item.getType()) {
                case HP:
                    player.addHp(item.getHp());
                    break;
                case STRENGTH:
                    player.addStrength(item.getStrength());
                    break;
                case AGILITY:
                    player.addAgility(item.getAgility());
                    break;
                case DEFENCE:
                    player.addDefence(item.getDefence());
                    break;
                default:
                    break;
            }
            itemsList.remove(makerIndex);
            UI.moveCursor(30, 30);
            System.out.print("You have just used a potion!");
            return player;
        } catch (IndexOutOfBoundsException e) {
            UI.moveCursor(30, 30);
            System.out.print("Potion required!");
            return player;
        }

    }

    private void printPlayerOnBoard(Board board, Being player) {
        String[][] playerStatistics = {{"PLAYER:"}, {" "}, {String.format("HP: %d", player.getHp())}, {" "}, 
                {String.format("STRENGTH: %d", player.getStrength())}, {" "},
                {String.format("DEFENCE: %d", player.getDefence())}, {" "}, 
                {String.format("AGILITY: %d", player.getAgility())}, {" "},
                {String.format("EXP: %d/100", player.getExp())}, {" "},
                {String.format("LEVEL: %d/2", player.getLevel())}};
        board.printOnBoard(playerStatistics, PLAYER_STATS_Y, PLAYER_STATS_X);
    }


}