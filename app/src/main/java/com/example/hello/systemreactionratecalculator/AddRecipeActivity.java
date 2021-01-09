package com.example.hello.systemreactionratecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An activity that allows the user to create a new recipe.
 * Some code adapted http://www.vik-20.com/java/5-5-more-widgets/,
 * https://stackoverflow.com/questions/449484/android-capturing-the-return-of-an-activity,
 * http://wptrafficanalyzer.in/blog/dynamically-adding-items-to-spinner-widget-in-android/,
 * http://www.vik-20.com/java/5-3-tipcalculator-app/, and
 * http://www.vik-20.com/java/5-4-improved-tipcalculator-app/
 */
public class AddRecipeActivity extends Activity {
    private Recipe recipe;
    private Item tempItem;
    //Instance variables for the data in Spinners
    private ArrayList<String> itemNames;
    private ArrayAdapter spinnerAdapter;
    //Reference instance variables for widgets
    private Button addItemButton;
    private EditText input1EditText;
    private Spinner input1Spinner;
    private EditText input2EditText;
    private Spinner input2Spinner;
    private EditText rateEditText;
    private CheckBox rateInfiniteCheckBox;
    private EditText output1EditText;
    private Spinner output1Spinner;
    private EditText output2EditText;
    private Spinner output2Spinner;
    private TextView input1StatsTextView;
    private TextView input2StatsTextView;
    private TextView output1StatsTextView;
    private TextView output2StatsTextView;

    //SharedPreferences for saving preferences
    private SharedPreferences savedPrefs;

    //Static instance variables for referencing
    private final static int ADD_NEW_ITEM_REQUEST = 0;

    /**
     * The method called when the activity is created.
     *
     * @param savedInstanceState A Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Initialize instance variables
        itemNames = new ArrayList<>();
        recipe = new Recipe();
        savedPrefs = getSharedPreferences("ProcessingRateCalcPrefs", MODE_PRIVATE);

        //Initialize instance variables for the widgets
        addItemButton = findViewById(R.id.addItemButton);
        input1EditText = findViewById(R.id.input1EditText);
        input1Spinner = findViewById(R.id.input1Spinner);
        input2EditText = findViewById(R.id.input2EditText);
        input2Spinner = findViewById(R.id.input2Spinner);
        rateEditText = findViewById(R.id.rateEditText);
        rateInfiniteCheckBox = findViewById(R.id.rateInfiniteCheckBox);
        output1EditText = findViewById(R.id.output1EditText);
        output1Spinner = findViewById(R.id.output1Spinner);
        output2EditText = findViewById(R.id.output2EditText);
        output2Spinner = findViewById(R.id.output2Spinner);
        input1StatsTextView = findViewById(R.id.input1StatsTextView);
        input2StatsTextView = findViewById(R.id.input2StatsTextView);
        output1StatsTextView = findViewById(R.id.output1StatsTextView);
        output2StatsTextView = findViewById(R.id.output2StatsTextView);

        //Set up the Spinners with an ArrayAdapter
        // Make an ArrayAdapter. Code from http://www.vik-20.com/java/5-5-more-widgets/ and
        // http://wptrafficanalyzer.in/blog/dynamically-adding-items-to-spinner-widget-in-android/
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Assign the ArrayAdapter to the Spinners
        input1Spinner.setAdapter(spinnerAdapter);
        input2Spinner.setAdapter(spinnerAdapter);
        output1Spinner.setAdapter(spinnerAdapter);
        output2Spinner.setAdapter(spinnerAdapter);

        // Update spinnerAdapter
        spinnerAdapter.notifyDataSetChanged();

        //Initialize and associate event handlers for the widgets
        // Event handling for Buttons
        addItemButton.setOnClickListener(new ButtonListener());
        // Event handling for EditTexts
        EditTextListener editTextListener = new EditTextListener();
        input1EditText.setOnEditorActionListener(editTextListener);
        input2EditText.setOnEditorActionListener(editTextListener);
        rateEditText.setOnEditorActionListener(editTextListener);
        output1EditText.setOnEditorActionListener(editTextListener);
        output2EditText.setOnEditorActionListener(editTextListener);
        // Event handling for Spinners
        SpinnerListener spinnerListener = new SpinnerListener();
        input1Spinner.setOnItemSelectedListener(spinnerListener);
        input2Spinner.setOnItemSelectedListener(spinnerListener);
        output1Spinner.setOnItemSelectedListener(spinnerListener);
        output2Spinner.setOnItemSelectedListener(spinnerListener);
        // Event handling for CheckBoxes
        rateInfiniteCheckBox.setOnClickListener(new CheckBoxListener());
    }

    /**
     * The method called when the activity is paused.
     * Code adapted from http://www.vik-20.com/java/5-4-improved-tipcalculator-app/
     */
    @Override
    public void onPause() {
        //Save a long string that encodes the recipe and all Items
        SharedPreferences.Editor prefsEditor = savedPrefs.edit();
        prefsEditor.putString("recipe", recipe.toString());
        prefsEditor.commit();

        //Call the parent onPause()
        super.onPause();
    }

