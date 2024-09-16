//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.InputMismatchException;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PlaneManagement {
    // This is the main method where the program execution starts
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Creating a Scanner object to read user input from the console

        // Creating an array to hold Ticket objects, with a size of 52
        Ticket[] ticketArray = new Ticket[52];

        boolean flag1 = true;  // Setting a boolean flag to control the main loop

        // Main loop of the program
        while (flag1){

            userMenu();// Calling the userMenu method to display the menu options to the user

            boolean flag = true;// Setting another boolean flag to control the input validation loop

            int selection = 0;// Variable to store the user's selection

            while (flag) {
                try {
                    System.out.println("select your option:");
                    String inputStr = input.nextLine(); // Read the input as a string

                    if (inputStr.trim().isEmpty()) { // Check if the input contains only whitespace
                        throw new InputMismatchException("Empty or whitespace input"); // Throwing an exception if the input is empty or whitespace
                    }

                    selection = Integer.parseInt(inputStr); // Convert input string to integer

                    if (selection >= 0 && selection <= 6) {// Converting the input string to an integer
                        flag = false; // Setting the flag to exit the input validation loop
                    } else {
                        System.out.println("Invalid option. Please enter a number between 0 and 6");
                    }
                } catch (InputMismatchException e) {  // Catching an exception if the input is not a number
                    System.out.println("Invalid input. Please enter a number.");
                } catch (NumberFormatException e) { // Catching an exception if the input format is invalid for conversion to integer
                    System.out.println("Invalid input format. Please enter a valid number.");
                }
            }

            switch (selection) {
                case 1:
                    buySeat_validations(input, ticketArray);
                    break;
                case 2:
                    cancel_seat(input,ticketArray);
                    break;
                case 3:
                    find_first_available_seat(ticketArray);
                    break;
                case 4:
                    show_seating_plan(ticketArray);
                    break;
                case 5:
                    print_ticket_info_total(ticketArray);
                    break;
                case 6:
                    searchTicket_by(input,ticketArray);
                    break;
                case 0:
                    flag1 = false;
                    break;
                default:
                    System.out.println("wrong input");
            }

        }

    }




    private static boolean isValidEmail(String email) {
        // Check if the email contains "@" and "."
        return !email.contains("@") || !email.contains(".") ||
                // Ensure "@" comes before "."
                email.indexOf('@') >= email.lastIndexOf('.');
    }

    private static void searchTicket_by(Scanner input, Ticket[] tickets) {
        // Method to search for a ticket by seat row and number


        String seatRow;// Variable to store the seat row letter
        int seatNumber = 0;// Variable to store the seat number

        do {// Prompt the user to enter the seat row and number, validating the inputs
            seatRow = get_seatRow(input);

            System.out.println("Enter seat number:");
            String seatNumberStr = input.nextLine().trim(); // Read the input as a string and trim leading/trailing whitespace

            if (seatNumberStr.isEmpty()) { // Check if the trimmed input is empty
                System.out.println("Seat number cannot be empty. Please enter a valid seat number.");
                continue; // Prompt the user again
            }

            try {
                seatNumber = Integer.parseInt(seatNumberStr); // Convert input string to integer

                if (seatNumber <= 0) {
                    System.out.println("Seat number must be greater than 0.");
                    continue; // Prompt the user again
                }

                if (check_seatRow_seatNumber(seatRow, seatNumber)) {
                    System.out.println("Invalid seat number for the given seat row. Please enter a valid seat number.");
                    continue; // Prompt the user again
                }

                break; // Exit the loop if input is valid
            } catch (NumberFormatException e) {//This catch block is triggered if the conversion of the user input from
                // a string to an integer (Integer.parseInt()) fails due to an invalid format
                System.out.println("Invalid input format. Please enter a valid number.");
            }
        } while (true);// this do-while loop ensures that the code inside the loop executes at least once and
        // then continues to execute repeatedly until a valid input is provided by the user and
        // the break statement is encountered or until the loop is exited by an exception or a return statement

        System.out.println("Seat number valid");
        // Search for the ticket corresponding to the entered seat row and number
        int z = search_ticket(seatRow, seatNumber, tickets);

        if (z != -1) {  // If the ticket is found, print its information
            System.out.println("seat row is: "+tickets[z].getSeatRow());
            System.out.println("seat number is: "+tickets[z].getSeatNumber());
            System.out.println("person details: "+tickets[z].getPerson().getFirstName() +" "+tickets[z].getPerson().getSureName());
            System.out.println("person email: "+tickets[z].getPerson().getEmail());
            System.out.printf("seat price: $"+tickets[z].getPrice());
            System.out.println();
        } else {
            System.out.println("Seat is not booked");
        } // If no ticket is found, indicate that the seat is not booked
    }

    private static void print_ticket_info_total(Ticket[] tickets) {
        // Method to print ticket information and total price


        double total=0;
        for (Ticket ticket:tickets){// Check if the current ticket is not null
            // Print ticket information: seat row, seat number, passenger's first name, and ticket price
            if(ticket != null){
                System.out.println("seat row is: "+ticket.getSeatRow());
                System.out.println("seat number is: "+ticket.getSeatNumber());
                System.out.println("person details: "+ticket.getPerson().getFirstName()+" "+ticket.getPerson().getSureName());
                System.out.println("person email: "+ticket.getPerson().getEmail());
                System.out.println("ticket price : $"+ticket.getPrice());
                System.out.println();
                // Add the ticket price to the total
                total=total + ticket.getPrice();
            }
        }// Add the ticket price to the total
        System.out.println("total is:$"+total);
    }

    private static void show_seating_plan(Ticket[] tickets) {
        // Method to show the seating plan based on the booked tickets

        String[] rows = {"A","B","C","D"}; // Define an array containing the rows of seats

        for(String row:rows){// Iterate through each row of seats
            if (row.equals("A") || row.equals("D")){// Check if the row is in the first or last section (rows "A" and "D")
                for (int i = 0; i < 14; i++) { // For rows A and D, iterate through 14 seats
                    int y = search_ticket(row,i+1,tickets); // Search for a ticket in the current row and seat number

                    if (y == -1){ // If no ticket is found, print "O" to represent an available seat; otherwise, print "X".value returning from search_ticket
                        System.out.print(" O ");
                    }else {
                        System.out.print(" X ");
                    }

                }
                System.out.println();
            }else{
                for (int i = 0; i < 12; i++) {
                    int y = search_ticket(row,i+1,tickets);

                    if (y == -1){// If no ticket is found, print "O" to represent an available seat; otherwise, print "X".value returning from search_ticket
                        System.out.print(" O ");

                    }else {
                        System.out.print(" X ");
                    }
                }
                System.out.println();// Move to the next line after printing all seats in the row
            }
        }
    }

    private static void find_first_available_seat(Ticket[] tickets) {// Method to find the first available seat in the venue
        String[] rows = {"A","B","C","D"}; // Define an array containing the rows of seats

        for(String row:rows){ // Iterate through each row of seats
            if (row.equals("A") || row.equals("D")){// Check if the row is in the first or last section
                for (int i = 0; i < 14; i++) {// For rows A and D, iterate through 14 seats

                    int y = search_ticket(row,i+1,tickets);  // Search for a ticket in the current row and seat number

                    if (y == -1){// If no ticket is found, this seat is available. this value is returning from the search_ticket methode
                        System.out.println("first available seat is "+row+(i+1));
                        return; //exit method
                    }

                }
            }else{
                for (int i = 0; i < 12; i++) { // For rows B and C, iterate through 12 seats
                    int y = search_ticket(row,i+1,tickets); // For rows B and C, iterate through 12 seats

                    if (y == -1){// If no ticket is found, this seat is available. this value is returning from the search_ticket methode
                        System.out.println("first available seat is "+row+(i+1));
                        return;//exit method
                    }

                }
            }
        }
        System.out.println("no seat available at the moment:");   // If no available seat is found, print a message indicating so
    }

    private static void cancel_seat(Scanner input, Ticket[] tickets) {
        // Method to cancel a seat booking

        String seatRow;
        int seatNumber = 0; // Initialize seatNumber to 0

        do {
            seatRow = get_seatRow(input);// Prompt the user to enter the seat row letter and validate it

            System.out.println("Enter seat number:");

            String inputStr = input.nextLine().trim(); // Read the input as a string and trim leading/trailing whitespace

            if (inputStr.isEmpty()) { // Check if the trimmed input is empty
                System.out.println("Seat number cannot be empty. Please enter a valid seat number.");
                continue; // Prompt the user again
            }

            try {
                seatNumber = Integer.parseInt(inputStr); // Convert input string to integer

                if (seatNumber <= 0) {
                    System.out.println("Seat number must be greater than 0.");
                    continue; // Prompt the user again
                }

                if (check_seatRow_seatNumber(seatRow, seatNumber)) {  // Check if the seat row and number combination is valid
                    System.out.println("Invalid seat number for the given seat row. Please enter a valid seat number.");
                    continue; // Prompt the user again
                }

                break; // Exit the loop if input is valid
            } catch (NumberFormatException e) {//This catch block is triggered if the conversion of the user input from
                // a string to an integer (Integer.parseInt()) fails due to an invalid format
                System.out.println("Invalid input format. Please enter a valid number.");
            }
        } while (true);

        System.out.println("Seat number valid");

        int x = search_ticket(seatRow, seatNumber, tickets);// Search for the ticket corresponding to the entered seat row and number

        if (x != -1) {   // If the ticket is found, cancel it by setting it to null
            tickets[x] = null;// what's happening is that it's setting the value at the index x in the tickets array to null to cancel the seat that we have booked
            System.out.println("Cancelled");
        } else {
            System.out.println("Seat is not already booked");
        }
    }

    private static void buySeat_validations(Scanner input, Ticket[] ticketArray){
        // Method to facilitate the purchase of a seat, gathering user details and validating input

        String seatRow; // Variable to store the seat row
        int seatNumber; // Variable to store the seat number

        do {
            seatRow = get_seatRow(input); // Prompt the user to enter the seat row letter and validate it
            seatNumber = 0;// Initialize seat number to 0
            try {
                System.out.println("Enter seat number:");
                String inputStr = input.nextLine().trim(); // Read the input as a string and trim leading/trailing whitespace

                if (inputStr.isEmpty()) { // Check if the trimmed input is empty
                    System.out.println("Seat number cannot be empty. Please enter a valid seat number.");
                    continue; // Prompt the user again
                }

                seatNumber = Integer.parseInt(inputStr); // Convert input string to integer

                if (seatNumber <= 0) {
                    System.out.println("Seat number must be greater than 0.");
                    continue; // Check if the seat number is less than or equal to 0 and Prompt the user again
                }
            } catch (NumberFormatException e) {// Handle the case where input is not in a valid number format
                System.out.println("Invalid input format. Please enter a valid number.");
                continue; // Prompt the user again
            }
        } while (check_seatRow_seatNumber(seatRow, seatNumber)); // Repeat the loop until a valid combination is entered

        System.out.println("Seat number valid");

            // checking whether the input seat row and seat number is already booked or not
        int existingTicketIndex = search_ticket(seatRow, seatNumber, ticketArray);
        if (existingTicketIndex != -1) {
            System.out.println("Sorry, the seat is already booked. Please select a different seat.");
            return; // Return without booking the seat
        }


        // // Prompt the user to enter their first name, last name  ensure it is not empty
        String firstName = "";
        while (firstName.isEmpty()) {
            System.out.println("Enter your first name:");
            firstName = input.nextLine();
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty.");// Display an error message if the first name is empty
            }
        }

        String sureName = "";
        while (sureName.isEmpty()) {
            System.out.println("Enter your sure name:");
            sureName = input.nextLine();
            if (sureName.isEmpty()) {
                System.out.println("Sure name cannot be empty.");// Display an error message if the sure name is empty
            }
        }
        // Prompt the user to enter their email and ensure it is not empty and in a valid format
        String email = "";
        while (isValidEmail(email)) {
            System.out.println("Enter your email:");
            email = input.nextLine();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty.");// Display an error message if the email is empty
            } else if (isValidEmail(email)) {
                // Display an error message if the email format is invalid
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }
        // Call buy_seat method with validated user details
        buy_seat(firstName, sureName, email, seatRow, seatNumber, ticketArray);
    }

    private static void buy_seat(String fistName,String sureName ,String email, String seatRow,int seatNumber,Ticket[] ticketArray){
        // Method to buy a seat and book it with the provided details

        int x = 0;// Initialize a counter to keep track of the index

        // Iterate through each ticket in the ticketArray
        for (Ticket ticketNew:ticketArray){
            if(ticketNew != null){// Check if the current ticket is not null
                x++;// Increment the counter
            }else{
                // If a null ticket is found, create a new Ticket object with the provided details
                // and assign it to the current index in the ticketArray
                ticketArray[x] = new Ticket(seatRow,seatNumber,new Person(fistName,sureName,email));
                return;
            }// Exit the method after booking the seat
        }
    }

    private static String get_seatRow(Scanner input){
        // Method to get a valid seat row letter from the user input

        // Loop continuously until a valid seat row is entered
        while (true) {
            System.out.println("Enter seat row letter:");
            String seatRow = input.nextLine().trim(); // Read the input as a string and trim leading/trailing whitespace

            if (seatRow.isEmpty()) { // Check if the input is empty
                System.out.println("Seat row cannot be empty. Please enter a valid seat row letter (A, B, C, or D).");
                continue; // Prompt the user again by continuing to the next iteration of the loop
            }

            if (seatRow.equals("A") || seatRow.equals("B") || seatRow.equals("C") || seatRow.equals("D")) {
                // If the input is valid, return the seat row letter
                return seatRow;
            } else {
                System.out.println("Invalid seat row. Please enter only A, B, C, or D for seat row.");
            }
        }
    }

    private static int search_ticket(String seatRow, int seatNumber, Ticket[]ticketArray){
        // Method to search for a ticket with a specific seat row and number within an array of tickets

        // Initializes a variable to keep track of the current index being checked
        int index = 0;
        // Iterate through each ticket in the ticketArray
        for(Ticket ticket:ticketArray){
            // Check if the current ticket is not null to avoid NullPointerException
            if (ticket != null){
                // Compare the seat row and seat number of the current ticket with the provided seatRow and seatNumber
                if (ticket.getSeatRow().equals(seatRow ) && ticket.getSeatNumber() == seatNumber){
                    // If the seat row and seat number match, return the current index
                    return index;
                }
            } // Increment the index to check the next ticket
            index ++;
        }// If the loop completes without finding a matching ticket, return -1
        return -1;
    }

    private static boolean check_seatRow_seatNumber(String seatRow, int seatNumber) {// This method checks if the given seat row and seat number are valid

        if (seatRow.equals("A") || seatRow.equals("D")){
            // If seat row is "A" or "D", check if seat number is between 1 and 14
            if (seatNumber<=14 && seatNumber>0){
                // If seat number is within the valid range, return false
                return false;
            }else {
                System.out.println("enter a number between 1-14. please enter your row letter and seat number again");
                // Return true to indicate that the seat row and seat number are not valid
                return true;
            }
        }else if(seatRow.equals("B")  || seatRow.equals("C")){
            // If seat row is "B" or "C", check if seat number is between 1 and 12
            if (seatNumber<=12 && seatNumber>0){
                // If seat number is within the valid range, return false
                return false;
            }else {
                System.out.println("enter a number between 1-12 please enter your row letter and seat number again");
                // Return true to indicate that the seat row and seat number are not valid
                return true;
            }
        }else{
            // If the seat row is neither "A", "B", "C", nor "D", return true
            // This indicates that the seat row is invalid
            return true;
        }
    }

    private static void userMenu(){
        System.out.println();
        System.out.println("WELCOME TO THE PLANE MANAGEMENT APPLICATION");
        System.out.println("*********************************");
        System.out.println("*              MENU             *");
        System.out.println("*********************************");
        System.out.println("   1) BUY SEAT");
        System.out.println("   2) CANCEL SEAT");
        System.out.println("   3) FIND FIRST AVAILABLE SEAT");
        System.out.println("   4) SHOW SEAT PLAN");
        System.out.println("   5) PRINT TICKET INFORMATION AND TOTAL SALES");
        System.out.println("   6) SEARCH TICKET");
        System.out.println("   0) QUITE");
        System.out.println("*********************************");
        System.out.println();
    }
}
