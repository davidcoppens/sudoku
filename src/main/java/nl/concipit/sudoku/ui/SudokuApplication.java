package nl.concipit.sudoku.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;
import nl.concipit.sudoku.model.SudokuSegment;

import org.apache.commons.io.IOUtils;

/**
 * Main entry point for the JavaFX user interface
 * 
 * @author dcoppens
 *
 */
@SuppressWarnings("restriction")
public class SudokuApplication extends Application {

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        Button btn = new Button();
        btn.setText("Generate Sudoku");
        btn.setOnAction(e -> setSudokuPane(root));

        root.setTop(btn);
        root.setCenter(null);
        Scene scene = new Scene(root, 350, 350);

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the center pane with the generated Sudoku
     * 
     * @param pane
     *            BorderPane to fill the center of
     */
    private void setSudokuPane(BorderPane pane) {
        try {
            pane.setCenter(getSudokuPane(generateGrid()));
        } catch (IllegalGridInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Creates a GridPane representing the Sudoku
     * 
     * @param grid
     *            Sudoku grid
     * @return GridPane representing the Sudoku
     */
    private GridPane getSudokuPane(SudokuGrid grid) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setVgap(0);
        gridPane.setHgap(0);
        gridPane.getChildren().clear();
        // add all segments to the grid
        addSegments(grid, gridPane);

        return gridPane;
    }

    /**
     * Add all segments of the grid to the grid pane
     * 
     * @param grid
     *            Grid
     * @param gridPane
     *            Grid pane
     */
    private void addSegments(SudokuGrid grid, GridPane gridPane) {
        for (int c = 0; c < grid.getNumberOfSegments(); c++) {
            for (int r = 0; r < grid.getNumberOfSegments(); r++) {
                SudokuSegment segment = grid.getSegment(
                        c * grid.getSegmentSize(), r * grid.getSegmentSize());
                GridPane segmentPane = new GridPane();
                segmentPane.setPadding(new Insets(1, 1, 1, 1));
                segmentPane.setVgap(2);
                segmentPane.setHgap(2);
                segmentPane.setStyle("-fx-background-color: EEEEEE; "
                        + "-fx-border-width: 1;" + "-fx-border-color: AAAAAA;");
                segmentPane.getChildren().clear();

                // add cells of the segment to the segment pane
                fillSegmentPane(segment, segmentPane);
                gridPane.add(segmentPane, c, r);
            }
        }
    }

    /**
     * Fills the provided segment GridPane with all the cells in the provided
     * segment of a sudoku
     * 
     * @param segment
     *            the segment holding the cells to add
     * @param segmentPane
     *            the pane to which to add the cells
     */
    private void fillSegmentPane(SudokuSegment segment, GridPane segmentPane) {
        // iterate cells in the segment
        for (int i = 0; i < segment.getSize(); i++) {
            for (int j = 0; j < segment.getSize(); j++) {
                SudokuCell cell = segment.getCell(i, j);
                TextField cellField = new TextField();
                cellField.setAlignment(Pos.CENTER);
                cellField.setMaxSize(30, 30);
                cellField.setMinSize(30, 30);
                if (cell.getValue() != null) {
                    cellField.setText(cell.getValue().toString());
                }
                segmentPane.add(cellField, i, j);
            }
        }
    }

    /**
     * Generate next Sudoku grid
     * 
     * @return SudokuGrid
     * @throws IllegalGridInputException
     *             if the sudoku grid is invalid
     */
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
