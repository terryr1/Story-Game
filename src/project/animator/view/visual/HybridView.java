package project.animator.view.visual;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import project.animator.model.shapes.ShapeOperations;
import project.animator.view.AbstractView;
import project.animator.view.animations.AnimationViewOperations;
import project.animator.view.shapes.ShapeViewOperations;
import project.animator.view.visitors.AnimationVisitor;
import project.animator.view.visitors.ShapeVisitor;

/**
 * Used to animate the given state using java swing and have an interface to control said view.
 */
public class HybridView extends AbstractView implements ExtendedInteractiveView {

  private int ticks;
  private boolean loop;
  private float lastElementTime;
  private ArrayList<JButton> buttons;
  private ArrayList<JCheckBox> shapeCheckBoxes;
  private JPanel shapeList;
  private JScrollPane shapes;
  private JTextField tempoField;
  private JSlider scrubBar;
  JFrame thisFrame;
  private Color background;

  /**
   * Used to construct this Hybrid View.
   */
  public HybridView(ShapeVisitor<ShapeViewOperations> sv,
                    AnimationVisitor<AnimationViewOperations> av) {
    //initializing.
    super(sv, av);
    this.ticks = 0;
    this.loop = false;
    this.buttons = new ArrayList<>();
    this.shapeCheckBoxes = new ArrayList<>();
    this.lastElementTime = 0;
    this.shapeList = new JPanel(new BorderLayout());
    scrubBar = new JSlider(JSlider.HORIZONTAL, 0, 1, 1);

    //frame setup
    thisFrame = new JFrame();
    thisFrame.setContentPane(new Container());
    thisFrame.setTitle("Animator Program");
    thisFrame.setSize(500,500);
    thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel animationPanel;
    Container contentPane = thisFrame.getContentPane();
    contentPane.setLayout(new BorderLayout());

    animationPanel = new AnimationPanel();

    //button setup
    JButton play = new JButton("PLAY");
    JButton pause = new JButton("PAUSE");
    JButton stop = new JButton("RESET");
    JButton loop = new JButton("LOOP");
    JButton svg = new JButton("EXPORT");
    JButton color = new JButton("SET COLOR");

    JButton speedUp = new JButton("SPEED UP");

    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);
    formatter.setMaximum(Integer.MAX_VALUE);
    formatter.setAllowsInvalid(true);
    this.tempoField = new JFormattedTextField(formatter);

    this.tempoField.setColumns(3);
    tempoField.setMaximumSize(tempoField.getPreferredSize());
    tempoField.setMinimumSize(tempoField.getPreferredSize());
    tempoField.setActionCommand("SPEED");

    JButton speedDown = new JButton("SLOW DOWN"); //PRINT OUT VALUE OF SPEED NEXT TO IT

    this.buttons.add(play);
    this.buttons.add(pause);
    this.buttons.add(stop);
    this.buttons.add(loop);
    this.buttons.add(svg);
    this.buttons.add(color);
    this.buttons.add(speedUp);
    this.buttons.add(speedDown);

    Box horizontalBox = Box.createHorizontalBox();

    for (JButton b: this.buttons) {
      b.setActionCommand(b.getName());
      if (b.equals(color)) {
        horizontalBox.add(b);
        horizontalBox.add(Box.createHorizontalGlue());
      }
      else if (b.equals(speedUp)) {
        horizontalBox.add(Box.createRigidArea(new Dimension(15, 0)));
        horizontalBox.add(b);
        horizontalBox.add(Box.createRigidArea(new Dimension(5, 0)));
        horizontalBox.add(tempoField);
        horizontalBox.add(Box.createRigidArea(new Dimension(5, 0)));
      }
      else {
        horizontalBox.add(b);
        horizontalBox.add(Box.createRigidArea(new Dimension(5, 0)));
      }
    }

    //panel setup, add stuff to frame
    JPanel animatorController = new JPanel(new BorderLayout());
    Box verticalBox = Box.createVerticalBox();
    verticalBox.add(scrubBar);
    verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
    verticalBox.add(horizontalBox);

    animatorController.add(verticalBox);
    animatorController.setBorder(BorderFactory.createTitledBorder("Controls"));
    animatorController.setPreferredSize(new Dimension(animationPanel.getWidth(), 80));

