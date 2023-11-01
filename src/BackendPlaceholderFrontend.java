import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a placeholder for the backend reference taken as an input by the Frontend class
 * @param <T>
 */
public class BackendPlaceholderFrontend<T> implements BackendInterface{

    /**
     * This method returns the expected output to System.out so the test methods can correctly test if the backend
     * is being reached
     * @param file The file to load
     */
    @Override
    public void readData(File file) {
        System.out.println("File has been loaded"); // Expected output
    }

    /**
     * This method returns the expected value of minMileage to System.out so the test methods can correctly test if the
     * backend is being reached
     * @param mileThreshold The maximum number of miles a car should have to be returned
     * @return A list of the minimum cars (null now as implementation is done by backend)
     */
    @Override
    public ArrayList<Car> minMileage(float mileThreshold) {
        // Print expected output
        System.out.println("31900,chevrolet,1500,2018,clean vehicle,22909.0,black,3gcukrec0jg176059,167763273,tennessee,usa");
        return null;
    }

    /**
     * This method returns the expected value of mileageThreshold to System.out so the test methods can correctly test
     * if the backend is being reached
     * @param mileThreshold The minimum number of miles a car should have to be returned
     * @return  A list of the maximum mileage cars (null now as implementation is done by backend)
     */
    @Override
    public ArrayList<Car> mileageThreshold(float mileThreshold) {
        // Print expected output
        System.out.println("10400,dodge,coupe,2009,clean vehicle,107856.0,orange,2b3lj54t49h509675,167753874,georgia,usa5430,chrysler,wagon,2017,clean vehicle,138650.0,gray,2c4rc1cg5hr616095,167656123,texas,usa");
        return null;
    }
}
