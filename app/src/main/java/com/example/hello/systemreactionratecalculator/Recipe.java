package com.example.hello.systemreactionratecalculator;

import java.util.LinkedList;

/**
 * A class to remember the Items and recipe
 *
 * Monday May 27 2019 12:14:02 pm
 *         Created this class
 *     12:15:00 pm - :25:19 pm
 *         Added instance variables LinkedList items, and int processingRate
 *         Added parameterized constructor, addItems() and setRate().
 *     1:39:14 pm - :44:10 pm
 *         Added sanity checking to addItems
 *     1:50:17 pm - :09:17 pm
 *         Added toString() method
 *         Added boolean infiniteRate instance variable
 *         Initialized infiniteRate in parameterized constructor
 *         Added setInfiniteRate()
 *         Added hasInfiniteRate()
 *         Added String spacer instance variable
 *     2:14:51 pm - :19:10 pm
 *         Changed String spacer to be a static instance variable
 *     2:53:05 pm - :53:22 pm
 *         Renamed (refactored) addItems() to addItems()
 *     11:27:13 pm - :27:54 pm
 *         Added argumentless constructor
 * Tuesday May 28 2019 12:15:50 am - 3:27:10 am
 *         Added Item input1, Item input2, Item output1, and Item output2
 *         Added setInput1(), getInput1(), setInput2(), getInput2(), setOutput1(), getOutput1(),
 *             setOutput2() and getOutput2()
 *         Added calculateRecipe()
 *         Added getRate()
 *         Added int input1Amount, int input2Amount, int output1Amount, and int output2Amount
 *             instance variables
 *         Added makeTempItem()
 *         Added calculateRecipe(), determineMaxProcessingRate()
 *             and determineLimitedProcessingRate()
 *         Added findItem()
 *         Added setInput1Amount(), setInput2Amount(), setOutput1Amount() and setOutput2Amount()
 *     3:43:12 am - :48:20 am
 *         Changed toString() to also include Item input1, Item input2, Item output1, Item output2,
 *             int input1Amount, int input2Amount, int output1Amount, and int output2Amount
 *             instance variables
 *     3:44:23 pm - :46:22 pm
 *         Changed parameterized constructor to initialize Item input1, Item input2, Item output1,
 *             and Item output2.
 *     9:18:10 pm - :24:46 pm
 *         Added String PLACE_HOLDER_ITEM_NAME instance variable
 *         Changed parameterized constructor to create only one placeholder Item and assign it as
 *             the inputs and outputs
 *         Changed parameterized constructor to assign default values of 0 to input amounts and
 *             output amounts
 *         Renamed (refactored) String spacer to String SPACER
 *     10:16:25 pm - :21:56
 *         Added getItems()
 * Wednesday May 29 2019 1:13:26 am - :15:19 am
 *         Changed getItems() to use items.removeFirst() instead of items.getFirst(), as the intent
 *             was to move the first item to the back, not copy the first item the back
 *     3:43:39 am - :46:02 am
 *         Added getInput1Amount(), getInput2Amount(), getOutput1Amount(), and getOutput2Amount()
 *
 *
 *
 *
 */
public class Recipe {
    private LinkedList<Item> items;
    private int processingRate;
    private boolean infiniteRate;
    private Item input1;
    private Item input2;
    private Item output1;
    private Item output2;
    //"Amount" in these variable names refer something similar to stoichometric ratios
    private int input1Amount;
    private int input2Amount;
    private int output1Amount;
    private int output2Amount;

    //Static variables
    //A String sequence used to separate entries in toString()
    public final static String SPACER = ";";
    //A String used as the name of temporary/placeholder Items
    public final static String PLACE_HOLDER_ITEM_NAME = "temp";


    //Argumentless constructor
    public Recipe() {
        this(0, false);
    }

