package allen.compsci;
import java.io.*;
/**
 * Created by taylor hudson on 1/2/2017.
 */
public class PowerShell {
    private Process powerShellProcess;
    private String command;
    private static String FileName = "raw.txt";
    public PowerShell() throws IOException{

        setScript1(FileName);
        String[] commandList = {"powershell.exe", "-command", command};

        //System.out.println("Starting Script");
        long t0 = System.currentTimeMillis();
        powerShellProcess = Runtime.getRuntime().exec(commandList);

        powerShellProcess.getOutputStream().close();


        String line;
        //System.out.println("Output:");

        BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            // System.out.println(line);
        }
        stdout.close();
        //System.out.println("Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            //System.out.println(line);
        }
        stderr.close();
        //System.out.println("Done");
        //System.out.println("Closing Script after " + (System.currentTimeMillis()-t0) + " ms.");
        boolean loop = true;
        while(loop){
            File newDirectory = new File("C:\\"+FileName);
            if(newDirectory.renameTo(new File(new java.io.File(".").getCanonicalPath()+"\\resources\\"+FileName))){
                //System.out.println("File Moved.");
                loop = false;
            }
            else{
                //System.out.println("Failed to Move");
                try {
                    Thread.sleep(99000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter("resources\\"+FileName, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println("ENDOFFILE-PLEASECLOSE");
            out.println("ENDOFFILE-PLEASECLOSE");
            out.println("ENDOFFILE-PLEASECLOSE");
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        finally {

            out.close();

            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }

    }

    private void setScript1(String FileName){
        //command =  "$strFilter = \"(&(objectCategory = *))\"; ";
        command = "$objDomain = New-Object System.DirectoryServices.DirectoryEntry; ";
        command += "$objSearcher = New-Object System.DirectoryServices.DirectorySearcher; ";
        command += "$objSearcher.SearchRoot = $objDomain; ";
        command += "$objSearcher.PageSize = 1000000; ";
        //command += "$objSearcher.SearchScope = \"Subtree\"; ";
        command += "$colResults = $objSearcher.FindAll(); ";
        command += "$(foreach($objResult in $colResults){$objItem = $objResult.Properties; $objItem})|out-file \"C:\\"+FileName+"\" ";
    }

    public void doNothing(){
        System.out.println("Done");
    }
}
