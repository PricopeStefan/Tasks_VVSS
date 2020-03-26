package ModelTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class TasksCRUDTest {
    private ArrayTaskList testTaskList;
    private Date taskStart= new Date();
    private Date taskEnd = new Date();

    @BeforeEach
    @Timeout(5)
    private void setUp()
    {
        testTaskList = new ArrayTaskList();

    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(1)
    void TC01_ECP_valid(){
        String title = new String("blabla");
        int interval = 1;
        int beforeLen = testTaskList.getAll().size();
        testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        int afterLen = testTaskList.getAll().size();
        assertEquals(beforeLen+1, afterLen);

        //tested valid case in partition (0,255]
        //other partitions are (-infinity, 0] and (255, infinity)
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(2)
    void TC2_BVA_valid() {
        String title = new String("jNpVL8mGNOA7AxV8PoFP3z7ZtQnHoHX2CLypljixAFqboe2s4zNu0o7O6P454vild4tX9roJjIYMZYsSrm9ymYZLxJAdJLH8fCJ8Poj9vU2j1tihb2hhXjavOq1HefsMBCgIYpshPQB4UjwZcBz0Dv04gcNCNZ6JEffHKcy5NnftmP3hHqfdPlYDF8iGXjkmGhpuY5e3eyTMokjkzl608Deculb38F2Dz1kfqk3HHFn6TufI2D2Qrdv1WJsnZWN");
        int interval = 1;
        int beforeLen = testTaskList.getAll().size();
        testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        int afterLen = testTaskList.getAll().size();
        assertEquals(beforeLen+1, afterLen);

        //tested valid case in partition (0,255], margin : 255 length title
        //other partitions are (-infinity, 0] and (255, infinity)
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(3)
    void TC3_ECP_invalid()
    {
        String title = new String("12345jNpVL8mGNOA7AxV8PoFP3z7ZtQnHoHX2CLypljixAFqboe2s4zNu0o7O6P454vild4tX9roJjIYMZYsSrm9ymYZLxJAdJLH8fCJ8Poj9vU2j1tihb2hhXjavOq1HefsMBCgIYpshPQB4UjwZcBz0Dv04gcNCNZ6JEffHKcy5NnftmP3hHqfdPlYDF8iGXjkmGhpuY5e3eyTMokjkzl608Deculb38F2Dz1kfqk3HHFn6TufI2D2Qrdv1WJsnZWN");
        int interval = 1;
        int beforeLen = testTaskList.getAll().size();
        assertThrows(ExceptionInInitializerError.class, () ->
        {
            testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        });
        int afterLen = testTaskList.getAll().size();
        assertNotEquals(beforeLen+1, afterLen);

        //tested valid case in partition (255,infinity), margin : 260 length title
        //other partitions are (-infinity, 0] and (255, infinity)
        //ar trebui sa crape
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(4)
    void TC4_BVA_invalid() {
        String title = new String("");
        int interval = 1;
        int beforeLen = testTaskList.getAll().size();
        assertThrows(ExceptionInInitializerError.class, () ->
        {
            testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        });

        int afterLen = testTaskList.getAll().size();
        assertNotEquals(beforeLen+1, afterLen);

        //tested valid case in partition (0,255], margin : 0 length title
        //other partitions are (-infinity, 0] and (255, infinity)
        //ar trebui sa crape
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(5)
    void TC01_ECP_validV2(){
        String title = new String("blabla");
        int interval = 5;
        int beforeLen = testTaskList.getAll().size();
        testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        int afterLen = testTaskList.getAll().size();
        assertEquals(beforeLen+1, afterLen);

        //tested valid case in partition [1,infinity]
        //other partitions are (-infinity, 0] and [1, infinity)
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(6)
    void TC2_BVA_validV2() {
        String title = new String("blabla");
        int interval = 1;
        int beforeLen = testTaskList.getAll().size();
        testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        int afterLen = testTaskList.getAll().size();
        assertEquals(beforeLen+1, afterLen);

        //tested valid case in partition [1,infinity], margin : 1 = value for interval
        //other partitions are (-infinity, 0] and [1, infinity)
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(7)
    void TC3_ECP_invalidV2()
    {
        String title = new String("blabla");
        int interval = -2;
        int beforeLen = testTaskList.getAll().size();
        assertThrows(IllegalArgumentException.class, () ->
        {
            testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        });

        int afterLen = testTaskList.getAll().size();
        assertNotEquals(beforeLen+1, afterLen);

        //tested valid case in partition [1,infinity), margin : -2 = value for interval
        //other partitions are (-infinity, 0] and [1, infinity)
        //ar trebui sa crape
    }
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Order(8)
    void TC4_BVA_invalidV2() {
        String title = new String("blabla");
        int interval = 0;
        int beforeLen = testTaskList.getAll().size();
        assertThrows(IllegalArgumentException.class, () ->
        {
            testTaskList.add(new Task(title, taskStart, taskEnd , interval));
        });

        int afterLen = testTaskList.getAll().size();
        assertNotEquals(beforeLen+1, afterLen);

        //tested valid case in partition (0,255], margin : 0 length title
        //other partitions are (-infinity, 0] and (0, infinity)
        //ar trebui sa crape
    }
}
