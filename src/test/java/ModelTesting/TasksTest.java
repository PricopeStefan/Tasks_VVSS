package ModelTesting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TasksTest {
    private static Task task1;
    private static Task task2;
    private static SimpleDateFormat sdf;
    private static TasksOperations tasksOps;
    private static ObservableList<Task> observableList;

    @BeforeAll
    @DisplayName("setup")
    static void setup() throws ParseException {

        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String dateInString1 = "01-04-2020 10:10";
        String dateInString2 = "01-04-2020 12:10";
        Date dateStar = sdf.parse(dateInString1);
        Date dateEnd = sdf.parse(dateInString2);
        task1 = new Task("Descriere", dateStar, dateEnd, 2);
        task1.setActive(true);


        dateInString1 = "01-04-2020 10:20";
        dateInString2 = "01-04-2020 12:20";
        dateStar = sdf.parse(dateInString1);
        dateEnd = sdf.parse(dateInString2);
        task2 = new Task("Descriere", dateStar, dateEnd, 2);
        task2.setActive(true);


    }


    //here we test if the program throws error when end date is null
    @ParameterizedTest
    @CsvSource({
            "02-09-2020 14:17,         null"
    })
    public void F02_TC1(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            tasksOps.incoming(dateStar, dateEnd);
            assert (false);
        } catch (RuntimeException ex) {
            assert (true);
        }

    }

    //here we test if the program throws error when start date is null
    @ParameterizedTest
    @CsvSource({
            "null,         02-09-2020 14:17"
    })
    public void F02_TC2(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            tasksOps.incoming(dateStar, dateEnd);
            assert (false);
        } catch (RuntimeException ex) {
            assert (true);
        }


    }


    //here we test if the program correctly steps over the if statement as the condition is not fulfilled
    @ParameterizedTest
    @CsvSource({
            "02-09-2020 13:10,         02-09-2020 13:20"
    })
    public void F02_TC3(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        observableList.add(task1);
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            Iterable<Task> list = tasksOps.incoming(dateStar, dateEnd);
            assertEquals(list.spliterator().getExactSizeIfKnown(), 0);
        } catch (RuntimeException ex) {
            assert (false);
        }
    }

    //here we test if the program correctly passes the second inner if (in the for statement) to still return no incoming tasks
    @ParameterizedTest
    @CsvSource({
            "01-04-2020 8:00,         01-04-2020 9:00"
    })
    public void F02_TC4(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        observableList.add(task1);
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            Iterable<Task> list = tasksOps.incoming(dateStar, dateEnd);
            assertEquals(list.spliterator().getExactSizeIfKnown(), 0);
        } catch (RuntimeException ex) {
            assert (false);
        }

    }
    //here we test if the program correctly adds the task up (all the if stataments pass)
    @ParameterizedTest
    @CsvSource({
            "01-04-2020 10:00,         01-04-2020 12:10"
    })
    public void F02_TC6(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        observableList.add(task1);
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            Iterable<Task> list = tasksOps.incoming(dateStar, dateEnd);
            assertEquals(list.spliterator().getExactSizeIfKnown(), 1);
        } catch (RuntimeException ex) {
            assert (false);
        }
    }

    //almost the same as the previous test but here we have multiple tasks
    @ParameterizedTest
    @CsvSource({
            "01-04-2020 10:00,         01-04-2020 15:00"
    })
    public void F02_TC5(String startTime, String endTime) throws ParseException {

        observableList = FXCollections.observableArrayList();
        observableList.add(task1);
        observableList.add(task2);
        tasksOps = new TasksOperations(observableList);

        Date dateStar;
        Date dateEnd;
        dateStar = (startTime.equals("null")) ? null : sdf.parse(startTime);
        dateEnd = (endTime.equals("null")) ? null : sdf.parse(endTime);

        try {
            Iterable<Task> list = tasksOps.incoming(dateStar, dateEnd);
            assertEquals(list.spliterator().getExactSizeIfKnown(), 2);
        } catch (RuntimeException ex) {
            assert (false);
        }

    }

}
