package nl.concipit.sudoku.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;
import nl.concipit.sudoku.model.SudokuSegment;
import nl.concipit.sudoku.solver.SimpleSolver;
import nl.concipit.sudoku.solver.Solver;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the JavaFX user interface
 * 
 * @author dcoppens
 *
 */
@SuppressWarnings("restriction")
public class SudokuApplication extends Application {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SudokuApplication.class);

    private SudokuGrid grid;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        HBox btnBox = new HBox();
        btnBox.setPadding(new Insets(5, 5, 5, 5));
        Button btn = new Button();
        btn.setText("Solve Sudoku");
        btn.setOnAction(e -> solveSudokuGrid(new SimpleSolver(), root));
        btnBox.getChildren().add(btn);

        VBox menuBox = new VBox();

        menuBox.getChildren().add(createMenu(root));
        menuBox.getChildren().add(btnBox);

        root.setTop(menuBox);
        root.setCenter(null);

        Scene scene = new Scene(root, 320, 380);

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create menu bar with action items
     * 
     * @param pane
     *            Pane to which to add/reset grid
     * @return MenuBar
     */
    private MenuBar createMenu(BorderPane pane) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> openSudokuGrid(pane));
        fileMenu.getItems().add(openItem);

        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Opens a Sudoku and sets it in the center of the supplied pane
     * 
     * @param pane
     *            Pane
     */
    private void openSudokuGrid(BorderPane pane) {
        try {
            grid = generateGrid();
            setSudokuPane(pane);
        } catch (IllegalGridInputException e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
        }
    }

    /**
     * Sets the center pane with the generated Sudoku
     * 
     * @param pane
     *            BorderPane to fill the center of
     */
    private void setSudokuPane(BorderPane pane) {

        pane.setCenter(getSudokuPane(grid));

    }

    /**
     * Solve the Sudoku and show result
     * 
     * @param solver
     *            Solver to use
     * @param pane
     *            Pane to show result in
     */
    private void solveSudokuGrid(Solver solver, BorderPane pane) {
        if (grid != null) {
            solver.solve(grid);
            setSudokuPane(pane);
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
                segmentPane.setStyle("-fx-background-color: DDDDDD; "
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
                if (cell.getValue() == null) {
                    cellField.setEditable(true);
                } else {
                    cellField.setEditable(false);
                    cellField.setStyle("-fx-background-color: #EEEEEE");
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
        return SudokuGridBuilder.buildGrid(IOUtils.toInputStream("8;;|;;|;;\n"
                + ";;3|6;;|;;\n" + ";7;|;9;|2;;\n" + ";5;|;;7|;;\n"
                + ";;|;4;5|7;;|\n" + ";;|1;;|;3;\n" + ";;1|;;|;6;8\n"
                + ";;8|5;;|;1;\n" + ";9;|;;|4;;"));
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
