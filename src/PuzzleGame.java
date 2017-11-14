import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tuanz1.puzzle.controller.Controller;
import tuanz1.puzzle.utils.Direction;
import tuanz1.puzzle.utils.Level;
import tuanz1.puzzle.utils.Selector;
import tuanz1.puzzle.view.PuzzlePanel;


public class PuzzleGame extends Application {

    private Controller ctrl = new Controller();
    private PuzzlePanel pp;
    private Label logText = new Label();
    private VBox ctrlBox = new VBox();
    private ToggleGroup groupLevel = new ToggleGroup();
    private RadioButton levelNormal = new RadioButton("简单");
    private RadioButton levelHigh = new RadioButton("中等");
    private RadioButton levelUltra = new RadioButton("困难");
    private ChoiceBox<String> AI = new ChoiceBox<>();
    private Button btnShuffle = new Button("打乱顺序");
    private Button btnPlay = new Button("自动完成");
    private Button btnShow = new Button("显示原图");
    private int[][] puzzleMap;
    private String start;
    private int size = 4;
    private BorderPane mainPane = new BorderPane();

    @Override
    public void start(Stage app) throws Exception {
        ctrl.setLevel(Level.HIGH);
        ctrl.setSelector(Selector.IDA);
        initComponent();
        pp = new PuzzlePanel(size);
        mainPane.setCenter(pp.getPuzzlePane());
        mainPane.setRight(ctrlBox);
        pp.setImages(false);

        Scene s = new Scene(mainPane);
        app.setTitle("PuzzleGame");
        app.setScene(s);
        app.show();
    }
    private void initComponent() {
        this.levelNormal.setToggleGroup(groupLevel);
        this.levelHigh.setToggleGroup(groupLevel);
        this.levelUltra.setToggleGroup(groupLevel);
        AI.getItems().addAll( "A*算法", "IDA*算法");
        AI.setValue("A*算法");
        AI.getSelectionModel().selectedIndexProperty().addListener((ov, old_v, cur_v)->{
            if (cur_v == (Number)(0)){
                ctrl.setSelector(Selector.ASTART);
            }else if(cur_v == (Number)(1)){
                ctrl.setSelector(Selector.IDA);
            }
        });

        HBox levelBox = new HBox();
        levelBox.getChildren().add(levelNormal);
        levelBox.getChildren().add(levelHigh);
        levelBox.getChildren().add(levelUltra);
        levelHigh.setSelected(true);
        groupLevel.selectedToggleProperty().addListener((ov, pre, cur)->{
            if (cur == levelNormal){
                ctrl.setLevel(Level.NORMAL);
                size =  3;
            }else if (cur == levelHigh){
                ctrl.setLevel(Level.HIGH);
                size = 4;
            }else if (cur == levelUltra){
                ctrl.setLevel(Level.ULTRA);
                size = 5;
            }
            refresh();
            btnPlay.setDisable(true);
        });
        btnShow.setOnMouseClicked(e->{
            Stage display = new Stage();
            ImageView picture = new ImageView(new Image("assets/default/" + Level.getName(size) + "/default.jpg"));
            HBox picBox = new HBox(picture);
            display.setScene(new Scene(picBox));
            display.show();
        });
        levelBox.setSpacing(4);
        levelBox.setAlignment(Pos.CENTER);

        // 占位空格面板

        ctrlBox.getChildren().add(logText);

        ctrlBox.getChildren().add(levelBox);
        ctrlBox.getChildren().add(btnShuffle);
        ctrlBox.getChildren().add(AI);
        ctrlBox.getChildren().add(btnPlay);
        btnPlay.setDisable(true);
        ctrlBox.getChildren().add(btnShow);
        btnShuffle.setOnMouseClicked(e->{
            this.start = ctrl.randomStart();
            puzzleMap = new int[size][size];
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    puzzleMap[i][j] = start.charAt(i * size + j) - 'A';
                }
            }
            pp.setPuzzleMap(puzzleMap);
            pp.setImages(true);
            btnPlay.setDisable(false);
        });
        btnPlay.setOnMouseClicked(e->{
            btnPlay.setDisable(true);
            puzzleMap = pp.getPuzzleMap();
            StringBuffer temp = new StringBuffer();
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    temp.append((char)(puzzleMap[i][j] + 'A'));
                }
            }
            Direction[] solution = ctrl.getSolution(temp.toString());
            this.logText.setText("检索数:" + ctrl.totalSearchNum + "\n步数  :" + solution.length);
            pp.autoPlay(solution);
        });
        setStyle();
    }
    private void refresh(){
        pp = new PuzzlePanel(size);
        pp.initPuzzleMapArray();
        pp.setImages(false);
        mainPane.setCenter(pp.getPuzzlePane());
        this.logText.setText("");
    }
    private void setStyle(){
        ctrlBox.setMinWidth(200);
        ctrlBox.setSpacing(50);
        AI.setMinWidth(130);
        ctrlBox.setAlignment(Pos.TOP_CENTER);
        btnShuffle.setMinWidth(130);
        btnPlay.setMinWidth(130);
        btnShow.setMinWidth(130);
        logText.setMaxHeight(50);
        logText.setMinHeight(50);
        logText.setMaxWidth(200);
        logText.setFont(Font.font(14));
        logText.setAlignment(Pos.CENTER);
    }
    public static void main(String[] args){
        launch(args);
    }
}