    //Parameterized constructor
    public Recipe(int rate, boolean infinite, Item... itemInputs ) {
        //Initialize temporary items for input and output
        Item temp = makeTempItem();
        setInput1(temp);
        setInput2(temp);
        setOutput1(temp);
        setOutput2(temp);
        //Initialize the amounts  for inputs and outputs
        setInput1Amount(0);
        setInput2Amount(0);
        setOutput1Amount(0);
        setOutput2Amount(0);
        //Initialize the linked list
        items = new LinkedList<>();
        //Use the arguments
        setRate(rate);
        setInfiniteRate(infinite);
        addItems(itemInputs);
    }

    //Method to add Items to the Items LinkedList
    public void addItems(Item... itemInputs) {
        //If no Items are given, then don't try to add nothing to the LinkedList
        if (itemInputs == null) {
            return;
        }
        for (Item i : itemInputs) {
            //Check if the Item referenced is null. If it is null, then don't add it
            if (i == null) {
                continue;
            }
            items.add(i);
        }
    }

    /**
     * An accessor that returns all Items in items
     *
     * Changes:
     *     Tuesday May 28 2019 10:17:30 pm - :21:48 pm for original code
     *     Wednesday May 29 2019 1:14:30 am - :15:12 am
     *             Changed to use items.removeFirst() instead of items.getFirst(), as the intent
     *                 was to move the first item to the back, not copy the first item the back
     *
     */
    public Item[] getItems() {
        //Save the items to an array
        Item temp;
        int size = items.size();
        Item returnArray[] = new Item[size];
        for (int i = 0; i < size; i++) {
            //Retrieve the first item, save it, then move it to the back
            temp = items.removeFirst();
            returnArray[i] = temp;
            items.addLast(temp);
        }

        //Return the array of Items
        return returnArray;
    }

    //Mutator method for the processing rate. Only positive numbers are allowed.
    public void setRate(int rate) {
        processingRate = Math.abs(rate);
    }

    /**
     * Accessor method that returns processingRate
     *
     * Changes:
     *     Tuesday May 28 2019 12:52:31 am - :54:14 am
     *             for original code
     */
    public int getRate() {
        //If the rate is infinite, return -1 to denote it
        if (infiniteRate) {
            return -1;
        }
        return processingRate;
    }

    //Mutator for if the recipe has infinite processing rate
    public void setInfiniteRate(boolean infinite) {
        infiniteRate = infinite;
    }

    //Accessor for if the recipe has infinite processing rate
    public boolean hasInfiniteRate() {
        return infiniteRate;
    }

    /**
     * A mutator method that sets input1 to a non-null Item
     *
     * Changes:
     *     Tuesday May 28 2019 12:24:57 am - 12:36:56 am
     *             for original code
     */
    public void setInput1(Item input) {
        if (input == null) {
            //If input is null, check if input1 is null.
            // If input1 is null, then set it to a "temp" Item.
            if (input1 == null) {
                input1 = makeTempItem();
            }
            //Otherwise, do not change input1, and let the method terminate
        } else {
            //If input is not null, set input1 to input
            input1 = input;
        }
    }

    /**
     * An accessor method that returns input1
     *
     * Changes:
     *     Tuesday May 28 2019 12:43:04 am - :43:55 am
     *             for original code (Code was auto completed by Android Studio (v 3.1.1))
     */
    public Item getInput1() {
        return input1;
    }

    /**
     * A mutator method that sets input2 to a non-null Item
     *
     * Changes:
     *     Tuesday May 28 2019 12:24:57 am - 12:36:56 am
     *             for original code from setInput1()
     *         12:37:17 am
     *             Copied from setInput1()
     *         12:38:40 am - :39:11 am
     *             Modified to apply to input2 instead of input1
     */
    public void setInput2(Item input) {
        if (input == null) {
            //If input is null, check if input1 is null.
            // If input1 is null, then set it to a "temp" Item.
            if (input2 == null) {
                input2 = makeTempItem();
            }
            //Otherwise, do not change input1, and let the method terminate
        } else {
            //If input is not null, set input1 to input
            input2 = input;
        }
    }

