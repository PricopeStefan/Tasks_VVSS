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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

public class ServiceIntegrationWithRepo {
    @Mock
    private ArrayTaskList repo;
    @InjectMocks
    private TasksService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test01_integration() {
        assertEquals(service.getObservableList().size(), 0);

        Task t1 = new Task("task1", new Date());
        Mockito.when(repo.getAll()).thenReturn(Arrays.asList(t1));
        assertEquals(service.getObservableList().size(), 1);
        Mockito.verify(repo, times(2)).getAll();
        assertEquals(FXCollections.observableArrayList(Arrays.asList(t1)), service.getObservableList());
    }

    @Test
    public void test02_integration() {
        Task t1 = new Task("task1", new Date(2020, Calendar.MAY, 7));
        Task t2 = new Task("task2", new Date(2020, Calendar.MAY, 6));
        Task t3 = new Task("task3", new Date(2020, Calendar.MAY, 5));
        Mockito.when(repo.getAll()).thenReturn(Arrays.asList(t1, t2, t3));

        Date date1 = new Date(2020, Calendar.JULY, 10);
        Date date2 = new Date(2020, Calendar.JULY, 11);
        ArrayTaskList filtered = new ArrayTaskList();
        service.filterTasks(date1, date2).forEach(filtered::add);
        Mockito.verify(repo, times(1)).getAll();
        assertEquals(filtered.size(), 0);
    }



}
