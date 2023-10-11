/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // File name to analyze.
    private String fileName;
    /**
     * Create an object to analyze hourly web accesses.
     * fileName allows entry of a specific file
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the total of accesses
     * in a 24HR period
     */
    public int totalAccesses()
    {
        int total = 0;
        
        for(int index = 0; index < hourCounts.length; index++)
        {
            total = hourCounts[index] + total;
        }
        
        return total;
    }
    
    /**
     * Finds and prints the index of the busiest hour in the hourCounts array.
     * The busiest hour is determined by finding the maximum value in the array.
     * 
     */
    public void busiestHour()
    {
        int maxValue = -1;
        int maxValueIndex = -1;
        
        for(int index = 0; index < hourCounts.length; index++)
        {
            if(hourCounts[index] > maxValue)
            {
                maxValue = hourCounts[index];
                maxValueIndex = index;
            }
        }
        
        System.out.println("The busiest hour is at index: " + maxValueIndex);
    }
    
    /**
     * Finds and prints the index of the quietest hour in the hourCounts array.
     * The quietest hour is determined by finding the minimum value in the array.
     * 
     */
    public void quietestHour()
    {
        int minValue = Integer.MAX_VALUE;
        int minValueIndex = -1;
        
        for(int index = 0; index < hourCounts.length; index++)
        {
            if(hourCounts[index] < minValue)
            {
                minValue = hourCounts[index];
                minValueIndex = index;
            }
        }
        
        System.out.println("The quietest hour is at index: " + minValueIndex);
    }
    
    /**
     * Finds and print the index of the two busiest hours in the hourCounts array
     * 
     */
    public void busiestTwoHour()
    {
        int maxTotalCount = 0;//holds the sum for the largets period
        int busiestPeriodStartHour = -1;//hold the hour of the busiest start period hour
        
        //loop through the 24hr period
        for(int startHour = 0; startHour < hourCounts.length; startHour++)
        {
            int currentPeriodTotalCount = 0;//hold the sum of the current period
            
            //increment by 2hr period
            for(int i = 0; i < 2; i++)
            {
                int hour = (startHour + i)%12; //handle the roll over special case
                currentPeriodTotalCount += hourCounts[hour];//pull the value from the current index & add
            }
            
            //update the initial variables to hold the highest hour & sum
            if(currentPeriodTotalCount > maxTotalCount)
            {
                maxTotalCount = currentPeriodTotalCount;
                busiestPeriodStartHour = startHour;
            }
        }
        
        System.out.println("The busiest two-hour period is from " + busiestPeriodStartHour + " to " 
                            + (busiestPeriodStartHour + 2));
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
