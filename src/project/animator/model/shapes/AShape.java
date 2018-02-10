package project.animator.model.shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import project.animator.model.animations.AnimationOperations;

/**
 * Represents any shape Object.
 */
public abstract class AShape implements ShapeOperations {

  /**
   * dimensions - an array of dimensions specific to the instance of shape you are in.
   * angle - the angle of this shape.
   * position - the position of this shape.
   * color - the color of this shape.
   * appears - the time this shape appears in our animation.
   * disappears - the time this shape disappears in our animation.
   * name - the name of this shape.
   * animations - all the animations this shape goes through.
   */
  protected ArrayList<IDimension> dimensions;
  protected float prevTick;
  protected float angle;
  protected IPosn position;
  protected Color color;
  protected final float ogAppears;
  protected float appears;
  protected final float disappears;
  protected final String name;
  protected ArrayList<AnimationOperations> animations;

  /**
   * The constructor for a shape.
   * @param angle - the angle this shape is at (used for rotation animations).
   * @param position - the position this shape is at.
   * @param color - the color of this shape.
   * @param name - the name of this shape.
   * @param appears - the time this shape begins to be visible in our animation.
   * @param disappears - the time the shape will no longer be visible in our animation.
   * @IllegalArgumentException - if the given from time occurs after the to time.
   */
  AShape(float angle, IPosn position, Color color, String name, float appears, float disappears) {
    prevTick = 0;
    this.animations = new ArrayList<>();
    this.angle = angle;
    this.position = position;
    this.color = color;
    this.name = name;
    this.dimensions = new ArrayList<>();
    if (appears > disappears) {
      throw new IllegalArgumentException("Appear time can't be over disappear time!!");
    }
    else {
      this.appears = appears;
      this.ogAppears = appears;
      this.disappears = disappears;
    }
  }

  //METHODS TO ANIMATE THIS SHAPE

  /**
   * Used to remove an animation from this Shape's animations.
   * @param toBeRemoved the animation we are removing.
   */
  @Override
  public void removeAnimation(AnimationOperations... toBeRemoved) {
    for (AnimationOperations a: toBeRemoved) {
      this.animations.remove(a);
    }
  }

  /**
   * Used to add an animation to this Shape's animations.
   * @param toBeAdded the animation we are added.
   */
  @Override
  public void addAnimation(AnimationOperations... toBeAdded) {
    for (AnimationOperations a: toBeAdded) {
      if (this.hasNoAnimationConflicts(a)) {
        a.setShapeToBeAnimated(this.name);
        animations.add(0, a);
      } else {
        throw new IllegalArgumentException("The animation you passed in conflicts with this shapes"
                + "previous animations!!");
      }
    }

    animations.sort(new AnimationComparator());

  }

  /**
   * Used to sort these animations by time.
   */
  private class AnimationComparator implements Comparator<AnimationOperations> {

    /**
     * Compares animations by time.
     * @param o1 the first animation we are comparing.
     * @param o2 the second animation we are comparing.
     * @return 0 if equal, 1 if greater, -1 if less.
     */
    @Override
    public int compare(AnimationOperations o1, AnimationOperations o2) {
      if (o1.getFromTime() < o2.getFromTime()) {
        return -1;
      }
      else if (o1.getFromTime() > o2.getFromTime()) {
        return 1;
      }
      else {
        return 0;
      }

    }
  }

  /**
   * Used to run all the animation this shape has.
   * @param currentTime The time we want to get the animation at.
   */
  @Override
  public void animate(float currentTime) {
    float tick = prevTick;

    while (tick != currentTime) {
      for (AnimationOperations a : this.animations) {
        a.animate(this, tick);
      }
      if (tick < currentTime) {
        tick++;
      } else {
        tick--;
      }
    }

    for (AnimationOperations a : this.animations) {
      a.animate(this, currentTime);
    }
    this.prevTick = currentTime;
  }

