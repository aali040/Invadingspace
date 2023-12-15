import processing.core.PApplet;
import processing.core.PImage;

public class SpaceShip extends PApplet{
    //Bullets bullets = new Bullets()
    private float width;
    private float height;
    public SpaceShip(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(PApplet window, PImage spaceCraft) {
        window.fill(0, 255, 0);
        window.image(spaceCraft,window.mouseX - width/2, window.mouseY - height/2 );
//        window.rect(window.mouseX - width/2, window.mouseY - height/2, this.width, this.height);
    }

    public boolean hitMeteorite(Meteorite Meteor, PApplet window) {

        double deltaX = Math.abs(Meteor.getXCoordinate() - (window.mouseX));
        double deltaY = Math.abs(Meteor.getYCoordinate() - (window.mouseY));

        double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return d < Meteor.getRadius() + width/2;

    }

}

