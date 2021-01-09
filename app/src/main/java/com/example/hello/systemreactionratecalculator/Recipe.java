package com.example.hello.systemreactionratecalculator;

import java.util.LinkedList;

/**
 * A class to remember the Items and recipe
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
     */
    public Item getInput1() {
        return input1;
    }

    /**
     * A mutator method that sets input2 to a non-null Item
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
     */
    public Item getInput2() {
        return input2;
    }

    /**
     * A mutator method that sets output1 to a non-null Item
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
     */
    public Item getOutput1() {
        return output1;
    }

    /**
     * A mutator method that sets input1 to a non-null Item
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
     */
    private Item makeTempItem() {
        return new Item("null", 0, false);
    }

    /**
     * A mutator for input1Amount
     */
    public void setInput1Amount(int input) {
        input1Amount = Math.abs(input);
    }

    /**
     * A mutator for input2Amount
     */
    public void setInput2Amount(int input) {
        input2Amount = Math.abs(input);
    }

    /**
     * A mutator for output1Amount
     */
    public void setOutput1Amount(int output) {
        output1Amount = Math.abs(output);
    }

    /**
     * A mutator for output2Amount
     */
    public void setOutput2Amount(int output) {
        output2Amount = Math.abs(output);
    }

    /**
     * A method to calculate the rates of the Items. Basically a limiting reactant calculation with
     * quite a few extras
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

    /**
     * A method that returns a String representation of the object
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