public class Car implements CarObject{

    public String brand;

    public String model;

    public int price;

    public int year;

    public float mileage;

    Car() {
        brand = null;
        model = null;
        price = 0;
        year = 0;
        mileage = 0f;
    }
    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public float getMileage() {
        return mileage;
    }

    @Override
    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    @Override
    public int compareTo(Car car){
        if (mileage > car.mileage){
            return 1;
        }
        else if (mileage < car.mileage){
            return -1;
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString(){
        return this.getPrice() + " " + this.getBrand() + " " + this.getModel() + " " + this.getYear() + " " +
                this.getMileage();
    }

}
