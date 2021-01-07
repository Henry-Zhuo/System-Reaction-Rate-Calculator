package com.example.hello.systemreactionratecalculator;

/**
 * A class that is used to store information about an item type.
 * Example: Name - Iron, Input - 4 per unit time.
 *
 * Changes:
 *     Sunday May 26 2019 12:46:53 pm
 *             Created this class.
 *         8:55:31 pm - :02:35 pm
 *             Added parameterized constructor
 *             Added String final name, int inputRate\
 *             Added setInputRate(inputRate)
 *         9:04:13 pm - 9:06:36 pm
 *             Added instance variable private boolean infiniteInput
 *             Added getInputRate()
 *             Set infiniteInput in parameterized constructor
 *     Monday May 27 2019 12:09:21 pm - :13:24 pm
 *             Changed getInputRate to return -1 if infiniteInput is true
 *         2:09:26 pm - :14:37 pm
 *             Added toString()
 *             Added SPACER instance variable
 *         2:19:25 pm - :46:48 pm
 *             Added consumptionRate instance variable
 *             Changed parameterized constructor to accept int consumption, and call
 *                 setConsumptionRate(consumption)
 *             Added setConsumptionRate() and getConsumptionRate()
 *             Changed String name to not be a final instance variable
 *             Changed parameterized constructor to call setName() instead of setting name
 *             Added setName() and getName()
 *             Added getStats()
 *         9:35:15 pm - :42:12 pm
 *             Added a second parameterized constructor, that does not require int consumption
 *             Changed getStats() to include "+" before input rate
 *     Tuesday May 28 2019 2:12:47 am - :14:26 am
 *             Added setInfiniteInput()
 *         4:02:37 am - :05:08 am
 *             Changed toString() to also include int consumptionRate instance variables
 *
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
     *
     * Changes:
     *     Tuesday May 28 2019 2:13:25 am - :14:18 am
     *             for original code
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
     *
     * Tuesday May 28 2019 4:04:07 am - :04:59 am
     *         Added code to save int consumptionRate
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