package gazeeebo.parsers;

import gazeeebo.UI.Ui;
import gazeeebo.commands.ByeCommand;
import gazeeebo.commands.expenses.ExpenseCommand;

import gazeeebo.commands.gpacalculator.GPACommand;
import gazeeebo.commands.note.GeneralNoteCommand;
import gazeeebo.commands.tasks.taskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.*;
import gazeeebo.commands.contact.ContactsCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.places.PlacesCommand;

public class Parser {
    public static Command parse(final String command, Ui ui) throws DukeException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("help")) {
            return new HelpCommand();
        } else if (command.equals("contact")) {
            return new ContactsCommand();
        } else if (command.equals("expenses")) {
            return new ExpenseCommand();
        } else if (command.equals("places")) {
            return new PlacesCommand();
        } else if (splitCommand[0].equals("bye")) {
            return new ByeCommand();
        } else if (splitCommand[0].equals("tasks")) {
            return new taskCommand();
        } else if (splitCommand[0].equals("gpa")) {
            int moduleCredit = 0;
            double score = 0.0;
            return new GPACommand(moduleCredit, score);
        } else if (splitCommand[0].equals("notes")) {
            return new GeneralNoteCommand();
        } else {
            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}