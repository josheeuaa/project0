import java.util.InputMismatchException;
import java.util.Scanner;

public class RestaurantCheckProject {
        //Gets the input in the form of a double from the user
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

        //Gets the input in the form of an integer from the user
        private static int getIntegerInput(Scanner scnr, String prompt) {
            while (true) {
                System.out.print(prompt);
                try {
                    int tempInt = scnr.nextInt();
                    if (tempInt < 0) {
                        System.out.println("Please enter a non-negative number.");
                        continue;
                    }

                    return tempInt; 
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
                System.out.println("Total sale so far: " + grandSale);
                System.out.println("Total pooled tip so far: " + grandTip);
    
                System.out.print("Do you wish to stop? (y/n): ");
                userInput = scnr.next();
                System.out.println();
                    if (userInput.equalsIgnoreCase("y")) {
                        break;
                    }
            }
    
            System.out.println("The total sale amount is: " + grandSale);
            System.out.println("The total pooled tip amount is: " + grandTip);
            System.out.println();
            
            while (true) {
                System.out.print("Do you wish to see how much each employee gets? (y/n): ");
                userInput = scnr.next();
                    if (userInput.equalsIgnoreCase("n")) {
                        System.out.println("Okay have a nice day.");
                        break;
                    }
                    
                int typesofworkers = getIntegerInput(scnr, "How many types of positions do you have? ");
                System.out.println();
                double percentTipTotal = 0;
                Workers[] workers = new Workers[typesofworkers];
                
                //Stores different types of positions including the number of workers and the percentage tip
                for (int i = 0; i < typesofworkers; i++) {
                    String tempString;
                    int tempInt;
                    double tempDouble;

                    System.out.print("Position (i.e. Server, Chef, Host): ");
                    tempString = scnr.next();

                    tempInt = getIntegerInput(scnr, "Number of Workers in Position (i.e. 2, 5, 10): ");

                    while(true){
                        try {
                            System.out.print("Percentage Tip For Position (i.e. 10, 25.4, 4; MUST EQUAL 100% AT THE END): ");
                            tempDouble = scnr.nextDouble();

                            if (tempDouble < 0) {
                                System.out.println("Please enter a non-negative number.");
                                continue;
                            }

                            if(tempDouble + percentTipTotal > 100) {
                                System.out.println("Total percentage is more than 100%. Please try again.");
                                continue;
                            }

                            percentTipTotal = tempDouble + percentTipTotal;
                            System.out.printf("Total Percentage of Tips so Far: %.2f", percentTipTotal);
                            System.out.println("%");
                            break;
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid format. Try again.");
                            scnr.next();  
                        }
                    }

                    workers[i] = new Workers(tempString, tempInt, tempDouble);
                    System.out.println();
                }
            if (percentTipTotal != 100) {
                System.out.printf("Total percent is %.2f%%. It must equal 100%%. Please re-enter all values.\n", percentTipTotal);
            }
            else {
                for (int i = 0; i < typesofworkers; i++) {
                    System.out.printf("The total amount of tips is: %.2f\n", grandTip);
                    double workertip = (grandTip * (workers[i].getTip()/100))/workers[i].getAmount();
                    System.out.printf("Each " + workers[i].getName() + " gets: $%.2f\n", workertip);
                }
                break;
            }
        }
    }
}
