package com.example.hello.systemreactionratecalculator;

/**
 * A class that is used to store information about an item type.
 * Example: Name - Iron, Input - 4 per unit time.
 */
public class Item {
    private String name;
    private int inputRate;
    private int consumptionRate;
    private boolean infiniteInput;
    //A String sequence used to separate entries in toString()
    public final static String spacer = ":";

    //Parameterized constructor
    public Item(String name, int inputRate, int consumption, boolean infiniteInput) {
        //Use the arguments to initialize instance variables
        setName(name);
        setInputRate(inputRate);
        setConsumptionRate(consumption);
        this.infiniteInput = infiniteInput;
    }

    //Another (second) parameterized constructor
    public Item(String name, int inputRate, boolean infiniteInput) {
        //Call the first parameterized constructor, defaulting consumption to 0
        this(name, inputRate, 0, infiniteInput);
    }

    //Mutator method for name
    public void setName(String newName) {
        //Check if newName is null or is empty. If so, set name to "!Invalid Name!" instead
        if (newName == null || newName.isEmpty()) {
            name = "!Invalid Name!";
            return;
        }
        name = newName;
    }

    //Accessor method for name
    public String getName() {
        return name;
    }

    //Mutator method for inputRate. Only positive values are accepted, as it is the rate of addition
    public void setInputRate(int inputRate) {
        this.inputRate = Math.abs(inputRate);
    }

    //Accessor method for inputRate
    public int getInputRate() {
        //If the input rate is infinite, return -1, as input should never be negative
        //Negative values are used to denote
        if (infiniteInput) {
            return -1;
        }
        return inputRate;
    }

    //Mutator method for consumptionRate. Only positive values are accepted
    public void setConsumptionRate(int consumption) {
        consumptionRate = Math.abs(consumption);
    }

    //Accessor method for consumptionRate
    public int getConsumptionRate() {
        return consumptionRate;
    }

    /**
     * A mutator method for infiniteInput
     */
    public void setInfiniteInput(boolean infinite){
        infiniteInput = infinite;
    }

    //A method that returns a String about the status of the Item
    public String getStats() {
        //A temporary String to save the parts of the String together
        String returnString = "";

        //Get the information about the Item
        // Add the name
        returnString += name + ": ";
        // Add the input rate
        returnString += "+";
        if (infiniteInput) {
            //If the input is infinite, denote that it is infinite
            returnString += "inf";
        } else {
            //Otherwise, put the input rate
            returnString += Integer.toString(getInputRate());
        }
        // Add a separator between input and consumption rate
        returnString += " / ";
        // Add the consumption rate
        returnString += Integer.toString(getConsumptionRate());

        //Return the String
        return returnString;
    }

    //A method that returns a String representation of the object
    /**
     * A method that returns a String representation of the object
     */
    public String toString() {
        //A temporary String to save the parts of the String together
        String returnString = "";

        //Add information about the Item
        returnString += name;
        returnString += spacer + Integer.toString(inputRate);
        returnString += spacer + Integer.toString(consumptionRate);
        returnString += spacer + Boolean.toString(infiniteInput);

        //Return the whole String
        return returnString;
    }
}