    JScrollPane s = new JScrollPane(animationPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    contentPane.add(animatorController, BorderLayout.PAGE_END);
    contentPane.add(s);


    thisFrame.pack();
    thisFrame.setVisible(true);

    animationPanel.setBackground(new Button().getBackground());
  }

  /**
   * The panel containing our animation.
   */
  class AnimationPanel extends JPanel {

    AnimationPanel() {
      // set a preferred size for the custom panel.
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createTitledBorder("View Animation"));
      this.setPreferredSize(new Dimension(800, 800));
    }


    /**
     * Used to paint our animation.
     * @param g The graphics used by java swing.
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      super.setBackground(background);

      for (ShapeViewOperations s : state) {
        s.animate(ticks);
        s.draw(g, ticks);
      }

    }
  }

  /**
   * The action listener called by our timer.
   */
  ActionListener taskPerformer = new ActionListener() {

    /**
     * The action to be performed for each timer increment.
     * @param evt The action event.
     */
    public void actionPerformed(ActionEvent evt) {
      tm.setDelay(1000 / tempo);

      ticks++;
      scrubBar.setValue(ticks);
      if (loop && ticks > lastElementTime) {
        while (ticks > 0) {
          for (ShapeViewOperations sv: state) {
            sv.animate(ticks);
          }
          ticks--;
        }
        ticks = 0;
        thisFrame.repaint();
      }
      else if (ticks <= lastElementTime) {
        thisFrame.repaint();
      }

    }

  };

  private Timer tm = new Timer(1, taskPerformer);

  /**
   * plays the animation for the given state.
   * @param tempo the rate we want to view our animation at.
   * @param state the animation we want to play.
   */
  @Override
  public void playAnimation(int tempo, ArrayList<ShapeOperations> state) {
    tm.stop();
    this.tempo = tempo;
    tm.setDelay(500 / this.tempo);
    initializeState(state);
    ActionListener a = buttons.get(0).getActionListeners()[0];
    tempoField.setText("" + tempo);
    ActionListener b = tempoField.getActionListeners()[0];
    tempoField.setActionCommand("SPEED");

    for (ShapeViewOperations s: this.state) {
      if (s.getDisappearTime() > this.lastElementTime) {
        this.lastElementTime = s.getDisappearTime();
      }
    }

    scrubBar.setMaximum((int)lastElementTime);
    Box verticalBox = Box.createVerticalBox();
    shapeCheckBoxes.clear();
    for (ShapeViewOperations sv: this.state) {
      JCheckBox cb = new JCheckBox(sv.getName());
      cb.setSelected(true);
      if (a != null) {
        cb.addActionListener(a);
      }
      cb.setActionCommand(sv.getName());
      verticalBox.add(cb);
      shapeCheckBoxes.add(cb);
    }

    this.shapeList = new JPanel();
    this.shapeList.add(verticalBox);
    shapeList.setBorder(BorderFactory.createTitledBorder("Shapes"));
    Container contentPane = thisFrame.getContentPane();
    if (contentPane.getComponentCount() > 3) {
      contentPane.remove(shapes);
    }
    JScrollPane shapes = new JScrollPane(shapeList);
    shapes.setPreferredSize(new Dimension(200, 800));
    this.shapes = shapes;

    contentPane.add(this.shapes, BorderLayout.EAST);
    thisFrame.pack();
    thisFrame.setVisible(true);
    ticks = 1;
  }

  /**
   * Used to get this animation as an svg.
   * @param svgHeight - the height of the svg animation
   * @param svgWidth - the width of the svg animation
   * @return the svg format of this animation as a string.
   */
  private String getSVG(int svgHeight, int svgWidth) {

    String svgOutput = "<svg width=\"" + svgHeight + "\" height=\"" + svgWidth +
            "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n";

    svgOutput += "<rect>\n" +
            "<animate id=\"base\" begin=\"0;base.end\" dur=\" " +
            this.lastElementTime / this.tempo +
            "s\" " +
            "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
            "</rect>\n";

    String backgroundColorAsHex = String.format("#%02X%02X%02X", this.background.getRed(),
            this.background.getGreen(), this.background.getBlue());

    svgOutput += "<rect x=\"0\" y=\"0\" width=\"100%\" height=\"100%\" fill=\"" +
            backgroundColorAsHex + "\" visibility=\"visible\">\n" +
            "</rect>\n";

    for (ShapeViewOperations s : this.state) {
      svgOutput += s.printAsSVG(this.tempo, loop) + "\n";
    }
    svgOutput += "</svg>";

    return svgOutput;
  }

