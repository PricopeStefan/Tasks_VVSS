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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

public class TasksServiceTesting {
    @Mock
    private ArrayTaskList repo;

    @InjectMocks
    private TasksService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test01_mock() {
        assertEquals(service.getObservableList().size(), 0);

        Task t1 = new Task("task1", new Date());
        Mockito.when(repo.getAll()).thenReturn(Arrays.asList(t1));
        assertEquals(service.getObservableList().size(), 1);
        Mockito.verify(repo, times(2)).getAll();
        assertEquals(FXCollections.observableArrayList(Arrays.asList(t1)), service.getObservableList());
    }

    @Test
    public void test02_mock() {
        Task t1 = new Task("task1", new Date(2020, 05, 21));
        Mockito.when(repo.getAll()).thenReturn(Arrays.asList(t1));

        ArrayList<Task> filtered = new ArrayList<>();
        service.filterTasks(new Date(2020, 04, 18), new Date(2020, 04, 20)).forEach(filtered::add);
        assertEquals(filtered.size(), 0);

        Date date1 = new Date(2020, 05, 20);
        Date date2 = new Date(2020, 05, 22);
        Mockito.when(service.filterTasks(date1, date2)).thenReturn(Arrays.asList(t1));
        service.filterTasks(date1, date2).forEach(filtered::add);
        assertEquals(filtered.size(), 0);
    }
}
