package project.animator.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import project.animator.model.AnimatorLayerModelOperations;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visual.ExtendedInteractiveView;

/**
 * This class is used to control any interactive animation view.
 */
public class Controller implements ControllerOperations, ChangeListener {

  private AnimatorLayerModelOperations model;
  private ExtendedInteractiveView view;

  /**
   * Changes the tick according to the state of the slider.
   * @param e the change event that's occurred.
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    view.setTick(source.getValue());
  }

  /**
   * This method is used to tell if a button has been pressed.
   * @param e The action event we are checking for action from.
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "PLAY":
        this.playClick();
        break;
      case "PAUSE":
        this.pauseClick();
        break;
      case "RESET":
        this.stopClick();
        break;
      case "LOOP":
        this.loopClick();
        break;
      case "EXPORT":
        view.exportSVG();
        break;
      case "SET COLOR":
        view.chooseColor();
        break;
      case "SPEED UP":
        this.speedUpClick();
        break;
      case "SPEED":
        if (e.getSource() instanceof JTextField) {
          JTextField tf = (JTextField) e.getSource();
          this.setViewTempo(tf.getText());
        }
        break;
      case "SLOW DOWN":
        this.slowDownClick();
        break;
      default:
        if (e.getSource() instanceof JCheckBox) {
          JCheckBox cb = (JCheckBox) e.getSource();
          this.shapeSelected(e.getActionCommand(), cb.isSelected());
        }
        break;
    }
  }

  /**
   * This method is used to add the model.
   * @param m the model we are adding to our controller.
   */
  @Override
  public void addModel(AnimatorLayerModelOperations m) {
    this.model = m;
  }

  /**
   * This method is used to add the view.
   * @param v the view we are adding to our controller.
   */
  @Override
  public void addView(ExtendedInteractiveView v) {
    this.view = v;
    this.view.setActionListener(this);
    this.view.setChangeListener(this);
  }

  /**
   * This method is used to start the animator.
   * @param tempo the tempo we are running at.
   */
  public void startAnimator(int tempo) {
    this.view.setBackground(model.getBackgroundColor());
    this.view.playAnimation(tempo, this.model.getState());
  }

  /**
   * This method is used to add and remove shapes while the animation is running.
   * @param shapeName the shape we are adding or removing.
   * @param isSelected is the check box selected or not.
   */
  private void shapeSelected(String shapeName, boolean isSelected) {
    ShapeOperations shapeToBeRemoved = this.model.getShape(shapeName);

    if (!isSelected) {
      model.hideShape(shapeToBeRemoved.getName());
      view.playAnimation(view.getTempo(), model.getState());
      view.play();
    }
    else {
      model.hideShape(shapeToBeRemoved.getName());
      view.playAnimation(view.getTempo(), model.getState());
      view.play();
    }
  }

  /**
   * This method is used to loop or not loop the animation.
   */
  private void loopClick() {
    this.view.loop();
  }

  /**
   * This method is used to pause the view.
   */
  private void stopClick() {
    this.view.reset();
  }

  /**
   * This method is used to change the tempo of the view.
   * @param tempo the tempo we want to change the view to.
   */
  private void setViewTempo(String tempo) {
    int viewTempo = Integer.parseInt(tempo);
    this.view.setTempo(viewTempo);
  }

  /**
   * This method is used to increase the tempo of the view.
   */
  private void speedUpClick() {
    this.view.setTempo(this.view.getTempo() + 1);
  }

  /**
   * This method is used to decrease the tempo of the view.
   */
  private void slowDownClick() {
    if (this.view.getTempo() > 1) {
      this.view.setTempo(this.view.getTempo() - 1);
    }
  }

  /**
   * This method is used to play the view.
   */
  private void playClick() {
    this.view.play();
  }

  /**
   * This method is used to pause the model.
   */
  private void pauseClick() {
    this.view.pause();
  }

}
