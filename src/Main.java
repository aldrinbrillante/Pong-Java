
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Jacob Gordon
 * @version 1.0
 * @date 11/26/18
 **/

public class Main {

    public static void main(String[] args) {
        new Main().start(); //start this application
    }

    /*
    this function starts our pong game
    the text drops down first
    then a ball appears and bounces
    player can select option with mouse 
     */
    public void start() {
        int w = 500;
        int h = 500;

        EZ.initialize(w, h);
        EZ.setBackgroundColor(new Color(12, 48, 94));

        //the locations we are starting from!
        //title drops down, buttons go up!
        Vec2 pTitle = new Vec2(250, -100);
        Vec2 pSP = new Vec2(130, 600);
        Vec2 pMP = new Vec2(380, 600);

        //speed of the drops
        Vec2 speedTitle = new Vec2(0f, 13);
        Vec2 speedButtons = new Vec2(0f, -13);


        //the locations that we want our text to go to!
        Vec2 posTitle = new Vec2(250, 166);
        Vec2 posSP = new Vec2(130, 450);
        Vec2 posMP = new Vec2(380, 450);

        //colors of our buttons
        Color buttonPre = new Color(156, 94, 144);
        Color buttonHover = new Color(255, 39, 116);

        //make the walls with 'q' thickness
        int q = 10;
        Rectangle w1 = new Rectangle(-q, 0, q, 500);
        Rectangle w2 = new Rectangle(500, 0, q, 500);
        Rectangle w3 = new Rectangle(0, -q, 500, q);
        Rectangle w4 = new Rectangle(0, 500, 500, q);



        //this is for the big pong text
        EZText title = EZ.addText("myFont.TTF", pTitle.getX(), pTitle.getY(), "PONG", new Color(255, 104, 233), 64);
        EZText sp = EZ.addText("myFont.TTF", pSP.getX(), pSP.getY(), "Single-Player", buttonPre, 18);
        EZText mp = EZ.addText("myFont.TTF", pMP.getX(), pMP.getY(), "Multi-Player", buttonHover, 18);


        Rectangle m = new Rectangle(0, 0, 20, 20); //mouse rectangle to check buttons

        //do the introduction here
        boolean doneT = false; boolean doneB = false;
        while (!doneT || !doneB) {
            //add the speeds to our positions!
            if (!doneT) pTitle.add(speedTitle);
            if (!doneB) pSP.add(speedButtons);
            if (!doneB) pMP.add(speedButtons);

            //check if title has exceeded its original pos
            if (pTitle.isGreater(posTitle)[1]) {
                doneT = true;
                pTitle.set(posTitle);
            }
            //check sp (assume it moving at the same speed as mp)
            if (!pSP.isGreater(posSP)[1]) {
                doneB = true;
                pSP.set(posSP);
                pMP.set(posMP);
            }
            title.translateTo(pTitle.getX(), pTitle.getY());
            sp.translateTo(pSP.getX(), pSP.getY());
            mp.translateTo(pMP.getX(), pMP.getY());
            EZ.refreshScreen();
        }

        //we are doing the loop for the menu now
        JakeBall b = new JakeBall(new Vec2(25, 25));
        b.vel = new Vec2(125, 80);


        boolean runningMenu = true;
        while (runningMenu) {
            float delta = EZ.getDeltaTime() / 1000f;
            m.setLocation(EZInteraction.getXMouse(), EZInteraction.getYMouse());
            Rectangle r1 = getRectangleFromText(sp);
            Rectangle r2 = getRectangleFromText(mp);

            title.translateTo(posTitle.getX(), posTitle.getY());

            if (m.intersects(r1)) { //mouse was over singleplayer
                sp.setColor(buttonHover);
                if (EZInteraction.wasMouseLeftButtonPressed()) {
                    /*
                    change so singleplayer HERE!
                     */
                    EZRectangle black = EZ.addRectangle(250, 250, 500, 500, Color.BLACK, true); //make the screen black
                    new Ball1().start();
                    return;
                }
            } else {
                sp.setColor(buttonPre); //set the color back
            }
            if (m.intersects(r2)) { //mouse was over multiplayer
                mp.setColor(buttonHover);
                if (EZInteraction.wasMouseLeftButtonPressed()) {
                    /*
                    change to mutliplayer HERE!
                     */
                    EZRectangle black = EZ.addRectangle(250, 250, 500, 500, Color.BLACK, true); //make the screen black
                    new Multiplayer().move();
                    return;
                }
            } else {
                mp.setColor(buttonPre);  //set the color back
            }

            //update ball
            b.update(delta);

            //check ball w the various texts
            b.checkRect(r1);
            b.checkRect(r2);
            b.checkRect(getRectangleFromText(title));
            b.checkRect(w1);
            b.checkRect(w2);
            b.checkRect(w3);
            b.checkRect(w4);

            EZ.refreshScreen();
        }


    }

