package project.animator.view.stringbased;

import java.io.IOException;
import java.util.ArrayList;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.AbstractView;
import project.animator.view.animations.AnimationViewOperations;
import project.animator.view.shapes.ShapeViewOperations;
import project.animator.view.visitors.AnimationVisitor;
import project.animator.view.visitors.ShapeVisitor;

/**
 * This view is used to output our animation as a string.
 */
public class TextualView extends AbstractView implements TextualViewOperations {

  private Appendable ap;

  /**
   * The constructor used to build our TextualView.
   * @param ap - where we want to output this view.
   */
  public TextualView(Appendable ap, ShapeVisitor<ShapeViewOperations> sv,
                     AnimationVisitor<AnimationViewOperations> av) {
    super(sv, av);
    this.ap = ap;
  }

  /**
   * Gets this state printed as a string, using the tempo to convert ticks to time.
   * @param tempo the rate we want to view our animation at.
   * @param state the state we are going to view.
   * @return this state as a string.
   */
  public String getInstance(int tempo, ArrayList<ShapeOperations> state) {
    this.tempo = tempo;
    this.initializeState(state);
    String stringOutput = "";

    for (ShapeViewOperations s : this.state) {
      stringOutput += s.toViewString(this.tempo) + "\n";
    }

    for (ShapeViewOperations s : this.state) {
      for (AnimationViewOperations a : s.getViewAnimations()) {
        stringOutput += a.toString() + "\n";
      }
    }

    return stringOutput.substring(0, stringOutput.length() - 1);
  }

  /**
   * Used to output the given state to somewhere.
   * @param tempo the rate we want to view our animation at.
   * @param state the state we are given to view.
   */
  public void playAnimation(int tempo, ArrayList<ShapeOperations> state) {
    this.tempo = tempo;
    this.initializeState(state);
    try {
      ap.append(this.getInstance(tempo, state));
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

}
