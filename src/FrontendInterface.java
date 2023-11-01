import java.io.File;
import java.util.List;

/**
 * This is the interface of a frontend class that prompts a user to access certain
 * details about a car dataset
 */
public interface FrontendInterface<T extends Comparable<T>> {
    /**
     * This is the main command loop which will continuously prompt the user to select a command
     *
     * @throws IllegalArgumentException If the user does not input a valid command
     */
    public void mainCommandLoop() throws IllegalArgumentException;

    /**
     * This is a command the user can use to load a data file
     *
     * @param file The file to be loaded
     */
    public void loadFile(File file);

    /**
     * Method that will return a list of cars that meet the minimum mileage.
     */
    public List<T> minMileage(float mileThreshold);

    /**
     * This command returns a list of generic type T of cars over a certain mileage
     *
     * @param mileage The mileage threshold over which cars will be placed in the list
     * @return A list of all cars all over a certain mileage
     */
    public List<T> listVehiclesAboveMileage(float mileage);

    /**
     * This command allows the user to exit the app
     */
    public void exitApp();
}
