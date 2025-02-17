
package gazeeebo.commands.places;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class DeletePlacesCommand {
    /**
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param places  Map each name to its own phone number
     * @throws IOException catch any error if read file fails
     */

    public DeletePlacesCommand(Ui ui, Storage storage, Map<String, String> places) throws IOException, ArrayIndexOutOfBoundsException{
        if (!ui.fullCommand.equals("delete") && ui.fullCommand.contains("-")) {
            String placeToDelete = ui.fullCommand.split("-")[1];
            if (places.containsKey(placeToDelete)) {
                places.remove(placeToDelete);
                System.out.println("Successfully deleted: " + placeToDelete);
            } else {
                System.out.println(placeToDelete + " is not found in the list.");
            }
        }
        else {
            System.out.println("You need to indicate what you want to delete, Format: delete-name");
        }
        String toStore = "";
        for (String key : places.keySet()) {
            toStore = toStore.concat(key + "|" + places.get(key) + "\n");
        }
        storage.Storages_Places(toStore);
    }
}