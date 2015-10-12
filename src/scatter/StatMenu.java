package scatter;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import masks.DataManager;
/**
 * A menu bar for the statistical application that handles
 * file operations.
 * @author Melinda Robertson
 * @version 20151011
 */
public class StatMenu extends MenuBar {
    /**
     * The data.
     */
    private DataManager data;
    /**
     * Creates a MenuBar with file saving and loading functions.
     * @param dm is the DataManager that handles all data operations.
     */
    public StatMenu(DataManager dm) {
        data = dm;
        buildFileMenu();
    }
    /**
     * Creates a menu to load, save data and exit the program.
     */
    private void buildFileMenu() {
        Menu file = new Menu();
        file.setText("_File");
        file.setMnemonicParsing(true);
        
        MenuItem miSave = new MenuItem();
        miSave.setText("_Save");
        miSave.setMnemonicParsing(true);
        miSave.setOnAction(event -> saveAction());
        miSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        
        MenuItem miLoad = new MenuItem();
        miLoad.setText("_Open");
        miLoad.setMnemonicParsing(true);
        miLoad.setOnAction(event -> loadAction());
        miLoad.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        
        MenuItem miExit = new MenuItem();
        miExit.setText("_Quit");
        miExit.setMnemonicParsing(true);
        miExit.setOnAction(event -> Platform.exit());
        miExit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        
        file.getItems().addAll(miLoad, miSave, miExit);
        this.getMenus().add(file);
    }
    /**
     * Opens a file chooser and saves the current listing of x y pairs.
     */
    private void saveAction() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Comma Separated", "*.csv"),
                new ExtensionFilter("All Files", "*"));
        File savefile = fc.showSaveDialog(null);
        data.save(savefile);
    }
    /**
     * Opens a file chooser and loads the x y pairs from it.
     */
    public void loadAction() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Comma Separated", "*.csv"),
                new ExtensionFilter("All Files", "*"));
        File loadfile = fc.showOpenDialog(null);
        data.save(loadfile);
    }

}
