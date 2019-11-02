package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class checkPrerequisiteCommand {
    public void execute(Ui ui, Storage storage) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> PrerequisiteList = new HashMap<String,ArrayList<String>>(storage.readFromPrerequisiteFile());
        if(!PrerequisiteList.get(ui.fullCommand.split(" ")[1]).isEmpty()){
            StringBuilder buffer = new StringBuilder();
            String Prefix = "";
            String ChildrenPrefix = "";
//            test(ui.fullCommand.split(" ")[1],Prefix,ChildrenPrefix,buffer,PrerequisiteList);
            dfsPrerequisite(ui.fullCommand.split(" ")[1],Prefix,ChildrenPrefix,buffer,PrerequisiteList);
            System.out.println(buffer);
        }else{
            System.out.println("This module "+ui.fullCommand+" does not have any pre-requisite~");
        }
        return;
    }
    private void dfsPrerequisite(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
        buffer.append(Prefix);
        buffer.append(ModuleCode);
        buffer.append("\n");
        if(PrerequisiteList.get(ModuleCode) != null) {
            for (Iterator<String> it = PrerequisiteList.get(ModuleCode).iterator(); it.hasNext(); ) {
                String next = it.next();
                if (it.hasNext()) {
                    Prefix = ChildrenPrefix + "├── ";
                    ChildrenPrefix += ChildrenPrefix + "│   ";
                    dfsPrerequisite(next, Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                } else {
                    Prefix = ChildrenPrefix + "└── ";
                    ChildrenPrefix += "    ";
                    dfsPrerequisite(next, Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                }
            }
        }
        return;
    }
//    private void test(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
//        System.out.println("Can.");
//    }
}


