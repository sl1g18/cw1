/** A special kind of meter that can handle batteries */
public class BatteryMeter extends Meter{
  Battery storageUnit;
  /** @param utility What kind of utility does it measure
    * @param cost What is the cost of one unit*/
  public BatteryMeter(String utility, double cost){
    super(utility,cost);
  }
  /** Calculates the cost of all the units consumed, accounting for power
    * stored in the batttery. Resets meter reading at the end
    * @return A double containing the cost of all the units consumed */
  @Override
  public double report(){
    float unitsFromBat = useBattery();
    double cost = calculateCost();
    System.out.println("-- Meter for "+getUtility()+" --");
    System.out.println("Power used from battery: "+unitsFromBat);
    System.out.println("Power used from main: "+getMeterReading());
    System.out.println(" ");
    setMeterReading(0);
    return cost;
  }
  /** Charges battery if excess energy is left in the meter,
   *  Drains battery to lower costs, if possible.
   *  @return A float containing the number of units drained from
   *          the battery, 0 if battery is charged*/
  private float useBattery(){
    float meterReading = getMeterReading();
    if(meterReading<0){ // If consumed units < produced units
      storageUnit.chargeBattery(meterReading*-1);
      setMeterReading(0);
    }
    else if(meterReading>0){ // If consumed units > produced units
      float unitsFromBat=storageUnit.dischargeBattery(meterReading);
      setMeterReading(meterReading-unitsFromBat);
      return unitsFromBat;
    }
    return 0;
  }
  /** @param bat A Battery to be linked to this meter */
  public void addBattery(Battery bat){
    storageUnit=bat;
  }
}
