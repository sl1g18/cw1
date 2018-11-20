/** An appliance with a chance of [probability] to be in an hour.
  * Cosumes [unitsPerHr] units every hour.*/
import java.util.Random;

public class RandomFixed extends Appliance{
  private int probability;
  private float unitsPerHr;
  /** @param name String holding appliance name
    * @param probability Probability of being on this hour
    * @param unitsPerHr Units consumed every hour */
  public RandomFixed(String name, int probability, float unitsPerHr){
    super(name);
    this.probability = probability;
    this.unitsPerHr = unitsPerHr;
  }
  /** Simulates the passing of on hour */
  public void timePasses(){
    Random rand = new Random();
    boolean isOn = rand.nextInt(probability)==0; // 1 in [probability] chance
    if(isOn==true){
      tellMeterToConsumeUnits(unitsPerHr);
    }
  }
}
