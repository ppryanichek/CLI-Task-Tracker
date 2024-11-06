package main.src;

public class TaskTracker {
    public static void main(String[] args) {

        TaskAction taskAction = new TaskAction();
        // displays a hint for using the program
        if ("-help".equals(args[0])) {
            System.out.println("taks-cli <command> [argument]");
            return;
        }
        if ("help".equals(args[0])) {
            System.out.println("maybe you mean \"-help\"");
            return;
        }

        switch (args[0]) {
            // adding task
            case "add" -> {
                if (!(args.length < 2)) {
                    if ("-help".equals(args[1])) {
                        System.out.println("taks-cli: add <description>");
                        break;
                    }
                    taskAction.addTask(args[1]);
                    break;
                }
            }

            // updating task description
            case "update" -> {
                if (!(args.length < 2)) {
                    if ("-help".equals(args[1])) {
                        System.out.println("taks-cli update <id> <description>");
                        break;
                    }
                    taskAction.updateDescription(args[1], args[2]);
                    break;
                }
            }

            // deleting task by id
            case "delete" -> {
                if (!(args.length < 2)) {
                    if ("-help".equals(args[1])) {
                        System.out.println("taks-cli delete <id>");
                        break;
                    }
                    taskAction.deleteTask(args[1]);
                    break;
                }
            }
            // displays a hint for using "mark" command
            case "mark" -> {
                System.out.println("task-cli <mark-done, mark-in-progress> <id>");
            }

            // marks task as "Done" by id
            case "mark-done" -> {
                if (!(args.length < 2)) {
                    if ("-help".equals(args[1])) {
                        System.out.println("taks-cli mark-done <id>");
                        break;
                    }
                    taskAction.markDone(args[1]);
                    break;
                }
            }

            // marks task as "In Progress" by id
            case "mark-in-progress" -> {
                if (!(args.length < 2)) {
                    if ("-help".equals(args[1])) {
                        System.out.println("taks-cli mark-in-progress <id>");
                        break;
                    }
                    taskAction.markInProgress(args[1]);
                    break;
                }
            }

            // displays task by status or all of them
            case "list" -> {
                if (args.length > 2 && "-help".equals(args[1])) {
                    System.out.println("task-cli list <status>");
                    System.out.println("task-cli list <> for all tasks");
                    break;
                }
                if (args.length < 2) {
                    taskAction.listTasks("All");
                } else {
                    Status filterStatus;
                    try {
                        filterStatus = Status.valueOf(args[1].toUpperCase().replace("-", "_").strip());
                    } catch (Exception e) {
                        System.out.println("Invalid status: " + args[1]);
                        break;
                    }
                    taskAction.listTasks(filterStatus.toString());
                }
            }

            // displays if command not found
            default -> {
                System.out.println("No such options!");
                break;
            }
        }

        // saving tasks in JSON file
        taskAction.saveTasks();

    }
}
