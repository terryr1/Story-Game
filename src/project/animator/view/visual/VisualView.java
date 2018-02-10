package project.animator.view.visual;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.Timer;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.AbstractView;
import project.animator.view.animations.AnimationViewOperations;
import project.animator.view.shapes.ShapeViewOperations;
import project.animator.view.visitors.AnimationVisitor;
import project.animator.view.visitors.ShapeVisitor;

/**
 * Represents a Visual View of our animation using Java Swing.
 */
public class VisualView extends AbstractView {

  /**
   * int ticks - the frame we are on in the animation
   * JFrame thisFrame - The Frame we are drawing on.
   */
  private int ticks = 1;
  private JFrame thisFrame;

  /**
   * Used to construct our Visual View.
   */
  public VisualView(ShapeVisitor<ShapeViewOperations> sv,
                    AnimationVisitor<AnimationViewOperations> av) {
    super(sv, av);
    this.thisFrame = new JFrame();

    thisFrame.setTitle("Animator Program");
    thisFrame.setSize(500,500);
    thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel animationPanel = new AnimationPanel();

    animationPanel.setPreferredSize(screenSize);
    thisFrame.add(animationPanel);
    JScrollPane scrollPane = new JScrollPane(animationPanel);
    thisFrame.add(scrollPane);

    thisFrame.pack();
    thisFrame.revalidate();
    thisFrame.setVisible(true);
  }

  /**
   * This is the panel we are drawing on.
   */
  class AnimationPanel extends JPanel {

    /**
     * Constructs the panel we are drawing on by giving it a standard size.
     */
    AnimationPanel() {
      setPreferredSize(new Dimension(420, 420));
    }

    /**
     * This method draws the animation and outputs what we draw.
     * @param g the graphics used to draw our animation.
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (state != null) {
        for (ShapeViewOperations s : state) {
          s.animate(ticks);
          s.draw(g, ticks);
        }

      }
    }
  }

  /**
   * This method is run on every timer tick. It updates our animation.
   */
  ActionListener taskPerformer = new ActionListener() {

    public void actionPerformed(ActionEvent evt) {
      ticks++;
      thisFrame.repaint();
    }

  };

  //The timer object which sets the rate of our animation and increments it.
  private Timer tm = new Timer(tempo, taskPerformer);

  /**
   * This method is used to play the animation given.
   * @param tempo the rate we want to view our animation at.
   * @param state the state we want to view.
   */
  @Override
  public void playAnimation(int tempo, ArrayList<ShapeOperations> state) {
    this.tempo = tempo;
    tm.setDelay(00 / tempo);
    this.initializeState(state);
    ticks = 0;
    tm.start();
  }

}
