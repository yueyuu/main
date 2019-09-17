import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import commands.Command;
import parsers.*;
import Exception.DukeException;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;


public class Duke {
    /**
     * Returns void main function for duke.
     *
     * @param args
     * @return Void.
     * @throws DukeException | ParseException | IOException | NullPointerException
     */
    public static void main(String[] args) {

        ArrayList<Task> list;
        Storage store = new Storage();
        boolean isExit = false;
        Ui ui = new Ui();
        ui.showWelcome();
        try {
            list = store.Readfile();
            ui.UpcomingTask(list);
            while(!isExit) {
                ui.ReadCommand();
                String command = ui.FullCommand.split(" ")[0];
                Command c = Parser.parse(command);
                c.execute(list, ui, store);
                isExit = c.isExit();
            }
        }
        catch (DukeException | ParseException | IOException | NullPointerException e){
            if(e instanceof ParseException){
                ui.showDateFormatError();
            }
            else if (e instanceof IOException){
                ui.showIOErrorMessage(e);
            }
            else{
                ui.showErrorMessage(e);
            }
        }
        finally{
            System.out.println("System exiting");
        }
    }
}