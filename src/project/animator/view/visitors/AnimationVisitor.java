package project.animator.view.visitors;

import project.animator.model.animations.IColorAnimation;
import project.animator.model.animations.IMoveAnimation;
import project.animator.model.animations.IScaleAnimation;

/**
 * A visitor interface used to add functionality to an animation object.
 * @param <T> The type our visitor methods will return.
 */
public interface AnimationVisitor<T> {

  /**
   * Used to add functionality to a move animation.
   * @param move the move animation we are adding functionality to.
   * @return whatever we wanted to use the move animation for.
   */
  T visit(IMoveAnimation move);

  /**
   * Used to add functionality to a scale animation.
   * @param scale the scale animation we are adding functionality to.
   * @return whatever we wanted to use the scale animation for.
   */
  T visit(IScaleAnimation scale);

  /**
   * Used to add functionality to a color animation.
   * @param changeColor the color animation we are adding functionality to.
   * @return whatever we wanted to use the color animation for.
   */
  T visit(IColorAnimation changeColor);

}
