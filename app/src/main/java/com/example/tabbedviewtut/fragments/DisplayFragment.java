package com.example.tabbedviewtut.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tabbedviewtut.DbHelper;
import com.example.tabbedviewtut.R;


public class DisplayFragment extends Fragment {
    DbHelper DB;
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

        // set button click listener
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbDataView();
            }
        });

    }

    private void setDbDataView() {
        // get data from db
        Cursor stud_res = DB.getStudentData();
        if (stud_res.getCount() == 0) {
            displayButton.setText("No data found");
            return;
        }

        // get data from units table
        Cursor units_res = DB.getUnitsData();
        if (units_res.getCount() == 0) {
            displayButton.setText("No data found");
            return;
        }


        StringBuffer buffer = new StringBuffer();
        while (stud_res.moveToNext()) {
            buffer.append("Student ID: " + stud_res.getString(2) + "\n");
            buffer.append("course: " + stud_res.getString(3) + "\n\n");

        }

        while (units_res.moveToNext()) {
            buffer.append("Unit 1: " + units_res.getString(0) + "\n");
            buffer.append("Unit 2: " + units_res.getString(1) + "\n");
            buffer.append("Unit 3: " + units_res.getString(2) + "\n");
            buffer.append("Unit 4: " + units_res.getString(3) + "\n");
            buffer.append("Unit 5: " + units_res.getString(4) + "\n\n\n");
        }
        showMessage("Student Data", buffer.toString());

    }

    void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}