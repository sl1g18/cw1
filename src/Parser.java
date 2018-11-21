import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Parser {
  File config;
  public Parser() {
  }
  /** Reads the config file so it can be processed
    * @param filePath The path to the config file
    * @return An array list of appliances found in the config file
    * @throws IOException if anything goes wrong with processing the config file*/
  public ArrayList<Appliance> readConfig(String filePath) throws IOException {
    ArrayList<Appliance> appList;
    if (!Files.exists(Paths.get(filePath))) {
      throw new IOException("Config - File cannot be accessed.");
    } else {
      config = new File(Paths.get(filePath).toUri());
    }
    try {
      appList = parseConfig(config);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
    return appList;
  }
  private ArrayList<Appliance> parseConfig(File config) throws IOException{
    Scanner scan = new Scanner(config);
    HashMap<String,String> appConfig = new HashMap<>();
    ArrayList<Appliance> appList = new ArrayList<>();
    int lineCount = 0;
    while(scan.hasNextLine()){
      String crtLine = normalizeStr(scan.nextLine());
      if(!crtLine.isEmpty()){
        lineCount++;
        String[] command = normalizeStr(crtLine.split("\\:"));
        if(!command[0].isEmpty()){
          if(command.length>1){
            appConfig.put(command[0],command[1]);
            System.out.println(command[0]+"-"+command[1]);
          }
          else appConfig.put(command[0]," ");
        }
        if(lineCount==8){
          try {
            ApplianceFactory appFactory = new ApplianceFactory();
            appList.add(appFactory.createApp(appConfig));
          } catch(IOException e){
            throw new IOException(e.getMessage());
          } finally {
            appConfig.clear();
            lineCount = 0;
          }
        }
      }
    }
    return appList;
  }
  /** Normalize = Trim and lower case the string
    * @param line A string to be normalized
    * @return A normalized string*/
  private String normalizeStr(String line){
    return line.trim().toLowerCase();
  }
  /** Normalize = Trim and lower case the string
   * @param lines An array of strings to be normalized
   * @return A normalized array of strings*/
  private String[] normalizeStr(String[] lines){
    for(int i =0;i<lines.length;i++){
      lines[i] = normalizeStr(lines[i]);
      lines[i] = normalizeStr(lines[i]);
    }
    return lines;
  }
}