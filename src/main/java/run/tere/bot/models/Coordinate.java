package run.tere.bot.models;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Coordinate set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Coordinate clone() {
        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate coordinate)) return false;
        return coordinate.x == x && coordinate.y == y;
    }

}