package project.animator.model.animations;

import project.animator.model.shapes.IPosn;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visitors.AnimationVisitor;

/**
 * represents a move animation object which is used to move a shape.
 */
public class MoveAnimation extends Animation implements IMoveAnimation {

  protected final IPosn from;
  protected final IPosn to;

  /**
   * the constructor for this move animation.
   * @param from field representing the position we are moving from.
   * @param to field representing the position we are moving to.
   * @param fromTime the time the transformation begins.
   * @param toTime the time the transformation ends.
   */
  public MoveAnimation(IPosn from, IPosn to, float fromTime, float toTime) {
    super(fromTime, toTime);
    this.from = from;
    this.to = to;
  }

  /**
   * change the shapeToBeChanged shape's attributes to the instance it should be at during the
   * currentTime.
   * @param currentTime - the time we want to get the animation at
   */
  @Override
  public void animate(ShapeOperations shapeToBeChanged, float currentTime) {

    if (currentTime >= fromTime && currentTime <= toTime) {
      shapeToBeChanged.changePosition(this.from, this.to, this.fromTime, this.toTime, currentTime);
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
      return givenTo.getPosition().equals(givenFrom.getPosition());
    }
  }

  /**
   * This is a visitor used to add new functionality to this MoveAnimation object.
   * @param v the visitor we are visiting.
   * @param <T> the type the method of the visitor is going to be returning.
   * @return returns the type the visitor would like to return.
   */
  @Override
  public <T> T accept(AnimationVisitor<T> v) {
    return v.visit(this);
  }

  /**
   * Returns the position this animation is changing from. (Used when creating a new AnimationView)
   * @return returns the IPosn the animation is changing from.
   */
  @Override
  public IPosn getFrom() {
    return this.from;
  }

  /**
   * Returns the position this animation is changing to. (Used when creating a new AnimationView)
   * @return returns the Posn the animation is changing to.
   */
  @Override
  public IPosn getTo() {
    return this.to;
  }

  /**
   * print the state of this move animation as a string.
   * @return returns the state of this move animation as a string.
   */
  @Override
  public String toString() {
    return "Shape " + this.shapeToBeAnimated + " moves from (" + from.getX() + ", " + from.getY()
            + ") to (" + to.getX() + ", " + to.getY()
            + ") from t=" + fromTime + " to t=" + toTime;
  }

  /**
   * Checks if the given object is equal to this Color Animation.
   * @param obj the object we are comparing to this Animation.
   * @return returns true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MoveAnimation) {
      MoveAnimation toBeCompared = (MoveAnimation) obj;
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
