package project.animator.model.animations;

import java.awt.Color;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visitors.AnimationVisitor;

/**
 * represents a color animation object which is used to change the color of a shape.
 */
public class ColorAnimation extends Animation implements IColorAnimation {

  protected final Color from;
  protected final Color to;

  /**
   * the constructor for this color animation.
   * @param from field representing the color we are changing from.
   * @param to field representing the color we are changing to.
   * @param fromTime the time we start this animation at.
   * @param toTime the time the this animation ends.
   */
  public ColorAnimation(Color from, Color to, float fromTime, float toTime) {
    super(fromTime, toTime);
    this.from = from;
    this.to = to;
  }

  /**
   * change the shapeToBeChanged shapes attributes to the instance it should be at during the
   * currentTime.
   * @param shapeToBeChanged - the shape we want to animate.
   * @param currentTime - the time we want to get the animation at.
   */
  @Override
  public void animate(ShapeOperations shapeToBeChanged, float currentTime) {

    if (currentTime >= fromTime && currentTime <= toTime) {
      shapeToBeChanged.changeColor(this.from, this.to, this.fromTime, this.toTime, currentTime);
    }

  }

  /**
   * A helper method for hasNoConflictsWith which we are using on the animation given in that method
   * to see if we are changing the same attribute as said animation between the givenFromTime and
   * givenToTime.
   * @param givenFrom the state of the shape we are changing after any animations at the
   *                        time the given animation begins.
   * @param givenTo the state of the shape we are changing after any animations at the time
   *                      the given animation ends.
   * @param givenFromTime the time we are changing from.
   * @param givenToTime the time we are changing until.
   * @return returns true if there are no conflicts with this animation.
   */
  @Override
  public boolean noConflictsWithHelper(ShapeOperations givenFrom, ShapeOperations givenTo,
                                       float givenFromTime, float givenToTime) {
    if (givenFromTime > this.toTime || givenToTime < this.fromTime) {
      return true;
    }
    else {
      return givenTo.getColor().equals(givenFrom.getColor());
    }
  }

  /**
   * This is a visitor used to add new functionality to this ColorAnimation object.
   * @param v the visitor we are visiting.
   * @param <T> the type the method of the visitor is going to be returning.
   * @return returns the type the visitor would like to return.
   */
  @Override
  public <T> T accept(AnimationVisitor<T> v) {
    return v.visit(this);
  }

  /**
   * Returns the Color this animation is changing from. (Used when creating a new AnimationView)
   * @return returns the Color the animation is changing from.
   */
  @Override
  public Color getFrom() {
    return this.from;
  }

  /**
   * Returns the Color this animation is changing to. (Used when creating a new AnimationView)
   * @return returns the Color the animation is changing to.
   */
  @Override
  public Color getTo() {
    return this.to;
  }

  /**
   * print the state of this move animation as a string.
   * @return returns the state of this move animation as a string.
   */
  @Override
  public String toString() {
    return "Shape " + this.shapeToBeAnimated + " changes color from Color: (" + this.from.getRed()
            + "," + this.from.getBlue() + "," + this.from.getGreen() + ") to Color: (" +
            this.to.getRed() + "," + this.to.getBlue() + "," + this.to.getGreen() + ") from t="
            + fromTime + " to t=" + toTime;
  }

  /**
   * Checks if the given object is equal to this Color Animation.
   * @param obj the object we are comparing to this Animation.
   * @return returns true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ColorAnimation) {
      ColorAnimation toBeCompared = (ColorAnimation) obj;
      return this.from.equals(toBeCompared.getFrom())
              && this.to.equals(toBeCompared.getTo())
              && this.fromTime - toBeCompared.getFromTime() <= .001
              && this.toTime - toBeCompared.getToTime() <= .001;
    }
    else {
      return false;
    }
  }

  /**
   * Returns a haschode consistent with this.equals.
   * @return returns the sum of the codes of the fields of this animation.
   */
  @Override
  public int hashCode() {
    Float fromT = this.fromTime;
    Float toT = this.toTime;
    return this.from.hashCode() + this.to.hashCode() + fromT.hashCode() +
            toT.hashCode();
  }
}