    /**
     * An accessor method that returns input2
     *
     * Changes:
     *     Tuesday May 28 2019 12:45:47 am - :46:23 am
     *             for original code (auto completed by Android Studio v3.1.1)
     */
    public Item getInput2() {
        return input2;
    }

    /**
     * A mutator method that sets output1 to a non-null Item
     *
     * Changes:
     *     Tuesday May 28 2019 12:24:57 am - 12:36:56 am
     *             for original code from setInput1()
     *         12:39:24 am
     *             Copied from setInput1()
     *         12:39:43 am - :40:46 am
     *             Modified to apply to output1
     */
    public void setOutput1(Item output) {
        if (output == null) {
            //If input is null, check if input1 is null.
            // If input1 is null, then set it to a "temp" Item.
            if (output1 == null) {
                output1 = makeTempItem();
            }
            //Otherwise, do not change input1, and let the method terminate
        } else {
            //If input is not null, set input1 to input
            output1 = output;
        }
    }

    /**
     * An accessor method that returns output1
     *
     * Changes:
     *     Tuesday May 28 2019 12:46:40 am - :46:55 am
     *             for original code (auto completed by Android Studio v3.1.1)
     */
    public Item getOutput1() {
        return output1;
    }

    /**
     * A mutator method that sets input1 to a non-null Item
     *
     * Changes:
     *     Tuesday May 28 2019 12:24:57 am - 12:36:56 am
     *             for original code from setInput1()
     *         12:41:13 am
     *             Copied from setInput1()
     *         12:41:29 am - :42:07 am
     *             Modified to apply to output2
     */
    public void setOutput2(Item output) {
        if (output == null) {
            //If input is null, check if input1 is null.
            // If input1 is null, then set it to a "temp" Item.
            if (output2 == null) {
                output2 = makeTempItem();
            }
            //Otherwise, do not change input1, and let the method terminate
        } else {
            //If input is not null, set input1 to input
            output2 = output;
        }
    }

    /**
     * An accessor method that returns output2
     *
     * Changes:
     *     Tuesday May 28 2019 12:47:11 am - :47:38 am
     *             for original code (auto completed by Android Studio v3.1.1)
     */
    public Item getOutput2() {
        return output2;
    }

    //Accessor for input1Amount
    public int getInput1Amount() {
        return input1Amount;
    }

    //Accessor for input1Amount
    public int getInput2Amount() {
        return input2Amount;
    }

    //Accessor for output1Amount
    public int getOutput1Amount() {
        return output1Amount;
    }

    //Accessor for output2Amount
    public int getOutput2Amount() {
        return output2Amount;
    }

    /**
     * A helper method that makes a "temp" Item
     *
     * Changes:
     *     Tuesday May 28 2019 2:34:55 am - :36:27 am
     *             for original code
     */
    private Item makeTempItem() {
        return new Item("null", 0, false);
    }

    /**
     * A mutator for input1Amount
     *
     * Changes:
     *     Tuesday May 28 2019 3:15:35 am - :16:27 am for original code
     */
    public void setInput1Amount(int input) {
        input1Amount = Math.abs(input);
    }

    /**
     * A mutator for input2Amount
     *
     * Tuesday May 28 2019 3:17:07 am - :17:39 am for original code
     */
    public void setInput2Amount(int input) {
        input2Amount = Math.abs(input);
    }

    /**
     * A mutator for output1Amount
     *
     * Tuesday May 28 2019 3:18:00 am - :18:30 am
     */
    public void setOutput1Amount(int output) {
        output1Amount = Math.abs(output);
    }

    /**
     * A mutator for output2Amount
     *
     * Tuesday May 28 2019 3:18:47 am - :19:22 am
     */
    public void setOutput2Amount(int output) {
        output2Amount = Math.abs(output);
    }