    //makes a bounding rectangle for our text
    public Rectangle getRectangleFromText(EZText text) {
        float length = text.fontSize * text.getMsg().length() * 1f;
        float height = text.fontSize * 1; //height is twice the font size
        Rectangle r = new Rectangle(Math.round((float) text.xCenter - (text.getWidth() / 2f)), Math.round((float) text.yCenter - (text.getHeight() / 2f)), Math.round(length), Math.round(height));
        return r;
    }

    //returns TRUE for least penetration on x axis, and false for y axis
    public static boolean leastPenetration(Rectangle r1, Rectangle r2) {
        Rectangle intersection = r1.intersection(r2);
        return intersection.height >= intersection.width;
    }
}

/*
This class represents a simple ball to reflect around given Rectangles.
 */
class JakeBall {
    public Vec2 pos;
    public Vec2 vel;
    EZCircle c;
    Rectangle r;

    public JakeBall(Vec2 pos) {
        this.pos = pos;
        c = EZ.addCircle(pos.getX(), pos.getY(), 10, 10, Color.WHITE, true);
        r = new Rectangle(0, 0, 10, 10);
    }

    public void checkRect(Rectangle r) {
        this.r.setLocation(pos.getX(), pos.getY());
        if (this.r.intersects(r)) {
            if (Main.leastPenetration(this.r, r)) {
                vel.scale(-1f, 1f);
            } else {
                vel.scale(1f, -1f);
            }
        }
    }

    public void update(float delta) {
        Vec2 aux = vel.copy().scale(delta);
        pos.add(aux);
        c.translateTo(pos.x, pos.y);
    }
}