  /**
   * Used to export this animation as an svg file
   */
  @Override
  public void exportSVG() {
    PrintStream out;
    File svgLocation;

    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter svgFilter = new FileNameExtensionFilter(
            "SVG", "svg");
    chooser.setFileFilter(svgFilter);
    int returnVal = chooser.showOpenDialog(thisFrame);
    svgLocation = chooser.getSelectedFile();

    try {
      if(svgLocation != null) {
        out = new PrintStream(svgLocation);
      }
      else {
        JOptionPane.showMessageDialog(null, "File export failed!",
                "Animator", JOptionPane.ERROR_MESSAGE);
        throw new IllegalArgumentException("File is NULL!");
      }
    }
    catch (FileNotFoundException e) {
      return;
    }
    catch (IllegalArgumentException e) {
      return;
    }

    Appendable ap = out;
    try {
      ap.append(this.getSVG(800, 800));
    } catch (IOException e) {
      System.err.println("caught IOException: " + e.getMessage());
    }

    JOptionPane.showMessageDialog(null, "File exported to SVG.\nLocation: " +
                    svgLocation.getAbsolutePath(),
            "Animator", JOptionPane.INFORMATION_MESSAGE);
}

  /**
   * This method is used by a controller to add an action listener to our buttons.
   * @param controller - the controller we are using to control our view.
   */
  @Override
  public void setActionListener(ActionListener controller) {
    for (JButton b: this.buttons) {
      b.addActionListener(controller);
    }
    for (JCheckBox cb: this.shapeCheckBoxes) {
      cb.addActionListener(controller);
    }
    this.tempoField.addActionListener(controller);
  }

  /**
   * This method is used by a controller to add a change listener to the slider.
   * @param controller - the controller we are using to control our view.
   */
  @Override
  public void setChangeListener(ChangeListener controller) {
    scrubBar.addChangeListener(controller);
  }

  @Override
  public void setTick(int tick) {
    //tm.stop();
    this.ticks = tick;
    thisFrame.repaint();
  }

  /**
   * This method resets all the shapes in the animation and runs it at the beginning.
   */
  @Override
  public void reset() {
    ticks = 0;
    thisFrame.repaint();
  }

  /**
   * This method is used to set the tempo of our animation.
   * @param tempo - the tempo this view is running at.
   */
  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
    tempoField.setText("" + tempo);
  }

  /**
   * This method plays the animation if its paused.
   */
  @Override
  public void play() {
    if (!this.tm.isRunning()) {
      tm.start();
    }
    if (this.tm.isRunning()) {
      this.buttons.get(1).setBackground(new Button().getBackground());
    }
    else {
      this.buttons.get(1).setBackground(Color.RED);
    }
  }

  /**
   * This method pauses the animation if its running.
   */
  @Override
  public void pause() {
    if (this.tm.isRunning()) {
      tm.stop();
    }
    if (this.tm.isRunning()) {
      this.buttons.get(1).setBackground(new Button().getBackground());
    }
    else {
      this.buttons.get(1).setBackground(Color.RED);
    }
  }

  /**
   * This method is used to toggle the loop function of the view.
   */
  @Override
  public void loop() {
    this.loop = !loop;
    if (this.loop) {
      this.buttons.get(3).setBackground(Color.GREEN);
    }
    else {
      this.buttons.get(3).setBackground(new Button().getBackground());
    }
  }

  /**
   * This method is used to get the tempo of this animation.
   * @return The tempo of this animation.
   */
  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Sets the background color of this view to the given Color.
   * @param b - The color that we will change the background to
   */
  @Override
  public void setBackground(Color b) {
    this.background = b;
  }

  /**
   * Sets the background color of this view to a chosen Color.
   */
  @Override
  public void chooseColor() {
    Color newColor = JColorChooser.showDialog(
            thisFrame,
            "Choose Background Color", new JButton().getBackground());
    this.background = newColor;
    thisFrame.repaint();
  }
}
