package allen.compsci;
import java.util.*;
import java.io.*;
/**
 * Created by taylor hudson on 1/2/2017.
 */
public class Student {
    private String ID; //samaccountname
    private String LastName; // Description
    private String FirstName; // Description
    private String FullName; // Description
    private String Email; // mail
    private String HOUSE; // Description LOW or AHS100 - AHS700
    private String Grade; // Description
    private String LOWHouse;
    private boolean HighSchool;

    /// CONSTRUCTORS

    /** Known Student with known data
     * @param id - SID
     * @param last - Last Name
     * @param first - First Name
     * @param full - Full Name including Middle initial if known
     * @param email - student email generally "*@student.allenisd.org"
     * @param house - AHS#00 format, we may need to check if this is AHS### or by grade to associate LOW### back
     * @param grade - currently 9 - 12 base cases
     */
    public Student(String id, String last, String first, String full, String email, String house, String grade){
        ID = id;
        LastName = last;
        FirstName = first;
        FullName = full;
        Email = email;
        HOUSE = house;
        Grade = grade;
    }

    public String[] getInfo(){
        String temp = FirstName +"," + LastName+","+FullName;
        return temp.split(",");
    }
    public Student(ArrayList <String> s){
        ID = LastName = FirstName = FullName = Email = HOUSE = Grade = "";
        setHighSchool(true);
        breakMember(s);
        //System.out.println(this.toString());
    }
    public Student(ArrayList <String> s, boolean hs){
        ID = LastName = FirstName = FullName = Email = HOUSE = Grade = "";
        setHighSchool(hs);
        ID = s.get(0);
        LastName = s.get(1);
        FirstName = s.get(4);
        FullName = s.get(5);
    }
    public Student(ArrayList<String> t, long idNUM){
    ID = String.valueOf(idNUM);
        LastName = t.get(0);
        Email = t.get(1);
        FirstName = t.get(2);
        FullName = t.get(3);
        Grade = t.get(4);
        HOUSE = sortingHat();
        if(Grade.equals("9")){
            setHighSchool(false);
        }
        else{
            setHighSchool(true);
        }
    }
    public String getFirstName(){
        return FirstName;
    }
    public String getLastName(){
        return LastName;
    }
    public String getFullName(){
        return FullName;
    }
    public String getID(){return ID;}
    /// ACCESSORS Below this line are functions that are either Utility or ACCESSOR Methods to assist in state setting or conformation

    /**
     * utilityLexicographicallyCompareStrings() is a utility function to compare lastName to House end case
     * @param lastName
     * @param House
     * @return Comparison of the Lexicographically order, if lastName is before House then True, else False
     */
    public boolean utilityLexicographicallyCompareStrings(String lastName, String House){
        return lastName.compareTo(House) < 0;
    }
    /**
     * sortingHat() creates the House Number for Students who don't have an AHS house designation
     * @return "100" - "700" based on Student state LastName
     */
    public String sortingHat(){
        String modifiedLastName = LastName.toUpperCase();
        sortLOW(modifiedLastName);
        setHighSchool(false);
        if(utilityLexicographicallyCompareStrings(modifiedLastName,"CAU")){
            return "100";
        }
        else if(utilityLexicographicallyCompareStrings(modifiedLastName,"FLJ")){
            return "200";
        }
        else if(utilityLexicographicallyCompareStrings(modifiedLastName,"IO")){
            return "300";
        }
        else if(utilityLexicographicallyCompareStrings(modifiedLastName,"MC")){
            return "400";
        }
        else if(utilityLexicographicallyCompareStrings(modifiedLastName,"PIM")){
            return "500";
        }
        else if(utilityLexicographicallyCompareStrings(modifiedLastName,"STEW")){
            return "600";
        }
        return "700";

    }
    /**
     * sortLOW() is a State Setting function to declare LOW House designation for 9th Grade
     * @param lastName takes in the Student's LastName
     */
    public void sortLOW(String lastName){
        if(utilityLexicographicallyCompareStrings(lastName,"GN")){
            LOWHouse = "LOW100";
        }
        else if (utilityLexicographicallyCompareStrings(lastName, "OM")){
            LOWHouse = "LOW200";
        }
        else{
            LOWHouse = "LOW300";
        }
    }
    /**
     * isHighSchool() returns the state of the Student in relation to High School Designation
     * @return returns state HighSchool
     */
    public boolean isHighSchool() {
        return HighSchool;
    }
    /**
     * printList() calls toList() to create .csv filled with an arrayList of Students
     * @param studentCollection - ArrayList of Students holding objects state
     * @param csv - file name of the .csv in most cases AHS.csv LOW.csv
     * @throws IOException - Exception from writing the csv
     */
    public static void printList(ArrayList<Student> studentCollection, String csv) throws IOException{
        FileWriter write = new FileWriter(csv);
        for(Student k : studentCollection){
            CSVUtils.writeLine(write, k.toList());
        }
        write.close();
    }
    /**
     * toList() generates a List<String> identifying the Student
     * -this is used in printing a *.csv
     * @return generates a List<String> similar to toString of Student
     */
    public List<String> toList(){
        List<String> result = new ArrayList<String>();
        result.add(ID);
        result.add(LastName);
        result.add(HOUSE);
        result.add(Email);
        result.add(FirstName);
        result.add(FullName);
        result.add(Grade);
        if(!HighSchool)
            result.add(LOWHouse);
        return result;
    }

