package project.animator.view;

import project.animator.view.stringbased.SVGView;
import project.animator.view.stringbased.TextualView;
import project.animator.view.visitors.ViewAnimationVisitor;
import project.animator.view.visitors.ViewShapeVisitor;
import project.animator.view.visual.HybridView;
import project.animator.view.visual.VisualView;

/**
 * Used so the views can be changed from a command line.
 */
public class ViewFactory {

  private  Appendable ap;

  public ViewFactory(Appendable ap) {
    this.ap = ap;
  }

  /**
   * Creates the proper model according to the given String.
   * @param viewType The type of the view to be created.
   * @return return the proper model
   */
  public AnimatorViewOperations getViewModel(String viewType) {
    if (viewType == null) {
      return null;
    }
    if (viewType.equalsIgnoreCase("text")) {
      return new TextualView(ap, new ViewShapeVisitor(), new ViewAnimationVisitor());
    }
    else if (viewType.equalsIgnoreCase("visual")) {
      return new VisualView(new ViewShapeVisitor(), new ViewAnimationVisitor());

    }
    else if (viewType.equalsIgnoreCase("interactive")) {
      return new HybridView(new ViewShapeVisitor(), new ViewAnimationVisitor());
    }
    else if (viewType.equalsIgnoreCase("svg")) {
      return new SVGView(800, 800, ap, new ViewShapeVisitor(), new ViewAnimationVisitor());
    }

    throw new IllegalArgumentException("Illegal model type!");
  }


}
