import java.util.ArrayList;

public class Main {
  public static void main(String[] args){
    Parser parser = new Parser();
    ArrayList<Appliance> appList = new ArrayList<>();
    House house = new House();
    try{
      appList=parser.readConfig(args[0]);
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
    for(Appliance app : appList){
      System.out.println(app.getName()+" "+app.getMeterType());
      if(app.getMeterType()==0){
        try{
          house.addElectricAppliance(app);
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
      }
      else if(app.getMeterType()==1){
        try{
          house.addWaterAppliance(app);
        }
        catch(Exception e){
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