    /**
     * The method called when the activity is resumed.
     * Code adapted from http://www.vik-20.com/java/5-4-improved-tipcalculator-app/ and
     * https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java
     */
    @Override
    public void onResume() {
        //Call the parent onResume()
        super.onResume();

        //Flush itemNames
        itemNames.clear();

        //Retrieve the saved string for the recipe
        String state = savedPrefs.getString("recipe", "");
        // If state has things to decode, then try decoding
        if (state.contains(Recipe.SPACER)) {
            //Split the data relating the instance variables of the recipe
            String recipeVariables[] = state.split(Recipe.SPACER);

            //Stop if there is not enough information to decode
            if (recipeVariables.length < 10) {
                return;
            }

            //Initialize variables using decoded information from the saved string
            int processingRate = Integer.parseInt(recipeVariables[0]);
            boolean infiniteRate = Boolean.parseBoolean(recipeVariables[1]);
            String input1String = recipeVariables[2];
            String input2String = recipeVariables[3];
            String output1String = recipeVariables[4];
            String output2String = recipeVariables[5];
            int input1Amount = Integer.parseInt(recipeVariables[6]);
            int input2Amount = Integer.parseInt(recipeVariables[7]);
            int output1Amount = Integer.parseInt(recipeVariables[8]);
            int output2Amount = Integer.parseInt(recipeVariables[9]);

            //Create a recipe and assign the variables
            recipe = new Recipe(processingRate, infiniteRate);
            recipe.setInput1Amount(input1Amount);
            recipe.setInput2Amount(input2Amount);
            recipe.setOutput1Amount(output1Amount);
            recipe.setOutput2Amount(output2Amount);

            //Decode and assign the Items
            Item input1 = decodeItem(input1String);
            Item input2 = decodeItem(input2String);
            Item output1 = decodeItem(output1String);
            Item output2 = decodeItem(output2String);
            //The Items here should only be those in Recipe.items, because they must be selected from
            // itemNames and itemNames and Recipe.items should be in sync
            recipe.setInput1(input1);
            recipe.setInput2(input2);
            recipe.setOutput1(output1);
            recipe.setOutput2(output2);


            //Decode and assign the Items in LinkedList items in Recipe
            // Check if there are Items to decode, and don't if there are none
            if (recipeVariables.length > 10) {
                // Grab only the Strings that are not yet decoded
                String codedState[] = Arrays.copyOfRange(recipeVariables, 10, recipeVariables.length);
                // Iterate through codedState for every Item and save it to recipe
                for (String code : codedState) {
                    Item temp = decodeItem(code);
                    recipe.addItems(temp);
                }

                //Sync itemNames to the Item list in recipe
                Item items[] = recipe.getItems();
                for (Item item : items) {
                    itemNames.add(item.getName());
                }
            }
        }

        //Add tempItem to recipe and itemNames, if there is any
        // tempItem is the Item that is returned from addItemActivity
        if (tempItem != null) {
            Toast.makeText(AddRecipeActivity.this,
                    "Item Added",
                    Toast.LENGTH_LONG).show();
            recipe.addItems(tempItem);
            itemNames.add(tempItem.getName());
            //Set tempItem back to null to avoid adding the same Item next time
            tempItem = null;
        }

        //Update the app
        update();
    }

    /**
     * A helper method that decodes a String and returns the Item.
     * Basically the reverse of Item.toString(), sort of.
     */
    private Item decodeItem(String input) {
        //Split the input string
        String itemString[] = input.split(Item.spacer);

        //Stop if there is not enough information to decode
        if (itemString.length < 4) {
            return null;
        }

        //Initialize variables using decoded information from the input string
        String name = itemString[0];
        int inputRate = Integer.parseInt(itemString[1]);
        int consumptionRate = Integer.parseInt(itemString[2]);
        boolean infiniteInput = Boolean.parseBoolean(itemString[3]);

        //Return an Item with the variables
        return new Item(name, inputRate, consumptionRate, infiniteInput);
    }

    /**
     * A method that handles the result of called activities.
     * Code adapted from
     * https://stackoverflow.com/questions/449484/android-capturing-the-return-of-an-activity
     */
    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        //Check if the activity finished the result ok
        if (result != Activity.RESULT_OK) {
            //If the activity did not return the result properly, let the user know
            Toast.makeText(AddRecipeActivity.this,
                    "The Item could not be created, sorry",
                    Toast.LENGTH_LONG).show();
            //Stop trying to decode information, as the information may not be useful
            return;
        }

