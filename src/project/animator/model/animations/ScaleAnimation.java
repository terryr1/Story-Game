package project.animator.model.animations;

import java.util.ArrayList;

import project.animator.model.shapes.IDimension;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visitors.AnimationVisitor;

/**
 * represents a scale animation object which is used to scale a shape.
 */
public class ScaleAnimation extends Animation implements IScaleAnimation {

  protected final ArrayList<IDimension> from;
  protected final ArrayList<IDimension> to;

  /**
   * the constructor for this scale animation.
   * @param from The dimensions we are starting at.
   * @param to The dimensions we are ending at.
   * @param fromTime the time the transformation begins.
   * @param toTime the time the transformation ends.
   */
  public ScaleAnimation(ArrayList<IDimension> from, ArrayList<IDimension> to, float fromTime,
                        float toTime) {
    super(fromTime, toTime);
    this.from = new ArrayList<>();
    this.from.addAll(from);
    this.to = new ArrayList<>();
    this.to.addAll(to);
    if (this.from.size() != this.to.size()) {
      throw new IllegalArgumentException("Not changing the same number of dimensions!");
    }
  }

  /**
   * change the shapeToBeChanged shape's attributes to the instance it should be at during the
   * currentTime.
   * @param currentTime - the time we want to get the animation at
   */
  @Override
  public void animate(ShapeOperations shapeToBeChanged, float currentTime) {

    if (currentTime >= fromTime && currentTime <= toTime) {
      shapeToBeChanged.changeDimension(this.from, this.to, this.fromTime, this.toTime, currentTime);
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
      return givenTo.getDimensions().equals(givenFrom.getDimensions());
    }
  }

  /**
   * This is a visitor used to add new functionality to this ScaleAnimation object.
   * @param v the visitor we are visiting.
   * @param <T> the type the method of the visitor is going to be returning.
   * @return returns the type the visitor would like to return.
   */
  @Override
  public <T> T accept(AnimationVisitor<T> v) {
    return v.visit(this);
  }

  /**
   * Returns the dimensions this animation is changing from.
   * @return returns the dimensions the animation is changing from.
   */
  @Override
  public ArrayList<IDimension> getFrom() {
    return this.from;
  }

  /**
   * Returns the dimensions this animation is changing to.
   * @return returns the dimensions the animation is changing to.
   */
  @Override
  public ArrayList<IDimension> getTo() {
    return this.to;
  }

  /**
   * print the state of this scale animation as a string.
   * @return returns the state of this scale animation as a string.
   */
  @Override
  public String toString() {
    String output = "";
    output = "Shape " + this.shapeToBeAnimated + " scales from ";
    for (int i = 0; i < this.from.size(); i++) {
      output += this.from.get(i).getType() +
              ": " + this.from.get(i).getValue();
      if (i != this.from.size() - 1) {
        output += ", ";
      }
    }
    output +=  " to ";
    for (int i = 0; i < this.to.size(); i++) {
      output += this.to.get(i).getType() +
              ": " + this.to.get(i).getValue();
      if (i != this.to.size() - 1) {
        output += ", ";
      }
    }
    output += " from t=" + fromTime + " to t=" + toTime;
    return output;
  }

  /**
   * Checks if the given object is equal to this Scale Animation.
   * @param obj the object we are comparing to this Animation.
   * @return returns true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ScaleAnimation) {
      ScaleAnimation toBeCompared = (ScaleAnimation) obj;
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
