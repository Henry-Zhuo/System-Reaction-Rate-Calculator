package com.example.hello.systemreactionratecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An activity that allows the creation of a new item.
 * This activity has a back button, that goes back to the previous activity that launched it.
 */
public class AddItemActivity extends Activity {
    //References for the widgets
    private EditText nameEditText;
    private EditText inputEditText;
    private CheckBox inputInfiniteCheckBox;
    private Button finishButton;

    //Static instance variables for referencing returned values
    public final static String NAME_ID = "name";
    public final static String INPUT_RATE_ID = "inputRate";
    public final static String INFINITE_INPUT_ID = "infiniteInput";

    /**
     * The method called when the activity is created.
     *
     * @param savedInstanceState A Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Initialize instance variables for the widgets
        nameEditText = findViewById(R.id.nameEditText);
        inputEditText = findViewById(R.id.inputEditText);
        inputInfiniteCheckBox = findViewById(R.id.inputInfiniteCheckBox);
        finishButton = findViewById(R.id.finishButton);

        //Initialize and associate event handlers for the widgets
        finishButton.setOnClickListener(new ButtonListener());
    }

    /**
     * An inner class that handlers events for buttons
     */
    class ButtonListener implements View.OnClickListener {

        /**
         * A method that handles what happens when the button is clicked.
         * Using www.informit.com/articles/article.aspx?p=1646053&seqNum=3 as a tutorial, along with
         *
         * @param v A View
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.finishButton) {
                //Prepare to return information about the new Item to the previous activity
                Intent returnIntent = new Intent();

                //Gather the information the user gave
                // Collect the name
                String name = nameEditText.getText().toString();
                returnIntent.putExtra(NAME_ID, name);
                // Collect the input rate
                int inputRate = 0;
                String input = inputEditText.getText().toString();
                try { //Catch if it is not an integer
                    inputRate = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    //Notify the user that the entered number is invalid, and default to 0
                    Toast.makeText(AddItemActivity.this,
                            "The entered input rate is invalid, defaulting to 0",
                            Toast.LENGTH_LONG).show();
                    inputRate = 0;
                }
                returnIntent.putExtra(INPUT_RATE_ID, inputRate);
                // Collect the infinite rate boolean state
                boolean infiniteInput = inputInfiniteCheckBox.isChecked();
                returnIntent.putExtra(INFINITE_INPUT_ID, infiniteInput);

                //Prepare to return the information
                setResult(Activity.RESULT_OK, returnIntent);

                //Return to the activity that called the outer class
                finish();
            }
        }
    }
}