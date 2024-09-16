public class Person {
    // Private fields to store person information
    private String firstName;
    private String sureName;
    private String email;

    // Default constructor
    public Person() {
    }

    // Parameterized constructor to initialize person with first name, surname, and email
    public Person(String firstName, String sureName, String email) {
        this.setFirstName(firstName);
        this.setSureName(sureName);
        this.setEmail(email);
    }


    public String getFirstName() {
        return firstName;
    } // Getter method for first name

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } // Setter method for first name

    public String getSureName() {
        return sureName;
    } // Getter method for sure name

    public void setSureName(String sureName) {
        this.sureName = sureName;
    } // Setter method for sure name

    public String getEmail() {
        return email;
    } // Getter method for email

    public void setEmail(String email) {
        this.email = email;
    } // Setter method for email
}
