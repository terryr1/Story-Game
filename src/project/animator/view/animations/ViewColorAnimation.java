package project.animator.view.animations;

import java.awt.Color;

import project.animator.model.animations.ColorAnimation;

/**
 * Represents a color animation which we can also view.
 */
public class ViewColorAnimation extends ColorAnimation implements AnimationViewOperations {

  /**
   * the constructor for this view color animation.
   * @param from field representing the color we are changing from.
   * @param to field representing the color we are changing to.
   * @param fromTime the time we start this animation at.
   * @param toTime the time the this animation ends.
   */
  public ViewColorAnimation(Color from, Color to, float fromTime, float toTime) {
    super(from, to, fromTime, toTime);
  }

  /**
   * Prints this animation into an svg format.
   * @param tempo used to convert the time in ticks into seconds for the svg.
   * @return returns a string of this animation formatted in svg.
   */
  @Override
  public String printAsSVG(int tempo, boolean loop) {
    String fromColorAsHex = String.format("#%02X%02X%02X", this.from.getRed(),
            this.from.getGreen(), this.from.getBlue());
    String toColorAsHex = String.format("#%02X%02X%02X", this.to.getRed(),
            this.to.getGreen(), this.to.getBlue());

    String svgToOutput;

    if (loop) {
      svgToOutput = "<animate attributeType=\"xml\" attributeName=\"fill\" from=\"" +
              fromColorAsHex + "\" to=\"" + toColorAsHex + "\" dur=\"" +
              ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"base.begin+" +
              (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n";
      svgToOutput += "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
              "attributeName=\"fill\" to=\"" + fromColorAsHex + "\" fill=\"freeze\" />\n";
    }
    else {
      svgToOutput = "<animate attributeType=\"xml\" attributeName=\"fill\" from=\"" +
              fromColorAsHex + "\" to=\"" + toColorAsHex + "\" dur=\"" +
              ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"" + (this.fromTime / tempo) +
              "s\" fill=\"freeze\" />\n";
    }

    return svgToOutput;
  }

  /**
   * Prints this animation as a string using seconds instead of ticks.
   * @param tempo Used to convert the time in ticks into seconds.
   * @return returns this animation as a string.
   */
  @Override
  public String toViewString(int tempo) {
    if (currentTime <= toTime) {
      return "Shape " + this.shapeToBeAnimated + " changes color from " + from.toString()
              + " to " + to.toString() + " from t=" + currentTime / tempo + " to t=" + toTime
              / tempo;
    }
    else {
      return "";
    }
  }
}
