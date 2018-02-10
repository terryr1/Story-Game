package project.animator.model;

import java.awt.*;

import project.animator.model.shapes.ShapeOperations;

public interface AnimatorLayerModelOperations extends AnimatorModelOperations {

  /**
   * Used to add a shape to this model at the given layer.
   * @param s adds s to the list of shapes in this model.
   */
  void addShapeToLayer(ShapeOperations s, int layer);

  /**
   * A getter method that gets the color of the background of our model.
   * @return a Color representing the background color
   */
  Color getBackgroundColor();

  /**
   * A setter method that sets the color of the background of our model.
   * @param backgroundColor - the color we are setting the background to.
   */
  void setBackgroundColor(Color backgroundColor);
}
