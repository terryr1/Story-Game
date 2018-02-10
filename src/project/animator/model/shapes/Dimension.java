package project.animator.model.shapes;

/**
 * Represents a position with an x and y coordinate.
 */
public class Dimension implements IDimension {

  private String type;
  private float value;

  /**
   * constructs a dimension with a given type and value.
   * @param type - what type of dimension this is(Height, Width, etc)
   * @param value - the value of this dimension
   */
  public Dimension(String type, float value) {
    this.type = type;
    this.value = value;
  }

  /**
   * Gets the type of this dimension.
   * @return the type of this dimension.
   */
  @Override
  public String getType() {
    return this.type;
  }

  /**
   * Gets the value of this dimension.
   * @return the value of this dimension.
   */
  @Override
  public float getValue() {
    return this.value;
  }

  /**
   * Checks if the given object is equal to this dimension.
   * @param obj the object we are comparing to this dimension.
   * @return returns true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof  Dimension) {
      Dimension toBeCompared = (Dimension) obj;
      return Math.abs(toBeCompared.getValue() - this.value) <= .001 &&
              this.type.equals(toBeCompared.getType());
    }
    else {
      return false;
    }
  }

  /**
   * Returns a haschode consistent with this.equals.
   * @return returns the sum of the haschodes of the fields of this Dimension.
   */
  @Override
  public int hashCode() {
    Float thisValue = this.value;
    return thisValue.hashCode() + type.hashCode();
  }

}
