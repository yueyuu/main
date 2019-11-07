package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;

import java.io.IOException;

import gazeeebo.exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DeleteCommand extends Command {
    /**
     * @param list         task lists
     * @param ui           deals with printing things to the user.
     * @param storage      deals with storing data.
     * @param commandStack keep stack of previous commands.
     * @param deletedTask  keep stack of deleted tasks.
     * @throws DukeException
     * @throws ParseException catch error if parse string to date fails.
     * @throws IOException catch the error if the read file fails.
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui,
                        final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException,
            ParseException, IOException {
        if (ui.fullCommand.length() == 6) {
            throw new DukeException("OOPS!!! The description of a deletion cannot be empty.");
        } else {
            if (ui.fullCommand.contains("all")) { //delete all tasks at once
                for (int i = 0; i < list.size(); i++) {
                    deletedTask.add(list.get(i));
                }
                list.clear();
                System.out.println("Noted. I've removed all the tasks.");
                System.out.println("Now you have " + list.size() + " tasks in the list.");

            } else if (ui.fullCommand.contains("and")) { //delete multiple chosen tasks
                int numOfAnds = 0;
                for (int i = 0; i < ui.fullCommand.length(); i++) {
                    if (ui.fullCommand.charAt(i) == 'a') {
                        numOfAnds++;
                    }
                }
                String[] strNumberList = ui.fullCommand.substring(7).split(" and ", numOfAnds + 1);
                int size = strNumberList.length;
                int[] intNumberList = new int[size];
                for (int j = 0; j < size; j++) {
                    intNumberList[j] = Integer.parseInt(strNumberList[j]);
                }
                Arrays.sort(intNumberList);
                int count = 1;
                System.out.println("Noted. I've removed this task: ");
                for (int k = 0; k < size; k++) {
                    int index = intNumberList[k] - count;
                    String taskremoved = list.get(index).listFormat();
                    deletedTask.add(list.get(index));
                    list.remove(index);
                    System.out.println(taskremoved);
                    count++;
                }
                System.out.println("Now you have " + list.size() + " tasks in the list.");
            } else if (ui.fullCommand.split(" ")[1] != null) {
                try {
                    int index = Integer.parseInt(ui.fullCommand.substring(6).trim()) - 1;
                    deletedTask.add(list.get(index));
                    String taskremoved = list.get(index).listFormat();
                    list.remove(index);
                    System.out.println("Noted. I've removed this task: ");
                    System.out.println(taskremoved);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input for delete command");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task number not found");
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
        }
    }

    /** isExit is false, won't terminate program.*/
    @Override
    public boolean isExit() {
        return false;
    }

}