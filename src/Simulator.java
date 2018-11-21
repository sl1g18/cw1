import java.io.IOException;

public class Simulator {
  House house;
  public Simulator(String config) throws IOException {

    BatteryMeter batteryMeter = new BatteryMeter("electric",0.013);
    Battery batEl = new Battery(5);
    Battery batWa = new Battery(5);
    batteryMeter.addBattery(batEl);
    BatteryMeter waterMeter = new BatteryMeter("water",0.002);
    waterMeter.addBattery(batWa);
    house = new House(batteryMeter,waterMeter);
    try {
      ConfigLoader.load(house, config);
    }catch (IOException e){
      throw new IOException(e.getMessage());
    }
  }
  public void simulate(int steps){
    double cost;
    cost = house.activate(steps);
    System.out.println("The cost for running "+house.numAppliances()+" appliances for "+steps+" steps is "+cost);
  }
}
