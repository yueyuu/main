package gazeeebo.storage;

import java.io.*;

import gazeeebo.commands.specialization.ModuleCategory;
import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.DoAfter;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Storage {

    public static String jarDir;

    private String absolutePath = "/resources/Save.txt";
    private String absolutePath_password = "/resources/Password.txt";
    private String absolutePath_Contact = "/resources/Contact.txt";
    private String absolutePath_Expenses = "/resources/Expenses.txt";
    private String absolutePath_Places = "/resources/Places.txt";
    private String absolutePath_Trivia = "/resources/Trivia.txt";
    private String absolutePath_CAP = "/resources/CAP.txt";
    private String absolutePathSpecialization = "/resources/Specialization.txt";
    private String absolutePath_StudyPlanner = "/resources/Study_Plan.txt";
    private String absolutePathCompletedElectives = "/resources/CompletedElectives.txt";
    private String absolutePath_Prerequisite = "/resources/Prerequisite.txt";


    public void getJarDir() throws URISyntaxException {
        //File jarDirectory = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(".")).getPath());
        //jarDir = jarDirectory.getAbsolutePath();
        jarDir = new File(getClass().getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getParentFile().getPath();
    }

    public void writeToSaveFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public ArrayList<Task> readFromSaveFile() throws FileNotFoundException {
        ArrayList<Task> tList = new ArrayList<Task>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String[] details = sc.nextLine().split("\\|");
            if (details[0].equals("T")) {
                Todo t = new Todo(details[2].trim());
                if (details[1].equals("D")) {
                    t.isDone = true;
                } else {
                    t.isDone = false;
                }
                tList.add(t);
            } else if (details[0].equals("D")) {
                Deadline d = new Deadline(details[2].trim(), details[3].substring(3).trim());
                if (details[1].equals("D")) {
                    d.isDone = true;
                } else {
                    d.isDone = false;
                }
                tList.add(d);
            } else if (details[0].equals("E)")) {
                Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                if (details[1].equals("D")) {
                    e.isDone = true;
                } else {
                    e.isDone = false;
                }
                tList.add(e);
            } else if (details[0].equals("P")) {
                Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                if (details[1].equals("D")) {
                    tb.isDone = true;
                } else {
                    tb.isDone = false;
                }
                tList.add(tb);
            } else if (details[0].equals("FD")) {
                FixedDuration FD = new FixedDuration(details[2].trim(), details[3].trim());
                if (details[1].equals("D")) {
                    FD.isDone = true;
                } else {
                    FD.isDone = false;
                }
                tList.add(FD);
            } else if (details[0].equals("DA")) {
                DoAfter DA = new DoAfter(details[3].trim(), details[3].trim(), details[2].trim());
                if (details[1].equals("D")) {
                    DA.isDone = true;
                } else {
                    DA.isDone = false;
                }
                tList.add(DA);
            } else if (details[0].equals("TE")) {
                ArrayList<String> timeslots = new ArrayList<String>();
                for (int i = 3; i < details.length; i++) {
                    timeslots.add(details[i]);
                }
                TentativeEvent TE = new TentativeEvent(details[2].trim(), timeslots);
                if (details[1].equals("D")) {
                    TE.isDone = true;
                } else {
                    TE.isDone = false;
                }
                tList.add(TE);
            } else {
                if (details[3].contains("at:") || details[3].contains("by:")) {
                    Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("D")) {
                        e.isDone = true;
                    } else {
                        e.isDone = false;
                    }
                    tList.add(e);
                } else if (details[0].contains("P")) {
                    Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        tb.isDone = true;
                    } else {
                        tb.isDone = false;
                    }
                    tList.add(tb);
                } else {
                    FixedDuration FD = new FixedDuration(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        FD.isDone = true;
                    } else {
                        FD.isDone = false;
                    }
                    tList.add(FD);
                }
            }
        }
        return tList;
    }

    /**
     * Write the encoded password into the Password.txt file
     *
     * @param fileContent string to put into the txt file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToPasswordFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_password);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read from the Password.txt file, decode the passwords and put it into an array.
     *
     * @return the arrays of password
     * @throws IOException catch the error if the read file fails.
     */
    public ArrayList<StringBuilder> readFromPasswordFile() throws FileNotFoundException {
        ArrayList<StringBuilder> passwordList = new ArrayList<>();
        //InputStream inputStream = Storage.class.getResourceAsStream(jarDir + absolutePath_password);
        File f = new File(jarDir + absolutePath_password);
        Scanner sc = new Scanner(f);
        //Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            String decodedPassword = sc.nextLine();
            char[] decryption = decodedPassword.toCharArray();
            StringBuilder realPassword = new StringBuilder();
            for (int i = decodedPassword.length() - 1; i >= 0; i--) {
                realPassword.append(decryption[i]);
            }
            passwordList.add(realPassword);
        }
        return passwordList;
    }

    /**
     * THis method writes to the file Contact.txt
     *
     * @param fileContent save the contact information into this file
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToContactFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_Contact);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file Contact.txt and put the details into a HashMap
     *
     * @return Returns the HashMap of contacts, key is the contact name and the value is the phone number.
     * @throws IOException catch the error if the read file fails.
     */
    public HashMap<String, String> readFromContactFile() throws FileNotFoundException {
        HashMap<String, String> contactList = new HashMap<String, String>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_Contact);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_Contact);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String[] split = sc.nextLine().split("\\|");
            contactList.put(split[0], split[1]);
        }
        return contactList;
    }

    public void Storages_Expenses(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_Expenses);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public void storagesPlaces(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_Places);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<LocalDate, ArrayList<String>> Expenses() throws FileNotFoundException {
        HashMap<LocalDate, ArrayList<String>> expenses = new HashMap<LocalDate, ArrayList<String>>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_Expenses);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_Expenses);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ArrayList<String> itemAndPriceList = new ArrayList<>();
            String[] split = sc.nextLine().split("\\|");
            LocalDate dateOfPurchase = LocalDate.parse(split[0], fmt);
            boolean isEqual = false;
            for (LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) { //if date equal
                    expenses.get(key).add(split[1]);
                    isEqual = true;
                }
            }
            if (isEqual == false) {
                itemAndPriceList.add(split[1]);
                expenses.put(dateOfPurchase, itemAndPriceList);
            }
        }
        return expenses;
    }

    public HashMap<String, String> readPlaces() throws IOException {
        HashMap<String, String> placesList = new HashMap<String, String>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_Places);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_Places);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String[] split = sc.nextLine().split("\\|");
            placesList.put(split[0], split[1]);
        }
        return placesList;
    }

    public Map<String, ArrayList<String>> Read_Trivia() throws FileNotFoundException {
        Map<String, ArrayList<String>> CommandMemory = new HashMap<>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_Trivia);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_Trivia);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String InputCommand = sc.nextLine();
            if (CommandMemory.containsKey(InputCommand.split(" ")[0])) {
                ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(InputCommand.split(" ")[0]));
                if (!oldlist.contains(InputCommand)) {
                    oldlist.add(InputCommand);
                    CommandMemory.put(InputCommand.split(" ")[0], oldlist);
                }
            } else {
                ArrayList<String> newlist = new ArrayList<String>();
                newlist.add(InputCommand);
                CommandMemory.put(InputCommand.split(" ")[0], newlist);
            }
        }
        sc.close();
        return CommandMemory;
    }

    public void Storage_Trivia(String fileContent) throws IOException {
        File file = new File(jarDir + absolutePath_Trivia);
        if(file.exists() && !file.canWrite()){
            System.out.println("File exists and it is read only, making it writable");
            file.setWritable(true);
        }
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_Trivia);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.newLine();
        bufferedWriter.write(fileContent);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * Write to the CAP.txt file (save in the file).
     *
     * @param fileContent string to put into the file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToCAPFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePath_CAP);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read from the file CAP.txt and put the details into a HashMap
     *
     * @return Returns the HashMap of contacts, key is the contact name and the value is the phone number
     * @throws IOException catch the error if the read file fails.
     */
    public HashMap<String, ArrayList<CAPCommand>> readFromCAPFile() throws IOException {
        HashMap<String, ArrayList<CAPCommand>> CAPList = new HashMap<String, ArrayList<CAPCommand>>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_CAP);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_CAP);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ArrayList<CAPCommand> moduleList = new ArrayList<>();
            String[] splitStringTxtFile = sc.nextLine().split("\\|");
            String semNumber = splitStringTxtFile[0];
            String moduleCode = splitStringTxtFile[1];
            int mc = Integer.parseInt(splitStringTxtFile[2]);
            String grade = splitStringTxtFile[3];
            CAPCommand newCAP = new CAPCommand(moduleCode, mc, grade);
            boolean isEqual = false;
            for (String key : CAPList.keySet()) {
                if (semNumber.equals(key)) {
                    CAPList.get(key).add(newCAP);
                    isEqual = true;
                }
            }
            /* semNumber doesn't exist in the list */
            if (isEqual == false) {
                moduleList.add(newCAP);
                CAPList.put(semNumber, moduleList);
            }
            /* semNumber doesn't exist in the list */
            if (isEqual == false) {
                moduleList.add(newCAP);
                CAPList.put(semNumber, moduleList);
            }
        }
        return CAPList;
    }

    public void specializationStorage(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePathSpecialization);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<String, ArrayList<ModuleCategory>> Specialization() throws IOException {
        HashMap<String, ArrayList<ModuleCategory>> specMap = new HashMap<>();
        if (new File(jarDir + absolutePathSpecialization).exists()) {
            File file = new File(jarDir + absolutePathSpecialization);
            Scanner sc = new Scanner(file);
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePathSpecialization);
        //Scanner sc = new Scanner(inputStream);
            while (sc.hasNext()) {
                String[] split = sc.nextLine().split("\\|");
                ArrayList<ModuleCategory> moduleBD = new ArrayList<>();
                ModuleCategory mC = new ModuleCategory(split[2]);
                moduleBD.add(mC);
                specMap.put(split[1], moduleBD);
            }
        }
        return specMap;
    }

    public void completedElectivesStorage(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(jarDir + absolutePathCompletedElectives);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<String, ArrayList<String>> completedElectives() throws IOException {
        HashMap<String, ArrayList<String>> completedEMap = new HashMap<>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePathCompletedElectives);
        //Scanner sc = new Scanner(inputStream);
        if (new File(jarDir + absolutePathCompletedElectives).exists()) {
            File file = new File(jarDir + absolutePathCompletedElectives);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                ArrayList<String> completedElectiveList = new ArrayList<>();
                String[] split = sc.nextLine().split("\\|");
                String checkKey = split[0];
                boolean isEqual = false;
                for (String key : completedEMap.keySet()) {
                    if (checkKey.equals(key)) { //if date equal
                        completedEMap.get(key).add(split[1]);
                        isEqual = true;
                    }
                    if (isEqual == false) {
                        completedElectiveList.add(split[1]);
                        completedEMap.put(checkKey, completedElectiveList);
                    }
                }
            }
        }
        return completedEMap;
    }

    public ArrayList<ArrayList<String>> Read_StudyPlan () throws IOException {
        ArrayList<ArrayList<String>> studyplan = new ArrayList<ArrayList<String>>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_StudyPlanner);
        //Scanner sc = new Scanner(inputStream);
        if (new File(jarDir + absolutePath_StudyPlanner).exists()) {
            File file = new File(jarDir + absolutePath_StudyPlanner);
            Scanner sc = new Scanner(file);
            for (int i = 0; i < 8; i++) {
                if (sc.hasNext()) {
                    String[] split = sc.nextLine().split(" ");
                    ArrayList<String> temp = Arrays.stream(split).collect(Collectors.toCollection(ArrayList::new));
                    studyplan.add(temp);
                } else {
                    ArrayList<String> temp = new ArrayList<String>();
                    studyplan.add(temp);
                }
            }
        }
        return studyplan;
    }

    public void Storage_StudyPlan (String fileContent) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(jarDir + absolutePath_StudyPlanner));
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<String, ArrayList<String>> readFromPrerequisiteFile() throws FileNotFoundException {
        HashMap<String,ArrayList<String>> PrerequisiteList = new  HashMap<String,ArrayList<String>>();
        //InputStream inputStream = Storage.class.getResourceAsStream(absolutePath_Prerequisite);
        //Scanner sc = new Scanner(inputStream);
        File f = new File(jarDir + absolutePath_Prerequisite);
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String WholeSentence = sc.nextLine();
            String head = WholeSentence.split(" ")[0];
            ArrayList<String> Prerequisites = new ArrayList<String>();
            for (int i = 1;i<WholeSentence.split(" ").length;i++){
                Prerequisites.add(WholeSentence.split(" ")[i]);
            }
            PrerequisiteList.put(head,Prerequisites);
        }
        return PrerequisiteList;
    }
}