/**
 * @author Jacob Gordon
 * @version 1.0
 * @date 11/17/18
 * This class represents a basic 2D Vector. I made it a while ago but decided to use it because its easier than working
 * with x and y values all the time.
 **/
 class Vec2 {

    /*
   Position of the vector.
    */
    public float x, y;

    /*
    Constructor that accepts floats for x and y.
     */
    public Vec2(float x, float y) {
        this.x = x; this.y = y;
    }

    public int getX() {
        return Math.round(x);
    }

    public int getY() {
        return Math.round(y);
    }

    /*
    Constructor that accepts a Vector2.
     */
    public Vec2(Vec2 v) {
        this.x = v.x; this.y = v.y;
    }

    /*
    Constructor without params default to 0f.
     */
    public Vec2() {
        this.x = 0f; this.y = 0f;
    }

    /*
    Returns a copy of this vec.
     */
    public Vec2 copy() {
        return new Vec2(x, y);
    }

    /*
    Adds floats x and y to this vec.
    Returns vec for chaining.
     */
    public Vec2 add(float x, float y) {
        this.x += x; this.y += y;
        return this;
    }

    /*
    Adds vector v to this vec.
    Returns vec for chaning.
     */
    public Vec2 add(Vec2 v) {
        return add(v.x, v.y);
    }


    /*
    Subs floats x and y to this vec.
    Returnns vec for chaining.
     */
    public Vec2 sub(float x, float y) {
        this.x -= x; this.y -= y;
        return this;
    }

    /*
    Subs vector v to this vec.
    Returns vec for chaning.
     */
    public Vec2 sub(Vec2 v) {
        return sub(v.x, v.y);
    }

    /*
    Returns the DOT PRODUCT of this vector compared to vector 'v'.
     */
    public float dot (Vec2 v) {
        return x * v.x + y * v.y;
    }

    /*
    Multiplies this vector by scalar floats 'x' and 'y'.
    Returns vec for chaining.
     */
    public Vec2 scale(float x, float y) {
        this.x *= x; this.y *= y;
        return this;
    }


    /*
    Mltiplies this vector by scalar floats 'xy'.
    Returns vec for chaining.
     */
    public Vec2 scale(float xy) {
        this.x *= xy; this.y *= xy;
        return this;
    }

    /*
    Multiplies this vector by scalar Vector.
    Returns vec for chaining.
     */
    public Vec2 scale(Vec2 scalar) {
        return scale(scalar.x, scalar.y);
    }

    /*
    Divides this vector by floats 'x' and 'y'.
    Returns this Vector for chaining.
     */
    public Vec2 div(float x, float y) {
        this.x /= x; this.y /= y;
        return this;
    }

    public Vec2 div(float xy) {
        this.x /= xy; this.y /= xy;
        return this;
    }

    /*
    Divides this vector by vector 'v'.
    Returns this vector.
     */
    public Vec2 div(Vec2 v) {
        return div(v.x, v.y);
    }

    /*
    Returns distance of this vector and 'v'.
    NOTE: I did not write this method, taken from LibGDX @ Vector2 class.
    @author com.badlogic.gdx
     */
    public float dst (Vec2 v) {
        final float x_d = v.x - x;
        final float y_d = v.y - y;
        return x_d * x_d + y_d * y_d;
    }


    /*
    Sets this Vectors value equal to 'x' and y'
    Returns this Vector for chaining.
     */
    public Vec2 set(float x, float y) {
        this.x = x; this.y = y;
        return this;
    }

    public Vec2 set(float xy) {
        return set(xy, xy);
    }


    /*
    Sets this Vectors value equal to the inputted Vector 'v'.
    Returns this vector for chaining
     */
    public Vec2 set(Vec2 v) {
        this.x = v.x; this.y = v.y;
        return this;
    }


    /*
    Returns the length of this vector.
     NOTE: calculation uses square root.
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
    Normalizes this float between the whole number float 0f - 1f.
    Returns this vector for chaining
     */
    public Vec2 normalize() {
        float l = getLength();
        if (l == 0) return this;
        return scale(1 / l);
    }

    public boolean[] isGreater(float x, float y) {
        boolean[] b = new boolean[2];
        b[0] = this.x > x;
        b[1] = this.y > y;
        return b;
    }

    public boolean[] isSmaller(float x, float y) {
        boolean[] b = new boolean[2];
        b[0] = this.x < x;
        b[1] = this.y < y;
        return b;
    }

    public boolean[] isGreater(Vec2 v) {
        return isGreater(v.x, v.y);
    }

    public boolean[] isSmaller(Vec2 v) {
        return isSmaller(v);
    }

    public boolean isBigger(float x, float y) {
        return this.x > x && this.y > y;
    }

    public boolean isBigger(Vec2 v) {
        return isBigger(v.x, v.y);
    }

    public Vec2 abs() {
        this.x = Math.abs(x); this.y = Math.abs(y);
        return this;
    }

    public boolean doesEqual(float x, float y) {
        return this.x == x && this.y == y;
    }

    public boolean doesEqual(Vec2 v) {
        return doesEqual(v.x, v.y);
    }


    public void print() {
        System.out.println(this.toString());
    }



    /*
    Adds 'v * s' to current.
    Returns this vector for chaining.
     */
    public Vec2 addsi( Vec2 v, float s )
    {
        v.scale(s);
        return add(v.x, v.y);
    }
    @Override
    public String toString() {
        return "x=" + x + ",y=" + y;
    }
}
