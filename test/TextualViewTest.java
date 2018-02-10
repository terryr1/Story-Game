import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.AnimatorModel;
import project.animator.model.animations.AnimationOperations;
import project.animator.model.animations.ColorAnimation;
import project.animator.model.animations.MoveAnimation;
import project.animator.model.shapes.Oval;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.stringbased.TextualView;
import project.animator.view.visitors.ViewAnimationVisitor;
import project.animator.view.visitors.ViewShapeVisitor;

import static org.junit.Assert.assertEquals;

public class TextualViewTest {

  TextualView view;
  Appendable ap;
  AnimatorModel model;

  /**
   * Used to initialize tests.
   */
  @Before
  public void initialize() {
    ArrayList<ShapeOperations> shapes = new ArrayList<>();
    Oval o1 = new Oval(10, 5, 0, new Posn(0, 5), Color.black,
            "o1", 0, 15, true);
    Rectangle r2 = new Rectangle(8, 8, 0, new Posn(20, 5), Color.black,
            "r2", 0, 15);
    AnimationOperations a1 = new MoveAnimation(r2.getPosition(), new Posn(30, 1), 6, 12);
    AnimationOperations a2 = new ColorAnimation(o1.getColor(), Color.GREEN, 6, 12);
    shapes.add(r2);
    shapes.add(o1);
    model = new AnimatorModel();
    for(ShapeOperations s: shapes) {
      model.addShape(s);
    }
    model.addAnimation(r2.getName(), a1);
    model.addAnimation(o1.getName(), a2);
    ap = new StringBuilder();
    view = new TextualView(ap, new ViewShapeVisitor(), new ViewAnimationVisitor());
  }

  //test a basic build of the model in svg.
  @Test
  public void checkBuild() {
    view.playAnimation(1, model.getState());
    assertEquals("Name: r2\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (20.0,5.0), Height: 8.0, Width: 8.0, Color: (0,0,0), Angle: 0.0\n" +
            "Appears at t=0.0s\n" +
            "Dissappears at t=15.0s\n" +
            "Name: o1\n" +
            "Type: oval\n" +
            "Center: (-10.0,0.0), x Radius: 10.0, y Radius: 5.0, Color: (0,0,0), Angle: 0.0\n" +
            "Appears at t=0.0s\n" +
            "Dissappears at t=15.0s\n" +
            "Shape r2 moves from (20.0, 5.0) to (30.0, 1.0) from t=6.0 to t=12.0\n" +
            "Shape o1 changes color from Color: (0,0,0) to Color: (0,0,255) from t=6.0 to t=12.0",
            ap.toString());
  }

}