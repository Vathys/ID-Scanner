package allen.compsci;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements KeyListener, WindowListener{
    private static Long Teacher;
    private static Long catchID;
    private static HashMap <Long, Junction>IDs ;
    private static Date start;
    private static Date end;
    private static Date current;
    private static Barcode input;
    private String labelText;
    public static String savedText;
    public static boolean isRunning;
    private JLabel tf;
    private Scanner get;
    private Color base;
    private boolean keyPress;
    private int unlockUpdate;
    private boolean isDone;

    public Main(){
        super("Time Credit System");
        isRunning = false;
        isDone = false;
        unlockUpdate = 0;
        get = new Scanner(System.in);
        savedText = "";
        labelText = "";
        setSize(new Dimension(500,150));
        //setVisible(true);
        setLocationRelativeTo(null);
        this.setFocusable(true);
        this.addKeyListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Font font = new Font("Dialog", Font.PLAIN, 28);
        tf = new JLabel("Scan Id", null,JLabel.CENTER);
        tf.setFont(font);
        tf.setHorizontalTextPosition(JLabel.CENTER);
        tf.setVerticalTextPosition(JLabel.TOP);




        setLayout(null);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(tf);
        c.setVisible(true);

        setVisible(true);
        generateTimeSheet();
    }

    private void UPDATE(){
        try {
            System.out.println("POWERSCRIPT - Begin Update");
            PowerShell attempt = new PowerShell();
            attempt.doNothing();
            System.out.println("FILETRANSFORM");
            FileTransform f = new FileTransform("raw.txt", "raw1.txt");
            System.out.println("FILE - DELETE");
            f.clear();
            isDone = true;
            dispose();
            System.exit(0);
        }catch(Exception e){

        }
    }
    private Long getLong(){
        Long result;
        isRunning = true;

        while(isRunning){
           pause();


        }
        result = Long.parseLong(savedText);
        savedText = "";


        return result;
    }

    private void generateTimeSheet(){
        input = new Barcode();
        start = new Date();
        System.out.println(start.toString());
        do {
            //Teacher = get.nextLong();
            //Teacher = input.nextLong();
            Teacher = getLong();
        }while(!rsc.TCHR.containsKey(Teacher.toString()));
        IDs = new HashMap();
        //System.out.println("TEACHER : " + Teacher);
        pause();
        do{
            //catchID =  get.nextLong();
            //catchID = input.nextLong();
            catchID = getLong();
            current = new Date();
            //System.out.print(current.toString() + "  : ");
            //System.out.println(catchID);
            if(!rsc.TCHR.containsKey(catchID.toString())) {
                if (!IDs.containsKey(catchID)) {
                    IDs.put(catchID, new Junction(catchID));
                }
                IDs.get(catchID).CLICK();
            }
            pause();
        }while(!rsc.TCHR.containsKey(catchID.toString()) && unlockUpdate != 16);
        if(unlockUpdate == 16){
            catchID = 0L;
        }
        long escp = System.currentTimeMillis();
        end = new Date();
        String dateEdit = start.toString();
        int index = dateEdit.indexOf(':')+3;
        dateEdit = dateEdit.substring(0,index);
        index = end.toString().indexOf(':');
        dateEdit += "-" + end.toString().substring(index-3, index + 2) + end.toString().substring(index + 5);
        String csv = "csvFile/" + rsc.TCHR.get(Teacher.toString()).getcsv() + dateEdit + ".csv";
        for(int i = 0; i < 2; i++) {
            index = csv.indexOf(':');
            csv = csv.substring(0, index) + csv.substring(index + 1);
        }

        input.print(csv, start, end, IDs, Teacher, catchID, escp);
        isDone = true;
        //System.out.println("END - Taylor");
        dispose();
        System.exit(0);
    }

    public void UPDATEID(){
        if(rsc.STUDENT)
        try{
            Student.printList(rsc.newHIGH, "resources/EDITED/AHS.csv");
            Student.printList(rsc.newLOW, "resources/EDITED/LOW.csv");
        }catch(Exception io){

        }


    }

    public static void main(String[] args) {
	// write your code here
        Main m = new Main();
        }

    /***
     * pause() - Method waits 45 milliseconds before exiting
     *
     */
    public static void pause(){
        try {
            Thread.sleep(45);
        }
        catch(Exception ie){
            ie.printStackTrace();
        }
    }
    public static void pause(int time){

        try {
            Thread.sleep(time);
        }
        catch(Exception ie){
            ie.printStackTrace();
        }
    }
    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(isRunning)
        {
            fun(ke);
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    void fun(KeyEvent ke) {

        if (ke.getKeyCode() == 10) {
            //keyPress = true;

            savedText = labelText;
            labelText = "";
            //System.out.println("Saved a string: " + savedText);
            //tf.setText("");

            isRunning = false;
        } else if (Character.isLetterOrDigit(ke.getKeyChar()) || ke.getKeyChar() == 32) {
            //System.out.println(ke.getKeyCode());
            labelText += new String().valueOf(ke.getKeyChar());

        }
        else if(ke.getKeyCode() == KeyEvent.VK_F8){
            unlockUpdate ++;
            if(unlockUpdate == 15) {
                int Choice = JOptionPane.showConfirmDialog((Component) null, "Do You Wish to Update","alert", JOptionPane.YES_NO_CANCEL_OPTION);

                if(Choice == JOptionPane.YES_OPTION) {
                    unlockUpdate++;
                    pause(1500);
                    setVisible(false);
                    UPDATE();

                }
                else{
                    unlockUpdate = 0;
                }
            }
        }
        else{

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        isRunning = false;
        unlockUpdate = 16;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        while(true){

            if(isDone){
                System.exit(0);
            }{
                unlockUpdate = 16;
            }
            try{
                Thread.sleep(100);
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
