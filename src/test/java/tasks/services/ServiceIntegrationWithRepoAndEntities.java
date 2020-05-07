package tasks.services;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

public class ServiceIntegrationWithRepoAndEntities {
    private ArrayTaskList repo;
    private TasksService service;

    @BeforeEach
    public void setUp() {
        repo = new ArrayTaskList();
        service = new TasksService(repo);
    }

    @Test
    public void test01_integration() {
        assertEquals(service.getObservableList().size(), 0);

        Task t1 = new Task("task1", new Date());
        repo.add(t1);

        assertEquals(service.getObservableList().size(), 1);
        assertEquals(FXCollections.observableArrayList(Collections.singletonList(t1)), service.getObservableList());

        Task t2 = new Task("", new Date());
        assertThrows(ExceptionInInitializerError.class, ()-> {
            repo.add(t2);
        });
        assertEquals(service.getObservableList().size(), 1);
        assertEquals(FXCollections.observableArrayList(Collections.singletonList(t1)), service.getObservableList());
    }

    @Test
    public void test02_integration() {
        Task t1 = new Task("task1", new Date(2020, Calendar.MAY, 7));
        Task t2 = new Task("task2", new Date(2020, Calendar.MAY, 6));
        Task t3 = new Task("task3", new Date(2020, Calendar.MAY, 5));
        repo.add(t1);
        repo.add(t2);
        repo.add(t3);
        assertEquals(service.getObservableList().size(), 3);

        Date date1 = new Date(2020, Calendar.JULY, 10);
        Date date2 = new Date(2020, Calendar.JULY, 11);
        ArrayTaskList filtered = new ArrayTaskList();
        service.filterTasks(date1, date2).forEach(filtered::add);
        assertEquals(filtered.size(), 0);
    }
}
