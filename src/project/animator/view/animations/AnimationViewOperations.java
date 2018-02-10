package project.animator.view.animations;

import project.animator.model.animations.AnimationOperations;

/**
 * Represents the functionality of an animation view object.
 * This adds views to our original animations.
 */
public interface AnimationViewOperations extends AnimationOperations {

  /**
   * Prints this animation into an svg format.
   * @param tempo used to convert the time in ticks into seconds for the svg.
   * @param loop if true the svg will loop.
   * @return returns a string of this animation formatted in svg.
   */
  String printAsSVG(int tempo, boolean loop);


  /**
   * Prints this animation as a String using the given tempo.
   * @param tempo Used to convert the time in ticks into seconds.
   * @return returns a string representing this animation.
   */
  String toViewString(int tempo);
}
