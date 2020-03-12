package tasks.ErrorHandling;

import javafx.scene.control.Alert;

/**
 * Error handling class for GUI
 */
public class ErrHandler implements Thread.UncaughtExceptionHandler {


    public void uncaughtException(Thread t, Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("RIP!");

        alert.showAndWait();
    }
}