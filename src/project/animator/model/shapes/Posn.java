package project.animator.model.shapes;

/**
 * Represents a position with an x and y coordinate.
 */
public class Posn implements IPosn {

  /**
   * x - the x coord of this posn.
   * y - the y coord of this posn.
   */
  private float x;
  private float y;

  /**
   * constructs a position with a given x and y coordinate.
   * @param x - the x coord of this posn
   * @param y - the y coord of this posn
   */
  public Posn(float x, float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x coord of this posn.
   * @return the x coord of this posn.
   */
  @Override
  public float getX() {
    return this.x;
  }

  /**
   * Gets the y coord of this posn.
   * @return the y coord of this posn.
   */
  @Override
  public float getY() {
    return this.y;
  }

  /**
   * Checks if the given object is equal to this posn.
   * @param obj the object we are comparing to this posn.
   * @return returns true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof  Posn) {
      Posn toBeCompared = (Posn) obj;
      return Math.abs(toBeCompared.getX() - this.x)
              <= .001 && Math.abs(toBeCompared.getY() - this.y) <= .001;
    }
    else {
      return false;
    }
  }

  /**
   * Returns a haschode consistent with this.equals.
   * @return returns the sum of the fields of this Posn.
   */
  @Override
  public int hashCode() {
    Float thisX = this.x;
    Float thisY = this.y;
    return thisX.hashCode() + thisY.hashCode();
  }

}
