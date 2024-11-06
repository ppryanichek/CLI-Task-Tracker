package main.src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private int id;
    private String description;
    private Status status;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private static int lastId = 0; // Static variable to know the last ID

    // task cuntructor
    public Task(String description) {
        this.id = ++lastId;
        this.description = description;
        this.status = Status.TODO;
        this.created = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    // mark task In Progress and change last modified time
    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.lastModified = LocalDateTime.now();
    }

    // mark task Done and change last modified time
    public void markDone() {
        this.status = Status.DONE;
        this.lastModified = LocalDateTime.now();
    }

    // changing task description and change last modified time
    public void changeDescription(String uptatedDescription) {
        this.description = uptatedDescription;
        this.lastModified = LocalDateTime.now();
    }

    // converts object properties into a JSON string
    public String toJSON() {
        return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() + "\", \"status\":\""
                + status.toString() + "\", \"createdAt\":\"" + created.format(formatter) + "\", \"lastModifiedAt\":\""
                + lastModified.format(formatter) + "\"}";
    }

    // converts task object properties from JSON file and returns
    public static Task fromJSON(String file) {
        file = file.replace("{", "").replace("}", "").replace("\"", "");
        String[] lines = file.split(",");

        String id = lines[0].split(":")[1].strip();
        String description = lines[1].split(":")[1].strip();
        String statusStr = lines[2].split(":")[1].strip();
        String createdStr = lines[3].split("[a-z]:")[1].strip();
        String lastModifiedStr = lines[4].split("[a-z]:")[1].strip();

        Status status = Status.valueOf(statusStr.toUpperCase().replace(" ", "_"));

        Task task = new Task(description);
        task.id = Integer.parseInt(id);
        task.status = status;
        task.created = LocalDateTime.parse(createdStr, formatter);
        task.lastModified = LocalDateTime.parse(lastModifiedStr, formatter);

        if (Integer.parseInt(id) > lastId) {
            lastId = task.id;
        }

        return task;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
        Task.lastId = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n Description: " + description.strip() + "\n Status: "
                + status.toString()
                + "\n Created: " + created.format(formatter) + "\n Last Update: " +
                lastModified.format(formatter);
    }
}