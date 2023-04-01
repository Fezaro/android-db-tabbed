package com.example.tabbedviewtut.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tabbedviewtut.DbHelper;
import com.example.tabbedviewtut.R;


public class DetailsFragment extends Fragment {

    EditText firstName,lastName,idNumber,courseName,gender;
    Button button;
    DbHelper DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detView = inflater.inflate(R.layout.fragment_details, container, false);
        DB = new DbHelper(getContext());
        setComponents(detView);
        return detView;


    }

    private void setComponents(View view) {
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        idNumber = view.findViewById(R.id.idNumber);
        courseName = view.findViewById(R.id.courseName);
        gender = view.findViewById(R.id.gender);
        button = view.findViewById(R.id.button);

        // set button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbInsert();
            }
        });

    }

    private String[] getInsertedDetails(){
        String[] studentDetails = new String[5];
        studentDetails[0] = firstName.getText().toString();
        studentDetails[1] = lastName.getText().toString();
        studentDetails[2] = idNumber.getText().toString();
        studentDetails[3] = courseName.getText().toString();
        studentDetails[4] = gender.getText().toString();
        return studentDetails;
    }

    void setDbInsert(){
        String[] studentDetails = getInsertedDetails();
        Boolean checkDataInsert = false;

        checkDataInsert = DB.insertUserdata(studentDetails[0],studentDetails[1],studentDetails[2],studentDetails[3],studentDetails[4]);
        if(checkDataInsert == true){
            // show success message
            clearInputFields();
            Toast.makeText(getContext(), "Student Details Inserted", Toast.LENGTH_SHORT).show();

        }else{
            // show error message
            Toast.makeText(getContext(), "Entry not Inserted", Toast.LENGTH_SHORT).show();
        }


    }

    private void clearInputFields() {
        firstName.getText().clear();;
        lastName.getText().clear();
        idNumber.getText().clear();
        gender.getText().clear();
        courseName.getText().clear();
        
    }


}