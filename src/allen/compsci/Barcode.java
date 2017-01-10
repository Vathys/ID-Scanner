package allen.compsci;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by taylor hudson on 1/4/2017.
 */
public class Barcode{



    private Long result;
    public static String vol;

    public Barcode(){
        vol = "";
        result = 0L;



    }

    public Long nextLong(){
        vol = "";
        while(Main.isRunning){
            try{
                Thread.sleep(32);

            }
            catch (Exception e){

            }


        }
        vol = Main.savedText;
        result = Long.parseLong(vol);
        Main.savedText = "";
        System.out.println(result);
        return result;
    }

    public void print(String csv, Date start, Date end, HashMap<Long, Junction> IDs, Long Teacher, Long catchID, long escp){
        try {
            File file = new File(csv);
            file.createNewFile();
            FileWriter write = new FileWriter(csv);
            List<String> open = new ArrayList<String>();
            open.add("Opened by : ");
            open.add(rsc.TCHR.get(Teacher.toString()).getName());
            open.add(start.toString());
            List<String> close = new ArrayList<String>();
            close.add("Closed by : ");
            if(catchID.equals(0L)){
                close.add(rsc.TCHR.get("000000").getName());
            }
            else {
                close.add(rsc.TCHR.get(catchID.toString()).getName());
            }
            close.add(end.toString());
            CSVUtils.writeLine(write, open);
            for (Junction t : IDs.values()) {
                t.STOP(escp);
                CSVUtils.writeLine(write, t.toList());
            }
            CSVUtils.writeLine(write, close);
            write.close();
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }


}
