package project.animator.view.animations;

import java.util.ArrayList;
import project.animator.model.animations.ScaleAnimation;
import project.animator.model.shapes.IDimension;

/**
 * Represents a scale animation which we can also view.
 */
public class ViewScaleAnimation extends ScaleAnimation implements AnimationViewOperations {

  /**
   * the constructor for this scale animation.
   * @param from The dimensions we are starting at.
   * @param to The dimensions we are ending at.
   * @param fromTime the time the transformation begins.
   * @param toTime the time the transformation ends.
   */
  public ViewScaleAnimation(ArrayList<IDimension> from, ArrayList<IDimension> to, float fromTime,
                            float toTime) {
    super(from, to, fromTime, toTime);
  }

  /**
   * Prints this animation into an svg format.
   * @param tempo used to convert the time in ticks into seconds for the svg.
   * @return returns a string of this animation formatted in svg.
   */
  @Override
  public String printAsSVG(int tempo, boolean loop) {
    String output = "";
    int length = this.from.size();

    if (loop) {
      for (int i = 0; i < length; i ++) {
        output += "<animate attributeType=\"xml\" attributeName=\""
                + from.get(i).getType() + "\" from=\"" + this.from.get(i).getValue()
                + "\" to=\"" + this.to.get(i).getValue() + "\" dur=\""
                + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"base.begin+" +
                (this.fromTime / tempo) + "s\" fill=\"freeze\" />\n";
        output += "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                "attributeName=\"" + from.get(i).getType() + "\" to=\"" +
                this.from.get(i).getValue() + "\" fill=\"freeze\" />\n";
      }
    }
    else {
      for (int i = 0; i < length; i ++) {
        output += "<animate attributeType=\"xml\" attributeName=\""
                + from.get(i).getType() + "\" from=\"" + this.from.get(i).getValue()
                + "\" to=\"" + this.to.get(i).getValue() + "\" dur=\""
                + ((this.toTime - this.fromTime) / tempo) + "s\" begin=\"" + (this.fromTime / tempo)
                + "s\" fill=\"freeze\" />\n";

      }
    }

    return output;
  }

  /**
   * Prints this animation as a string using seconds instead of ticks.
   * @param tempo Used to convert the time in ticks into seconds.
   * @return returns this animation as a string.
   */
  @Override
  public String toViewString(int tempo) {
    String output = "";
    if (currentTime <= toTime) {
      output = "Shape " + this.shapeToBeAnimated + " scales from ";
      for (int i = 0; i < this.from.size(); i++) {
        output += this.from.get(i).getType() +
                ": " + this.from.get(i).getValue();
        if (i != this.from.size() - 1) {
          output += ", ";
        }
      }
      output +=  " to ";
      for (int i = 0; i < this.to.size(); i++) {
        output += this.to.get(i).getType() +
                ": " + this.to.get(i).getValue() + ", ";
        if (i != this.to.size() - 1) {
          output += ", ";
        }
      }
      output += " from t=" + currentTime / tempo + " to t=" + toTime / tempo;
    }
    return output;
  }
}
