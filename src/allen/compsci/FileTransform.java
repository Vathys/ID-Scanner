package allen.compsci;

import java.io.*;
import java.util.*;

public class FileTransform {

	private String fileName;
	private String toWrite;
	private BufferedReader bRead;
	private BufferedWriter bWrite;
	private Scanner sc;
	private FileReader fRead;
	private FileWriter fWrite;
	private ArrayList<String> Member;
	private Integer count;
	private ArrayList <Student> AHS;
	private ArrayList <Student> LOW;
	private ArrayList<Faculty> TCH;
	private ArrayList<String> pTCH;
	
	public String delimit(char []t){
		String hold = "";
		for(int i = 1; i < t.length; i++){
			hold += t[i];
			i++;
		}
		return hold;
	}
	void print(char [] t){
		System.out.print("[ ");
		for(char e : t ){
			System.out.print(e + ", ");
		}
		System.out.println("\b\b]");
	}
	
	public void PrintAL(){
		boolean student = false;
		if(Member.size() > 0){
			for(String kk : Member){
				if(kk.startsWith("userprincipalname")){
					if(kk.indexOf("student")>0){
						student = true;
					}
				}
				else if(student && kk.startsWith("distinguishedname")){
					if(kk.indexOf("Old") >=0 || kk.indexOf("Special") >= 0 || kk.indexOf("Delete")>= 0){
						student = false;
					}
				}
			}
			if(student)
			try{
				bWrite.write("Member : " + count++);
				bWrite.newLine();
				for(String s : Member){
					//System.out.println(s);
					if(s.startsWith("userprincipalname") || s.startsWith("mail") || s.startsWith("distinguishedname") || s.startsWith("description") || s.startsWith("displayname") || s.startsWith("department") || s.startsWith("adspath") || s.startsWith("company") || s.startsWith("samaccountname")  )
					{
						bWrite.write(s);
						bWrite.newLine();
					}
				}
				bWrite.newLine();
				
				// Create Student CSV Lowery Seperate from AHS
				Student copy = new Student(Member);
				if(copy.isHighSchool()){
					System.out.println("AHS : " + (AHS.size()+1));
					AHS.add(new Student(Member));
                    //System.out.println(Member.get(0));
				}
				else{
					System.out.println("LOW : " + (LOW.size()+1));
					LOW.add(new Student(Member));
                    //System.out.println(Member.get(0));
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		while(Member.size() > 0)
			Member.remove(0);
		return;
	}
	
	public FileTransform(String fileIn, String fileOut){
		AHS = new ArrayList<Student>();
		LOW = new ArrayList<Student>();
		TCH = new ArrayList<Faculty>();
		pTCH = new ArrayList<String>();
		fileName = "resources/"+ fileIn;
		toWrite = "resources/"+ fileOut;
		String line = null;
		boolean writeNo = false;
		boolean old = false;
		boolean succeed = false;
		boolean teacher = true;
		count = 0;
		Member = new ArrayList <String>();
        int countBlank = 0;
		try{
			fRead = new FileReader(fileName);
			//System.out.println(fileIn + " is Opened.");
			bRead = new BufferedReader(fRead);
			fWrite = new FileWriter(toWrite);
			//System.out.println(fileOut + " is Opened");
			bWrite = new BufferedWriter(fWrite);
			sc = new Scanner(fRead);
			//System.out.println("Scanner Opened.");
			line = sc.nextLine();
            boolean stopScan = false;
			boolean clean = false;
			while(sc.hasNextLine() && !stopScan){
				line = sc.nextLine();

             	if(!sc.hasNextLine()){
                    //System.out.println("No Next Line");
					break;
				}
                line = sc.nextLine();
				line = delimit(line.toCharArray());
                //System.out.println(line);

                if(line == null || line.equals("")){
                    countBlank ++;
                    if(countBlank > 400){
                        stopScan = true;
                    }
                }
                else{
                    countBlank = 0;
                }
				String possTeacher = line;
				if(line.startsWith("ENDOFFILE-PLEASECLOSE")){
					break;
				}
				if(line.startsWith("department")){
					if(line.indexOf("Allen High School") >= 0 || line.indexOf("Lowery Freshman Center") >=0){
						if(!old)
						succeed = true;
					}
				}
				if(line.startsWith("adspath")){
					if(line.indexOf("OU=Delete")>=0 ){//|| line.indexOf("OU=OldStudents") >= 0){
						old = true;
						succeed = false;
					}
					else
					{
						old = false;
					}
				}
				else if(line.startsWith("objectclass") || line.startsWith("objectguid")) {
                    //System.out.println(line);
                    //System.out.println("TEACHER : " + teacher + ", Succeed : " + succeed + ", old : " + old + ", clean : " + clean + ", Write : " + writeNo);
                    //System.out.println(Member.toString());
                    teacher = false;
                    clean = true;

                    if (writeNo) { // first check if we have found a path as of yet
                        if (succeed) {
                            PrintAL();
                            succeed = false;
                        }
                    } else {
                        writeNo = true;
                    }
                    old = false;

                    if (clean) {
                        clean = false;
                        old = false;
                        while (pTCH.size() > 0)
                            pTCH.remove(0);
                        while (Member.size() > 0)
                            Member.remove(0);
                    }
                }
				if(writeNo){
					Member.add(line);
				}
				line = null;
				if(possTeacher.startsWith("objectclass") && possTeacher.indexOf("person")>=0){
					teacher = true;
				}
				else if(possTeacher.startsWith("objectclass")){
                    //System.out.println(possTeacher);
                    teacher = false;
                }
				if(teacher && possTeacher.startsWith("name")){
                    if(possTeacher.indexOf('{') <0 || possTeacher.indexOf('}') < 0 || possTeacher.indexOf('}') < possTeacher.indexOf('{') ){
                        teacher = false;
                    }
                    else if(possTeacher.indexOf(' ', possTeacher.indexOf('{')) < 0 || possTeacher.indexOf(' ', possTeacher.indexOf('{')) > possTeacher.indexOf('}')){
                        teacher = false;
                    }
                    else {
                        //System.out.println(possTeacher);
                        pTCH.add(possTeacher.substring(possTeacher.indexOf('{') + 1, possTeacher.indexOf('}')));
                    }
				}
				if(teacher && possTeacher.startsWith("department")){
					if(possTeacher.indexOf("Allen High School") < 0 || possTeacher.contains("Student")){
						teacher = false;
						while(pTCH.size() > 0)
							pTCH.remove(0);
					}
					else{
                        //System.out.println(possTeacher);
						pTCH.add(possTeacher.substring(possTeacher.indexOf('{')+1, possTeacher.indexOf('}')));
					}
				}
				if(teacher && possTeacher.startsWith("employeeid")){
                    //System.out.println(possTeacher);
					pTCH.add(possTeacher.substring(possTeacher.indexOf('{')+1, possTeacher.indexOf('}')));
				}
				if(teacher && possTeacher.startsWith("employeetype")){
					teacher = false;
					if(pTCH.size() == 3){
                       	TCH.add(new Faculty(pTCH));
                        System.out.println("TCH : " + TCH.size());
					}
					while(pTCH.size() > 0)
						pTCH.remove(0);

				}
				possTeacher = null;

		}
			
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
        System.out.println("STOPPED FILETRANSFORM");
        try {
		if(succeed){
			PrintAL();
			Member.clear();
			}
	
		bRead.close();
		bWrite.close();
		fRead.close();
		fWrite.close();
		sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("EXIT");
		try {
			Student.printList(AHS, "resources/AHS.csv");
			Student.printList(LOW, "resources/LOW.csv");
			Faculty.printList(TCH, "resources/TCHR.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void clear(){

		File file1 = new File(fileName);
		file1.delete();
		File file2 = new File(toWrite);
		file2.delete();

	}
}
