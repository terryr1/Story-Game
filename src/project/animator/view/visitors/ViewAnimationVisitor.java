package project.animator.view.visitors;

import project.animator.model.animations.IColorAnimation;
import project.animator.model.animations.IMoveAnimation;
import project.animator.model.animations.IScaleAnimation;
import project.animator.view.animations.AnimationViewOperations;
import project.animator.view.animations.ViewColorAnimation;
import project.animator.view.animations.ViewMoveAnimation;
import project.animator.view.animations.ViewScaleAnimation;

/**
 * Used to build view animations from regular animations.
 */
public class ViewAnimationVisitor implements AnimationVisitor<AnimationViewOperations> {

  /**
   * Create a view move animation from the given move animation.
   * @param move the move animation we are adding functionality to.
   * @return the new view move animation.
   */
  @Override
  public AnimationViewOperations visit(IMoveAnimation move) {
    return new ViewMoveAnimation(move.getFrom(), move.getTo(), move.getFromTime(),
            move.getToTime());
  }

  /**
   * Create a view scale animation from the given scale animation.
   * @param scale the scale animation we are adding functionality to.
   * @return the new view scale animation.
   */
  @Override
  public AnimationViewOperations visit(IScaleAnimation scale) {
    return new ViewScaleAnimation(scale.getFrom(), scale.getTo(), scale.getFromTime(),
            scale.getToTime());
  }

  /**
   * Create a view color animation from the given color animation.
   * @param changeColor the color animation we are adding functionality to.
   * @return the new view color animation.
   */
  @Override
  public AnimationViewOperations visit(IColorAnimation changeColor) {
    return new ViewColorAnimation(changeColor.getFrom(), changeColor.getTo(),
            changeColor.getFromTime(), changeColor.getToTime());
  }

}
