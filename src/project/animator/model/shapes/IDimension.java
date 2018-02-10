package project.animator.model.shapes;

/**
 * Represents the functionality of a Dimension Object.
 */
public interface IDimension {

  /**
   * Gets the type of this dimension (Height, Width, etc.).
   * @return the type of this dimension.
   */
  String getType();

  /**
   * Gets the value of this dimension.
   * @return the value of this dimension.
   */
  float getValue();
}
