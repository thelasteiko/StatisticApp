package scatter;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import masks.DataManager;

public class StatApp extends Application {
    
    private DataManager data;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Scatter Chart");

        data = new DataManager();

        Group root = new Group();
        Scene scene = new Scene(root);
        VBox mainholder = new VBox();
        
        //TODO have a way to load different stylesheets?
        scene.getStylesheets().add(getClass().getResource("linechart.css").toExternalForm());
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
