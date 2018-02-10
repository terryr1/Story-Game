package project.animator.view;

import java.util.ArrayList;

import project.animator.model.shapes.ShapeOperations;

/**
 * Represents the functionality of an VisualView object.
 */
public interface AnimatorViewOperations {

  /**
   * Plays the animation according to the tick rate of the view.
   */
  void playAnimation(int tempo, ArrayList<ShapeOperations> state);

}