  /**
   * Makes sure none of the animations in this shape cause conflicts with the given animation.
   * @param toBeAdded the animation we are trying to add to this shape.
   * @return returns true of there are no conflicts.
   */
  protected boolean hasNoAnimationConflicts(AnimationOperations toBeAdded) {
    boolean noConflicts = true;

    for (AnimationOperations a : this.animations) {
      ShapeOperations stateAtFromTime = this.makeClone();
      ShapeOperations stateAtToTime = this.makeClone();
      a.animate(stateAtFromTime, toBeAdded.getFromTime());
      a.animate(stateAtToTime, toBeAdded.getToTime());
      noConflicts = noConflicts && a.hasNoConflictsWith(stateAtFromTime, stateAtToTime, toBeAdded);
    }

    return noConflicts;
  }

  //CHANGE METHODS

  /**
   * Used to change the color of this shape from the fromColor to the toColor over the given time
   * interval at the currentTime.
   * @param fromColor - the color we are changing from.
   * @param toColor - the color we are  changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  @Override
  public void changeColor(Color fromColor, Color toColor, float from, float to,
                          float currentTime) {
    if (currentTime >= this.appears && currentTime <= this.disappears) {
      this.color = new Color((int) changeHelper(fromColor.getRed(),
              toColor.getRed(), from, to, currentTime) % 256,
              (int) changeHelper(fromColor.getGreen(),
                      toColor.getGreen(), from, to, currentTime) % 256,
              (int) changeHelper(fromColor.getBlue(),
                      toColor.getBlue(), from, to, currentTime) % 256);
    }

  }

  /**
   * Used to change the angle of this shape from the fromAngle to the toAngle over the given time
   * interval at the currentTime.
   * @param fromAngle - the angle we are changing from.
   * @param toAngle - the angle we are  changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  @Override
  public void changeAngle(float fromAngle, float toAngle, float from, float to,
                          float currentTime) {
    if (currentTime >= this.appears && currentTime <= this.disappears) {
      this.angle = changeHelper(fromAngle, toAngle, from, to, currentTime);
    }

  }

  /**
   * Used to change the position of this shape from the fromPosn to the toPosn over the given time
   * interval at the currentTime.
   * @param fromPosn - the position we are changing from.
   * @param toPosn - the position we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  @Override
  public void changePosition(IPosn fromPosn, IPosn toPosn, float from, float to,
                             float currentTime) {
    if (currentTime >= this.appears && currentTime <= this.disappears) {
      this.position = new Posn(changeHelper(fromPosn.getX(), toPosn.getX(), from, to, currentTime),
              changeHelper(fromPosn.getY(), toPosn.getY(), from, to, currentTime));
    }

  }

  /**
   * Used to change the dimensions of this shape from the fromDimension to the toDimension over the
   * given time interval at the currentTime.
   * @param fromDimension - the dimensions we are changing from.
   * @param toDimension - the dimensions we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we are at right now.
   */
  @Override
  public void changeDimension(ArrayList<IDimension> fromDimension,
                              ArrayList<IDimension> toDimension, float from, float to,
                              float currentTime) {

    if (fromDimension.size() != toDimension.size()
            || fromDimension.size() != this.dimensions.size()) {
      throw new IllegalArgumentException("Not changing the same number of dimensions!");
    }
    else if (currentTime >= this.appears && currentTime <= this.disappears || from < to) {
      for (int i = 0; i < fromDimension.size(); i++) {
        this.dimensions.set(i, new Dimension(dimensions.get(i).getType(),
                changeHelper(fromDimension.get(i).getValue(), toDimension.get(i).getValue(),
                        from, to, currentTime)));
      }
    }

  }

  /**
   * A helper method which properly changes the animation from the from attribute to the to
   * attribute over the given time interval.
   * @param fromAttribute - the attribute we are changing from.
   * @param toAttribute - the attribute we are changing to.
   * @param from - the time we are changing from.
   * @param to - the time we are changing until.
   * @param currentTime - the time we want to get the change at.
   * @return returns the given values properly changed.
   */
  protected float changeHelper(float fromAttribute, float toAttribute, float from, float to,
                               float currentTime) {
    return fromAttribute * ((to - currentTime) / (to - from)) + toAttribute *
            ((currentTime - from) / (to - from));
  }



  //GET METHODS

  /**
   * Used to get the time this shape appears.
   * @return the time this shape appears.
   */
  @Override
  public float getAppearTime() {
    return this.appears;
  }