    //// MUTATORS Below this line are methods which set the state of the object Student

    /**
     * breakMember is the function called by Student Constructor
     * @param lineSet is the ArrayList <String> member of the incoming Student
     * this is characterized by a member being both in AHS or Lowery containing
     * mutliple disticnt lines, which include samaccountname, description, & mail
     * samaccountname, which yeilds an ID number
     * description, which yeilds a full name, grade, and can set HOUSE
     * mail, holds student email address
     */
    public void breakMember(ArrayList <String> lineSet){
        for(String itterationLine : lineSet){
            String instance = itterationLine;
            if(instance.startsWith("samaccountname")){
                instance = instance.substring(instance.indexOf('{') + 1, instance.indexOf('}'));
                ID = instance;
            }
            if(instance.startsWith("description") && (instance.indexOf("AHS") >= 0 || instance.indexOf("LOW") >= 0)){
                instance = instance.substring(instance.indexOf('{')+1);
                setHouse(instance);
                instance = instance.substring(instance.indexOf(' ') + 1);
                Grade = instance.substring(0, instance.indexOf( ' '));
                instance = instance.substring(instance.indexOf(' ') + 1);
                //System.out.println(k);
                FullName = instance.substring(0, instance.indexOf('}'));
                FirstName = FullName.substring(0, FullName.indexOf(' '));
                LastName = FullName.substring(FullName.lastIndexOf(' ')+1);
                //System.out.println(LastName);
                if(HOUSE.startsWith("LOW")){
                    setHighSchool(false);
                    HOUSE = "AHS"+sortingHat();
                }

            }
            if(instance.startsWith("mail")){
                if(instance.indexOf('}') < 0){
                    instance = instance.substring(instance.indexOf('{')+1, instance.indexOf('@'));
                    instance += "@student.allenisd.org";
                }
                else
                    instance = instance.substring(instance.indexOf('{') + 1, instance.indexOf('}'));
                Email = instance;
            }
        }
    }

    /**
     * setHouse() takes in a modified string from breakMember containing "AHS/LOW### ..." sets HOUSE state
     * the ... is extra information and the is House designation is broken away through a substring application.
     * @param modifiedDescription contains "AHS### " or "LOW### "  the space is necessary
     */
    public void setHouse(String modifiedDescription){ // MUTATOR
        HOUSE = modifiedDescription.substring(0, modifiedDescription.indexOf(' '));
    }
    /**
     * setHighSchool() takes a boolean and defines if grade is !9th
     * @param highSchool boolean signified by LOW or 9th Grade designation
     */
    public void setHighSchool(boolean highSchool) {
        HighSchool = highSchool;
    }

    /// Overwriting functions

    /**
     * toString() instead of calling the objects memory address will now call relevant information about the Student in comma delimited format
     */
    public String toString(){
        return ID + ", " + FullName + ", " + Grade + ", " + HOUSE + ", " + Email;
    }

}

class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }


}
