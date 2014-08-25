package nl.concipit.sudoku.ui;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

/**
 * Main entry point for the JavaFX user interface
 * 
 * @author dcoppens
 *
 */
@SuppressWarnings("restriction")
public class SudokuApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Button btn = new Button();
        btn.setText("Generate Sudoku");
        btn.setOnAction((e) -> System.out.println("Generate"));
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Start the application
     * 
     * @param args
     *            arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
