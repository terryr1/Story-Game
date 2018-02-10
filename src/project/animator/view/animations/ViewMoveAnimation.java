package project.animator.view.animations;

import project.animator.model.animations.MoveAnimation;
import project.animator.model.shapes.IPosn;

/**
 * Represents a move animation which we can also view.
 */
public class ViewMoveAnimation extends MoveAnimation implements AnimationViewOperations {

  /**
   * the constructor for this move animation.
   * @param from field representing the position we are moving from.
   * @param to field representing the position we are moving to.
   * @param fromTime the time the transformation begins.
   * @param toTime the time the transformation ends.
   */
  public ViewMoveAnimation(IPosn from, IPosn to, float fromTime, float toTime) {
    super(from, to, fromTime, toTime);
  }

  /**
   * Prints this animation into an svg format.
   * @param tempo used to convert the time in ticks into seconds for the svg.
   * @return returns a string of this animation formatted in svg.
   */
  @Override
  public String printAsSVG(int tempo, boolean loop) {

    String svgOutput;

    if (loop) {
      svgOutput = "<animate attributeType=\"xml\" attributeName=\"x\" from=\"" +
              this.from.getX() + "\" to=\"" + this.to.getX() +
              "\" dur=\"" + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"base.begin+" +
              (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" attributeName=\"y\" from=\"" +
              this.from.getY() + "\" to=\"" + this.to.getY() +
              "\" dur=\"" + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"base.begin+" +
              (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n";
      svgOutput += "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
              "attributeName=\"x\" to=\"" + this.from.getX() + "\" fill=\"freeze\" />\n";
      svgOutput += "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
              "attributeName=\"y\" to=\"" + this.from.getY() + "\" fill=\"freeze\" />\n";
    }
    else {
      svgOutput = "<animate attributeType=\"xml\" attributeName=\"x\" from=\"" +
              this.from.getX() + "\" to=\"" + this.to.getX() +
              "\" dur=\"" + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"" +
              (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" attributeName=\"y\" from=\"" +
              this.from.getY() + "\" to=\"" + this.to.getY() +
              "\" dur=\"" + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"" +
              (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n";
    }

    return svgOutput;
  }

  /**
   * Prints this animation as a string using seconds instead of ticks.
   * @param tempo Used to convert the time in ticks into seconds.
   * @return returns this animation as a string.
   */
  @Override
  public String toViewString(int tempo) {
    if (currentTime <= toTime) {
      return "Shape " + this.shapeToBeAnimated + " moves from " + from.toString()
              + " to " + to.toString() + " from t=" + currentTime + " to t=" + toTime;
    }
    else {
      return "";
    }
  }
}
