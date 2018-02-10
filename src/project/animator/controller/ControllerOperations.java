package project.animator.controller;

import java.awt.event.ActionEvent;

import project.animator.model.AnimatorLayerModelOperations;
import project.animator.view.visual.ExtendedInteractiveView;

public interface ControllerOperations extends  java.awt.event.ActionListener {

  /**
   * This method is used to add the model.
   * @param m the model we are adding to our controller.
   */
  void addModel(AnimatorLayerModelOperations m);

  /**
   * This method is used to add the view.
   * @param v the view we are adding to our controller.
   */
  void addView(ExtendedInteractiveView v);

  /**
   * This method is used to start the animator.
   * @param tempo the tempo we are running at.
   */
  void startAnimator(int tempo);

  /**
   * This method is used to tell if a button has been pressed.
   * @param e The action event we are checking for action from.
   */
  void actionPerformed(ActionEvent e);

}
