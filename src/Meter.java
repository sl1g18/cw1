/** A meter keeps track of how many units of [utilityName] are consumed*/
public class Meter {
  private String utilityName;
  private double unitCost;
  private float meterReading;
  /** @param utility What kind of utility does it measure
   * @param cost What is the cost of one unit*/
  public Meter(String utility, double cost){
    utilityName = utility;
    unitCost = cost;
    meterReading = 0;
  }
  /** Updates the reading of the meter with [consumed] units
    * @param consumed Units to add/substract from the meter reading */
  public void consumeUnits(float consumed){
    meterReading += consumed;
  }
  /** @return A String containing the name of the units this meter tracks*/
  public String getUtility(){
    return utilityName;
  }
  /** @return a float representing the amount of units recorded so far */
  public float getMeterReading(){
    return meterReading;
  }
  /** @param units Setting the amount of units recorded by the meter*/
  protected void setMeterReading(float units){
    meterReading=units;
  }
  /** @return A double containing the cost of using [meterReading] units*/
  protected double calculateCost(){
    return (double)meterReading*unitCost;
  }
  /** @return A doublec containing the cost of using [meterReading] units*/
  public double report(){
    double cst = calculateCost();
    //System.out.println(utilityName+" "+meterReading+" "+cst);
    meterReading=0;
    return cst;
  }
}
