package project.animator.view.visitors;

import project.animator.model.shapes.IOval;
import project.animator.model.shapes.IRectangle;
import project.animator.view.shapes.ShapeViewOperations;
import project.animator.view.shapes.ViewOval;
import project.animator.view.shapes.ViewRectangle;

/**
 * Used to build view shapes from regular shapes.
 */
public class ViewShapeVisitor implements ShapeVisitor<ShapeViewOperations> {

  /**
   * Create a view rectangle from the given rectangle.
   * @param rect the rectangle we are adding functionality to.
   * @return the new view rectangle.
   */
  @Override
  public ShapeViewOperations visit(IRectangle rect) {
    return new ViewRectangle(rect.getDimensions().get(0).getValue(),
            rect.getDimensions().get(1).getValue(), rect.getAngle(), rect.getPosition(),
            rect.getColor(), rect.getName(), rect.getAppearTime(), rect.getDisappearTime());
  }

  /**
   * Create a view oval from the given oval.
   * @param oval the oval we are adding functionality to.
   * @return the new view oval.
   */
  @Override
  public ShapeViewOperations visit(IOval oval) {
    return new ViewOval(oval.getDimensions().get(0).getValue(),
            oval.getDimensions().get(1).getValue(), oval.getAngle(), oval.getPosition(),
            oval.getColor(), oval.getName(), oval.getAppearTime(), oval.getDisappearTime(), false);
  }

}
