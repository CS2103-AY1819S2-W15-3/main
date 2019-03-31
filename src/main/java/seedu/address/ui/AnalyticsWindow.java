package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a analytics page
 */
public class AnalyticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(AnalyticsWindow.class);
    private static final String FXML = "AnalyticsWindow.fxml";

    @FXML
    private TextArea analyticsDisplay;

    /**
     * Creates a new AnalyticsWindow.
     *
     * @param root Stage to use as the root of the AnalyticsWindow.
     */
    public AnalyticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new AnalyticsWindow.
     */
    public AnalyticsWindow() {
        this(new Stage());
    }

    /**
     * Shows the Analytics window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(String analytics) {
        logger.fine("Showing analytics results.");
        getRoot().show();
        analyticsDisplay.setText(analytics);
    }

    /**
     * Returns true if the analytics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the analytics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the analytics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
