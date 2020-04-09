package tasks.model;

import javafx.collections.ObservableList;

import java.util.*;
import java.util.logging.Logger;

/**
 * Class that handles task operations: incoming, calendar
 */
public class TasksOperations {

    private ArrayList<Task> tasks;

    public TasksOperations(ObservableList<Task> tasksList){
        tasks=new ArrayList<>();
        tasks.addAll(tasksList);
    }

    public Iterable<Task> incoming(Date start, Date end)  {
        ArrayList<Task> incomingTasks = new ArrayList<>();
        if(start == null || end == null)
            throw new RuntimeException("Date shouldn't be null.");
        else {
            for (Task task : tasks) {
                Date nextTime = task.nextTimeAfter(start);
                if (nextTime != null) {
                    if (nextTime.before(end) || nextTime.equals(end)) {
                        incomingTasks.add(task);
                        Logger.getLogger(task.getTitle());
                    }
                }
            }
        }
        return incomingTasks;
    }
    public SortedMap<Date, Set<Task>> calendar( Date start, Date end){
        Iterable<Task> incomingTasks = incoming(start, end);
        TreeMap<Date, Set<Task>> calendar = new TreeMap<>();

        for (Task t : incomingTasks){
            Date nextTimeAfter = t.nextTimeAfter(start);
            while (nextTimeAfter!= null && (nextTimeAfter.before(end) || nextTimeAfter.equals(end))){
                if (calendar.containsKey(nextTimeAfter)){
                    calendar.get(nextTimeAfter).add(t);
                }
                else {
                    HashSet<Task> oneDateTasks = new HashSet<>();
                    oneDateTasks.add(t);
                    calendar.put(nextTimeAfter,oneDateTasks);
                }
                nextTimeAfter = t.nextTimeAfter(nextTimeAfter);
            }
        }
        return calendar;
    }
}
