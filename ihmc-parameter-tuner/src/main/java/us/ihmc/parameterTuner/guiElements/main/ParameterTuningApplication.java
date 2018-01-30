package us.ihmc.parameterTuner.guiElements.main;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import us.ihmc.parameterTuner.guiElements.GuiParameter;
import us.ihmc.parameterTuner.guiElements.GuiRegistry;

public abstract class ParameterTuningApplication extends Application
{
   private static final String FXML_FILE = "gui.fxml";
   private static final String CSS_FILE = "gui.css";

   @Override
   public void start(Stage primaryStage) throws Exception
   {
      ParameterGuiInterface guiInterface = createInputManager();

      FXMLLoader mainLoader = new FXMLLoader();
      mainLoader.setLocation(ParameterTuningApplication.class.getResource(FXML_FILE));
      Scene mainScene = new Scene(mainLoader.<Pane> load());
      mainScene.getStylesheets().add(ParameterTuningApplication.class.getResource(CSS_FILE).toString());

      GuiController controller = mainLoader.getController();
      controller.addInputNode(guiInterface.getInputManagerNode());

      AnimationTimer animationTimer = new AnimationTimer()
      {
         @Override
         public void handle(long timestamp)
         {
            // Check if registry structure needs to be reloaded.
            if (guiInterface.pollReloadAll())
            {
               GuiRegistry fullRegistry = guiInterface.getFullRegistryCopy();
               controller.setRegistry(fullRegistry);
            }

            // If parameters were changed in the GUI forward copies to the interface.
            List<GuiParameter> changedParameters = controller.pollChangedParameters();
            if (changedParameters != null && !changedParameters.isEmpty())
            {
               guiInterface.submitChangedParameters(changedParameters);
            }

            // If parameters were changed externally update the internal parameters.
            List<GuiParameter> updatedParameters = guiInterface.pollUpdatedParameters();
            if (updatedParameters != null && !updatedParameters.isEmpty())
            {
               controller.updateParameters(updatedParameters);
            }
         }
      };

      animationTimer.start();
      primaryStage.setOnCloseRequest(event -> {
         animationTimer.stop();
         guiInterface.shutdown();
      });

      primaryStage.setTitle(getClass().getSimpleName());
      primaryStage.setScene(mainScene);
      primaryStage.setHeight(800.0);
      primaryStage.setWidth(1400.0);
      primaryStage.show();
   }

   protected abstract ParameterGuiInterface createInputManager();
}
