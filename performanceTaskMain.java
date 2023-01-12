import java.util.Scanner;
import java.util.InputMismatchException;

public class performanceTaskMain
{
    Scanner scanner = new Scanner(System.in);
    String input = "";
    int simSize;
    int LOWER_BOUND = 20;
    int UPPER_BOUND = 40;
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
                for (int a = 0;a < simSize;a++) {
                    for (int i = 0;i < simSize;i++) {
                        System.out.print(sim[i][a]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println("[Z] - advance simulation");
            }
            System.out.println("[N] - new simulation");
            System.out.println("[C] - close program");
            input = scanner.next();
            if (input.equals("C")) {
                shouldContinue = false;
            }
            if (input.equals("N")) {
                System.out.println("how big?");
                try {
                    simSize = scanner.nextInt();
                    if (simSize < LOWER_BOUND || simSize > UPPER_BOUND) {
                        System.out.println("please enter an integer between 8 - 16");
                    } else {
                        sim = createSimulation(simSize + 2);
                        activeSim = true;
                    }
                } catch (InputMismatchException error) {
                    System.out.println("please enter an integer between 8 - 16");
                }
            }
            if (input.equals("Z")) {
                sim = simNextGen();
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
                if (i == 0 || a == 0 || i == simSize -1 || a == simSize -1) {
                    array2d[i][a] = 0;
                } else {
                    status = (int)(Math.random() * 2);
                    array2d[i][a] = status;
                }
            }
        }
        return array2d;
    }
    
    public int[][] simNextGen() {
        int[][] array2d = new int[simSize][simSize];
        int status;
        int adjLiving;
        for (int a = 1;a < simSize - 1;a++) {
            for (int i = 1;i < simSize - 1;i++) {
                adjLiving = sim[i-1][a-1] + sim[i-1][a] + sim[i-1][a+1] + sim[i][a+1] + sim[i][a-1] + sim[i+1][a-1] + sim[i+1][a] + sim[i+1][a+1];
                if (sim[i][a] == 0) {
                    if (adjLiving == 3) {
                        array2d[i][a] = 1;
                    }
                } else if (adjLiving == 2 || adjLiving == 3) {
                    array2d[i][a] = 1;
                } else {
                    array2d[i][a] = 0;
                }
            }
        }
        return array2d;
    }
}