    /**
     * A method to calculate the rates of the Items. Basically a limiting reactant calculation with
     * quite a few extras
     *
     * Changes:
     *     Tuesday May 28 2019 12:49:43 am - 2:24:45 am
     *             for original code
     *     Wednesday May 29 2019 3:13:05 am -
     *             Removed int input1Input and int input2Input
     *             Changed check for if recipe is infinitely fast so if it fails, it does not do anything,
     *                 but the code original for when it fails now applies at the end of the function.
     *             Added return statement for the check if the inputs are infinite when the processing rate is infinite
     */
    public void calculateRecipe() {
        //Determine the processing rate based on any limiting input(s)
        // Determine if processingRate should be infinite
        if (infiniteRate) {
            //Determine the highest multiple for processing rate, using the limiting input
            processingRate = determineMaxProcessingRate();

            if (processingRate == Integer.MAX_VALUE) {
                //If the processing rate is unlimited, then input and output are infinite,
                // as long as they are positive.
                if (output1Amount > 0) {
                    output1.setInfiniteInput(true);
                } else {
                    output1.setInputRate(0);
                }
                if (output2Amount > 0) {
                    output2.setInfiniteInput(true);
                } else {
                    output2.setInputRate(0);
                }

                //Finish the function
                return;
            }
        }
        //Otherwise, determine what the processingRate should be,
        // given the possibility of a limiting ratio
        processingRate = determineLimitedProcessingRate();

        //Determine the amount of input consumed
        input1.setConsumptionRate(processingRate * input1Amount);
        input2.setConsumptionRate(processingRate * input2Amount);
        //Determine the amount of output created
        output1.setInputRate(processingRate * output1Amount);
        output2.setInputRate(processingRate * output2Amount);
    }

    /**
     * A helper method that determines what the maximum processing rate is
     *
     * Changes:
     *     Tuesday May 28 2019 1:40:46 am - 2:08:54 am
     *             for original code
     *         2:25:02 am - :30:36 am
     *             Changed so if an input Item has infinite input, then treat it the same
     *                 as not needing input
     *     Wednesday May 29 2019 1:23:06 am - 1:25:02 am
     *             Changed so it returns the processing rate, and not the ratio needed to multiply
     *                 the current processing rate by to get the max rate
     *
     */
    private int determineMaxProcessingRate() {
        int input1UseRatio;
        int input2UseRatio;

        //Determine the input rates of the inputs
        int input1Input = input1.getInputRate();
        int input2Input = input2.getInputRate();

        //Determine the "use ratios" of the inputs
        // A "use ratio" is the ratio of the inputRate of the Item to the required amount of it
        //  Basically, how many whole number times can the input be used
        if ((input1Input == 0 && input1Amount > 0) || (input2Input == 0 && input2Amount > 0)) {
            //If an input is not receiving any input and is needed, the recipe is not doable
            return 0;
        }
        if (input1Amount == 0 || input1.getInputRate() == -1) { //-1 denotes infinite input
            //If input1 is not needed, then it cannot be a limiting input, or
            // if it has infinite input, then it doesn't matter how much is consumed.
            // Set to the largest number, so it is only considered if the largest number is the
            //  processing rate
            input1UseRatio = Integer.MAX_VALUE;
        } else {
            //Otherwise, calculate the ratio
            input1UseRatio = input1Input/ input1Amount;
        }
        if (input2Amount == 0 || input2.getInputRate() == -1) { //-1 denotes infinite input
            //If input2 is not needed, then it cannot be a limiting input, or
            // if it has infinite input, then it doesn't matter how much is consumed.
            // Set to the largest number, so it is only considered if the largest number is the
            //  processing rate
            input2UseRatio = Integer.MAX_VALUE;
        } else {
            //Otherwise, calculate the ratio
            input2UseRatio = input2Input / input2Amount;
        }

        //Return the processing rate. If they are both Integer.MAX_VALUE, then the rate is treated
        // as being unlimited.
        int ratio = Math.min(input1UseRatio, input2UseRatio);
        if (ratio == Integer.MAX_VALUE) {
            return ratio;
        }
        return processingRate * ratio;
    }

