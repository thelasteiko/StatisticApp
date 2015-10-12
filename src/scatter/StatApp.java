package scatter;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import masks.DataManager;
import tabs.TabManager;
/**
 * Starting point of an application for displaying the relationship
 * between two sets of data.
 * @author Melinda Robertson
 * @version 20151011
 */
public class StatApp extends Application {
    /**
     * The data manager holds and manages the data for
     * every window object.
     */
    private DataManager data;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Scatter Chart");

        data = new DataManager();

        Group root = new Group();
        Scene scene = new Scene(root);
        BorderPane mainholder = new BorderPane();
        
        StatMenu menu = new StatMenu(data);
        TableBox table = new TableBox(data);
        TabManager tm = new TabManager(data);
        
        mainholder.setTop(menu);
        mainholder.setLeft(table);
        mainholder.setCenter(tm);
        
        //TODO have a way to load different stylesheets?
        //TODO compact stylesheets
        scene.getStylesheets().add(getClass().getResource("linechart.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("scatter.css").toExternalForm());
        
        stage.show();
    }

    /**
     * Main is main.
     * @param args is the args.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
