import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tuanz1.puzzle.controller.Controller;
import tuanz1.puzzle.view.CtrlPane;
import tuanz1.puzzle.view.PuzzlePanel;


public class PuzzleGame extends Application {
    @Override
    public void start(Stage app) throws Exception {
        Controller ctrl = new Controller();
        PuzzlePanel pp = new PuzzlePanel(3);
        BorderPane mainPane = new BorderPane();
        CtrlPane cp = new CtrlPane(ctrl, pp, mainPane);
        mainPane.setCenter(pp.getPuzzlePane());
        mainPane.setRight(cp.getCtrlPane());
        pp.setImages(false);
        Scene s = new Scene(mainPane);
        app.setTitle("PuzzleGame");
        app.setScene(s);
        app.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
