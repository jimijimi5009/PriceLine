package utility;

import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;

public class OsUtill {


    public static String getOperatingSystemSystemUtils() {
        String os = SystemUtils.OS_NAME;
        return os;
    }

    public static void killAllProcesses(String appName) throws InterruptedException {
        String cmdString[] = null;

        switch (appName.toLowerCase()) {
            case "chrome":
                cmdString = new String[] { "chrome.exe", "chromedriver.exe" };
                break;
            case "ie":
                cmdString = new String[] { "iexplore.exe", "IEDriverServer.exe" };
                break;
            case "edge":
                cmdString = new String[] { "msedge.exe", "msedgedriver.exe" };
                break;
            case "provider98":
            case "reportworx":
            case "eligible2000":
            case "smsportal":
            case "cignaportal":
                cmdString = new String[] { "mstsc.exe" };
                break;
            case "notepad":
                cmdString = new String[] { "notepad.exe", "Winium.Desktop.Driver.exe" };
                break;
            default:
                cmdString = new String[] { appName };
                break;
        }

        Process process = null;
        for (int i = 0; i < cmdString.length; i++) {
            String runCMD = "taskkill /f /im " + cmdString[i] + " /t";
            try {
                process = Runtime.getRuntime().exec(runCMD);
                process.waitFor();
                process.destroy();
                System.out.println("Closing " + cmdString[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
