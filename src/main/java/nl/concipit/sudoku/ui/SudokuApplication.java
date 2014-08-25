package nl.concipit.sudoku.ui;

import org.apache.commons.io.IOUtils;

import javafx.application.Application;
import javafx.scene.control.TextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

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
        BorderPane root = new BorderPane();

        Button btn = new Button();
        btn.setText("Generate Sudoku");
        btn.setOnAction((e) -> setSudokuPane(root));

        root.setTop(btn);
        root.setCenter(null);
        Scene scene = new Scene(root, 350, 350);

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    private void setSudokuPane(BorderPane pane) {
        try {
            pane.setCenter(getSudokuPane(generateGrid()));
        } catch (IllegalGridInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private GridPane getSudokuPane(SudokuGrid grid) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.setStyle("-fx-background-color: DAE6F3;");
        gridPane.getChildren().clear();
        for (int c = 0; c < grid.getGridSize(); c++) {
            for (int r = 0; r < grid.getGridSize(); r++) {
                SudokuCell cell = grid.getCell(c, r);
                TextField cellField = new TextField();
                cellField.setAlignment(Pos.CENTER);
                cellField.setMaxSize(30, 30);
                cellField.setMinSize(30, 30);
                if (cell.getValue() != null) {
                    cellField.setText(cell.getValue().toString());
                }
                gridPane.add(cellField, c, r);
            }
        }

        return gridPane;
    }

    private SudokuGrid generateGrid() throws IllegalGridInputException {
        return SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;6;9|;8;|;;\n" + ";;|;;1|5;;\n"
                        + "5;4;|;9;2|6;;\n" + ";;|1;;|;;3\n" + ";;|;;|7;;|\n"
                        + ";1;|5;7;|;4;\n" + "9;2;|;;|;7;\n"
                        + ";3;6|;;7|1;2;\n" + "7;;|;;|4;5;6"));
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
