package allen.compsci;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taylor hudson on 1/3/2017.
 */
public class Faculty {
    private String FName;
    private String LName;
    private String FullName;
    private String ID;
    private String CSVTopic;
    public Faculty(String [] elments){
        ID = elments[0];
        FName = elments[1];
        LName = elments[2];
        CSVTopic = elments[3];
        FullName = FName + " " + LName;
        setID();
    }
    public Faculty(ArrayList<String> TCHR){
        FName = LName = ID = CSVTopic = FullName = "";
        LName = TCHR.get(0).substring(TCHR.get(0).lastIndexOf(' ')).trim();
        FullName = TCHR.get(0).trim();
        FName = FullName.substring(0, FullName.indexOf(' ')).trim();
        CSVTopic = TCHR.get(1).trim();
        ID = TCHR.get(2).trim();
        setID();
    }
    public String getID(){ return ID; }
    public void setID(){
        if(ID.equals("861127")){
            CSVTopic = "Computer Science Club";
        }
        else if(ID.equals("316414") || ID.equals("242348")){
            CSVTopic = "Robotics";
        }
        else if(ID.equals("775518")){
            CSVTopic = "Architecture & Design";
        }

    }
    public String getName(){
        return FullName;
    }
    public String comma(){
        return LName + ", " + FName;
    }
    public String getcsv(){ return CSVTopic; }
    public boolean compareID(long value){
        return compareID(String.valueOf(value));
    }
    public boolean compareID(Long value){
        return compareID(String.valueOf(value));
    }
    public boolean compareID(String value){
        return value.equals(ID);
    }
    public List<String> toList(){
        List<String> t = new ArrayList<>();
        t.add(ID);
        t.add(FName);
        t.add(LName);
        t.add(CSVTopic);
        return t;
    }
    public static void printList(ArrayList<Faculty> studentCollection, String csv) throws IOException {
        FileWriter write = new FileWriter(csv);
        List<String> sys = new ArrayList<>();
        sys.add("000000");
        sys.add("The");
        sys.add("System");
        sys.add("Blank");
        for(Faculty k : studentCollection){
            CSVUtils.writeLine(write, k.toList());
        }
        CSVUtils.writeLine(write,sys);
        write.close();
    }
}
