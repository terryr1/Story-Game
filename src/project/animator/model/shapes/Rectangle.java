package project.animator.model.shapes;

import java.awt.Color;

import project.animator.view.visitors.ShapeVisitor;

/**
 * Represents a Rectangle AShape Object.
 */
public class Rectangle extends AShape implements IRectangle {

  /**
   * The constructor used to build our Rectangle.
   * @param height - the height of this Rectangle.
   * @param width - the width of this Rectangle.
   * @param angle - the angle this Rectangle is at (used for rotation animations).
   * @param position - the position this Rectangle is at.
   * @param color - the color of this Rectangle.
   * @param name - the name of this Rectangle.
   * @param appears - the time this Rectangle begins to be visible in our animation.
   * @param disappears - the time the Rectangle will no longer be visible in our animation.
   */
  public Rectangle(float width, float height, float angle, IPosn position, Color color, String name,
                   float appears, float disappears) {
    super(angle, position, color, name, appears, disappears);
    super.dimensions.add(new Dimension("Width", width)); //dimensions.get(0) represents the
    // height
    super.dimensions.add(new Dimension("Height", height)); //dimensions.get(1) represents the
    // width
  }

  /**
   * Returns the type of this rectangle.
   * @return the type of this rectangle as a string.
   */
  @Override
  protected String typeToString() {
    return "Type: rectangle";
  }

  /**
   * Returns the position of this rectangle.
   * @return the position of this rectangle.
   */
  @Override
  protected String posnToString() {
    return "Lower-left corner: (" + position.getX() + "," + position.getY() + ")";
  }

  /**
   * Used to add functionality to this rectangle.
   * @param v The visitor that's visiting us.
   * @param <T> The type the visitor is returning.
   * @return The type the visitor method is returning.
   */
  @Override
  public <T> T accept(ShapeVisitor<T> v) {
    return v.visit(this);
  }

  /**
   * returns a new Rectangle with all the same fields as this Rectangle.
   * @return a new Rectangle with identical fields to this Rectangle.
   */
  public ShapeOperations makeClone() {
    return new Rectangle(this.dimensions.get(0).getValue(), this.dimensions.get(1).getValue(),
            this.angle, this.position, this.color, this.name, this.appears, this.disappears);
  }

}
