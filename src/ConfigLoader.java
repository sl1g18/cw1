import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigLoader {
  public ConfigLoader(House house, String config)throws IOException{
      load(house, config);
  }
  public static void load(House house, String config) throws IOException{
    ArrayList<Appliance> appList = new ArrayList<>();
    Parser parser = new Parser();
    try {
      appList = parser.readConfig(config);
      for (Appliance app : appList) {
        if (app.getMeterType() == 0) {
          house.addElectricAppliance(app);
        } else if (app.getMeterType() == 1) {
          house.addWaterAppliance(app);
        }
      }
    } catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
}
