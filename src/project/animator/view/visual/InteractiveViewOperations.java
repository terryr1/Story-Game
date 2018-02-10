package project.animator.view.visual;

import java.awt.event.ActionListener;

import project.animator.view.AnimatorViewOperations;

public interface InteractiveViewOperations extends AnimatorViewOperations {

  /**
   * This method is used to get the tempo of this animation.
   * @return The tempo of this animation.
   */
  int getTempo();

  /**
   * This method is used to toggle the loop function of the view.
   */
  void loop();

  /**
   * This method pauses the animation if its running.
   */
  void pause();

  /**
   * This method plays the animation if its paused.
   */
  void play();

  /**
   * This method is used to set the tempo of our animation.
   * @param tempo - the tempo this view is running at.
   */
  void setTempo(int tempo);

  /**
   * This method resets all the shapes in the animation and runs it at the beginning.
   */
  void reset();

  /**
   * This method is used by a controller to add an action listener to our buttons.
   * @param controller - the controller we are using to control our view.
   */
  void setActionListener(ActionListener controller);

  /**
   * Used to export this animation as an svg file
   */
  void exportSVG();

}