  /**
   * Used to get the time this shape disappears.
   * @return the time this shape appears.
   */
  @Override
  public float getDisappearTime() {
    return this.disappears;
  }

  /**
   * Returns the array list of this shapes animations.
   * @return a copy of the array list of this shapes animations.
   */
  @Override
  public ArrayList<AnimationOperations> getAnimations() {
    ArrayList<AnimationOperations> copy = new ArrayList<>();
    copy.addAll(this.animations);
    return copy;
  }

  /**
   * Gets the dimensions array of this shape.
   * @return the dimensions array of this shape as an ArrayList.
   */
  @Override
  public ArrayList<IDimension> getDimensions() {
    ArrayList<IDimension> copy = new ArrayList<>();
    copy.addAll(this.dimensions);
    return copy;
  }

  /**
   * Returns the name of this shape.
   * @return returns the name of this shape.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * gets the angle of this shape.
   * @return the angle of this shape as an Float.
   */
  @Override
  public float getAngle() {
    return this.angle;
  }

  /**
   * gets the position of this shape.
   * @return the position of this shape as a Posn.
   */
  @Override
  public IPosn getPosition() {
    return this.position;
  }

  /**
   * gets the color of this shape.
   * @return the color of this shape as a Color.
   */
  @Override
  public Color getColor() {
    return this.color;
  }

  //TO STRING METHODS TO HELP WITH TO STRING

  /**
   * Helper returns the postition of this shape as a String.
   * @return the postition of this shape.
   */
  protected abstract String posnToString();

  /**
   * Helper returns the angle of this shape as a String.
   * @return the angle of this shape as a String.
   */
  protected String angleToString() {
    return "Angle: " + this.angle;
  }

  /**
   * Helper to return the color of this shape as a String.
   * @return the color of this shape as a String.
   */
  protected String colorToString() {
    return "Color: (" + this.color.getRed() + "," + this.color.getBlue() + ","
            + this.color.getGreen() + ")";
  }

  /**
   * Helper to return the dimensions of this string as a String.
   * @return the dimensions of this shape as a String.
   */
  protected String dimensionsToString() {
    return this.dimensions.get(0).getType() + ": " + this.dimensions.get(0).getValue() + ", " +
            this.dimensions.get(1).getType() + ": " + this.dimensions.get(1).getValue();
  }

  /**
   * Helper to return the type of this shape.
   * @return the type of this shape as a String.
   */
  protected abstract String typeToString();

  //OTHER METHODS

  /**
   * Gets this shape as a string using all the helper methods above.
   * @return shape as a string.
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "\n" + this.typeToString() + "\n" + this.posnToString()
            + ", " + this.dimensionsToString() + ", " + this.colorToString() + ", "
            + this.angleToString() + "\nAppears at t=" + this.appears + "\nDisappears at t="
            + this.disappears;
  }

  //EQUALITY

  /**
   * Checks if the given object is equal to this Oval.
   * @param obj the object we are comparing to this Oval.
   * @return returns true if they are equals, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AShape) {
      AShape toBeCompared = (AShape) obj;
      return this.dimensions.equals(toBeCompared.getDimensions())
              && this.angle - toBeCompared.getAngle() <= .001
              && this.position.equals(toBeCompared.getPosition())
              && this.color.equals(toBeCompared.getColor())
              && this.appears - toBeCompared.getAppearTime() <= .001
              && this.disappears - toBeCompared.getDisappearTime() <= .001
              && this.name.equals(toBeCompared.getName());
    }
    else {
      return false;
    }
  }

  /**
   * Returns a haschode consistent with this.equals.
   * @return returns the sum of the codes of the fields of this Oval.
   */
  @Override
  public int hashCode() {
    Float thisAngle = this.angle;
    Float thisAppears = this.appears;
    Float thisDisappears = this.disappears;

    return this.dimensions.hashCode() + thisAngle.hashCode() + this.position.hashCode()
            + this.color.hashCode() + thisAppears.hashCode() + thisDisappears.hashCode()
            + this.name.hashCode();
  }

  @Override
  public void hide() {
    if(Math.abs(this.appears - this.ogAppears) < .001) {
      this.appears = this.disappears;
    }
    else {
      this.appears = this.ogAppears;
    }

  }

}

