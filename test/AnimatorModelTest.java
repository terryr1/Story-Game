import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import project.animator.model.AnimatorModel;
import project.animator.model.animations.MoveAnimation;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;

import static org.junit.Assert.assertEquals;

/**
 * Used to test the Animator Model.
 */
public class AnimatorModelTest {
  AnimatorModel a;
  Rectangle r;
  Rectangle r2;
  Rectangle r3;
  Rectangle r4;
  MoveAnimation move;
  MoveAnimation move2;

  /**
   * Initializes the fields.
   */
  @Before
  public void initialize() {
    a = new AnimatorModel();
    r = new Rectangle(3, 5, 0, new Posn(5, 9), Color.BLACK, "R1",
            5, 40);
    r2 = new Rectangle(3, 5, 0, new Posn(10, 12), Color.BLACK, "R2",
            5, 40);
    r3 = new Rectangle(3, 5, 0, new Posn(0, 2), Color.BLACK, "R3",
            5, 40);
    r4 = new Rectangle(3, 5, 0, new Posn(10, 12), Color.BLACK, "R4",
            5, 40);
    move = new MoveAnimation(r3.getPosition(), r4.getPosition(), 20, 40);
    move2 = new MoveAnimation(r.getPosition(), r2.getPosition(), 20, 40);
  }

  //check if iterate works
  @Test
  public void testAddShape() {
    a.addShape(r);
    assertEquals(true, a.getState().get(0).equals(r));
    assertEquals(true, a.contains(r.getName()));
  }

  //check if iterate works
  @Test
  public void testAddAnimation() {
    a.addShape(r);
    a.addAnimation(r.getName(), move);
    assertEquals(true, a.getState().get(0).equals(r));
  }

  @Test
  public void testRemoveShape() {
    a.addShape(r);
    assertEquals(true, a.getState().get(0).equals(r));
    assertEquals(true, a.contains(r.getName()));
    a.removeShape(r.getName());
    assertEquals(false, a.contains(r.getName()));
  }

  @Test
  public void testGetShape() {
    a.addShape(r);
    ShapeOperations s = a.getShape(r.getName());
    assertEquals(r, s);
  }

  @Test
  public void testAddShape2() {
    a.addShape(r);
    a.addShape(r2);
    assertEquals(r3, a.getState().get(1));
  }

  @Test
  public void testToString() {
    a.addShape(r);
    a.addShape(r2);
    a.addShape(r4);
    assertEquals("Name: R1\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (5.0,9.0), Height: 3.0, Width: 5.0, Color: (0,0,0), Angle: 0.0\n" +
            "Appears at t=5.0\n" +
            "Dissappears at t=40.0\n" +
            "Name: R3\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (0.0,2.0), Height: 3.0, Width: 5.0, Color: (0,0,0), Angle: 0.0\n" +
            "Appears at t=5.0\n" +
            "Dissappears at t=40.0\n" +
            "Name: R2\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (10.0,12.0), Height: 3.0, Width: 5.0, Color: (0,0,0), Angle: 0.0\n"
            + "Appears at t=5.0\n" +
            "Dissappears at t=40.0\n" +
            "Name: R4\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (10.0,12.0), Height: 3.0, Width: 5.0, Color: (0,0,0), Angle: 0.0\n"
            + "Appears at t=5.0\n" +
            "Dissappears at t=40.0", a.toString());
  }
}
