public interface CarObject extends Comparable<Car> {

    // private IterableMultiKeySortedCollectionInterface interface;
    /**
     * Constructor for the car objects that will be made with this method.
     */
    /**
     // public BackendInterface(IterableMultiKeySortedCollectionInterface interface){
     this.interface=interface;
     }
     */

    /**
     * Method that will return the brand of the car.
     */
    public String getBrand();

    /**
     * Method will set brand of car with a string input.
     */
    public void setBrand(String brand);

    /**
     * Method that will return the model of the car.
     */
    public String getModel();

    /**
     * Method that will set the model of the car with a string input.
     */
    public void setModel(String model);

    /**
     * Method that will return the year of the car.
     */
    public int getYear();

    /**
     * Method that will set the year of the car with an int.
     */
    public void setYear(int year);

    /**
     * Method that will return sale price of the car.
     */
    public int getPrice();

    /**
     * Method that will set sale price of the car with an int.
     */
    public void setPrice(int price);

    /**
     * Method that will return the mileage of the car.
     */
    public float getMileage();

    /**
     * Method that will set the mileage of the car with a float.
     */
    public void setMileage(float mileage);

    public int compareTo(Car car);


}
