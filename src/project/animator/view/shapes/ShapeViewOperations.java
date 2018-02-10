package project.animator.view.shapes;

import java.awt.Graphics;
import java.util.ArrayList;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.animations.AnimationViewOperations;

/**
 * Represents the functionality of any shape view object.
 */
public interface ShapeViewOperations extends ShapeOperations {

  /**
   * Draws this shape using java swing.
   * @param g the graphics we are using to draw this shape.
   * @param currentTime the time we are drawing the animation at.
   */
  void draw(Graphics g, float currentTime);

  /**
   * Used to add a view animation to this shape.
   * @param toBeAdded The view animation we are going to add, can be multiple.
   */
  void addAnimation(AnimationViewOperations... toBeAdded);

  /**
   * Used to return the animation views of this shape.
   * @return returns the animation views of this shape.
   */
  ArrayList<AnimationViewOperations> getViewAnimations();


  /**
   * Used to print this shape in svg format.
   * @param tempo the rate we want to play the animation at.
   * @param loop if true the svg will loop.
   * @return returns this shape in svg format.
   */
  String printAsSVG(int tempo, boolean loop);

  /**
   * Prints this shape as a string using the tempo to make ticks into seconds.
   * @param tempo the rate we are playing this animation at.
   * @return returns this animation as a string.
   */
  String toViewString(int tempo);
}
