package project.animator.view.shapes;


import java.awt.*;
import java.util.ArrayList;

import project.animator.model.animations.AnimationOperations;
import project.animator.model.shapes.IPosn;
import project.animator.model.shapes.Rectangle;
import project.animator.view.animations.AnimationViewOperations;

/**
 * Represents a rectangle which we are able to view.
 */
public class ViewRectangle extends Rectangle implements ShapeViewOperations {

  private ArrayList<AnimationViewOperations> viewAnimations;

  /**
   * The constructor used to build our View Rectangle.
   * @param height - the height of this Rectangle.
   * @param width - the width of this Rectangle.
   * @param angle - the angle this Rectangle is at (used for rotation animations).
   * @param position - the position this Rectangle is at.
   * @param color - the color of this Rectangle.
   * @param name - the name of this Rectangle.
   * @param appears - the time this Rectangle begins to be visible in our animation.
   * @param disappears - the time the Rectangle will no longer be visible in our animation.
   */
  public ViewRectangle(float height, float width, float angle, IPosn position, Color color,
                       String name, float appears, float disappears) {
    super(height, width, angle, position, color, name, appears, disappears);
    viewAnimations = new ArrayList<>();
  }

  /**
   * Used to add a view animation to this Shape's view animations.
   * @param toBeAdded the view animation we are adding.
   */
  @Override
  public void addAnimation(AnimationViewOperations... toBeAdded) {
    for (AnimationViewOperations a: toBeAdded) {
      if (this.hasNoAnimationConflicts(a)) {
        a.setShapeToBeAnimated(this.name);
        this.viewAnimations.add(0, a);
      }
      else {
        throw new IllegalArgumentException("The animation you passed in conflicts with this " +
                "rectangle's previous animations!!");
      }
    }
  }

  /**
   * Used to return the animation views of this shape.
   * @return returns the animation views of this shape.
   */
  @Override
  public ArrayList<AnimationViewOperations> getViewAnimations() {
    ArrayList<AnimationViewOperations> copy = new ArrayList<>();
    copy.addAll(this.viewAnimations);
    return copy;
  }

  /**
   * Overriding our animate method so that it relies on the view animations rather than regular
   * animations. This shape has no regular animations since it doesn't need any.
   * @param currentTime the time we want to get out animation at.
   */
  @Override
  public void animate(float currentTime) {
    float tick = prevTick;

    while (tick != currentTime) {
      for (AnimationOperations a : this.viewAnimations) {
        a.animate(this, tick);
      }
      if (tick < currentTime) {
        tick++;
      } else {
        tick--;
      }
    }

    for (AnimationOperations a : this.viewAnimations) {
      a.animate(this, currentTime);
    }
    this.prevTick = currentTime;
  }

  /**
   * Draws this rectangle using java swing.
   * @param g the graphics we are using to draw this shape.
   * @param currentTime the time we are drawing the animation at.
   */
  @Override
  public void draw(Graphics g, float currentTime) {
    Graphics2D g2 = (Graphics2D)g;
    if (currentTime > this.appears && currentTime < this.disappears) {
      Float d1 = dimensions.get(0).getValue();
      Float d2 = dimensions.get(1).getValue();
      g2.setColor(this.color);
      g2.fillRect((int)position.getX(), (int)position.getY(), d1.intValue(), d2.intValue());
    }
  }

  /**
   * Used to print this rectangle and its animations in svg format.
   * @param tempo the rate we want to play the animation at.
   * @param loop svg will loop if true.
   * @return returns this shape in svg format.
   */
  @Override
  public String printAsSVG(int tempo, boolean loop) {

    String colorAsHex = String.format("#%02X%02X%02X", this.color.getRed(),
            this.color.getGreen(), this.color.getBlue());

    String svgOutput = "<rect x=\"" + this.position.getX() + "\" y=\"" + this.position.getY() +
            "\" width=\"" + this.dimensions.get(0).getValue() + "\" height=\"" +
            this.dimensions.get(1).getValue() + "\" fill=\"" + colorAsHex + "\" transform=\"rotate("
            + this.angle + ")\" visibility=\"hidden\">\n";

    if (loop) {
      svgOutput += "<animate attributeType=\"xml\" attributeName=\"visibility\" from=\"visible\" " +
              "to=\"visible\" dur=\"" + ((this.disappears / tempo - this.appears / tempo)) + "s\" "
              + "begin=\"base.begin+" + (this.appears / tempo) + "s\" />\n";
    }
    else {
      svgOutput += "<animate attributeType=\"xml\" attributeName=\"visibility\" from=\"visible\" " +
              "to=\"visible\" dur=\"" + ((this.disappears / tempo - this.appears / tempo)) + "s\" "
              + "begin=\"" + (this.appears / tempo) + "s\" />\n";
    }

    for (AnimationViewOperations a : viewAnimations) {
      svgOutput += a.printAsSVG(tempo, loop);
      if (svgOutput.contains("Height")) {
        svgOutput = svgOutput.replace("Height", "height");
        svgOutput = svgOutput.replace("Width", "width");
      }
    }

    svgOutput += "</rect>";

    return svgOutput;
  }


  /**
   * Prints this shape as a string using the tempo to make ticks into seconds.
   * @param tempo the rate we are playing this animation at.
   * @return returns this animation as a string.
   */
  @Override
  public String toViewString(int tempo) {
    return "Name: " + this.name + "\n" + this.typeToString() + "\n" + this.posnToString()
            + ", " + this.dimensionsToString() + ", " + this.colorToString() + ", "
            + this.angleToString() + "\nAppears at t=" + this.appears / tempo +
            "s\nDissappears at t=" + this.disappears / tempo + "s";
  }

  /**
   * Checks if the given object is equal to this Rectangle.
   * @param obj the object we are comparing to this Rectangle.
   * @return returns true if they are equals, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ViewRectangle) {
      ViewRectangle toBeCompared = (ViewRectangle) obj;
      return this.dimensions.equals(toBeCompared.getDimensions())
              && this.angle - toBeCompared.getAngle() <= .001
              && this.position.equals(toBeCompared.getPosition())
              && this.color.equals(toBeCompared.getColor())
              && this.appears - toBeCompared.getAppearTime() <= .001
              && this.disappears - toBeCompared.getDisappearTime() <= .001
              && this.name.equals(toBeCompared.getName())
              && this.viewAnimations.equals(toBeCompared.getViewAnimations());
    }
    else {
      return false;
    }
  }

  /**
   * Gets the hashcode based on the equals method.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + this.viewAnimations.hashCode();
  }

}
