package com.example.tabbedviewtut.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tabbedviewtut.DbHelper;
import com.example.tabbedviewtut.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;



public class UnitsFragment extends Fragment {
    DbHelper DB;

    TextInputEditText editTextStudentId;
    Button buttonSaveUnits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detView = inflater.inflate(R.layout.fragment_units, container, false);
        DB = new DbHelper(getContext());
        setComponents(detView);
        displayUnitCheckboxes(detView);

        return detView;
    }

    private void setComponents(View view) {
        editTextStudentId = view.findViewById(R.id.edit_text_student_id);
        buttonSaveUnits = view.findViewById(R.id.units_save_btn);


// check if student id is valid on focus change
        editTextStudentId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // get the student ID
                    String studentId = editTextStudentId.getText().toString();
                    //show student id on a Toast message


                    // check if the student ID is valid
                    if (isValidStudentId(studentId)) {
                        // display the units taken by the student
                        Toast.makeText(getContext(), "Student found", Toast.LENGTH_SHORT).show();
                        updateSelectedCheckboxes(v, studentId);
                    }
                    else {
                        // Student does not exist, display Toast error message
                        Toast.makeText(getContext(), "Student ID doesn't exist", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        buttonSaveUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the student ID
                String studentId = editTextStudentId.getText().toString();

                // get the layout where the checkboxes are
                LinearLayout checkboxLayout = requireView().findViewById(R.id.checkbox_layout_units);

                // iterate through the checkboxes and save the selected units
                for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) checkboxLayout.getChildAt(i);

                    // get the unit ID
                    String unitId = (String) checkBox.getTag();

                    // check if the checkbox is checked
                    if (checkBox.isChecked()) {
                        // add the unit to the student
                        DB.addStudentUnits(studentId, unitId);
                    } else {
                        // remove the unit from the student
                        DB.deleteStudentUnit(studentId, unitId);
                    }
                }
                Toast.makeText(getContext(), "Units Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private Boolean isValidStudentId(String studentId) {
    Boolean isValid = false;
//    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
    if (studentId.trim().length() != 0) {
        isValid = DB.checkStudentId(studentId);
        Log.d("STUDENT_ID_CHECK", "isValid after calling checkStudentId(): " + isValid);
    }
    Log.d("STUDENT_ID_CHECK", "Final value of isValid: " + isValid);
    return isValid;
}


    private void displayUnitCheckboxes(View view) {
        Log.d("UnitsFragment", "displayUnitCheckboxes() called");

        // get all units from the database
        Cursor cursor = DB.getUnitsData();
        List<String> allUnitCodes = new ArrayList<>();
        List<String> allUnitIds = new ArrayList<>();

        if (cursor.moveToFirst()) {
            Log.d("CursorCheckTAG", "data in cursor");
            do {
                allUnitCodes.add(cursor.getString(cursor.getColumnIndexOrThrow("unitCode")));
                allUnitIds.add(cursor.getString(cursor.getColumnIndexOrThrow("unitId")));
                Log.d("addCursorCheckTAG", "data added from cursor");
            } while (cursor.moveToNext());
        }else {
            Log.d("TAG", "No data in cursor");
        }

        // check the list of unit IDs
        for (String unitId : allUnitIds) {
            Log.d("UnitsFragment UNit IDS", "Unit ID: " + unitId);
        }

        // get the layout where the checkboxes will be added
        LinearLayout checkboxLayout = view.findViewById(R.id.checkbox_layout_units);

        // add null check
        if (checkboxLayout != null) {
            // clear the checkboxes
            checkboxLayout.removeAllViews();

            // create a new checkbox for each unit CODE AND SET TAG TO UNIT id
            int index =0;
            for (String unitCode : allUnitCodes) {
                String unitIdTag = allUnitIds.get(index++);
                Log.d("UnitsFragment", "Creating checkbox for unit " + unitCode);
                Log.d("UnitsFragment", "Creating checkbox Unit ID: " + unitIdTag);
                // create a new checkbox for the unit
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(unitCode);
                checkBox.setTag(unitIdTag);

                // add the checkbox to the container
                checkboxLayout.addView(checkBox);
            }
        }
    }


    private void updateSelectedCheckboxes(View view, String studentId) {
        Log.d("UnitsFragment", "updateSelectedCheckboxes() called");
        // log out the student ID
        Log.d("UnitsFragment", "Student ID: " + studentId);
        // get the units already taken by the student
        List<String> takenUnits = DB.getStudentUnits(studentId);
        // check if the list is empty
        if (takenUnits.isEmpty()) {
            Log.d("UnitsFragment", "No units taken");
        }
        // check the list values
        for (String unitId : takenUnits) {
            Log.d("UnitsFragment: ", "Unit ID: " + unitId);
        }

        // get the layout where the checkboxes are located
        LinearLayout checkboxLayout = requireView().findViewById(R.id.checkbox_layout_units);

            // iterate through the checkboxes and set the state based on whether the unit is already taken
            for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
                View child = checkboxLayout.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    String unitId = checkBox.getText().toString();
                    checkBox.setChecked(takenUnits.contains(unitId));
                }
            }

    }



}