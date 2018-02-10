package project.animator.runanimation.util;

/**
 * This interface contains all the methods that the AnimationFileReader class
 * calls as it reads a file containing the animation and builds a model It is
 * parameterized over the actual model type.
 */

public interface TweenLayerModelBuilder<T> extends TweenModelBuilder<T> {

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
  TweenLayerModelBuilder<T> addOval(
          String name,
          float cx, float cy,
          float xRadius, float yRadius,
          float red, float green, float blue,
          int startOfLife, int endOfLife, int layer);

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
  TweenLayerModelBuilder<T> addRectangle(
          String name,
          float lx, float ly,
          float width, float height,
          float red, float green, float blue,
          int startOfLife, int endOfLife, int layer);

  /**
   * Represents the background color of our animation.
   * @param red the red value of the background color.
   * @param green the green value of the background color.
   * @param blue the blue value of the background color.
   * @return the builder object.
   */
  TweenLayerModelBuilder<T> addBackground(
          float red, float green, float blue);

}
