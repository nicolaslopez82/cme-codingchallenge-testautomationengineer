package extentreport;

import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nicolaslopez82.
 */

public class ExtentManager {

    private static ExtentReports extent;

    /**
     * Setting *.HTML reporting file location
     * @return
     */
    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            String workingDir = System.getProperty("user.dir");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            extent = new ExtentReports(workingDir + File.separator + "ExtentReports" + File.separator + "ExtentReportsResults-" + dateFormat.format(date) + ".html", true);
            extent.loadConfig(new File(workingDir + File.separator + "src" + File.separator + "resources" +File.separator + "extent-config.xml"));
        }
        return extent;
    }
}
