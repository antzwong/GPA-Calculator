package com.company;
import java.util.Scanner;
/*
TASKS TO DO

#2. make it so that when # of courses entered is tested,
it is testing if the input is also an INT, NOT just the int value

*/


public class Main {

    public static void main(String[] args) {


        Scanner in = new Scanner (System.in); //declares scanner

        //introduction to program. sets up basic array indexes based off of user input
        System.out.println("Welcome to the GPA Calculator"); //welcomes user to the program

        boolean mainRun = true; //sets up asking if the user wants to run again at the end
        while (mainRun == true) {
            mainRun = false;

            int indexes; //declares indexes until re-declared later on by scanner and user input
            System.out.println("How many courses do you want to calculate? "); //asks user how many courses (indexes)
            indexes = in.nextInt(); //user inputs how many courses. used to create indexes for the arrays


            //checks whether or not a valid number of courses was entered
            while (indexes < 1) {
                System.out.println("That is not a valid number of courses. Please enter at least one course. ");
                indexes = in.nextInt(); //allows the user to re-input the number of courses
            }

            //declares the arrays necessary
            String[] courses = new String[indexes]; //creates array to store the name of the courses
            int[] credits = new int[indexes]; //creates new array to store the number of credit hours per class.
            double[] gpa = new double[indexes]; //creates new array to store the gpa received for the course


            //checks how the user would like to input their grades
            String gradingScale;
            System.out.println("Would you like to input your grades as letters or as GPA points? (letters/points)");
            gradingScale = in.next();
            gradingScale = gradingScale.toLowerCase(); //changes all characters to lower case to avoid case sensitivity

            while (!gradingScale.equals("letters") && !gradingScale.equals("points")) {
                System.out.println("That is not a valid choice. please choose either 'letters' or 'points'");
                gradingScale = in.next();
                gradingScale = gradingScale.toLowerCase();
            }


            if (gradingScale.equals("letters")) { //calculates the GPA from letter grades

                System.out.println("You have chosen to calculate your GPA from letter grades");
                System.out.println("You will now input in this order, the title of the course, " +
                        "how many credits it was worth, and the letter grade received in the course");

                int count = 0; //tracks which index of the array the program is on
                while (count < indexes) {
                    System.out.println("Course " + Integer.toString(count + 1)); //tracks which course the user is on
                    System.out.print("Course Title: "); //advises the user to input the course title
                    courses[count] = in.next();
                    System.out.print("Course Credits: ");
                    credits[count] = Integer.parseInt(in.next());
                    System.out.print("Course GPA: ");
                    gpa[count] = conversion((in.next()).toUpperCase()); //TRACKS GPA THROUGH LETTER GRADES
                    System.out.println(); //spaces out the entries so they're easier to read
                    count++; //iterates through so we aren't in an infinite loop
                }
            } else { //calculates the GPA from GPA points

                System.out.println("You have chosen to calculate your GPA from GPA points");
                System.out.println("You will now input in this order, the title of the course, " +
                        "how many credits it was worth, and the GPA received in the course");

                int count = 0; //tracks which index of the array the program is on
                while (count < indexes) {
                    System.out.println("Course " + Integer.toString(count + 1)); //tracks which course the user is on
                    System.out.print("Course Title: "); //advises the user to input the course title
                    courses[count] = in.next();
                    System.out.print("Course Credits: ");
                    credits[count] = Integer.parseInt(in.next());
                    System.out.print("Course GPA: ");
                    gpa[count] = Double.parseDouble(in.next()); //TRACKS GPA THROUGH POINTS
                    count++; //iterates through so we aren't in an infinite loop
                }
            }

            //outputs all the final data
            int creditTotal = calculateCreditTotal(credits); //calls the method calculateCreditTotal to return the total credits the user has input
            double cumulativeGPA = calculateGPA(credits, gpa); //calls the method calculate GPA to find out what the students raw GPA is
            System.out.println("Your credit total is: " + Integer.toString(creditTotal)); //prints the credit total
            System.out.println("Your total grade points are: " + Double.toString(cumulativeGPA)); //prints the raw gpa
            System.out.println("Your actual GPA is: " + Double.toString(cumulativeGPA / creditTotal)); //prints the actual gpa


            //asks if they would like a final report. all the nice stuff.
            String finalReport;
            System.out.println("Would you like a final report? (y/n)"); //asks user if they want a final report
            finalReport = in.next(); //received user input for whether or not they want a final report
            finalReport = finalReport.toLowerCase();

            //makes sure final report is a legitimate variable
            finalReport = checker(finalReport);



            //runs the final report assuming it was requested
            if (finalReport.equals("y")) {
                report(courses, credits, gpa);
                System.out.println();
                System.out.println("Final Cumulative GPA: " + Double.toString(cumulativeGPA / creditTotal));
            }


            //asks if they would like to know what they need to maintain their GPA to
            System.out.println("Would you like to know what you need to do to raise your GPA? (y/n)");
            String GPARaiser;
            GPARaiser = in.next();
            System.out.println();
            GPARaiser = checker(GPARaiser);


            if (GPARaiser.equals("y")) {
                //tests if the user wants to use the gparaiser again
                boolean runRaiserAgain=true;
                while (runRaiserAgain == true) {
                    runRaiserAgain = false;

                    System.out.println("What GPA would you like to have next semester? ");
                    double goalGPA = Double.parseDouble(in.next());

                    //tests to make sure the goalGPA is a valid value
                    System.out.println();
                    while (goalGPA > 4.0 || goalGPA < 0.0) {
                        System.out.println("That is not a valid GPA value. Please enter a value between 4 and 0 (inclusive)");
                        goalGPA = Double.parseDouble(in.next());
                    }

                    System.out.println("How many credits are you taking next semester? ");
                    int nextCredits = in.nextInt();
                    System.out.println();
                    gradeRaiser(goalGPA, cumulativeGPA / creditTotal, nextCredits, creditTotal);

                    System.out.println("Would you like to test a different GPA? (y/n)");

                    String raiserAgain;
                    raiserAgain = in.next();
                    raiserAgain = raiserAgain.toLowerCase();
                    raiserAgain = checker(raiserAgain);


                    if (raiserAgain.equals("y")) {
                        runRaiserAgain = true;
                    }
                }
            }

            //tests if the user wants to use the gpa calculator again
            System.out.println("Would you like to use the GPA calculator again? (y/n) ");

            String again;
            again = in.next();
            again.toLowerCase();
            again = checker(again);


            if (again.equals("y")) { //if user says to use it again, changes run to true so the while loop at the top executes.
                mainRun = true;
            }


        }
        System.out.println();
        System.out.println("Thank you for using the GPA Calculator!");
        System.out.println();



    }

