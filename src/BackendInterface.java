import java.io.File;
import java.util.ArrayList;


public interface BackendInterface<T extends Comparable<T>> {

    /**
     * Method that will read the data of a csv file.
     */
    public void readData(File file);

    /**
     * Method that will return a list of cars that meet the minimum mileage.
     */
    public ArrayList<T> minMileage(float mileThreshold);

    /**
     * Method that will return a list of cars with mileage at or above a certain threshold
     */
    public ArrayList<T> mileageThreshold(float mileThreshold);
}
