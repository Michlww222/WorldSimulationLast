package World;

import java.util.Objects;

class Vector2d {
    private final int x;
    private final int y;
    // creator
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // return info in string
    public String toString() {
        return "(" + this.x +
                "," + this.y +
                ')';
    }
    // check if this vector precedes other
    public boolean precedes(Vector2d other) {
        if (this.x <= other.x && this.y <= other.y)
            return true;
        else {
            return false;
        }
    }
    // check if this vector follows other
    public boolean follows(Vector2d other) {
        if (this.x >= other.x && this.y >= other.y)
            return true;
        else {
            return false;
        }
    }
    // create new vector with 2 higher component
    public Vector2d upperRight(Vector2d other) {
        int a = 0;
        int b = 0;
        if (this.x <= other.x) {
            a = other.x;
        } else {
            a = this.x;
        }
        if (this.y <= other.y) {
            b = other.y;
        } else {
            b = this.y;
        }
        Vector2d newvector = new Vector2d(a, b);
        return newvector;
    }
    // create new vector with 2 lower component
    public Vector2d lowerLeft(Vector2d other) {
        int a = 0;
        int b = 0;
        if (this.x >= other.x) {
            a = other.x;
        } else {
            a = this.x;
        }
        if (this.y >= other.y) {
            b = other.y;
        } else {
            b = this.y;
        }
        Vector2d newvector = new Vector2d(a, b);
        return newvector;
    }
    //add this vector to other
    public Vector2d add(Vector2d other) {
        int a = this.x + other.x;
        int b = this.y + other.y;
        Vector2d newvector = new Vector2d(a, b);
        return newvector;
    }
    //subtract other vector from this
    public Vector2d subtract(Vector2d other) {
        int a = this.x - other.x;
        int b = this.y - other.y;
        Vector2d newvector = new Vector2d(a, b);
        return newvector;
    }
    // check if 2 vectors are equal
    public boolean equals(Vector2d other) {
        if (this.x == other.x && this.y == other.y) {
            return true;
        } else {
            return false;
        }
    }
    //get x component of vector
    public int getX() {
        return x;
    }
    //get y component of vector
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
