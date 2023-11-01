import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
public class Backend extends IterableMultiKeyRBT implements BackendInterface{

    IterableMultiKeyRBT<Car> cars;

    public Backend(){
        this.cars = new IterableMultiKeyRBT<>();
    }
    @Override
    public void readData(File file){
        try {
            Scanner scnr = new Scanner(file);
            scnr.nextLine();
            while (scnr.hasNextLine()) {
                String data = scnr.nextLine();
                String[] carData = data.split(",");
                Car car = new Car();
                car.setPrice(Integer.parseInt(carData[0]));
                car.setBrand(carData[1]);
                car.setModel(carData[2]);
                car.setYear(Integer.parseInt(carData[3]));
                car.setMileage(Float.parseFloat(carData[5]));
                this.cars.insertSingleKey(car);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Car> minMileage(float minMiles) {
        ArrayList<Car> minCars = new ArrayList<>();
        Iterator<Car> iter = this.cars.iterator();

        while (iter.hasNext()){
            Car current = iter.next();
            if (current.getMileage() < minMiles){
                minCars.add(current);
            }
        }

        return minCars;
    }


    @Override
    public ArrayList<Car> mileageThreshold(float mileThreshold) {
        ArrayList<Car> minCars = new ArrayList<>();
        Iterator<Car> iter = this.cars.iterator();

        while (iter.hasNext()){
            Car current = iter.next();
            if (current.getMileage() > mileThreshold){
                minCars.add(current);
            }
        }

        return minCars;
    }

    public static void main(String[] args){
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        frontend.mainCommandLoop();
    }


}
