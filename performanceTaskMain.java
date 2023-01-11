import java.util.Scanner;
import java.util.InputMismatchException;

public class performanceTaskMain
{
    Scanner scanner = new Scanner(System.in);
    String input = "";
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
                System.out.println("how big?");
                try {
                    simSize = scanner.nextInt();
                } catch (InputMismatchException error) {
                    System.out.println("please enter an integer between 8 - 16");
                }
                sim = createSimulation(simSize);
                for (int a = 0;a < simSize;a++) {
                    for (int i = 0;i < simSize;i++) {
                        System.out.print(sim[i][a]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                activeSim = true;
            }
        }
    }
    
    public static void main (String[] args) {
        performanceTaskMain conwayGame = new performanceTaskMain();
        conwayGame.runEventLoop();
    }
    
    public int[][] createSimulation(int simulation_size) {
        simSize = simulation_size;
        int[][] array2d = new int[simSize][simSize]; 
        int status;
        for (int a = 0;a < simSize;a++) {
            for (int i = 0;i < simSize;i++) {
                status = (int)(Math.random() * 2);
                array2d[i][a] = status;
            }
        }
        return array2d;
    }
}