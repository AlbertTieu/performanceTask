import java.util.Scanner;
import java.util.InputMismatchException;

public class performanceTaskMain {
    Scanner scanner = new Scanner(System.in);
    String input = "";
    int simSize;
    int LOWER_BOUND = 16;
    int UPPER_BOUND = 35;
    int time = 1000;
    int genNum = 0;
    boolean shouldContinue = true;
    boolean activeSim = false;
    boolean errorSimSize = false;
    boolean errorAuto = false;
    boolean successMsg = false;
    int [][] sim;
    public performanceTaskMain() {
        // :D
    }
    
    public void runEventLoop() {
        while (shouldContinue) {
            System.out.print('\u000C');
            input = "";
            if (errorAuto == true) {
                System.out.println("error: please enter integer");
                errorAuto = false;
            }
            if (errorSimSize == true) {
                System.out.println("error: please enter an integer between " + LOWER_BOUND + " - " + UPPER_BOUND);
                errorSimSize = false;
            }
            
            if (successMsg) {
                System.out.println("interval set successfully");
                successMsg = false;
            }
            
            if (activeSim == false) {
                System.out.println("Hi!");
            } else {
                printSim();
                System.out.println("[Z] - advance simulation");
                System.out.println("[A] - autoplay simulation");
            }
            System.out.println("[N] - new simulation");
            System.out.println("[C] - close program");
            System.out.println("[O] - options");
            input = scanner.next();
            
            if (input.equals("O")) {
                System.out.println("[A] - auto interval");
                input = scanner.next();
                if (input.equals("A")) {
                    try {
                        System.out.println("current interval - " + time + " ms");
                        System.out.println("input interval (ms)");
                        time = scanner.nextInt();
                        successMsg = true;
                    } catch (InputMismatchException error) {
                        errorAuto = true;
                    }
                }
                input = "";
                scanner.nextLine();
            }
            
            if (input.equals("C")) {
                System.out.print('\u000C');
                shouldContinue = false;
            }
            
            if (input.equals("N")) {
                System.out.println("how big? " + "(" + LOWER_BOUND + " - " + UPPER_BOUND + ")");
                try {
                    int saveSize = simSize;
                    simSize = scanner.nextInt();
                    if (simSize < LOWER_BOUND || simSize > UPPER_BOUND) {
                        errorSimSize = true;
                        simSize = saveSize;
                    } else {
                        sim = createSimulation(simSize + 2);
                        activeSim = true;
                    }
                } catch (InputMismatchException error) {
                    errorSimSize = true;
                }
                input = "";
                genNum = 0;
                scanner.nextLine();
            }
            
            if (input.equals("Z") && activeSim == true) {
                sim = simNextGen();
                input = "";
                genNum++;
                scanner.nextLine();
            } else if (input.equals ("Z") && activeSim == false) {
                input = "";
                scanner.nextLine();
            }
            
            if (input.equals ("A") && activeSim == true) {
                System.out.println("interval - " + time + " ms");
                System.out.println("how many generations?");
                int gens;
                try {
                    gens = scanner.nextInt();
                    for (int b = 0;b < gens;b++) {
                        System.out.print('\u000C');
                        sim = simNextGen();
                        genNum++;
                        printSim();
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    }
                } catch (InputMismatchException error) {
                    errorAuto = true;
                    scanner.nextLine();
                }
            } else if (input.equals ("A") && activeSim == false) {
                scanner.nextLine();
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
    
    public void printSim() {
        //this is the method for printing the conway simulation game itself
        for (int a = 0;a < simSize;a++) {
            for (int i = 0;i < simSize;i++) {
                if (sim[i][a] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print("X");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.print("generation " +genNum);
        System.out.println();
    }
}