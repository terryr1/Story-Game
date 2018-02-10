package project.animator.model.animations;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visitors.AnimationVisitor;

/**
 * represents the functionality of an animation object.
 */
public interface AnimationOperations {

  /**
   * change the shapes attributes to the instance it should be at during the currentTime.
   * @param currentTime - the time we want to get the animation at
   */
  void animate(ShapeOperations shapeToBeChanged, float currentTime);

  /**
   * print the state of this animation as a string.
   * @return returns the state of this animation as a string.
   */
  String toString();

  /**
   * This method is used to get the time this shape is changed from.
   * @return the time this animation begins.
   */
  float getFromTime();

  /**
   * This method is used to get the time this animation is changed until.
   * @return the time this animation ends.
   */
  float getToTime();

  /**
   * This is a visitor used to add new functionality to an AnimationOperations object.
   * @param v the visitor we are visiting.
   * @param <T> the type the method of the visitor is going to be returning.
   * @return returns the type the visitor would like to return.
   */
  <T> T accept(AnimationVisitor<T> v);

  /**
   * Returns the value this animation is changing from. (Used when creating a new AnimationView)
   * @param <T> We are using generics because each animation is changing a different attribute.
   * @return returns the value the animation is changing from.
   */
  <T> T getFrom();

  /**
   * Returns the value this animation is changing to.
   * @param <T> We are using generics because each animation is changing a different attribute.
   * @return returns the value the animation is changing to.
   */
  <T> T getTo();

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
  boolean hasNoConflictsWith(ShapeOperations stateAtFromTime, ShapeOperations stateAtToTime,
                             AnimationOperations a);

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
  boolean noConflictsWithHelper(ShapeOperations givenFrom,
                                ShapeOperations givenTo, float givenFromTime,
                                float givenToTime);

  /**
   * This method is used to set the name of the shape this animation is changing. We set the name
   * of the shape in the shape so that any shape can use any animation if said animation is
   * added to the shape.
   * @param shapeName - the name of the shape this animation will be changing.
   */
  void setShapeToBeAnimated(String shapeName);

}
