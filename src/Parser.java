import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

enum Property{
  NAME("name",0),
  SUBCLASS("subclass",1),
  METER("meter",2),
  MIN_UNITS("min units consumed",3),
  MAX_UNITS("max units consumed",4),
  FIXED_UNITS("fixed units consumed",5),
  PROBABILITY("probability switched on",6),
  CYCLE("cycle length",7);

  private final String prop;
  private final int spot;
  Property(String property, int spot){
    this.prop = property;
    this.spot = spot;
  }
  @Override
  public String toString(){
    return prop;
  }
  public int spot(){
    return spot;
  }
}
enum AppType{
  CYCLIC_FIXED("cyclicfixed", new int[]{1, 1, 1, 0, 0, 1, 0, 1}),
  CYCLIC_VARIES("cyclicvaries", new int[]{1, 1, 1, 1, 1, 0, 0, 1}),
  RANDOM_FIXED("randomfixed", new int[]{1, 1, 1, 0, 0, 1, 1, 0}),
  RANDOM_VARIES("randomvaries", new int[]{1, 1, 1, 1, 1, 0, 1, 0});

  private final String prop;
  private final int[] check;


  AppType(String type, int[] check){
    this.prop = type;
    this.check = check;
  }

  @Override
  public String toString(){
    return prop;
  }
  public int[] checkList(){
    return check;
  }
}
public class Parser {
  File config;
  /** Creates a parser */
  public Parser(){
  }
  public ArrayList<Appliance> readConfig(String filePath) throws Exception{
    ArrayList<Appliance> appArrayList = new ArrayList<>();
    if(!Files.exists(Paths.get(filePath))){
      throw new FileNotFoundException("File can not be accesed");
    }
    else{
      config = new File(Paths.get(filePath).toUri());
    }
    try{
      appArrayList = parseConfig(config);
    }
    catch(Exception e){
      throw new Exception(e.getMessage());
    }
    return appArrayList;
  }
  private ArrayList<Appliance> parseConfig(File config) throws Exception{
    ArrayList<Appliance> appList = new ArrayList<>();
    Scanner scan;
    try{
      scan = new Scanner(config);
    }
    catch(FileNotFoundException e){
      throw new FileNotFoundException("Configuration file not found!");
    }
    System.out.println("We got to read the file");
    String[] appDetails = {"empty","empty","empty","empty","empty","empty","empty","empty"};
    int numDetails = 0;
    while(scan.hasNextLine()){
      String read = scan.nextLine();
      String[] command = read.split("\\:");
      if(!read.trim().isEmpty()){
        numDetails++;
        System.out.println("We got to read " + numDetails);
        for (Property p : Property.values()) {
          if (command[0].trim().equalsIgnoreCase(p.toString())) {
            if(command.length>1) {
              if (!command[1].trim().isEmpty()) {
                appDetails[p.spot()] = command[1].trim().toLowerCase();
              } else appDetails[p.spot()] = "empty";
            } else appDetails[p.spot()] = "empty";
          }
        }
      }
      if(numDetails==8){
        try {
          appList.add(processAppliance(appDetails));
        }
        catch (NoSuchFieldException e) {
          e.printStackTrace();
        }
        finally{
          numDetails=0;
        }
      }
    }
    return appList;
  }
  private Appliance processAppliance(String[] appDetails) throws Exception{
    System.out.println("We got to process the appliance");
    int[] check = new int[8];
    boolean fixed=false;
    boolean cyclic=false;
    for(AppType a : AppType.values()){
      if(appDetails[1].equals(a.toString()))check=a.checkList();
    }
    if(check.length==0)throw new NoSuchFieldException(appDetails[0]+" - "+appDetails[1]+" is not a valid subclass");
    for(int i = 0;i<check.length;i++){
      if(check[i]==1&&appDetails[i].equals("empty"))throw new NoSuchFieldException(appDetails[0]+" - missing property "+Property.values()[i].toString());
    }
    if(check[7]==1)cyclic=true;
    if(check[5]==1)fixed=true;
    if(cyclic==true){
      String[] cycleField = appDetails[7].split("\\/");
      int cycle;
      try{
        cycle = Integer.parseInt(cycleField[0]);
      }
      catch(Exception e){
        throw new NumberFormatException(appDetails[0]+" - incorrect cycle configuration. 1");
      }
      if(cycle<1||cycle>24)throw new Exception(appDetails[0]+" - incorrect cycle configuration. 2");
      else appDetails[7] = Integer.toString(cycle);
    }
    else{
      String[] probabilityField = appDetails[6].split(" ");
      int[] probability = new int[2];
      try{
        probability[0]=Integer.parseInt(probabilityField[0]);
        probability[1]=Integer.parseInt(probabilityField[2]);
      }
      catch(Exception e){
        throw new NumberFormatException(appDetails[0]+" - incorrect probability configuration.");
      }
      if(probability[0]!=1||probability[1]<1)throw new Exception(appDetails[0]+" - incorrect probability configuration.");
      else appDetails[6]=Integer.toString(probability[1]);
    }
    if(fixed==true){
      float units;
      try{
        units = Float.parseFloat(appDetails[5]);
      }
      catch(Exception e){
        throw new NumberFormatException(appDetails[0]+" - incorrect fixed units configuration.");
      }
      appDetails[5]=Float.toString(units);
    }
    else{
      float minUnits;
      float maxUnits;
      try{
        minUnits = Float.parseFloat(appDetails[3]);
        maxUnits = Float.parseFloat(appDetails[4]);
      }
      catch(Exception e){
        throw new NumberFormatException(appDetails[0]+" - incorrect min/max units range configuration.");
      }
      if(minUnits*maxUnits<0)throw new Exception(appDetails[0]+" - incorrect min/max units range configuration.");
    }
    if(appDetails[2].equals("electric"))appDetails[2]=Integer.toString(0);
    else if(appDetails[2].equals("water"))appDetails[2]=Integer.toString(1);
    else if(appDetails[2].equals("both"))appDetails[2]=Integer.toString(2);
    else throw new Exception(appDetails[0]+" - incorrect meter configuration.");
    return declareAppliance(appDetails);
  }
  private Appliance declareAppliance(String[] appDetails){
    System.out.println("We got to declare de appliance");
    Appliance app;
    if(appDetails[1].equals(AppType.CYCLIC_FIXED.toString())){
      app = new CyclicFixed(appDetails[0],Integer.parseInt(appDetails[7]),Float.parseFloat(appDetails[5]));
    }
    else if(appDetails[1].equals(AppType.CYCLIC_VARIES.toString())){
      app = new CyclicVaries(appDetails[0],Integer.parseInt(appDetails[7]),Float.parseFloat(appDetails[3]),Float.parseFloat(appDetails[4]));
    }
    else if(appDetails[1].equals(AppType.RANDOM_FIXED.toString())){
      app = new RandomFixed(appDetails[0],Integer.parseInt(appDetails[6]),Float.parseFloat(appDetails[5]));
    }
    else{
      app = new RandomVaries(appDetails[0],Integer.parseInt(appDetails[6]),Float.parseFloat(appDetails[3]),Float.parseFloat(appDetails[4]));
    }
    app.setMeterType(Integer.parseInt(appDetails[2]));
    System.out.println(app.getName()+" is now in the list");
    return app;
  }
}
