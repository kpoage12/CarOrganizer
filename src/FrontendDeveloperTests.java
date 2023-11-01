import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;

/**
 * This class tests the frontend class
 */
public class FrontendDeveloperTests {

    /**
     * This method tests the functionality of the loadFile() method
     */
    @Test
    public void testMainCommandLoop() {
        // Make a new TextUITester object that has an invalid prompt
        TextUITester invalidPrompt = new TextUITester("This is an invalid prompt\nExit the app\n");
        // Create a new Frontend object that takes a backend reference and scanner as input
        Scanner scan = new Scanner(System.in);

        BackendPlaceholderFrontend backendReference = new BackendPlaceholderFrontend();
        Frontend frontend = new Frontend(backendReference, scan);
        // Run the frontends main loop
        frontend.mainCommandLoop();
        // Check the frontends output
        String output = invalidPrompt.checkOutput();
        scan.close();
        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Invalid response: Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\" \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Exiting the app\n";
        // Check if the output is what we want the frontend to do when the user inputs an invalid request
        Assertions.assertEquals(output, expected);
    }

    /**
     * This method tests the functionality of the loadFile() method
     */
    @Test
    public void testLoadFile() {
        // Make a new TextUITester object that prompts the frontend to load a file, and then gives that file
        TextUITester loadFileInput = new TextUITester("Load a file\nsrc/small.csv\nExit the app\n");
        // Create a new Frontend object that takes a backend reference and scanner as input
        Scanner scan = new Scanner(System.in);
        BackendPlaceholderFrontend backendReference = new BackendPlaceholderFrontend();
        Frontend frontend = new Frontend(backendReference, scan);

        // Run the frontends main loop
        frontend.mainCommandLoop();
        // Check the frontends output
        String output = loadFileInput.checkOutput();
        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "What file would you like to load\n" +
                "File has been loaded\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Exiting the app\n";
        Assertions.assertEquals(output, expected);
    }

    /**
     * This method tests the functionality of the listVehiclesAboveMileage() method
     */
    @Test
    public void testListVehiclesAboveMileage() {
        // Make a new TextUITester object that prompts the frontend to list vehicles above a mileage
        TextUITester listCarsAboveMileageInput = new TextUITester("Load a file\nsrc/small.csv\nList all vehicles above a mileage\n" +
                "100000\nExit the app");
        // Create a new Frontend object that takes a backend reference and scanner as input
        Scanner scan = new Scanner(System.in);
        BackendPlaceholderFrontend backendReference = new BackendPlaceholderFrontend();
        Frontend frontend = new Frontend(backendReference, scan);
        // Run the frontends main loop and load a file with cars
        frontend.mainCommandLoop();
        // Check the frontends output
        String output = listCarsAboveMileageInput.checkOutput();
        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "What file would you like to load\n" +
                "File has been loaded\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "How large should the cars' mileage be?\n" +
                "10400,dodge,coupe,2009,clean vehicle,107856.0,orange,2b3lj54t49h509675,167753874,georgia,usa5430,chrysler,wagon,2017,clean vehicle,138650.0,gray,2c4rc1cg5hr616095,167656123,texas,usa\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Exiting the app\n";
        // Make sure it prompts the user to input a mileage threshold
        Assertions.assertEquals(output, expected);
    }

    /**
     * This method tests the functionality of the minMileage() method
     */
    @Test
    public void testMinMileage() {
        // Make a new TextUITester object that prompts the frontend to list vehicles with the lowest mileage
        TextUITester listCarsLowestMileage = new TextUITester("Load a file\nsrc/small.csv\nList all vehicles with the lowest mileage\n" +
                "30000\nExit the app");
        // Create a new Frontend object that takes a backend reference and scanner as input
        Scanner scan = new Scanner(System.in);
        BackendPlaceholderFrontend backendReference = new BackendPlaceholderFrontend();
        Frontend frontend = new Frontend(backendReference, scan);
        // Run the frontends main loop and load a file with cars
        frontend.mainCommandLoop();

        // Check the frontends output
        String output = listCarsLowestMileage.checkOutput();
        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "What file would you like to load\n" +
                "File has been loaded\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "How small should the cars' mileage be?\n" +
                "31900,chevrolet,1500,2018,clean vehicle,22909.0,black,3gcukrec0jg176059,167763273,tennessee,usa\n" +
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Exiting the app\n";
        // Make sure it prompts the user to input a mileage threshold
        Assertions.assertEquals(output, expected);
    }

    /**
     * This method tests the functionality of the exitApp() method
     */
    @Test
    public void testExitApp() {
        // Make a new TextUITester object that prompts the frontend to exit the app
        TextUITester exitAppInput = new TextUITester("Exit the app\n");
        // Create a new Frontend object that takes a backend reference and scanner as input
        Scanner scan = new Scanner(System.in);
        BackendPlaceholderFrontend backendReference = new BackendPlaceholderFrontend();
        Frontend frontend = new Frontend(backendReference, scan);
        // Run the frontends main loop
        frontend.mainCommandLoop();
        // Check the frontends output
        String output = exitAppInput.checkOutput();
        Assertions.assertEquals(output, "Please ask to either \"Load a file\",  \"List all vehicles with the " +
                "lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\nExiting the app\n");
    }

    /**
     * This method tests the functionality of the backend's mileageThreshold() and how it works with the frontend
     * implementation
     */
    @Test
    public void IntegrationMileageThreshold(){
        // Create a new TextUITester to input into the frontend
        TextUITester listCarsAboveMileageInput = new TextUITester("Load a file\nsrc/small.csv\nList all vehicles above a mileage\n" +
                "100000\nExit the app");
        Scanner scan = new Scanner(System.in);
        Backend backend = new Backend();
        // Create a new frontend with backend and scanner
        Frontend frontend = new Frontend(backend, scan);

        // Prompt the frontend
        frontend.mainCommandLoop();

        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
        "What file would you like to load\n" + "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "How large should the cars' mileage be?\n" +
        "10400 dodge coupe 2009 107856.0\n"+
        "5430 chrysler wagon 2017 138650.0\n"+
        "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
        "Exiting the app\n";

        // Test if the expected output was returned
        String actual = listCarsAboveMileageInput.checkOutput();

        Assertions.assertEquals(actual, expected);
    }

    /**
     * This tests the functionality of the backend's minMileage() method with the frontend
     */
    @Test
    public void IntegrationMinMileage(){
        // Create a new TextUITester to input into the frontend
        TextUITester listCarsLowestMileage = new TextUITester("Load a file\nsrc/small.csv\nList all vehicles with the lowest mileage\n" +
                "30000\nExit the app");
        Scanner scan = new Scanner(System.in);
        Backend backend = new Backend();
        // Create a new frontend with backend and scanner
        Frontend frontend = new Frontend(backend, scan);

        // Prompt the frontend
        frontend.mainCommandLoop();

        String expected = "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "What file would you like to load\n" + "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "How small should the cars' mileage be?\n" +
                "31900 chevrolet 1500 2018 22909.0\n"+
                "Please ask to either \"Load a file\",  \"List all vehicles with the lowest mileage\", \"List all vehicles above a mileage\", or  \"Exit the app\"\n" +
                "Exiting the app\n";

        // Test if the expected output was returned
        String actual = listCarsLowestMileage.checkOutput();

        Assertions.assertEquals(actual, expected);
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Backend backend = new Backend();
        // Create a new frontend with backend and scanner
        Frontend frontend = new Frontend(backend, scan);

        // Prompt the frontend
        frontend.mainCommandLoop();

    }
}