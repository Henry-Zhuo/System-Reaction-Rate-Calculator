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
 *
 * Changes:
 *     Thursday May 23 2019 1:25:15 pm
 *             Created this class
 *     Sunday May 26 2019 9:02:58 pm - :30:51 pm
 *             Added instance variables nameEditText, inputEditText, and inputInfiniteCheckBox
 *             Set up instance variables nameEditText, inputEditText, and inputInfiniteCheckBox
 *                 in onCreate()
 *     Monday May 27 2019 2:54:51 pm - :05:55 pm
 *             Added CheckBoxListener()
 *             Added boolean infiniteInput instance variable.
 *             Added String name and int inputRate instance variables.
 *         8:35:47 pm - 9:34:40 pm
 *             Added Button finishButton instance variable
 *             Changed onCreate() to not set event handlers for nameEditText and inputEditText.
 *                 onCreate() now initializes and sets event handler for finishButton
 *             Created ButtonListener()
 *             Added String nameID, String inputRateID, and String infiniteInputID final static instance variables
 *             Changed onCreate() to no longer add an event handler for inputInfiniteCheckBox
 *             Removed String name, int inputRate, and boolean infiniteInput instance variables
 *             Removed CheckBoxListener()
 *         11:34:00 pm - :39:29 pm
 *             Renamed nameID to NAME_ID, inputRateID to INPUT_RATE_ID, and infiniteInputID
 *                 to INFINITE_INPUT_ID. Changed ButtonListener.onClick() as a result.
 *         9:58:02 pm - 10:05:35 pm
 *             Changed ButtonListener.onClick() to instantiate an Intent using something other than
 *                 (Intent) null as the argument. Found this bug by running on my sister's tablet
 *                 and checking the run console. Toast messages after the Intent initialization
 *                 were not appearing too, and that should have raised flags.
 *             Changed onCreate() to assign R.id.inputEditText instead of R.id.input1EditText. It causes
 *                 the activity to crash when calling getText() on null in ButtonListener.onClick()
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
     * Changes:
     *     Sunday May 26 2019 9:08:30 pm - :26:53 pm
     *             Set up instance variables nameEditText, inputEditText, and inputInfiniteCheckBox
     *             Added event handlers for nameEditText, inputEditText, and inputInfiniteCheckBox
     *     Monday May 27 2019 8:36:39 pm - :40:38 pm
     *             Set up finishButton instance variable
     *             Added event handler for finishButton
     *             Removed event handler for nameEditText and inputEditText
     *         9:22:36 pm - :23:49 pm
     *             Removed assignment of a ButtonListener to finishButton
     *         9:29:43 pm - 9:32:57 pm
     *             Added assignment of a ButtonListener to finishButton
     *             Removed assignment of CheckBoxListener to inputInfiniteCheckBox
     *     Tuesday May 28 2019 10:03:48 pm - :04:24 pm
     *             Changed to assign R.id.inputEditText instead of R.id.input1EditText. It causes
     *                 the activity to crash when calling getText() on null in ButtonListener.onClick()
     *
     *
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
     *
     * Changes:
     *     Monday May 27 2019 8:41:56 pm - 9:21:12 pm for original code
     *     Tuesday May 28 2019 10:00:07 pm - :03:28 pm
     *             Changed onClick() to instantiate the Intent differently
     *
     */
    class ButtonListener implements View.OnClickListener {

        /**
         * A method that handles what happens when the button is clicked.
         * Using www.informit.com/articles/article.aspx?p=1646053&seqNum=3 as a tutorial, along with
         * stackoverflow.com/questions/449484/android-capturing-the-return-of-an-activity#449824
         *
         * Changes:
         *     Monday May 27 2019 8:42:45 pm - 9:20:54 pm for original code
         *     Tuesday May 28 2019 10:00:35 pm -  10:03:20 pm
         *             Changed to instantiate the Intent differently
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