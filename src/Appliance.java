/** This class works as a super class for all types of appliances */
public abstract class Appliance {
  private String name;
  private Meter meter;
  private int meterType; // 0 electric, 1 water, 2 both
  /** @param nameArg The name of the appliance to be created*/
  public Appliance(String nameArg){
    name = nameArg;
  }
  /** @param meterArg Attaches a meter to track units consumed*/
  public void attachToMeter(Meter meterArg){
    meter = meterArg;
  }
  /** All appliances must simulate the passing of an hour*/
  public abstract void timePasses();
  /** @return A string containing the name of the appliance*/
  public String getName(){
    return name;
  }
  /** @return A string containing the type of utility consumed*/
  public String getMeterUtility(){
    return meter.getUtility();
  }
  /** @return An integer containing the type of meter(0 electric, 1 water, 2 both)*/
  public int getMeterType(){
    return meterType;
  }
  /** @param type An integer representing the type of meter this appliance uses*/
  public void setMeterType(int type){
    meterType=type;
  }
  /** @param consume A float reporting the number of units consumed in 1hr*/
  protected void tellMeterToConsumeUnits(float consume){
    meter.consumeUnits(consume);
  }
}
