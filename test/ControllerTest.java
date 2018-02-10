import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JTextField;
import project.animator.controller.Controller;
import project.animator.model.AnimatorLayerModel;
import project.animator.model.AnimatorLayerModelOperations;
import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visual.ExtendedInteractiveView;
import project.animator.view.visual.InteractiveViewOperations;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

  ActionEvent a1;
  Controller c1;
  AnimatorLayerModelOperations model;
  InteractiveViewOperations view;
  Appendable ap;
  JButton b;

  /**
   * Used to initialize fields.
   */
  @Before
  public void initialize() {
    b = new JButton();
    ap = new StringBuilder();
    ArrayList<ShapeOperations> shapes = new ArrayList<>();
    model = new AnimatorLayerModel();
    for(ShapeOperations s: shapes) {
      model.addShape(s);
    }
    view = new TestView(ap);
    c1 = new Controller();
    c1.addModel(model);
    c1.addView((ExtendedInteractiveView)view);
  }

  //tests the speed command.
  @Test
  public void testSpeed() {
    JTextField t = new JTextField();
    t.setText("2");
    a1 = new ActionEvent(t, 1, "SPEED");
    c1.actionPerformed(a1);
    assertEquals("SET TEMPO", ap.toString());
  }

  //tests the play command.
  @Test
  public void testPlay() {
    a1 = new ActionEvent(b, 1, "PLAY");
    c1.actionPerformed(a1);
    assertEquals("PLAY", ap.toString());
  }

  //tests the pause command.
  @Test
  public void testPause() {
    a1 = new ActionEvent(b, 1, "PAUSE");
    c1.actionPerformed(a1);
    assertEquals("PAUSE", ap.toString());
  }

  //tests the reset command.
  @Test
  public void testReset() {
    a1 = new ActionEvent(b, 1, "RESET");
    c1.actionPerformed(a1);
    assertEquals("RESET", ap.toString());
  }

  //tests the loop command.
  @Test
  public void testLoop() {
    a1 = new ActionEvent(b, 1, "LOOP");
    c1.actionPerformed(a1);
    assertEquals("LOOP", ap.toString());
  }

  //tests the speed up command.
  @Test
  public void testSU() {
    a1 = new ActionEvent(b, 1, "SPEED UP");
    c1.actionPerformed(a1);
    assertEquals("TEMPOSET TEMPO", ap.toString());
  }

  //tests the slow down command.
  @Test
  public void testSD() {
    a1 = new ActionEvent(b, 1, "SLOW DOWN");
    c1.actionPerformed(a1);
    assertEquals("TEMPO", ap.toString());
  }

}
