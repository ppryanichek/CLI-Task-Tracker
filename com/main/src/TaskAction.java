package main.src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskAction {
    private final Path path = Path.of("tasks.json");
    private final List<Task> tasks;

    // loads all tasks when the object is created
    public TaskAction() {
        this.tasks = loadTasksFromJson();
    }

    // loads all tasks from JSON file to list
    private List<Task> loadTasksFromJson() {
        List<Task> storedTasks = new ArrayList<>();

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            String content = Files.readString(path);
            String[] taskList = content.replace("[", "")
                    .replace("]", "")
                    .split(",\n");
            for (String taskJSON : taskList) {
                taskJSON = taskJSON + "}";
                storedTasks.add(Task.fromJSON(taskJSON));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return storedTasks;
    }

    public void saveTasks() {
        // set new id for each task (number of task)
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setId(i + 1);
        }

        // stores tasks in string and write it to JSON
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJSON());
            if (i < tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added (ID: " + newTask.getId() + ")");
    }

    public void deleteTask(String id) {
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException());
        tasks.remove(task);
        System.out.println("Task " + id + " has been deleted!");
    }

    public void updateDescription(String id, String updated_description) {
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException());
        task.changeDescription(updated_description);
    }

    public void markDone(String id) {
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException());
        task.markDone();
    }

    public void markInProgress(String id) {
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException());
        task.markInProgress();
    }

    // displays a list of tasks with entered status or all of them at once
    public void listTasks(String type) {
        for (Task task : tasks) {
            String status = task.getStatus().toString();
            if (type.equals("All") || status.equals(type)) {
                System.out.println(task.toString());
            }
        }
    }

    // finds the task by id
    public Optional<Task> findTask(String id) {
        return tasks.stream().filter((task) -> task.getId() == Integer.parseInt(id)).findFirst();
    }

}
