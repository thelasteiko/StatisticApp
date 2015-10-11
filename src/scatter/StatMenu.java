package scatter;

import javafx.scene.control.MenuBar;
import masks.DataManager;

public class StatMenu extends MenuBar {
    
    private DataManager data;
    
    public StatMenu(DataManager dm) {
        data = dm;
        //MenuBar > Menu > MenuItem
        //.setOnAction(actionevent -> something);
        //TODO think I'll just start with file
        //so the question is now, do i put the file chooser here? or in XYChartIO?
    }

}
