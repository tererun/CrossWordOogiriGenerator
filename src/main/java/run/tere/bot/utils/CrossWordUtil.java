package run.tere.bot.utils;

import run.tere.bot.models.Coordinate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CrossWordUtil {

    public static int[][] generate(int x, int y) {
        int[][] board = initBoard(x, y);

        for (int boardX = 0; boardX < board.length; boardX++) {
            int[] boardXBoard = board[boardX];
            for (int boardY = 0; boardY < boardXBoard.length; boardY++) {
                if (isInRange(board, boardX, boardY)) continue;
                boolean placeable = canPlaceBlock(board, boardX, boardY);
                if (placeable) board[boardX][boardY] = 1;
            }
        }

        return board;
    }

    public static int[][] initBoard(int x, int y) {
        int[][] board = new int[x][y];
        for (int[] ints : board) {
            Arrays.fill(ints, 0);
        }
        return board;
    }

    public static boolean canPlaceBlock(int[][] board, int x, int y) {
        Random random = new Random();
        boolean placeBlock = random.nextBoolean();
        if (!placeBlock) return false;

        int leftX = x - 1;
        int rightX = x + 1;
        int upY = y - 1;
        int downY = y + 1;

        return true;
    }

    public static boolean isInRange(int[][] board, int boardX, int boardY) {
        return (boardX == 0 || boardX >= (board.length - 1) || boardY == 0 || boardY >= (board[boardX].length - 1));
    }

}
