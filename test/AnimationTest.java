import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.animations.AnimationOperations;
import project.animator.model.animations.ColorAnimation;
import project.animator.model.animations.MoveAnimation;
import project.animator.model.animations.ScaleAnimation;
import project.animator.model.shapes.Dimension;
import project.animator.model.shapes.IDimension;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;

import static org.junit.Assert.assertEquals;

public class AnimationTest {
  Rectangle r;
  MoveAnimation move;
  ScaleAnimation scale;
  ColorAnimation color;

  /**
   * Initalizes the fields before every test.
   */
  @Before
  public void initialize() {
    r = new Rectangle(3, 5, 0, new Posn(5, 9), Color.BLACK, "R1",
            5, 40);
  }

  //check if the rectangle r can be moved properly
  @Test
  public void checkAnimateMove() {
    move = new MoveAnimation(r.getPosition(), new Posn(3, 1), 7, 20);
    r.addAnimation(move);
    r.animate(30);
    assertEquals(new Posn(3, 1), r.getPosition());
  }

  //check if the rectangle r can be scaled properly
  @Test
  public void checkAnimateScale() {

    ArrayList<IDimension> d1 = new ArrayList<>();
    ArrayList<IDimension> d2 = new ArrayList<>();
    d1.add(r.getDimensions().get(0));
    d1.add(r.getDimensions().get(1));
    d2.add(new Dimension("Height", 5));
    d2.add(new Dimension("Width", 7));

    scale = new ScaleAnimation(d1, d2, 7, 20);
    r.addAnimation(scale);
    r.animate(20);
    assertEquals(d2, r.getDimensions());
  }

  //check if the rectangle r can change color properly
  @Test
  public void checkAnimateColor() {

    Color c1 = r.getColor();
    Color c2 = Color.BLUE;

    color = new ColorAnimation(c1, c2, 7, 20);
    r.addAnimation(color);
    r.animate(20);
    assertEquals(Color.BLUE, r.getColor());
  }

  //check if the rectangle r can change color properly
  @Test
  public void checkAnimationConflicts() {

    Color c1 = r.getColor();
    Color c2 = Color.BLUE;

    color = new ColorAnimation(c1, c2, 7, 20);
    AnimationOperations color2 = new ColorAnimation(c1, c2, 19, 20);
    r.addAnimation(color);
    r.animate(19);
    ShapeOperations s1 = r.makeClone();
    r.animate(20);
    ShapeOperations s2 = r.makeClone();

    assertEquals(false, color.hasNoConflictsWith(s1, s2, color2));

  }

}