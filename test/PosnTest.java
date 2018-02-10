import org.junit.Before;
import org.junit.Test;

import project.animator.model.shapes.Posn;

import static org.junit.Assert.assertEquals;

public class PosnTest {
  Posn p;

  @Before
  public void initialize() {
    p = new Posn(5, 9);
  }

  //check if getX returns 5
  @Test
  public void checkGetX() {
    assertEquals(true, p.getX() - 5 < .001);
  }

  //check if getY returns 9
  @Test
  public void checkGetY() {
    assertEquals(true, p.getY() - 9 < .001);
  }


}
