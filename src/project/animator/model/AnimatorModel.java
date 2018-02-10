package project.animator.model;

import java.awt.Color;
import java.util.ArrayList;

import project.animator.model.animations.AnimationOperations;
import project.animator.model.animations.ColorAnimation;
import project.animator.model.animations.MoveAnimation;
import project.animator.model.animations.ScaleAnimation;
import project.animator.model.shapes.IDimension;
import project.animator.runanimation.util.TweenModelBuilder;
import project.animator.model.shapes.Dimension;
import project.animator.model.shapes.Oval;
import project.animator.model.shapes.Posn;
import project.animator.model.shapes.Rectangle;
import project.animator.model.shapes.ShapeOperations;

/**
 * represents a model for our animator which handles constructing our animation.
 */
public class AnimatorModel implements AnimatorModelOperations {

  /**
   * currentShapes - the shapes in our model right now.
   */
  private ArrayList<ShapeOperations> currentShapes;

  public AnimatorModel() {
    currentShapes = new ArrayList<>();
  }

  /**
   * Represents the Builder for this model.
   */
  public static final class Builder implements TweenModelBuilder<AnimatorModelOperations> {
    private AnimatorModelOperations model = new AnimatorModel();

    /**
     * Add a new oval to the model with the given specifications.
     *
     * @param name        the unique name given to this shape
     * @param cx          the x-coordinate of the center of the oval
     * @param cy          the y-coordinate of the center of the oval
     * @param xRadius     the x-radius of the oval
     * @param yRadius     the y-radius of the oval
     * @param red         the red component of the color of the oval
     * @param green       the green component of the color of the oval
     * @param blue        the blue component of the color of the oval
     * @param startOfLife the time tick at which this oval appears
     * @param endOfLife   the time tick at which this oval disappears
     * @return the builder object
     */
    public TweenModelBuilder<AnimatorModelOperations> addOval(
            String name,
            float cx, float cy,
            float xRadius, float yRadius,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {

      this.model.addShape(new Oval(xRadius, yRadius, 0, new Posn(cx, cy),
              new Color(red, green, blue), name, startOfLife, endOfLife, true));

      return this;
    }

    /**
     * Add a new rectangle to the model with the given specifications.
     *
     * @param name        the unique name given to this shape
     * @param lx          the x-coordinate of the lower left corner of the
     *                    rectangle
     * @param ly          the y-coordinate of the lower left corner of the
     *                    rectangle
     * @param width       the width of the rectangle
     * @param height      the height of the rectangle
     * @param red         the red component of the color of the rectangle
     * @param green       the green component of the color of the rectangle
     * @param blue        the blue component of the color of the rectangle
     * @param startOfLife the time tick at which this rectangle appears
     * @param endOfLife   the time tick at which this rectangle disappears
     * @return the builder object
     */
    public TweenModelBuilder<AnimatorModelOperations> addRectangle(
            String name,
            float lx, float ly,
            float width, float height,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {
      this.model.addShape(new Rectangle(width, height, 0, new Posn(lx, ly),
              new Color(red, green, blue), name, startOfLife, endOfLife));
      return this;
    }

    /**
     * Move the specified shape to the given position during the given time
     * interval.
     *
     * @param name      the unique name of the shape to be moved
     * @param moveFromX the x-coordinate of the initial position of this shape.
     *                  What this x-coordinate represents depends on the shape.
     * @param moveFromY the y-coordinate of the initial position of this shape.
     *                  what this y-coordinate represents depends on the shape.
     * @param moveToX   the x-coordinate of the final position of this shape. What
     *                  this x-coordinate represents depends on the shape.
     * @param moveToY   the y-coordinate of the final position of this shape. what
     *                  this y-coordinate represents depends on the shape.
     * @param startTime the time tick at which this movement should start
     * @param endTime   the time tick at which this movement should end
     */
    public TweenModelBuilder<AnimatorModelOperations> addMove(
            String name,
            float moveFromX, float moveFromY, float moveToX, float moveToY,
            int startTime, int endTime) {
      Posn fromPosn = new Posn(moveFromX, moveFromY);
      Posn toPosn = new Posn(moveToX, moveToY);
      this.model.addAnimation(name, new MoveAnimation(fromPosn, toPosn, startTime, endTime));
      return this;
    }

    /**
     * Change the color of the specified shape to the new specified color in the
     * specified time interval.
     *
     * @param name      the unique name of the shape whose color is to be changed
     * @param oldR      the r-component of the old color
     * @param oldG      the g-component of the old color
     * @param oldB      the b-component of the old color
     * @param newR      the r-component of the new color
     * @param newG      the g-component of the new color
     * @param newB      the b-component of the new color
     * @param startTime the time tick at which this color change should start
     * @param endTime   the time tick at which this color change should end
     */
    public TweenModelBuilder<AnimatorModelOperations> addColorChange(
            String name,
            float oldR, float oldG, float oldB, float newR, float newG, float newB,
            int startTime, int endTime) {
      Color fromColor = new Color(oldR, oldG, oldB);
      Color toColor = new Color(newR, newG, newB);
      this.model.addAnimation(name, new ColorAnimation(fromColor, toColor, startTime, endTime));
      return this;
    }

    /**
     * Change the x and y extents of this shape from the specified extents to the
     * specified target extents. What these extents actually mean depends on the
     * shape, but these are roughly the extents of the box enclosing the shape.
     */
    public TweenModelBuilder<AnimatorModelOperations> addScaleToChange(String name, float fromSx,
                                                                       float fromSy, float toSx,
                                                                       float toSy, int startTime,
                                                                       int endTime) {

      ShapeOperations shape = this.model.getShape(name);
      ArrayList<IDimension> fromDimensions = new ArrayList<>();
      fromDimensions.add(new Dimension(shape.getDimensions().get(0).getType(), fromSx));
      fromDimensions.add(new Dimension(shape.getDimensions().get(1).getType(), fromSy));


      ArrayList<IDimension> toDimensions = new ArrayList<>();
      toDimensions.add(new Dimension(shape.getDimensions().get(0).getType(), toSx));
      toDimensions.add(new Dimension(shape.getDimensions().get(1).getType(), toSy));

      this.model.addAnimation(name, new ScaleAnimation(fromDimensions, toDimensions, startTime,
              endTime));
      return this;
    }

    /**
     * Return the model built so far.
     *
     * @return the model that was constructed so far
     */
    public AnimatorModelOperations build() {
      return this.model;
    }

  }

  /**
   * Used to remove an animation from this model.
   * @param shapeName - the name of the shape we want to remove from.
   * @param anim - represents the animation to be removed.
   */
  @Override
  public void removeAnimation(String shapeName, AnimationOperations anim) {
    for (ShapeOperations s: currentShapes) {
      if (s.getName().equals(shapeName)) {
        s.removeAnimation(anim);
      }
    }
  }

  /**
   * Used to remove a shape from this model.
   * @param name removes the shape with the given name from the list of shapes in this model.
   */
  @Override
  public void removeShape(String name) {
    ShapeOperations toBeRemoved = null;
    for (ShapeOperations s: this.currentShapes) {
      if (s.getName().equals(name)) {
        toBeRemoved = s;
        break;
      }
    }
    this.currentShapes.remove(toBeRemoved);
  }

  /**
   * Used to add a new animation to this model.
   * @param shapeName - the name of the shape we are adding the animation to.
   * @param anim - represents the animation to be added.
   */
  @Override
  public void addAnimation(String shapeName, AnimationOperations anim) {
    //this will add the animation to the list of animations in the proper shape
    for (ShapeOperations s : currentShapes) {
      if (s.getName().equals(shapeName)) {
        s.addAnimation(anim);
      }
    }
  }

  /**
   * Used to add a shape to this model.
   * @param s adds s to the list of shapes in this model.
   */
  @Override
  public void addShape(ShapeOperations s) {
    currentShapes.add(s);
  }

  /**
   * Returns the shape with the given name from the model. Throws an exception if the shape
   * doesn't exist.
   * @param name The shape we are looking to return.
   * @return The shape with the given name.
   */
  @Override
  public ShapeOperations getShape(String name) {
    for (ShapeOperations s: this.currentShapes) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    throw new IllegalArgumentException("The given shape doesn't exist!");
  }

  /**
   * Checks if the given shape is in this model.
   * @param name The name of the shape we want to know about.
   * @return true if the shape is in the model, false if not.
   */
  @Override
  public boolean contains(String name) {
    for (ShapeOperations s: this.currentShapes) {
      if (s.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Used to hide a shape.
   * @param name - the shape to be hidden.
   */
  @Override
  public void hideShape(String name) {
    for (ShapeOperations s: this.currentShapes) {
      if (s.getName().equals(name)) {
        s.hide();
      }
    }
  }

  /**
   * Used to return the current state of our animation.
   */
  @Override
  public ArrayList<ShapeOperations> getState() {
    ArrayList<ShapeOperations> copy = new ArrayList<>();
    copy.addAll(this.currentShapes);
    return copy;
  }

  /**
   * Used to return this model as a string.
   */
  @Override
  public String toString() {
    String state = "";

    for (ShapeOperations s : currentShapes) {
      state += s.toString() + "\n";
    }

    for (ShapeOperations s : currentShapes) {
      for (AnimationOperations a : s.getAnimations()) {
        state += a.toString() + "\n";
      }
    }

    return state.substring(0, state.length() - 1);
  }

}
