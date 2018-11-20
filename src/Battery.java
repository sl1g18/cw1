/** A battery can hold [capacity] amount of units for later use*/
public class Battery {
  private float capacity;
  private float charge;
  /** @param capacity Maximum charge of battery */
  public Battery(int capacity){
    this.capacity = capacity;
    this.charge = 0;
  }
  /** Add or consume battery charge
    * @param units How many units to consume/charge */
  public void chargeBattery(float units){
    if(charge+units<capacity) {
      charge += units;
    }
    else charge=capacity;
  }
  public float dischargeBattery(float units){
    if(charge-units>0){
      charge-=units;
      return units;
    }
    else{
      float crtCharge = charge;
      charge = 0;
      return crtCharge;
    }
  }
  /** @return Current charge of the battery*/
  public float getCharge(){
    return charge;
  }
}
