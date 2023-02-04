package run.tere.bot.utils;

import run.tere.bot.models.BlockExamineResult;
import run.tere.bot.models.Coordinate;
import run.tere.bot.models.WallDirection;

import java.util.*;

public class CrossWordUtil {

    public static int[][] generate(int x, int y) {
        int[][] board = initBoard(x, y);

        for (int boardX = 0; boardX < board.length; boardX++) {
            int[] boardXBoard = board[boardX];
            for (int boardY = 0; boardY < boardXBoard.length; boardY++) {
                if (canPlaceBlock(board, boardX, boardY)) {
                    board[boardX][boardY] = 1;
                }
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
        if (!isInRange(board, x, y)) return false;

        if ((x == 0 && y == 0) || (x == 0 && y == board[x].length - 1) || (x == board.length - 1 && y == 0) || (x == board.length - 1 && y == board[x].length - 1)) return false;

        if (isInRange(board, x - 1, y) && board[x - 1][y] == 1) return false;
        if (isInRange(board, x + 1, y) && board[x + 1][y] == 1) return false;
        if (isInRange(board, x, y - 1) && board[x][y - 1] == 1) return false;
        if (isInRange(board, x, y + 1) && board[x][y + 1] == 1) return false;

        boolean result = isSurrounded(board, x, y);
        return !result;
    }

    public static boolean isSurrounded(int[][] board, int x, int y) {
        int[][] tempBoard = new int[board.length][];
        for (int i = 0; i < tempBoard.length; i++) {
            tempBoard[i] = board[i].clone();
        }
        tempBoard[x][y] = 2; //判定用の仮置き

        if (!isInRange(tempBoard, x - 1, y)) return false; //範囲外(一番上の列)は絶対に囲まれていない

        List<Coordinate> checkedCoordinates = new ArrayList<>();
        List<Coordinate> placeableCheckedCoordinates = new ArrayList<>();

        check(tempBoard, x - 1, y, new Coordinate(x - 1, y), checkedCoordinates, placeableCheckedCoordinates);

        if (placeableCheckedCoordinates.isEmpty()) return true;

        Comparator<Coordinate> comparator = Comparator.comparingInt(o -> o.x);

        placeableCheckedCoordinates.sort(comparator);
        Coordinate lastCoordinate = placeableCheckedCoordinates.get(placeableCheckedCoordinates.size() - 1);

        return lastCoordinate.x <= x;
    }

    public static void check(int[][] board, int checkX, int checkY, Coordinate startCoordinate, List<Coordinate> checkedCoordinates, List<Coordinate> placeableCheckedCoordinates) {
        if (!isInRange(board, checkX, checkY)) return; //範囲外は無視
        if (checkedCoordinates.contains(new Coordinate(checkX, checkY))) return; //チェック済みは無視

        checkedCoordinates.add(new Coordinate(checkX, checkY)); //チェックフラグを立てる
        int type = board[checkX][checkY]; //チェック場所の種類
        boolean result = startCoordinate.equals(new Coordinate(checkX, checkY));

        if (type == 0) {
            //壁ではないのでもう一回チェックが可能
            if (!result) placeableCheckedCoordinates.add(new Coordinate(checkX, checkY));
            check(board, checkX - 1, checkY, startCoordinate, checkedCoordinates, placeableCheckedCoordinates);
            check(board, checkX + 1, checkY, startCoordinate, checkedCoordinates, placeableCheckedCoordinates);
            check(board, checkX, checkY - 1, startCoordinate, checkedCoordinates, placeableCheckedCoordinates);
            check(board, checkX, checkY + 1, startCoordinate, checkedCoordinates, placeableCheckedCoordinates);
        }
    }

    public static boolean isInRange(int[][] board, int boardX, int boardY) {
        return (boardX >= 0 && boardX <= (board.length - 1) && boardY >= 0 && boardY <= (board[boardX].length - 1));
    }

}
