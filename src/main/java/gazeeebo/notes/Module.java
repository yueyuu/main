package gazeeebo.notes;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class Module {
    public String name;
    private ArrayList<Assessment> assessments;
    private ArrayList<String> miscellaneousInfo;

    public Module(String name) {
        this.name = name;
        this.assessments = new ArrayList<>();
        this.miscellaneousInfo = new ArrayList<>();
    }

    public void viewModule() {
        System.out.println("[ " + name + " ]");
        System.out.println("Assessments:");
        for (int i = 0; i < assessments.size(); i++) {
            System.out.println((i+1) + ". " + assessments.get(i).toString());
        }
        System.out.print("\n");
        System.out.println("Miscellaneous:");
        for (int i = 0; i < miscellaneousInfo.size(); i++) {
            System.out.println((i+1) + ". " + miscellaneousInfo.get(i));
        }
    }
    public void editName(Ui ui) throws IOException {
        System.out.println("What do you want to edit the name to?");
        ui.readCommand();
        name = ui.fullCommand;
        System.out.println("Okay we have successfully updated the module name to:");
        System.out.println(name);
    }

    //ASSESSMENT FEATURES-------------------------------------------------------------------------
    public void addAssessment(Ui ui) throws IOException {
        System.out.println("What assessment do you want to add?");
        ui.readCommand();
        String name = ui.fullCommand;
        System.out.println("What is the weightage of the assessment?");
        boolean isInt = false;
        int percentage = -1;
        do {
            ui.readCommand();
            try {
                percentage = Integer.parseInt(ui.fullCommand);
                if (percentage >= 0) {
                    isInt = true;
                } else {
                    System.out.println("Please input a positive number.");
                }
            } catch (NumberFormatException n) {
                System.out.println("Please input a number.");
            }
        } while (!isInt);
        Assessment newAssessment = new Assessment(name, percentage);
        assessments.add(newAssessment);
        System.out.println("Okay we have successfully added this assessment:");
        System.out.println(newAssessment.toString());
    }

    public void editAssessmentName(Ui ui) throws IOException {
        System.out.println("Which assessment do you want to edit?");
        String[] indexAndOldName = checkIfValidIndexAssmt(ui);
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null: "Bug in notes.Module: editAssessment: oldName";
        assert assmtNum != -1 : "Bug in notes.Module: editAssessment: assmtNum";
        System.out.println("What do you want to change the name to?");
        ui.readCommand();
        assessments.get(assmtNum).name = ui.fullCommand;
        System.out.println("Okay we have successfully changed the name of \"" + oldName + "\" to:");
        System.out.println(ui.fullCommand);
    }

    private String[] checkIfValidIndexAssmt(Ui ui) throws IOException {
        int assmtNum = -1;
        String assmtName = null;
        boolean isValidIndex = false;
        do {
            ui.readCommand();
            try {
                assmtNum = Integer.parseInt(ui.fullCommand)-1;
                try {
                    assmtName = assessments.get(assmtNum).name;
                    isValidIndex = true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sorry there is no such index.");
                }
            } catch (NumberFormatException n) {
                System.out.println("Please input the index of the assessment.");
            }
        } while (!isValidIndex);
        return new String[]{Integer.toString(assmtNum), assmtName};
    }

    public void editAssessmentWeightage(Ui ui) throws IOException {
        System.out.println("Which assessment do you want to edit?");
        String[] indexAndOldName = checkIfValidIndexAssmt(ui);
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null: "Bug in notes.Module: editAssessment: oldName";
        assert assmtNum != -1 : "Bug in notes.Module: editAssessment: assmtNum";
        System.out.println("What do you want to change the weightage to?");
        boolean isInt = false;
        do {
            ui.readCommand();
            try {
                assessments.get(assmtNum).weightage = Integer.parseInt(ui.fullCommand);
                if (assessments.get(assmtNum).weightage >= 0) {
                    isInt = true;
                } else {
                    System.out.println("Please input a positive number.");
                }
            } catch (NumberFormatException n) {
                System.out.println("Please input a number.");
            }
        } while (!isInt);
        System.out.println("Okay we have successfully changed the weightage to:");
        System.out.println(ui.fullCommand);
    }

    public void deleteAssessment(Ui ui) throws IOException {
        System.out.println("Which assessment do you want to delete?");
        String[] indexAndAssmtToDelete = checkIfValidIndexAssmt(ui);
        int assmtNum = Integer.parseInt(indexAndAssmtToDelete[0]);
        String assmtToDelete = indexAndAssmtToDelete[1];
        assert assmtToDelete != null: "Bug in notes.Module: deleteAssessment: assmtToDeleteName";
        assert assmtNum != -1 : "Bug in notes.Module: deleteAssessment: assmtNum";
        assessments.remove(assmtNum);
        System.out.println("Okay we have successfully deleted this assessment:");
        System.out.println(assmtToDelete);
    }

    //MISCELLANOUS INFORMATION FEATURES-----------------------------------------------------------------
    public void addMiscellaneous(Ui ui) throws IOException {
        System.out.println("What miscellaneous information do you want to add?");
        ui.readCommand();
        miscellaneousInfo.add(ui.fullCommand);
        System.out.println("Okay we have successfully added this miscellaneous information:");
        System.out.println(ui.fullCommand);
    }

    private String[] checkIfValidIndexMsc(Ui ui) throws IOException {
        int mscNum = -1;
        String mscName = null;
        boolean isValidIndex = false;
        do {
            ui.readCommand();
            try {
                mscNum = Integer.parseInt(ui.fullCommand)-1;
                try {
                    mscName = miscellaneousInfo.get(mscNum);
                    isValidIndex = true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sorry there is no such index.");
                }
            } catch (NumberFormatException n) {
                System.out.println("Please input the index of the miscellaneous information.");
            }
        } while (!isValidIndex);
        return new String[]{Integer.toString(mscNum), mscName};
    }

    public void editMiscellaneous(Ui ui) throws IOException {
        System.out.println("Which miscellaneous information do you want to edit?");
        ui.readCommand();
        String[] indexAndMscToEdit = checkIfValidIndexMsc(ui);
        int mscNum = Integer.parseInt(indexAndMscToEdit[0]);
        String mscToEdit = indexAndMscToEdit[1];
        assert mscToEdit != null: "Bug in notes.Module: editMiscellaneous: mscToEdit";
        assert mscNum != -1 : "Bug in notes.Module: editMiscellaneous: mscNum";
        System.out.println("What do you want to change the miscellaneous information to?");
        ui.readCommand();
        miscellaneousInfo.set(mscNum, ui.fullCommand);
        System.out.println("Okay we have successfully changed \"" + mscToEdit + "\" to:");
        System.out.println(ui.fullCommand);
    }

    public void deleteMiscellaneous(Ui ui) throws IOException {
        System.out.println("Which miscellaneous information do you want to delete?");
        ui.readCommand();
        String[] indexAndMscToDelete = checkIfValidIndexMsc(ui);
        int mscNum = Integer.parseInt(indexAndMscToDelete[0]);
        String mscToDelete = indexAndMscToDelete[1];
        assert mscToDelete != null: "Bug in notes.Module: deleteMiscellaneous: mscToDelete";
        assert mscNum != -1 : "Bug in notes.Module: deleteMiscellaneous: mscNum";
        miscellaneousInfo.remove(mscNum);
        System.out.println("Okay we have successfully deleted this miscellaneous information:");
        System.out.println(mscToDelete);
    }

}
