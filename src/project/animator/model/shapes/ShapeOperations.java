package project.animator.model.shapes;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.animations.AnimationOperations;
import project.animator.view.visitors.ShapeVisitor;

/**
 * represents the functionality of a shape object.
 */
public interface ShapeOperations {

  /**
   * Used to change the color of this shape from the fromColor to the toColor over the given time
   * interval at the currentTime.
   * @param fromColor - the color we are changing from.
   * @param toColor - the color we are  changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  void changeColor(Color fromColor, Color toColor, float from, float to,
                   float currentTime);

  /**
   * Used to change the angle of this shape from the fromAngle to the toAngle over the given time
   * interval at the currentTime.
   * @param fromAngle - the angle we are changing from.
   * @param toAngle - the angle we are  changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  void changeAngle(float fromAngle, float toAngle, float from, float to,
                   float currentTime);

  /**
   * Used to change the position of this shape from the fromPosn to the toPosn over the given time
   * interval at the currentTime.
   * @param fromPosn - the position we are changing from.
   * @param toPosn - the position we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  void changePosition(IPosn fromPosn, IPosn toPosn, float from, float to,
                      float currentTime);

  /**
   * Used to change the dimensions of this shape from the fromDimension to the toDimension over the
   * given time interval at the currentTime.
   * @param fromDimension - the dimensions we are changing from.
   * @param toDimension - the dimensions we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  void changeDimension(ArrayList<IDimension> fromDimension, ArrayList<IDimension> toDimension,
                       float from, float to, float currentTime);

  /**
   * gets the dimensions array of this shape.
   * @return the dimensions array of this shape as an ArrayList.
   */
  ArrayList<IDimension> getDimensions();

  /**
   * gets the angle of this shape.
   * @return the angle of this shape as a float.
   */
  float getAngle();

  /**
   * gets the name of this shape.
   * @return the name of this shape.
   */
  String getName();

  /**
   * gets the position of this shape.
   * @return the position of this shape as a Posn.
   */
  IPosn getPosition();

  /**
   * gets the color of this shape.
   * @return the color of this shape as a Color.
   */
  Color getColor();

  /**
   * gets the time this shape appears.
   * @return the time this shape appears.
   */
  float getAppearTime();

  /**
   * gets the time this shape disappears.
   * @return the time shape disappears.
   */
  float getDisappearTime();

  /**
   * Returns the array list of this shapes animations.
   * @return a copy of the array list of this shapes animations.
   */
  ArrayList<AnimationOperations> getAnimations();

  /**
   * Used to add an animation to this Shape's animations.
   * @param toBeAdded the animation we are added.
   */
  void addAnimation(AnimationOperations... toBeAdded);

  /**
   * Used to run all the animation this shape has.
   * @param currentTime The time we want to get the animation at.
   */
  void animate(float currentTime);

  /**
   * Used to hide this shape.
   */
  void hide();

  /**
   * Used to remove an animation from this Shape's animations.
   * @param toBeRemoved the animation we are removing.
   */
  void removeAnimation(AnimationOperations... toBeRemoved);

  /**
   * returns a new Shape with all the same fields as this Shape.
   * @return a new Shape with identical fields to this Shape.
   */
  ShapeOperations makeClone();

  /**
   * Used to add functionality to this shape.
   * @param v The visitor that's visiting us.
   * @param <T> The type the visitor is returning.
   * @return The type the visitor method is returning.
   */
  <T> T accept(ShapeVisitor<T> v);

  /**
   * returns this AShape as a String.
   * @return this AShape as a String.
   */
  String toString();
}
