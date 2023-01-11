import java.util.Scanner;

/**
 * Write a description of class performanceTaskMain here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class performanceTaskMain
{
    Scanner scanner = new Scanner(System.in);
    String input = "";
    int LOWER_BOUND = 8;
    int UPPER_BOUND = 16;
    int simSize;
    boolean shouldContinue = true;
    boolean activeSim = false;
    int [][] sim;
    public performanceTaskMain() {
        // :D
    }
    
    public void runEventLoop() {
        while (shouldContinue) {
            if (activeSim == false) {
                System.out.println("Hi!");
            } else {
                
            }
            System.out.println("[C] - close program");
            System.out.println("[N] - new simulation");
            input = scanner.next();
            if (input.equals("C")) {
                shouldContinue = false;
            }
            if (input.equals("N")) {
                sim = createSimulation(simSize);
                activeSim = true;
            }
        }
    }
    
    public static void main (String[] args) {
        performanceTaskMain conwayGame = new performanceTaskMain();
        conwayGame.runEventLoop();
    }
    
    public int[][] createSimulation(int simSize) {
        int[][] array2d = new int[simSize][simSize]; 
        for (int a = 0;a < simSize;a++) {
            for (int i = 0;i < simSize;i++) {
                array2d[a][i] = 0;
            }
        }
        return array2d;
    }
    
}