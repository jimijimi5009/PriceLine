package utility.ReportingTools;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import utility.OsUtill;

import java.io.PrintWriter;


public class ReportUtils {


    private static String allureReport = System.getProperty("user.dir") +"/target/allure-report";
    private static String allureResults = System.getProperty("user.dir") +"/target/allure-results";

    public static void generateAllureReport() {

        String[] command =
                {
                        "cmd",
                };
        Process p = null;
        String osName = OsUtill.getOperatingSystemSystemUtils();
        try {

           if (osName.contains("Win")) {
               p = Runtime.getRuntime().exec(command);
           }
           else if (osName.contains("Linux")) {
               p = Runtime.getRuntime().exec("sh");
//               p = Runtime.getRuntime().exec(command);
           }
//            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("pip3 install allure-combine");
            stdin.println("allure generate " + allureResults+ " -o "+allureReport+ " --auto-create-folders");
            stdin.println("allure-combine " + allureReport);
            stdin.close();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
