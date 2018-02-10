import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.animations.MoveAnimation;
import project.animator.model.shapes.Dimension;
import project.animator.model.shapes.IDimension;
import project.animator.model.shapes.Oval;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;

import static org.junit.Assert.assertEquals;

public class ShapeTest {
  Rectangle r;
  Rectangle r2;
  Rectangle rCopy;
  Oval o;


  /**
   * Initializes the fields before every test.
   */
  @Before
  public void initialize() {
    r = new Rectangle(5, 9, 0, new Posn(0, 5), Color.BLACK, "R1",
            5, 10);
    r2 = new Rectangle(10, 12, 0, new Posn(0, 5), Color.BLACK, "R1",
            5, 10);
    rCopy = new Rectangle(5, 9, 0, new Posn(0, 5), Color.BLACK, "R1",
            5, 10);
    o = new Oval(5, 9, 0, new Posn(0, 5), Color.BLACK, "R1",
            5, 10, true);
  }

  //check if change color works properly
  @Test
  public void checkChangeColor() {
    r.changeColor(r.getColor(), Color.RED, 7, 8, 8);
    o.changeColor(o.getColor(), Color.RED, 7, 8, 8);
    assertEquals(Color.RED, r.getColor());
    assertEquals(Color.RED, o.getColor());
  }

  //check if change angle works properly
  @Test
  public void checkChangeAngle() {
    r.changeAngle(r.getAngle(), 5, 7, 8, 8);
    assertEquals(5, r.getAngle(), .0001);
    o.changeAngle(o.getAngle(), 5, 7, 8, 8);
    assertEquals(5, o.getAngle(), .0001);
  }

  //check if change position works properly
  @Test
  public void checkChangePosition() {
    r.changePosition(r.getPosition(), new Posn(7, 2), 7, 8, 8);
    assertEquals(new Posn(7, 2), r.getPosition());
    o.changePosition(o.getPosition(), new Posn(7, 2), 7, 8, 8);
    assertEquals(new Posn(2, -7), o.getPosition());
  }


  //check if change dimension works properly
  @Test
  public void checkChangeDimension() {
    ArrayList<IDimension> rd = new ArrayList<>();
    Dimension height = new Dimension("Height", 5);
    Dimension width = new Dimension("Width", 5);
    rd.add(height);
    rd.add(width);
    r.changeDimension(r.getDimensions(), rd, 7, 8, 8);
    assertEquals(rd, r.getDimensions());
    ArrayList<IDimension> od = new ArrayList<>();
    Dimension xr = new Dimension("x Radius", 5);
    Dimension yr = new Dimension("y Radius", 5);
    od.add(xr);
    od.add(yr);
    o.changeDimension(o.getDimensions(), od, 7, 8, 8);
    assertEquals(od, o.getDimensions());
  }

  @Test
  public void addAnimations() {
    MoveAnimation a1 = new MoveAnimation(new Posn(0, 0), new Posn(2, 3), 4, 6);
    r.addAnimation(a1);

    assertEquals(new Posn(2, 3), r.getAnimations().get(0).getTo());
  }

  @Test
  public void checkAnimate() {
    MoveAnimation a1 = new MoveAnimation(new Posn(0, 0), new Posn(2, 3), 4, 6);
    r.addAnimation(a1);
    r.animate(7);
    assertEquals(true, r.getPosition().equals(new Posn(2, 3)));
  }

  @Test
  public void removeAnimation() {
    MoveAnimation a1 = new MoveAnimation(new Posn(0, 0), new Posn(2, 3), 4, 6);
    r.addAnimation(a1);
    r.animate(7);
    r.removeAnimation(a1);
    assertEquals(0, r.getAnimations().size());
  }

  @Test
  public void testClone() {
    MoveAnimation a1 = new MoveAnimation(new Posn(0, 0), new Posn(2, 3), 4, 6);
    r.addAnimation(a1);
    r.animate(7);
    ShapeOperations rect = r.makeClone();
    assertEquals(rect, r);
  }

  @Test
  public void testToString() {
    MoveAnimation a1 = new MoveAnimation(new Posn(0, 0), new Posn(2, 3), 4, 6);
    r.addAnimation(a1);
    r.animate(7);
    assertEquals("Name: R1\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (2.0,3.0), Height: 5.0, Width: 9.0, Color: (0,0,0), Angle: 0.0\n" +
            "Appears at t=5.0\n" +
            "Dissappears at t=10.0", r.toString());
  }
}
