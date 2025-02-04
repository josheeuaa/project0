import java.util.InputMismatchException;
import java.util.Scanner;

public class OriginalRestaurantCheckProject {
    //Gets the input in the form of a non-negative double from the user
    private static double getDoubleInput(Scanner scnr, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double tempDouble = scnr.nextDouble();
                if (tempDouble < 0) {
                    System.out.println("Please enter a non-negative number.");
                    continue;
                }

                return tempDouble; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid format. Try again.");
                scnr.next();  
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        double saleAmount;
        double tip;
        double totalAmount;
        int checkCount = 0;
        double grandSale = 0;
        double grandTip = 0;
        double grandAmount = 0;
        String userInput;
        
        System.out.println("Restaurant Check Manager\n");

        while (true) {
            // sale amount
            saleAmount = getDoubleInput(scnr, "Please enter the sale amount. (ex. 20.3, 4.5, 24.8...): ");

            // tip
            tip = getDoubleInput(scnr, "Please enter the tip amount. (ex. 20.3, 4.5, 24.8...): ");

            // total amount
            totalAmount = getDoubleInput(scnr, "Please enter the total amount. (ex. 20.3, 4.5, 24.8...): ");

            // edge cases where customers write total but not tip AND when customers write tip and total the same
            if (tip == 0.0 || tip == totalAmount) {
                tip = totalAmount - saleAmount;
            }
            
            // edge case where total is left blank
            if (totalAmount == 0.0) {
                totalAmount = saleAmount + tip;
            }

            // edge case when everything is blank 
            if (saleAmount == 0.0 && tip == 0.0 && totalAmount == 0.0) {
                tip = 0.0;
            }
            
            // case when things dont add up
            if (tip + saleAmount != totalAmount) {
                tip = totalAmount - saleAmount;
                if (totalAmount - saleAmount < 0){ 
                    tip = 0.0;
                }
            }

            // case when total is put as less than the sale
            if (totalAmount < saleAmount) {
                tip = 0.0;
                totalAmount = saleAmount;
            }

            grandSale += saleAmount;
            grandTip += tip;
            grandAmount += totalAmount;
            ++checkCount;

            System.out.println();
            System.out.println("Check count: " + checkCount);
            System.out.printf("Total sale so far: $%.2f\n", grandSale);
            System.out.printf("Total pooled tip so far: $%.2f\n", grandTip);

            System.out.print("Do you wish to stop? (y/n): ");
            userInput = scnr.next();
            System.out.println();
                if (userInput.equalsIgnoreCase("y")) {
                    break;
                }
        }

        System.out.printf("The total sale amount is: $%.2f\n", grandSale);
        System.out.printf("The total pooled tip amount is: $%.2f\n", grandTip);
        System.out.println();
        
        while (true) {

            System.out.print("Do you wish to see how much each employee gets? (y/n): ");
            userInput = scnr.next();
                if (userInput.equalsIgnoreCase("n")) {
                    System.out.println("Okay have a nice day.");
                    break;
                }
    
            //Get the amount of workers
            String[] workers = {"servers", "chefs", "sous-chefs", "kitchen aids", "hosts", "bussers"};
            int[] workeramounts = new int[6];
            for (int i = 0; i < 6; i++) {
                while (true) {
                    try {
                        System.out.println("How many " + workers[i] + " worked today?");
                        workeramounts[i] = scnr.nextInt();

                        if (workeramounts[i] < 0) {
                            System.out.println("Please enter a non-negative number.");
                            continue;
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Please use whole numbers. Try again.");
                        scnr.next(); 
                    }
                }
            }
            
            System.out.println();
            
            //Calculates and prints tips for each worker
            String[] worker = {"server","chef", "sous-chef", "kitchen aid", "host", "busser"};
            double[] workerpercentages = {.5, .15, .09, .06, .1, .1};
            System.out.printf("The total pooled tip amount is: $%.2f\n", grandTip);
            for(int i = 0; i<6; i++) {
                double workertip;
                if (workeramounts[i] == 0) {
                    continue;
                }
                else {
                    workertip = (grandTip * workerpercentages[i])/workeramounts[i];
                    System.out.printf("Each " + worker[i] + " gets: $%.2f", workertip);
                    System.out.println();
                }
            }

            break;
        }
    }
}