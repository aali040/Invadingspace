import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    ArrayList<Meteorite> meteors;
    ArrayList<Bullets> bullets;

    Meteorite m ;

    boolean gameOver=false;
    int score=0;
    int delayTime=0;
    int bulletDelay;
    int lastBulletTime;
    int topScore = 0;

    int meteorDelay;
    int lastMeteorTime;
//    int lastMeteorHeight;

    PImage background, spaceCraft, meteorite;
    public void settings() {
        size(600, 600);   // set the window size
    }

    public void setup() {
        // TODO: initialize game variables
        background = loadImage("images/background.jpg");
        background.resize(600,600);
        spaceCraft = loadImage("images/spacecraft.png");
        spaceCraft.resize(70,70);
        meteorite = loadImage("images/meteor.png");

        meteors = new ArrayList<Meteorite>();
        bullets = new ArrayList<Bullets>();
        mouseX = width/2;
        mouseY = height;

        bulletDelay = 300;
        lastBulletTime = 0;

        meteorDelay = 600;
        lastMeteorTime = 0;
//        lastMeteorHeight = 0;
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {

        background(background);    // paint screen white

        // Creating spaceship
        SpaceShip myShip = new SpaceShip(50, 50);
        myShip.draw(this, spaceCraft);

//      Displaying meteor
        for (int i = 0; i < meteors.size() ; i++) {
            Meteorite meteor = meteors.get(i);

            meteor.update();
            meteor.draw(this, meteorite);

            // Removing meteors that exceed windows height
            if (meteor.getYCoordinate() > height) {
                meteors.remove(i);
                i--;
            }

            // Check for collisions with bullets
            for (int j = bullets.size() - 1; j >= 0; j--) {
                Bullets bullet = bullets.get(j);
                if (meteor.hits(bullet)) {
                    meteors.remove(i);
                    bullets.remove(j);
                    score++;
                }
            }

            if (myShip.hitMeteorite(meteor, this) ) {
                try {
                    BufferedReader in = new BufferedReader(new FileReader("topScore.txt"));
                    String line;
                    if ((line=in.readLine()) != null) {
                        String[] val = line.split(":");
                        topScore = Integer.parseInt(val[1].trim());
                    }
                    in.close();
                    if (score > topScore) {
                        PrintWriter out = new PrintWriter(new FileWriter("topScore.txt"));
                        out.println("topScore: " + score);
                        out.close();
                        displayGameOver(score, topScore, true);
                        gameOver=true;
                        noLoop();
                    }
                    else {
                        displayGameOver(score, topScore, false);
                        gameOver=true;
                        noLoop();
                    }
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
//                exitActual();
            }

        }
        endgameCond2(meteors);

//      Displaying bullets
        for (Bullets bullet : bullets) {
            bullet.move();
            fill(255, 255, 0);
            ellipse(bullet.getXCoordinate(), bullet.getYCoordinate(), bullet.getRadius() * 2, bullet.getRadius() * 2);
        }

//      Creating delay between each fire
        int currentTime = millis();

        if (currentTime - lastMeteorTime > meteorDelay) {
            drawMeteorite();
            lastMeteorTime = currentTime;
        }

        if (currentTime - lastBulletTime > bulletDelay) {
            bullets.add(new Bullets(mouseX, mouseY));
            lastBulletTime = currentTime;
        }
    }

//    public void keyPressed() {
//        if (key == 'S' || key == 's') {
//            bullets.add(new Bullets(mouseX, mouseY));
//        }
//    }

    void displayGameOver(int score, int topScore, boolean newRecord) {
        background(background);

        textAlign(CENTER, CENTER);
        textSize(50);
        fill(255);
        if (newRecord) {
            text("GAME OVER \n Congratulations!\nYou made new Record\n" + score, width / 2, height / 2);
        }
        else {
            text("GAME OVER \n Score: " + score + "\n Top Score: " + topScore, width / 2, height / 2);
        }
    }


    public void drawMeteorite() {
        int x = (int) (Math.random() * (((width-20) - 20) + 1)) + 20;
        m = new Meteorite(x, 0);
        meteors.add(m);
//        if (init) {
//            for (int x = 20; x < width ; x+=40) {
//                m = new Meteorite(x, 0);
//                meteors.add(m);
//            }
//        }
//        else {
//            Meteorite lastMeteor = meteors.get(meteors.size()-1);
//            int lastMeteorYCoordinate = (int) lastMeteor.getYCoordinate();
//            for (int x = 20; x < width ; x+=40) {
//
//                // if last meteors grid line has travelled less than 30 steps
//                if (lastMeteorYCoordinate < lastMeteor.getRadius()) {
//                    m = new Meteorite(x, -(30-lastMeteorYCoordinate));
//                }
//                else {
//                    m = new Meteorite(x, 0);
//                }
//                meteors.add(m);
//            }
//        }
    }

//    public void exitActual() {
//        try {
//            System.out.println("Game Over! \nYour have destroyed "+score+ " Meteorites" );
//            System.exit(0);
//        } catch (SecurityException var1) {
//        }
//
//    }

    public void keyPressed() {
//        System.out.println("Here");
        if (gameOver) {
//            System.out.println("Here2");
            if (key == 'r' || key == 'R') {
//                System.out.println("Here3");
                gameOver=false;
                meteors.clear();
                bullets.clear();
                score=0;
                loop();
            }
        }
    }
    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
    public void endgameCond2 (ArrayList <Meteorite> mlist){
        for (int i = 0; i < mlist.size(); i++) {
            if (mlist.get(i).getYCoordinate() == 600 ){
                displayGameOver(score, topScore, true);
            }
        }
    }
}
