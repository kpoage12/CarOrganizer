import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the frontend of a car database prompting a user to ask different commands to see information
 * about cars in a dataset
 */
public class Frontend implements FrontendInterface{

    private Scanner scan; // The scanner taken by the frontend constructor
    private Boolean keepRunning = true; // Stays true until the user prompts the frontend to exit the app
    BackendPlaceholderFrontend backendPlaceholderFrontend; // The backend reference taken by the frontend
    private Backend backend; // Backend reference
    private Boolean isPlaceholder;

    /**
     * Constructor for Frontend class that creates a new Frontend reference with a backendPlaceholder and Scanner
     * @param backendPlaceholder The backendPlaceholder to assign to this Frontend
     * @param scan The Scanner to assign to this Frontend
     */
    public Frontend(BackendPlaceholderFrontend backendPlaceholder, Scanner scan){
        this.backendPlaceholderFrontend = backendPlaceholder;
        this.scan = scan;
        this.isPlaceholder = true;
    }

    /*
    This is the constructor for the actual backend reference
    */
    public Frontend(Backend backend, Scanner scan){
        this.backend = backend;
        this.scan  = scan;
        this.isPlaceholder = false;
    }

    /**
     * This method keeps prompting the user to either load a file, list all vehicles with the lowest mileage, list all
     * vehicles above a mileage, or to exit the app. It keeps running until the user asks to exit the app
     */
    @Override
    public void mainCommandLoop() {
        while (keepRunning){
            // Prompt user
            System.out.println("Please ask to either \"Load a file\", " +
                    " \"List all vehicles with the lowest mileage\"," + " \"List all vehicles above a mileage\", or " +
                    " \"Exit the app\"");

            String userInput = scan.nextLine(); // Take in user input to see what they want done

            // Check if user wants to load a file
            if (userInput.equals("Load a file")){
                // Prompt user to load the file
                System.out.println("What file would you like to load");
                File fileName = new File(scan.nextLine());
                this.loadFile(fileName);
            }

            // Check if the user wants to list all vehicles with the lowest mileage
            else if (userInput.equals("List all vehicles with the lowest mileage")){
                // Prompt the user to enter the mileage threshold
                System.out.println("How small should the cars' mileage be?");
                if (isPlaceholder){
                    minMileage(Float.parseFloat(scan.nextLine()));
                }
                else {
                    ArrayList<Car> cars = minMileage(Float.parseFloat(scan.nextLine()));
                    for (int i = 0; i < cars.size(); i++) {
                        System.out.println(cars.get(i).toString());
                    }
                }
            }

            // Check if the user wants to list all vehicles above a certain mileage
            else if (userInput.equals("List all vehicles above a mileage")){
                // Prompt the user to enter the mileage threshold
                System.out.println("How large should the cars' mileage be?");
                if (isPlaceholder){
                    listVehiclesAboveMileage(Float.parseFloat(scan.nextLine()));
                }
                else {
                    ArrayList<Car> cars = listVehiclesAboveMileage(Float.parseFloat(scan.nextLine()));
                    for (int i = 0; i < cars.size(); i++) {
                        System.out.println(cars.get(i).toString());
                    }
                }

            }

            // Check if the user wants to exit the app
            else if (userInput.equals("Exit the app")){
                System.out.println("Exiting the app");
                exitApp();
            }

            // If the user types anything else in, it is an invalid prompt
            else {
                // Give user detailed feedback on what to prompt the program for
                System.out.println("Invalid response: Please ask to either \"Load a file\", " +
                        " \"List all vehicles with the lowest mileage\"" + " \"List all vehicles above a mileage\", or "
                        + " \"Exit the app\"");
            }
        }
    }

    /**
     * This method calls the readData() method from the backend
     * @param file The file to be loaded
     */
    @Override
    public void loadFile(File file) {
        if (isPlaceholder){
            backendPlaceholderFrontend.readData(file);
        }
        else {
            backend.readData(file);
        }
    }

    /**
     * This method calls the mileageThreshold() method from the backend
     * @param mileage The mileage threshold over which cars will be placed in the list
     * @return the list of cars above the mileage threshold
     */
    @Override
    public ArrayList<Car> listVehiclesAboveMileage(float mileage) {
        if (isPlaceholder){
            return backendPlaceholderFrontend.mileageThreshold(mileage);
        }
        return backend.mileageThreshold(mileage);
    }

    /**
     * This method calls the minMileage() method from the backend
     * @param mileage The mileage threshold under which cars will be placed in the list
     * @return the list of cars below the mileage threshold
     */
    @Override
    public ArrayList<Car> minMileage(float mileage) {
        if (isPlaceholder){
            return backendPlaceholderFrontend.minMileage(mileage);
        }
        return backend.minMileage(mileage);
    }

    /**
     * This method exits the mainCommandLoop by setting keepRunning to false
     */
    @Override
    public void exitApp() {
        keepRunning = false;
    }

}
