import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.visual.InteractiveViewOperations;

public class TestView implements InteractiveViewOperations {

  Appendable ap;

  TestView(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public int getTempo() {
    try {
      ap.append("TEMPO");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
    return 0;
  }

  @Override
  public void loop() {
    try {
      ap.append("LOOP");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

  @Override
  public void pause() {
    try {
      ap.append("PAUSE");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

  @Override
  public void play() {
    try {
      ap.append("PLAY");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

  @Override
  public void setTempo(int tempo) {
    try {
      ap.append("SET TEMPO");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

  @Override
  public void reset() {
    try {
      ap.append("RESET");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

  @Override
  public void setActionListener(ActionListener controller) {
    //not needed for test
  }

  @Override
  public void playAnimation(int tempo, ArrayList<ShapeOperations> state) {
    //not needed for test;
  }

  @Override
  public void exportSVG() {
    try {
      ap.append("EXPORT");
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }
  }

}
