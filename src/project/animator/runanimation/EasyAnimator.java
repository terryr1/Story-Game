package project.animator.runanimation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import project.animator.controller.Controller;
import project.animator.model.AnimatorLayerModel;
import project.animator.model.AnimatorLayerModelOperations;
import project.animator.runanimation.util.AnimationFileReader;
import project.animator.runanimation.util.TweenLayerModelBuilder;
import project.animator.view.AnimatorViewOperations;
import project.animator.view.ViewFactory;
import project.animator.view.visual.ExtendedInteractiveView;
import project.animator.view.visual.InteractiveViewOperations;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

/**
 * Class we are using to run animators.
 */
public class EasyAnimator {

  /**
   * Used to run the animator using views.
   * @param args the args we are given by the command line
   */
  public static void main(String[] args) {

    String animationFile = "";
    String viewType = "";
    String outputType = "";
    int ticksPerSecond = 1;
    JFrame errorFrame = new JFrame();

    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("-if")) {
        animationFile = args[i + 1];
        i = i + 1;
      } else if (args[i].equals("-iv")) {
        viewType = args[i + 1];
        i = i + 1;
      } else if (args[i].equals("-o")) {
        outputType = args[i + 1];
        i = i + 1;
      } else if (args[i].equals("-speed")) {
        try {
          ticksPerSecond = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(errorFrame, error,
                  "Argument" + args[i + 1] + " must be an integer.", JOptionPane.ERROR_MESSAGE);
          System.exit(1);
        }
        i = i + 1;
      } else {
        JOptionPane.showMessageDialog(errorFrame, error,"Illegal Arguments!",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    AnimationFileReader fr = new AnimationFileReader();
    TweenLayerModelBuilder<AnimatorLayerModelOperations> builder = new AnimatorLayerModel.Builder();
    AnimatorLayerModelOperations model = new AnimatorLayerModel();

    try {
      model = fr.readFile(animationFile, builder);
    } catch (FileNotFoundException e) {
      System.err.println("Argument " + animationFile + " must be an valid file.");
      System.exit(1);
    }

    PrintStream out = System.out;
    if (outputType.equals("out")) {

      out = new PrintStream(System.out);
    }
    else {
      try {
        out = new PrintStream(new FileOutputStream(outputType));
      }
      catch (FileNotFoundException e) {
        System.err.println("Argument " + outputType + " must be an valid file.");
      }
    }


    ViewFactory factory = new ViewFactory(out);
    AnimatorViewOperations view = factory.getViewModel(viewType);

    if (view instanceof InteractiveViewOperations) {
      Controller controller = new Controller();
      controller.addModel(model);
      controller.addView((ExtendedInteractiveView) view);
      controller.startAnimator(ticksPerSecond);
    } else {
      view.playAnimation(ticksPerSecond, model.getState());
    }


  }
}
