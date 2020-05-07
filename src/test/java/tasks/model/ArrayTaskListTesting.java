package tasks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;

//clasa de testat clasa ArrayTaskList using mockito spies
public class ArrayTaskListTesting {
    private ArrayTaskList tasks_array;

    @BeforeEach
    public void setUp() {
        tasks_array = new ArrayTaskList();
    }

    @Test
    public void test01_spy() {
        ArrayTaskList spied_array = spy(tasks_array);

        Task t1 = new Task("task01", new Date());
        Mockito.doNothing().when(spied_array).add(t1);
        spied_array.add(t1);
        assert spied_array.getAll().size() == 0;

        Task t2 = new Task("", new Date());
        Mockito.doThrow(ExceptionInInitializerError.class).when(spied_array).add(t2);
        assertThrows(ExceptionInInitializerError.class, () -> {
            spied_array.add(t2);
        });
    }

    @Test
    public void test02_spy() {
        ArrayTaskList spied_array = spy(tasks_array);

        assert spied_array.getAll().size() == 0;
        Task t1 = new Task("task01", new Date());
        Mockito.when(spied_array.getAll()).thenReturn(Arrays.asList(t1));
        assert spied_array.getAll().size() == 1;
    }
}
