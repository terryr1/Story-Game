package project.animator.view.visual;

import java.awt.*;
import javax.swing.event.ChangeListener;

public interface ExtendedInteractiveView extends InteractiveViewOperations {

  /**
   * Sets the background color of this view to the given Color.
   * @param background - The color that we will change the background to
   */
  void setBackground(Color background);

  /**
   * Sets the background color of this view to a chosen Color.
   */
  void chooseColor();

  /**
   * This method is used by a controller to add a change listener to the slider.
   * @param controller - the controller we are using to control our view.
   */
  void setChangeListener(ChangeListener controller);

  /**
   * This method is used to set the frame of our animation.
   * @param tick the frame we want to be at.
   */
  void setTick(int tick);

}
