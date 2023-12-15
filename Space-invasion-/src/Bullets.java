import processing.core.PApplet;
//
public class Bullets extends PApplet {
    float xCoordinate, yCoordinate;
    float speed;
    float radius;

    public Bullets(float x, float y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.speed = -5;
        this.radius = 5;
    }

    public float getXCoordinate() {
        return this.xCoordinate;
    }

    public float getYCoordinate() {
        return this.yCoordinate;
    }

    public float getRadius() {
        return this.radius;
    }

    public void move() {
        yCoordinate += speed;
    }

}
