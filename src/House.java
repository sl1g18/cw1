import java.util.ArrayList;

public class House {
  Meter electricMeter;
  Meter waterMeter;
  ArrayList<Appliance> appliances = new ArrayList<>();
  /** Creates an instance of House and two new meters to track used units*/
  public House(){
    // Creating an instance of an electric meter for the house with a cost of 0.013 GBP per unit
    electricMeter = new Meter("electric",0.013);
    //Creating an instance of a water meter for the house with a cost of 0.002 GBP per unit
    waterMeter = new Meter("water", 0.002);
  }
  /** Creates an instance of House and attaches meters to track electricity/water consumption
    * @param electricMeter Meter to track electricity units, can be BatteryMeter
    * @param waterMeter Meter to track water units*/
  public House(Meter electricMeter, Meter waterMeter){
    this.electricMeter = electricMeter;
    this.waterMeter = waterMeter;
  }
  /** Adds an Appliance that uses water to the house
    * @param An Appliance to be added
    * @throws Exception when appliance doesn't use electricity*/
  public void addElectricAppliance(Appliance newApp) throws Exception{
    if(newApp.getMeterType()!=0){
      throw new Exception("Appliance "+newApp.getName()+" is not an electricity only appliance!");
    }
    else {
      newApp.attachToMeter(electricMeter);
      appliances.add(newApp);
    }
  }
  /** Adds an Appliance that uses water to the house
    * @param An Appliance to be added
    * @throws Exception when appliance doesn't use water*/
  public void addWaterAppliance(Appliance newApp) throws Exception{
    if(newApp.getMeterType()!=1){
      throw new Exception("Appliance "+newApp.getName()+" is not a water only appliance!");
    }
    else {
      newApp.attachToMeter(waterMeter);
      appliances.add(newApp);
    }
  }
  /** Removes the appliance for the house
   * @param An Appliance to be removed
   * @throws Exception when appliance is not present */
  public void removeAppliance(Appliance oldApp) throws Exception{
    if(appliances.contains(oldApp)==false){
      throw new Exception("Appliance "+oldApp.getName()+" is not in the house.");
    }
    else appliances.remove(oldApp);
  }
  /** @return number of appliances in house*/
  public int numAppliances(){
    return appliances.size();
  }
  /** Simulates the passing of 1hr in the house
    * @return A double recording final cost of running all the appliances.*/
  public double activate(){
    for(Appliance crtAppliance : appliances){
      crtAppliance.timePasses();
    }
    double finalCost = electricMeter.report()+waterMeter.report();
    return finalCost;
  }
  /** Simulates the passing of [hours] hours in the house
    * @param hours Numbers of hours to run the house for
    * @return A double containing the total cost of running the house for [hours]*/
  public double activate(int hours){
    double totalCost = 0.0;
    for(int i=1;i<=hours;i++){
      try {
        Thread.sleep(500);
        totalCost += activate();
      }
      catch(InterruptedException e){
        System.out.println(e.getMessage());
      }
    }
    return totalCost;
  }
}
