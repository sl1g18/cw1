/** An appliance with a chance of [probability] to be in an hour.
  * Cosumes between [minUnits] and [maxUnits] units every hour.*/

import java.util.Random;

public class RandomVaries extends Appliance {
  private int probability;
  private float minUnits;
  private float maxUnits;
  private boolean generator; // True if appliance generates units
  /** @param name String holding appliance name
    * @param probability Probability of being on this hour
    * @param minUnits Minimum of units per hour
    * @param maxUnits Maximum of units per hour */
  public RandomVaries(String name, int probability, float minUnits, float maxUnits){
    super(name);
    this.probability=probability;
    if(minUnits<0)generator=true;
    this.minUnits=Math.abs(minUnits);
    this.maxUnits=Math.abs(maxUnits);
  }
  public void timePasses(){
    Random rand = new Random();
    boolean isOn = rand.nextInt(probability)==0; // 1 in [probability] chance
    if(isOn==true){
      float randBound = (maxUnits-minUnits)+1;
      float consumed = rand.nextFloat()*(maxUnits-minUnits)+minUnits; // Making sure the number is in the range
      if(generator==true)consumed*=-1; // It generates the units instead of consuming them
      tellMeterToConsumeUnits(consumed);
    }
  }
}
