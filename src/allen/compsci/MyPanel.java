package allen.compsci;
import javax.swing.*;
import java.util.*;
/**
 * Created by taylor hudson on 1/9/2017.
 */
public class MyPanel extends JPanel {
    private JTextField fname = new JTextField(15);
    private JTextField lname = new JTextField(15);
    private JTextField middle = new JTextField(1);
    private JTextField email = new JTextField(50);
    private JTextField grade = new JTextField(2);
    // .... other fields ? ...

    public MyPanel() {
        add(new JLabel("First Name:"));
        add(fname);
        add(new JLabel("Middle Initial:"));
        add(middle);
        add(new JLabel("Last Name:"));
        add(lname);
        add(new JLabel("Email Address:"));
        add(fname);
        add(new JLabel("Grade:"));
        add(grade);
    }

    public ArrayList<String> getInfo(){
        ArrayList<String> t = new ArrayList<>();
        t.add(lname.getText());
        t.add(email.getText());
        t.add(fname.getText());
        t.add(fname.getText() + " " + middle.getText() + " " + lname.getText());
        t.add(grade.getText());
        return t;
    }

}