package us.ihmc.parameterTuner.guiElements.tabs;

import java.util.Map;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import us.ihmc.parameterTuner.guiElements.GuiParameter;
import us.ihmc.parameterTuner.guiElements.tuners.Tuner;

public class TuningTabManager
{
   private final TabPane tabPane;
   private Map<String, Tuner> tunerMap;

   public TuningTabManager(TabPane tabPane)
   {
      this.tabPane = tabPane;

      MenuItem closeTab = new MenuItem("Close Open Tab");
      closeTab.setOnAction(event -> {
         Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
         tabPane.getTabs().remove(selectedTab);
         closeTab.setDisable(tabPane.getTabs().size() == 0);
      });
      MenuItem createNewTab = new MenuItem("New Tab");
      createNewTab.setOnAction(event -> {
         String name = createUniqueName(tabPane, "NewTab");
         TuningTab newTab = new TuningTab(name, tabPane);
         newTab.setTunerMap(tunerMap);
         closeTab.setDisable(false);
      });
      ContextMenu tabContextMenu = new ContextMenu();
      tabContextMenu.getItems().add(createNewTab);
      tabContextMenu.getItems().add(closeTab);
      tabPane.setContextMenu(tabContextMenu);

      new TuningTab("TuningTab", tabPane);
   }

   public static String createUniqueName(TabPane tabPane, String name)
   {
      String uniqueName = name;
      int count = 1;
      while (doesTabExist(tabPane, uniqueName))
      {
         uniqueName = name + count++;
      }
      return uniqueName;
   }

   private static boolean doesTabExist(TabPane tabPane, String name)
   {
      return !tabPane.getTabs().filtered(tab -> name.equals(((TuningTab) tab).getName())).isEmpty();
   }

   public void setTunerMap(Map<String, Tuner> tunerMap)
   {
      this.tunerMap = tunerMap;
      tabPane.getTabs().forEach(tab -> ((TuningTab) tab).setTunerMap(tunerMap));
   }

   public void handleNewParameter(GuiParameter parameter)
   {
      TuningTab activeTab = (TuningTab) tabPane.getSelectionModel().getSelectedItem();
      activeTab.handleNewParameter(parameter);
   }

}
