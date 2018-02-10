import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.AnimatorModel;
import project.animator.model.animations.AnimationOperations;
import project.animator.model.animations.MoveAnimation;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.stringbased.SVGView;
import project.animator.view.visitors.ViewAnimationVisitor;
import project.animator.view.visitors.ViewShapeVisitor;

import static org.junit.Assert.assertEquals;

public class SVGViewTest {

  SVGView view;
  Appendable ap;
  AnimatorModel model;

  /**
   * Used to initalize fields.
   */
  @Before
  public void initialize() {
    ArrayList<ShapeOperations> shapes = new ArrayList<>();
    Rectangle r1 = new Rectangle(10, 5, 0, new Posn(0, 5), Color.black,
            "r1", 0, 10);
    Rectangle r4 = new Rectangle(10, 5, 0, new Posn(5, 10), Color.black,
            "r4", 0, 10);
    shapes.add(r1);
    shapes.add(r4);
    Rectangle r2 = new Rectangle(8, 8, 0, new Posn(20, 5), Color.black,
            "r2", 0, 10);
    AnimationOperations a1 = new MoveAnimation(r1.getPosition(), r4.getPosition(), 6, 12);
    shapes.add(r2);
    model = new AnimatorModel();
    for(ShapeOperations s: shapes) {
      model.addShape(s);
    }
    model.addAnimation(r1.getName(), a1);
    ap = new StringBuilder();
    view = new SVGView(800, 800, ap, new ViewShapeVisitor(),
            new ViewAnimationVisitor());
  }

  //test a basic build of the model in svg.
  @Test
  public void checkBuild() {
    view.playAnimation(1, model.getState());
    assertEquals("<svg width=\"800\" height=\"800\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect x=\"0.0\" y=\"5.0\" width=\"10.0\" height=\"5.0\" fill=\"#000000\" " +
            "transform=\"rotate(0.0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" attributeName=\"visibility\" from=\"visible\" " +
            "to=\"visible\" dur=\"10.0s\" begin=\"0.0s\" />\n" +
            "<animate attributeType=\"xml\" attributeName=\"x\" from=\"0.0\" to=\"5.0\" " +
            "dur=\"6.0s\" begin=\"6.0s\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" attributeName=\"y\" from=\"5.0\" to=\"10.0\" " +
            "dur=\"6.0s\" begin=\"6.0s\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<rect x=\"5.0\" y=\"10.0\" width=\"10.0\" height=\"5.0\" fill=\"#000000\" " +
            "transform=\"rotate(0.0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" attributeName=\"visibility\" from=\"visible\" " +
            "to=\"visible\" dur=\"10.0s\" begin=\"0.0s\" />\n" +
            "</rect>\n" +
            "<rect x=\"20.0\" y=\"5.0\" width=\"8.0\" height=\"8.0\" fill=\"#000000\" " +
            "transform=\"rotate(0.0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" attributeName=\"visibility\" from=\"visible\" " +
            "to=\"visible\" dur=\"10.0s\" begin=\"0.0s\" />\n" +
            "</rect>\n" +
            "</svg>", ap.toString());
  }

}