    /**
     * A helper method that determines the processing rate that will not over-consume the limiting
     * input
     *
     * Changes:
     *     Tuesday May 28 2019 2:18:21 am - :21:30 am
     *             for original code
     *     Wednesday May 29 2019 3:30:08 am - :31:22 am
     *             Removed + 0.0001 from the check if limitingRatio is less than 1.0.
     *                 It was supposed to account for floating point imprecision,
     *                 but it can mess up the logic when limitingRatio = 1.0
     */
    private int determineLimitedProcessingRate() {
        //Determine the input rates of the inputs
        int input1Input = input1.getInputRate();
        int input2Input = input2.getInputRate();

        // Determine how much of each input is needed
        int input1UseRate = input1Amount * processingRate;
        int input2UseRate = input2Amount * processingRate;

        // Determine the limiting ratios of the inputs
        //  The ratio is a ratio of input to usage, and is the factor at which the processing needs
        //   to be multiplied by in order to just consume the input Item. No more no less.
        double input1LimitingRatio = input1Input * 1.0 / input1UseRate;
        double input2LimitingRatio = input2Input * 1.0 / input2UseRate;
        // Limit the processing rate to the most limiting input, if any
        //  processingRate is not used to update the EditText, and so the new value is invisible to the user
        //  A ratio less than 1 means that inputs are over-consumed / there is a limiting input
        double limitingRatio = Math.min(input1LimitingRatio, input2LimitingRatio);
        if (limitingRatio < 1.0) {
            return (int) Math.floor(processingRate * limitingRatio);
        }
        //If there is no limiting ratio / input, then return the processing rate itself, as the
        // processingRate is limiting and does not need to be reduced
        return processingRate;
    }

    /**
     * A method that finds and returns an Item based on the name of it.
     *
     * Changes:
     *     Tuesday May 28 2019 2:43:21 am - :58:01 am
     *             for original code
     */
    public Item findItem(String name) {
        //Use linear search to find an Item with the same name
        Item temp;
        //Iterate through the whole LinkedList once
        for (int i = 0; i < items.size(); i++) {
            //Grab the first item
            temp = items.removeFirst();
            //Put the first item to the back
            items.addLast(temp);
            //Stop if the item picked up is the one with the right name
            if (name.equals(temp.getName())) {
                return temp;
            }
        }
        //If the loop ends, that means that the Item was not found, so return null
        return null;
    }

    //A method that returns a String representation of the object
    /**
     * A method that returns a String representation of the object
     *
     * Tuesday May 28 2019 3:45:03 am - :48:13 am
     *         Added code to save Item input1, Item input2, Item output1, Item output2,
     *             int input1Amount, int input2Amount, int output1Amount, and int output2Amount
     *             instance variables
     */
    public String toString() {
        //Use a temporary String to save the parts of the String together
        String returnString = "";

        //Save information about the recipe itself
        returnString += Integer.toString(processingRate);
        returnString += SPACER;
        returnString += Boolean.toString(infiniteRate);
        returnString += SPACER;
        returnString += input1.toString();
        returnString += SPACER;
        returnString += input2.toString();
        returnString += SPACER;
        returnString += output1.toString();
        returnString += SPACER;
        returnString += output2.toString();
        returnString += SPACER;
        returnString += Integer.toString(input1Amount);
        returnString += SPACER;
        returnString += Integer.toString(input2Amount);
        returnString += SPACER;
        returnString += Integer.toString(output1Amount);
        returnString += SPACER;
        returnString += Integer.toString(output2Amount);
        returnString += SPACER;

        //Call toString for each item and add it to the temporary String.
        for (Item i : items) {
            returnString += SPACER + i.toString();
        }

        //Return the whole String
        return returnString;
    }
}