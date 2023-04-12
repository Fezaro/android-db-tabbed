package com.example.tabbedviewtut.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tabbedviewtut.DbHelper;
import com.example.tabbedviewtut.R;
import com.example.tabbedviewtut.fragments.UnitsFragment;

import java.util.List;


public class DisplayFragment extends Fragment {
    DbHelper DB;
    EditText displayStudentId;
    Button displayButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detView = inflater.inflate(R.layout.fragment_display, container, false);
        DB = new DbHelper(getContext());
        setComponents(detView);
        return detView;
    }

    private void setComponents(View view) {
        displayButton = view.findViewById(R.id.displayButton);
        displayStudentId = view.findViewById(R.id.studentIdDisplay);

        // set button click listener
        displayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setDbDataView();
            }
        });

    }

    private void setDbDataView() {
        // get the student ID
        String studentId = displayStudentId.getText().toString();
        // check if the student id is valid
        if (studentId.isEmpty()) {
            displayButton.setText("Please enter a student ID");
            return;
        }
        //check the id entered against the database
        if(!DB.checkStudentId(studentId)){
            Toast.makeText(getContext(), "Student ID not found.", Toast.LENGTH_SHORT).show();;
            return;
        }

        // get data from student table
        Cursor stud_res = DB.getStudentDetails(studentId);

        // check if the student id is valid
        if (!stud_res.moveToFirst()) {
            Toast.makeText(getContext(), "No data found for student ID: " + studentId, Toast.LENGTH_SHORT).show();
            return;
        }

        // get student ID, and course from the database data returned
        String stuId = stud_res.getString(2);
        String course = stud_res.getString(3);

        // get the units the student has selected
        List units_res = DB.getStudentUnits(stuId);

        // create a string buffer to hold the data

        StringBuffer buffer = new StringBuffer();
        buffer.append("Student ID: " + stuId + "\n");
        buffer.append("course: " + course + "\n\n");
        // for list of units selected add to the string buffer
        for (int i = 0; i < units_res.size(); i++) {
            buffer.append("Unit " + (i + 1) + ": " + units_res.get(i) + "\n");
        }
//        buffer.append("Unit 1: " + units_res.get(0) + "\n");
//        buffer.append("Unit 2: " + units_res.get(1) + "\n");
//        buffer.append("Unit 3: " + units_res.get(2) + "\n");
//        buffer.append("Unit 4: " + units_res.get(3) + "\n");
//        buffer.append("Unit 5: " + units_res.get(4) + "\n\n\n");
        showMessage("Student Details", buffer.toString());
    }

    void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}