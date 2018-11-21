import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    try {
      Simulator sim = new Simulator(args[0]);
      sim.simulate(5);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}
