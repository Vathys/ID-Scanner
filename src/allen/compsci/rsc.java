package allen.compsci;
import java.util.*;
import java.io.*;

/**
 * Created by taylor hudson on 1/2/2017.
 */
public class rsc {

    public static boolean STUDENT = false;

    public static ArrayList<Student> newLOW = new ArrayList<Student>(){
        {
            try {
                BufferedReader low = new BufferedReader(new FileReader("resources/EDITED/LOW.csv"));
                String line;
                while ((line = low.readLine()) != null) {
                    add(new Student(creation(line), true));
                }
                low.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static ArrayList<Student> newHIGH = new ArrayList<Student>(){
        {
            try {
                BufferedReader ahs = new BufferedReader(new FileReader("resources/EDITED/AHS.csv"));
                String line;
                while ((line = ahs.readLine()) != null) {
                    add(new Student(creation(line), true));
                }
                ahs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static ArrayList<Faculty> newFAC = new ArrayList<Faculty>(){
        {
            try {
                BufferedReader tchr = new BufferedReader(new FileReader("resources/EDITED/TCHR.csv"));
                String line;
                while ((line = tchr.readLine()) != null) {
                    add(new Faculty(line.split(",")));
                }
                tchr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static final HashMap<String, Student> AHS = new HashMap<String, Student>(){
        {
            try{
                BufferedReader ahs = new BufferedReader(new FileReader("resources/AHS.csv"));
                String line;
                while((line= ahs.readLine()) != null){
                    put(line.substring(0, line.indexOf(',')), new Student(creation(line), true));

                }
                for(Student t : newHIGH){
                    put(t.getID(), t);
                }
                ahs.close();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static final HashMap<String, Student> LOW = new HashMap<String, Student>(){
        {
            try{
                BufferedReader low = new BufferedReader(new FileReader("resources/LOW.csv"));
                String line;
                while((line= low.readLine()) != null){
                    put(line.substring(0, line.indexOf(',')), new Student(creation(line), false));
                }
                for(Student t : newLOW){
                    put(t.getID(), t);
                }
                low.close();

            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static final HashMap<String, Faculty> TCHR = new HashMap<String, Faculty>(){
        {
            try{
                BufferedReader tchr = new BufferedReader(new FileReader("resources/TCHR.csv"));
                String line;
                while((line= tchr.readLine()) != null){
                    put(line.substring(0, line.indexOf(',')), new Faculty(line.split(",")));
                }
                for(Faculty t : newFAC){
                    put(t.getID(), t);
                }
                tchr.close();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static ArrayList<String> creation(String line){
        String [] elements = line.split(",");
        ArrayList<String> tbd = new ArrayList<>();
        for(String t : elements){
            tbd.add(t);
        }
        return tbd;
    }

    public static int anonymous = 0;
}
