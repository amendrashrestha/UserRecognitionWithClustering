package uni.cluster.main;

import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.SleepingCluster;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */
public class Test {
    public static void main(String[] args) throws ParseException, IOException{
        //String testTime = "23:12:45";
        //Timestamp timestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(testTime));
        //System.out.println(timestamp.getHours());
        IOReadWrite ioRW = new IOReadWrite();
        List<FirstActivityCluster> a = new ArrayList();
        List sdfd = new SleepingCluster().getAllFilesForSleepingCluster();
         List<FirstActivityCluster> ab = new ArrayList();
    }
}
