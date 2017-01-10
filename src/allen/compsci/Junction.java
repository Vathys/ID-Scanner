package allen.compsci;

import javax.swing.*;
import java.util.*;
/**
 * Created by taylor hudson on 1/2/2017.
 */
public class Junction {
    private Date create;
    private Long ID;
    private String Name;
    private String LName;
    private String FName;
    private long stopwatch;
    private long totalTime;
    private boolean click;
    private MyPanel myPanel;
    public Junction(Long id){
        click = false;
        totalTime = 0;
        stopwatch = 0;
        ID = Long.valueOf(id);
        setName();
        create = new Date();
        myPanel = new MyPanel();
    }
    public void setName(){
        if(rsc.AHS.containsKey(String.valueOf(ID))){
            FName = rsc.AHS.get(String.valueOf(ID)).getFirstName();
            LName = rsc.AHS.get(String.valueOf(ID)).getLastName();
            Name = rsc.AHS.get(String.valueOf(ID)).getFullName();
        }
        else if(rsc.LOW.containsKey(String.valueOf(ID))){
            FName = rsc.LOW.get(String.valueOf(ID)).getFirstName();
            LName = rsc.LOW.get(String.valueOf(ID)).getLastName();
            Name = rsc.LOW.get(String.valueOf(ID)).getFullName();
        }
        else {
/* THIS IS A POSSIBLE REAL TIME ADD STUDENT TO ROSTER
            int result = JOptionPane.showConfirmDialog(null, myPanel);
            if (result == JOptionPane.OK_OPTION) {
                Student hold = new Student(myPanel.getInfo());
                if (hold.isHighSchool()) {
                    rsc.AHS.put(ID.toString(), hold);
                    rsc.newHIGH.add(hold);
                } else {
                    rsc.LOW.put(ID.toString(), hold);
                    rsc.newLOW.add(hold);
                }
                rsc.STUDENT = true;
            } else {
*/
                FName = "unknown";
                LName = "unknown";
                Name = "unknown" + rsc.anonymous++ + " : " + String.valueOf(ID);

  //          }
        }
    }
    public void CLICK(){
        long timeStop = System.currentTimeMillis();

        if(click){
            totalTime = timeStop - stopwatch;
            stopwatch = 0;
        }
        else{
            stopwatch = timeStop;
            stopwatch -= totalTime;
        }
        click = !click;
    }
    public void STOP(long esc){
        if(click){
            totalTime = (esc - stopwatch);
            stopwatch = 0;
        }
        totalTime = (long)Math.ceil((totalTime/60000.0));
    }
    public String getTime(){
        return String.valueOf(totalTime/60000) + " minutes.";
    }
    public String getName(){
        return Name;
    }
    public String toString(){
        return getName() + ": " + getTime();
    }
    public List<String> toList(){
        List<String> result = new ArrayList<String>();
        result.add(Name);
        result.add(String.valueOf(totalTime));
        result.add("Minutes");
        result.add(create.toString());
        return result;
    }
}
