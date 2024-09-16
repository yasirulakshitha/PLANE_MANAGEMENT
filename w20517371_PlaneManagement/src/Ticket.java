import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    // Private fields to store ticket information
    private String seatRow;
    private Integer seatNumber;
    private double price;
    private Person person;

    // Default constructor
    public Ticket() {
    }

    // Parameterized constructor to initialize ticket with seat information and person details
    public Ticket(String seatRow, Integer seatNumber, Person person) {
        this.setSeatRow(seatRow);
        this.setSeatNumber(seatNumber);
        this.setPrice();// Calculate price based on seat number
        this.setPerson(person);
        print_ticket();// Print ticket information to a file
    }


    public String getSeatRow() {
        return seatRow;
    }  // Getter method for seat row

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    } // Setter method for seat row

    public Integer getSeatNumber() {
        return seatNumber;
    } // getter method for seat number

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    } // Setter method for seat number

    public double getPrice() {
        return price;
    } // getter method for price

    public void setPrice() {// Method to calculate ticket price based on seat number

        if (this.seatNumber <= 5){
            this.price = 200;
        } else if (this.seatNumber <= 9){
            this.price = 150;
        }else{
            this.price = 180;
        }

    }

    public Person getPerson() {
        return person;
    }// Getter method for person details

    public void setPerson(Person person) {
        this.person = person;
    }// setter method for person details

    public void print_ticket(){// Method to print ticket information to a file

        try {
            FileWriter myWriter = new FileWriter(seatRow+seatNumber+".txt");
            myWriter.write("seat row and seat number: " + this.seatRow + this.getSeatNumber()+"\n");
            myWriter.write("person details: " +this.getPerson().getFirstName()+" "+this.getPerson().getSureName() +"\n");
            myWriter.write("email: "+this.getPerson().getEmail()+"\n");
            myWriter.write("seat price: $"+this.price+"\n");

            myWriter.write(" ");
            System.out.println("ticket successfully written to file");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("error writing ticket to file");
        }

    }

}

