import java.io.IOException;
import java.util.HashMap;

public class ApplianceFactory {
  public ApplianceFactory(){
  }
  public Appliance createApp(HashMap<String,String> appConfig) throws IOException {
    Appliance app;
    String type = appConfig.getOrDefault("subclass"," ");
    String meter = appConfig.getOrDefault("meter", " ");
    if(type.equals(" "))throw new IOException("Config - Missing subclass field");
    try {
      System.out.println("Appliance is "+type);
      System.out.println(" ");
      if (type.equals("cyclicfixed"))app=buildCyclicFixed(appConfig);
      else if(type.equals("cyclicvaries"))app=buildCyclicVaries(appConfig);
      else if(type.equals("randomfixed"))app=buildRandomFixed(appConfig);
      else if(type.equals("randomvaries"))app=buildRandomVaries(appConfig);
      else throw new IOException("Config - Incorrect subclass field");
      setMeterType(app,meter);
      return app;
    } catch(IOException e){
      throw new IOException(e.getMessage());
    }
  }
  private Appliance buildCyclicFixed(HashMap<String,String> appConfig) throws IOException{
    String name = appConfig.getOrDefault("name","defaultName");
    CyclicFixed result;
    float unitsPerHr;
    int opHours;
    try{
      unitsPerHr = checkFixed(appConfig);
      opHours = checkCyclic(appConfig);
      result = new CyclicFixed(name,opHours,unitsPerHr);
      return result;
    }catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
  private Appliance buildCyclicVaries(HashMap<String,String> appConfig) throws IOException{
    String name = appConfig.getOrDefault("name","defaultName");
    CyclicVaries result;
    float[] units;
    int opHours;
    try{
      opHours = checkCyclic(appConfig);
      units = checkVaries(appConfig);
      result = new CyclicVaries(name,opHours,units[0],units[1]);
      return result;
    }catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
  private Appliance buildRandomFixed(HashMap<String,String> appConfig) throws IOException{
    String name = appConfig.getOrDefault("name","defaultName");
    RandomFixed result;
    float units;
    int probability;
    try{
      probability = checkRandom(appConfig);
      units = checkFixed(appConfig);
      result = new RandomFixed(name,probability,units);
      return result;
    }catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
  private Appliance buildRandomVaries(HashMap<String,String> appConfig) throws IOException{
    String name = appConfig.getOrDefault("name","defaultName");
    RandomVaries result;
    float[] units;
    int probability;
    try{
      probability = checkRandom(appConfig);
      units = checkVaries(appConfig);
      result = new RandomVaries(name,probability,units[0],units[1]);
      return result;
    }catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
  private float checkFixed(HashMap<String,String>appConfig) throws IOException{
    String unitString = appConfig.getOrDefault("fixed units consumed", "def");
    float unitsPerHr;
    try{
      unitsPerHr = Float.parseFloat(unitString);
    } catch (Exception e){
      throw new IOException("Config - Incorrect fixed units field");
    }
    return unitsPerHr;
  }
  private int checkCyclic(HashMap<String,String>appConfig) throws IOException{
    String cycleField = appConfig.getOrDefault("cycle length","def");
    String[] cycleCommand = cycleField.split("\\/");
    int opHours;
    try {
      opHours = Integer.parseInt(cycleCommand[0]);
      if (Integer.parseInt(cycleCommand[1]) != 24) throw new IOException("Config - Incorrect cycle field 1");
      if(opHours<1||opHours>24)throw new IOException("Config - Incorrect cycle field 2");
      return opHours;
    } catch(Exception e){
      throw new IOException(e.getMessage());
    }
  }
  private int checkRandom(HashMap<String,String>appConfig) throws IOException{
    String probField = appConfig.getOrDefault("probability switched on", "def");
    String[] probCommand = probField.split(" ");
    int probability=0;
    try{
      if(Integer.parseInt(probCommand[0])!=1)throw new IOException("Config - Incorrect probability field");
      probability=Integer.parseInt(probCommand[2]);
      if(probability<1)throw new IOException("Config - Incorrect probability field");
      return probability;
    } catch(Exception e){
      throw new IOException("Config - Incorrect probability field");
    }
  }
  private float[] checkVaries(HashMap<String,String>appConfig) throws IOException{
    String minUnitsField = appConfig.getOrDefault("min units consumed", "def");
    String maxUnitsField = appConfig.getOrDefault("max units consumed", "def");
    float[] units = new float[2];
    try{
      units[0] = Float.parseFloat(minUnitsField);
      units[1] = Float.parseFloat(maxUnitsField);
      if(units[0]*units[1]<0)throw new IOException("Config - Incorrect min/max units field");
      return units;
    } catch(Exception e){
      throw new IOException("Config - Incorrect min/max units field");
    }
  }
  private void setMeterType(Appliance app, String meterType) throws IOException{
    if(meterType.equals("electric"))app.setMeterType(0);
    else if(meterType.equals("water"))app.setMeterType(1);
    else if(meterType.equals("both"))app.setMeterType(2);
    else throw new IOException("Config - Incorrect meter field");
  }
}
