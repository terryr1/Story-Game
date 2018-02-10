package project.animator.model.shapes;

import java.awt.Color;

import project.animator.view.visitors.ShapeVisitor;

/**
 * Represents a Oval AShape Object.
 */
public class Oval extends AShape implements IOval {

  /**
   * The constructor used to build our Oval.
   *
   * @param xRadius    - the x radius of this Oval.
   * @param yRadius    - the y radius of this Oval.
   * @param angle      - the angle this Oval is at (used for rotation animations).
   * @param position   - the position this Oval is at.
   * @param color      - the color of this Oval.
   * @param name       - the name of this Oval.
   * @param appears    - the time this Oval begins to be visible in our animation.
   * @param disappears - the time the Oval will no longer be visible in our animation.
   * @param center     - if true the position coordinates are located in the center of the oval
   */
  public Oval(float xRadius, float yRadius, float angle, IPosn position, Color color, String name,
              float appears, float disappears, boolean center) {
    super(angle, position, color, name, appears, disappears);
    if (center) {
      this.position = new Posn(this.position.getX() - xRadius, this.position.getY() - yRadius);
    }
    super.dimensions.add(new Dimension("X Radius", xRadius));//dimensions.get(0) represents the
    // x radius
    super.dimensions.add(new Dimension("Y Radius", yRadius)); //dimensions.get(1) represents
    // the y radius
  }

  /**
   * Returns the type of this oval.
   * @return the type of this oval as a string.
   */
  @Override
  protected String typeToString() {
    return "Type: oval";
  }

  /**
   * Returns the position of this oval.
   * @return the position of this oval.
   */
  @Override
  protected String posnToString() {
    return "Center: (" + position.getX() + "," + position.getY() + ")";
  }

  /**
   * Used to add functionality to this oval.
   * @param v The visitor that's visiting us.
   * @param <T> The type the visitor is returning.
   * @return The type the visitor method is returning.
   */
  @Override
  public <T> T accept(ShapeVisitor<T> v) {
    return v.visit(this);
  }

  /**
   * Used to change the position of this oval. Overrided so that we take into account that these
   * coordinates should be in the center of the oval, not the top left.
   * @param fromPosn - the position we are changing from.
   * @param toPosn - the position we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  @Override
  public void changePosition(IPosn fromPosn, IPosn toPosn, float from, float to,
                             float currentTime) {
    float preY = fromPosn.getY() - this.getDimensions().get(1).getValue();
    float preX = fromPosn.getX() - this.getDimensions().get(0).getValue();
    float toY = toPosn.getY() - this.getDimensions().get(1).getValue();
    float toX = toPosn.getX() - this.getDimensions().get(0).getValue();
    this.position = new Posn(changeHelper(preX, toX, from, to, currentTime),
            changeHelper(preY, toY, from, to, currentTime));
  }

  /**
   * returns a new Oval with all the same fields as this Oval.
   *
   * @return a new Oval with identical fields to this Oval.
   */
  @Override
  public ShapeOperations makeClone() {
    return new Oval(this.dimensions.get(0).getValue(), this.dimensions.get(1).getValue(),
            this.angle, this.position, this.color, this.name, this.appears, this.disappears, false);
  }

}
