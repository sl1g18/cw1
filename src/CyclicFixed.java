/** An appliance switched on for a set cycle every day from hour 1 to opHours
  * and consumes unitsPerHr every hour.*/
public class CyclicFixed extends Appliance{
  private float unitsPerHr;
  private int opHours;
  private int crtClock=0; // Keeps track of the hour of the day
  /** @param name A String - holds appliance name
    * @param opHours Length of daily cycle
    * @param unitsPerHr Units consumed every hour */
  public CyclicFixed(String name, int opHours,float unitsPerHr){
    super(name);
    this.opHours = opHours;
    this.unitsPerHr = unitsPerHr;
  }
  /** Simulates the passing of on hour */
  public void timePasses(){
    crtClock++;
    if(crtClock==25)crtClock=1; // If end of day, reset to 1
    if(crtClock<=opHours)tellMeterToConsumeUnits(unitsPerHr);
  }
}
