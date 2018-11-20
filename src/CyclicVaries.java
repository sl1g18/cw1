/** An appliance switched on for a set cycle every day from hour 1 to opHours
  * and consumes a random number of units every hour.*/
import java.util.Random;

public class CyclicVaries extends Appliance{
  private float maxUnits;
  private float minUnits;
  private int opHours;
  private int crtClock=0; // Keeps track of the hour of the day
  private boolean generator; // True if appliance generates units
  /** @param name A String - holds appliance name
    * @param opHours Length of daily cycle
    * @param minUnits Minimum of units per hour
    * @param maxUnits Maximum of units per hour */
  public CyclicVaries(String name,int opHours, float minUnits, float maxUnits){
    super(name);
    this.opHours = opHours;
    if(minUnits<0)generator=true;
    this.minUnits = Math.abs(minUnits);
    this.maxUnits = Math.abs(maxUnits);
  }
  /** Simulates the passing of on hour */
  public void timePasses(){
    crtClock++;
    if(crtClock==25)crtClock=1; // If end of day, reset to 1
    if(crtClock<=opHours){
      Random rand = new Random();
      float randBound = (maxUnits-minUnits)+1;
      float consumed = rand.nextFloat()*(maxUnits-minUnits)+minUnits; // Making sure the number is in the range
      if(generator==true)consumed*=-1; // It generates the units instead of consuming them
      tellMeterToConsumeUnits(consumed);
    }
  }
}
