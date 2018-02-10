package project.animator.model.animations;

import project.animator.model.shapes.ShapeOperations;

/**
 * bahaviors of any animation that can be performed by the shapes which implement ShapeOperations.
 */
public abstract class Animation implements AnimationOperations {
  /**
   * currentTime - field representing the shape which is being moved.
   * fromTime - the time the transformation begins.
   * toTime - the time the transformation ends.
   * shapeToBeAnimated - represents the name of the shape we are changing.
   */
  protected float currentTime;
  protected final float fromTime;
  protected final float toTime;
  protected String shapeToBeAnimated;
  protected boolean changedValue;

  /**
   * Constructor for an animation object. Every animation has a fromTime and a toTime
   * @param fromTime the time the transformation begins.
   * @param toTime the time the transformation ends.
   * @IllegalArgumentException - thrown if the from time happens after the to time.
   */
  protected Animation(float fromTime, float toTime) {
    if (fromTime > toTime) {
      throw new IllegalArgumentException("End time can't be after start time!!!");
    }
    else {
      this.currentTime = fromTime;
      this.fromTime = fromTime;
      this.toTime = toTime;
      this.changedValue = false;
    }

  }

  /**
   * This method is used to get the time this shape is changed from.
   * @return the time this animation begins.
   */
  @Override
  public float getFromTime() {
    return this.fromTime;
  }

  /**
   * This method is used to get the time this animation is changed until.
   * @return the time this animation ends.
   */
  @Override
  public float getToTime() {
    return this.toTime;
  }

  /**
   * This method is used to set the name of the shape this animation is changing. We set the name
   * of the shape in the shape so that any shape can use any animation if said animation is
   * added to the shape.
   * @param shapeName - the name of the shape this animation will be changing.
   */
  @Override
  public void setShapeToBeAnimated(String shapeName) {
    this.shapeToBeAnimated = shapeName;
  }

  /**
   * Checks if this animation conflicts with the given animation. It would be conflicting if
   * it changes the same parameter within time periods which overlap.
   * @param stateAtFromTime the state of the shape we are changing after any animations at the
   *                        time the given animation begins.
   * @param stateAtToTime the state of the shape we are changing after any animations at the time
   *                      the given animation ends.
   * @param a the animation we are checking for conflicts with.
   * @return returns true if there are no conflicts with this animation.
   */
  @Override
  public boolean hasNoConflictsWith(ShapeOperations stateAtFromTime, ShapeOperations stateAtToTime,
                                    AnimationOperations a) {
    return a.noConflictsWithHelper(stateAtFromTime, stateAtToTime, this.fromTime,
            this.toTime);
  }

  /**
   * print the state of this animation as a string.
   * @return returns the state of this animation as a string.
   */
  @Override
  public abstract String toString();
}
