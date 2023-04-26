import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class performanceTaskMain {
    Scanner scanner = new Scanner(System.in);
    String input = "";
    int simSize;
    int LOWER_BOUND = 16;
    int UPPER_BOUND = 35;
    int time = 1000;
    int genNum = 0;
    int w;
    int h;
    boolean shouldContinue = true;
    boolean activeSim = false;
    boolean errorSimSize = false;
    boolean errorInt = false;
    boolean successMsg = false;
    boolean activeCusSim = false;
    int [][] sim;
    int [][] lastGen;
    List <int[][]> genHist = new ArrayList <int[][]> (10);
    public performanceTaskMain() {
        // :D
    }
    
    public void runEventLoop() {
        while (shouldContinue) {
            System.out.print('\u000C');
            input = "";
            if (errorInt == true) {
                System.out.println("error: please enter valid integer");
                errorInt = false;
            }
            if (errorSimSize == true) {
                System.out.println("error: please enter an integer between " + LOWER_BOUND + " - " + UPPER_BOUND);
                errorSimSize = false;
            }
            
            if (activeSim == false && activeCusSim == false) {
                System.out.println("Hi!");
            } else {
                printSim();
                System.out.println("[Z] - advance simulation");
                System.out.println("[X] - undo simulation");
                System.out.println("[A] - autoplay simulation");
            }
            System.out.println("[N] - new simulation");
            System.out.println("[M] - new custom sim");
            System.out.println("[C] - close program");
            input = scanner.next();
            
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
                        int modSize = simSize+2;
                        int[][] array2d = new int[modSize][modSize]; 
                        int status;
                        for (int a = 0;a < modSize;a++) {
                            for (int i = 0;i < modSize;i++) {
                                if (i == 0 || a == 0 || i == modSize -1 || a == modSize -1) {
                                    array2d[i][a] = 0;
                                } else {
                                    status = (int)(Math.random() * 2);
                                    array2d[i][a] = status;
                                }
                            }
                        }
                        sim = array2d;
                        activeSim = true;
                        activeCusSim = false;
                    }
                } catch (InputMismatchException error) {
                    errorSimSize = true;
                }
                input = "";
                genNum = 0;
                scanner.nextLine();
            }
            if (input.equals("M")) {
                System.out.println("enter width:");
                try {
                    w = scanner.nextInt();
                } catch (InputMismatchException error) {
                    errorInt = true;
                }
                if (errorInt != true) {
                    System.out.println("enter length:");
                    try {
                        h = scanner.nextInt();
                        createCustomSimulation(h,w);
                    } catch (InputMismatchException error) {
                        errorInt = true;
                    }
                }
            }
            
            if (input.equals("Z") && (activeSim == true || activeCusSim == true)) {
                sim = simNextGen();
                input = "";
                genNum++;
                scanner.nextLine();
            } else if (input.equals ("Z") && activeSim == false) {
                input = "";
                scanner.nextLine();
            }
            if (input.equals("X") && (activeSim == true || activeCusSim == true) && genNum > 0) {
                sim = genHist.get(genHist.size() - 1);
                genHist.remove(genHist.size()-1);
                genNum = genNum - 1;
            }
            
            if (input.equals ("A") && (activeSim == true || activeCusSim == true)) {
                System.out.println("define an interval (ms)");
                try {
                    time = scanner.nextInt();
                } catch (InputMismatchException error) {
                    errorInt = true;
                }
                System.out.println("how many generations?");
                if (errorInt != true) {
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
                        errorInt = true;
                        scanner.nextLine();
                    }
                }
            } else if (input.equals ("A") && (activeSim == false || activeCusSim == false)) {
                scanner.nextLine();
            }
        }
    }
    
    public static void main (String[] args) {
        performanceTaskMain conwayGame = new performanceTaskMain();
        conwayGame.runEventLoop();
    }
    
    public void createCustomSimulation(int height, int width) {
        if (width < 0 || height < 0) {
            errorInt = true;
        } else {
           int[][] array2d = new int[height+2][width+2];
           int status;
           for (int a = 0;a < width+2;a++) {
               for (int i = 0;i < height+2;i++) {
                    if (i == 0 || a == 0 || i == height +1 || a == width +1) {
                       array2d[i][a] = 0;
                   } else {
                       status = (int)(Math.random() * 2);
                       array2d[i][a] = status;
                   }
                   
               }
           }
           sim = array2d;
           activeCusSim = true;
           activeSim = false;
        }
    }
    
    public int[][] simNextGen() {
        genHist.add(sim);
        int array2d[][]=new int [999][999];
        if (activeSim == true) {
        int[][] arrayN2d = new int[simSize + 2][simSize +2];
        int status;
        int adjLiving;
        for (int a = 1;a < simSize +1;a++) {
            for (int i = 1;i < simSize +1;i++) {
                adjLiving = sim[i-1][a-1] + sim[i-1][a] + sim[i-1][a+1] + sim[i][a+1] + sim[i][a-1] + sim[i+1][a-1] + sim[i+1][a] + sim[i+1][a+1];
                if (sim[i][a] == 0) {
                    if (adjLiving == 3) {
                        arrayN2d[i][a] = 1;
                    }
                } else if (adjLiving == 2 || adjLiving == 3) {
                    arrayN2d[i][a] = 1;
                } else {
                    arrayN2d[i][a] = 0;
                }
            }
        }
        array2d = arrayN2d;
    }
    if (activeCusSim == true) {
        int[][] arrayM2d = new int[h + 2][w +2];
        int status;
        int adjLiving;
        for (int a = 1;a < w +1;a++) {
            for (int i = 1;i < h +1;i++) {
                adjLiving = sim[i-1][a-1] + sim[i-1][a] + sim[i-1][a+1] + sim[i][a+1] + sim[i][a-1] + sim[i+1][a-1] + sim[i+1][a] + sim[i+1][a+1];
                if (sim[i][a] == 0) {
                    if (adjLiving == 3) {
                        arrayM2d[i][a] = 1;
                    }
                } else if (adjLiving == 2 || adjLiving == 3) {
                    arrayM2d[i][a] = 1;
                } else {
                    arrayM2d[i][a] = 0;
                }
            }
        }
        array2d = arrayM2d;
    }
    return array2d;
    }
    
    public void printSim() {
        //this is the method for printing the conway simulation game itself
        if (activeSim == true) {
        for (int a = 0;a < simSize +2;a++) {
            for (int i = 0;i < simSize+2;i++) {
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
        if (activeCusSim == true) {
            for (int a = 0;a < w +2;a++) {
            for (int i = 0;i < h+2;i++) {
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
}