        //Check which call to which activity finished
        switch (request) {
            case ADD_NEW_ITEM_REQUEST:
                //If a new item was to be added, add it
                // Gather the arguments for the Item
                String name = data.getStringExtra(AddItemActivity.NAME_ID);
                int inputRate = data.getIntExtra(AddItemActivity.INPUT_RATE_ID, 0);
                boolean infiniteInput = data.getBooleanExtra(AddItemActivity.INFINITE_INPUT_ID, false);
                // Save tbe item temporarily
                tempItem = new Item(name, inputRate, infiniteInput);

                break;
        }
    }

    /**
     * A method that updates the data on the screen
     */
    private void update() {
        //Update Spinners
        spinnerAdapter.notifyDataSetChanged();

        //Retrieve data from the activity for the recipe
        recipe.setInput1Amount(Integer.parseInt(input1EditText.getText().toString()));
        recipe.setInput2Amount(Integer.parseInt(input2EditText.getText().toString()));
        recipe.setRate(Integer.parseInt(rateEditText.getText().toString()));
        recipe.setInfiniteRate(rateInfiniteCheckBox.isChecked());
        recipe.setOutput1Amount(Integer.parseInt(output1EditText.getText().toString()));
        recipe.setOutput2Amount(Integer.parseInt(output2EditText.getText().toString()));

        //Calculate the recipe
        recipe.calculateRecipe();

        //Update the stats at the bottom
        input1StatsTextView.setText(recipe.getInput1().getStats());
        input2StatsTextView.setText(recipe.getInput2().getStats());
        output1StatsTextView.setText(recipe.getOutput1().getStats());
        output2StatsTextView.setText(recipe.getOutput2().getStats());
    }

    /**
     * An inner class to handle events for buttons
     */
    class ButtonListener implements View.OnClickListener {

        /**
         * A method that handles what happens when a button is clicked.
         * Using http://www.informit.com/articles/article.aspx?p=1646053&seqNum=3 as a tutorial,
         * stackoverflow.com/questions/449484/android-capturing-the-return-of-an-activity#449824
         * for additional help and https://developer.android.com/training/basics/intents/result
         * for more info.
         *
         * @param v A View
         */
        @Override
        public void onClick( View v ) {
            if (v.getId() == R.id.addItemButton) {
                //Launch the AddItemActivity
                Intent addItemActivity = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(addItemActivity, ADD_NEW_ITEM_REQUEST);
            }
        }
    }

    /**
     * An inner class to handle events for EditTexts
     * Code adapted from http://www.vik-20.com/java/5-3-tipcalculator-app/
     */
    class EditTextListener implements TextView.OnEditorActionListener {

        /**
         * A method that handles what happens when the EditText is edited.
         * Code adapted from http://www.vik-20.com/java/5-3-tipcalculator-app/
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ( actionId == EditorInfo.IME_ACTION_DONE ) {
                update();
            }
            return false;
        }
    }

    /**
     * An inner class to handle events for Spinners
     * Code adapted from http://www.vik-20.com/java/5-5-more-widgets/
     */
    class SpinnerListener implements OnItemSelectedListener {

        /**
         * A method that handles what happens when an item is selected from the Spinner
         *
         * @param parent An AdapterView<?>
         * @param view A View
         * @param position An int
         * @param id A long
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Set the items  for input1, input2, output1, and output2
            Item selection;
            selection = recipe.findItem(input1Spinner.getSelectedItem().toString());
            recipe.setInput1(selection);
            selection = recipe.findItem(input2Spinner.getSelectedItem().toString());
            recipe.setInput2(selection);
            selection = recipe.findItem(output1Spinner.getSelectedItem().toString());
            recipe.setOutput1(selection);
            selection = recipe.findItem(output2Spinner.getSelectedItem().toString());
            recipe.setOutput2(selection);

            //Update the recipe
            update();
        }

        /**
         * A method that handles what happens when nothing is selected from the Spinner
         *
         * @param parent
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Not Used / Implemented
        }
    }

    /**
     * An inner class to handle events for CheckBoxes
     */
    class CheckBoxListener implements View.OnClickListener {

        /**
         * A method that handles what happens when a CheckBox is checked/unchecked
         * Code adapted from http://www.vik-20.com/java/5-5-more-widgets/
         */
        @Override
        public void onClick( View v ) {
            recipe.setInfiniteRate(rateInfiniteCheckBox.isChecked());
        }
    }
}