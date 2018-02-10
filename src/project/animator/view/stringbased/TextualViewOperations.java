package project.animator.view.stringbased;


import java.util.ArrayList;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.AnimatorViewOperations;

/**
 * Represents the operations of any textaul view for an animator object.
 * We'll use this for both the SVG and textual view since both rely
 * on strings for their output.
 */
public interface TextualViewOperations extends AnimatorViewOperations {

  /**
   * Returns the view as a string.
   * @param tempo the rate we want to view our animation at.
   * @param state the state we want to view.
   * @return  returns the view in text.
   */
  String getInstance(int tempo, ArrayList<ShapeOperations> state);

}