    //checks to make sure strings are either 'y' or 'n'
    public static String checker (String input) {
        Scanner check = new Scanner (System.in);
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("That is not a valid input. Please use 'y' or 'n'");
            input = check.next();
            input = input.toLowerCase();
        }
        return input;
    }

        private static void gradeRaiser (double goalGPA, double currentGPA, int nextCredits, int currentCredits){
            double averageGPA = (goalGPA * (currentCredits + nextCredits) - (currentGPA * currentCredits)) / nextCredits;
            if (averageGPA > 4.0) {
                double bestCase = (4.0 * (nextCredits) + (currentGPA * currentCredits)) / (nextCredits + currentCredits);
                System.out.println("You will need more than a 4.0 next semester to reach your goal GPA.");
                System.out.println("Your best case scenario, assuming a 4.0 next semester is a cumulative "
                        + Double.toString(bestCase) + " GPA. ");
            } else {
                System.out.println("You will need to maintain an average GPA of " + Double.toString(averageGPA)
                        + " next semester to get a GPA of " + Double.toString(goalGPA));
            }
        }



    private static void report (String[] courses, int[] credits, double[] GPA) {
        for(int i=0; i<courses.length; i++) {
            System.out.println("Course Title: " + courses[i]);
            System.out.println("Course Credit Value: " + credits[i]);
            System.out.println("Course GPA: " + GPA[i]);
            System.out.println();
        }
    }

    private static int calculateCreditTotal(int[] credits) {
        int creditTotal=0;
        for (int i=0; i<credits.length; i++) {
            creditTotal+=credits[i];
        }
        return creditTotal;
    }

    private static double calculateGPA(int[] credits, double[] gpa) { //calculates cumulative gpa (sum of all GPA*credits)
        double cumulative=0; //sets initial cumulative gpa to 0
        for (int i=0; i<credits.length; i++) { //runs through the length of the array
            cumulative += credits[i] * gpa[i]; //adds on the product of credit and gpa to the cumulative title
        }

        return cumulative;
    }

    private static double conversion(String grade) {
        double gpa; //sets the gpa value that's returned as gpa

        switch(grade) {
            case "A": gpa = 4.0;
            break;

            case "A-": gpa = 3.7;
            break;

            case "B+": gpa = 3.3;
            break;

            case "B": gpa = 3.0;
            break;

            case "B-": gpa = 2.7;
            break;

            case "C+": gpa = 2.3;
            break;

            case "C": gpa = 2.0;
            break;

            case "C-": gpa = 1.7;
            break;

            case "D+": gpa = 1.3;
            break;

            case "D": gpa = 1.0;
            break;

            case "F": gpa = 0.0;
            break;

            default: gpa = 0.0;
            break;
        }

        return gpa;
    }



}
