package com.example.tabbedviewtut.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tabbedviewtut.DbHelper;
import com.example.tabbedviewtut.MainActivity2;
import com.example.tabbedviewtut.R;


public class DetailsFragment extends Fragment {

    EditText firstName,lastName,idNumber,courseName,gender;
    Button button, updateButton, deleteButton, adminButton;
    DbHelper DB;

    String[] genderItems = {"Male", "Female"};
    String[] courseItems = {"APT", "IST"};
    AutoCompleteTextView autoCompleteTextViewGender, autoCompleteTextViewCourse;
    ArrayAdapter<String> adapterItemsGender, adapterItemsCourse;

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
//        courseName = view.findViewById(R.id.courseName);
//        gender = view.findViewById(R.id.gender);
        button = view.findViewById(R.id.button);
        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        adminButton = view.findViewById(R.id.adminButton);
        autoCompleteTextViewGender = view.findViewById(R.id.auto_complete_text_gender);
        autoCompleteTextViewCourse = view.findViewById(R.id.auto_complete_text_course);

        adapterItemsGender = new ArrayAdapter<String>(getContext(), R.layout.list_item, genderItems);
        adapterItemsCourse = new ArrayAdapter<String>(getContext(), R.layout.list_item, courseItems);

        autoCompleteTextViewGender.setAdapter(adapterItemsGender);
        autoCompleteTextViewCourse.setAdapter(adapterItemsCourse);
        autoCompleteTextViewGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCompleteTextViewCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();

            }
        });





        // set button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbInsert();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbUpdate()
                ;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbDelete();
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity2.class);
                startActivity(intent);

            }
        });

    }

    private void setDbDelete() {
        String[] studentDetails = getInsertedDetails();
        Boolean checkDataDelete;

        checkDataDelete = DB.deleteUserData(studentDetails[2]);
        if(checkDataDelete){
            // show success message
            //clearInputFields();
            Toast.makeText(getContext(), "Student Details Deleted", Toast.LENGTH_SHORT).show();

        }else{
            // show error message
            Toast.makeText(getContext(), "Entry not Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDbUpdate() {
        String[] studentDetails = getInsertedDetails();
        Boolean checkDataUpdate = false;

        checkDataUpdate = DB.updateUserData(studentDetails[0],studentDetails[1],studentDetails[2],studentDetails[3],studentDetails[4]);
        if(checkDataUpdate){
            // show success message
            //clearInputFields();
            Toast.makeText(getContext(), "Student Details Updated", Toast.LENGTH_SHORT).show();

        }else{
            // show error message
            Toast.makeText(getContext(), "Entry not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] getInsertedDetails(){
        String[] studentDetails = new String[5];
        studentDetails[0] = firstName.getText().toString();
        studentDetails[1] = lastName.getText().toString();
        studentDetails[2] = idNumber.getText().toString();
        studentDetails[3] = autoCompleteTextViewCourse.getText().toString();
        studentDetails[4]  = autoCompleteTextViewGender.getText().toString();

        return studentDetails;
    }

    void setDbInsert(){
        String[] studentDetails = getInsertedDetails();
        Boolean checkDataInsert = false;

        checkDataInsert = DB.insertUserData(studentDetails[0],studentDetails[1],studentDetails[2],studentDetails[3],studentDetails[4]);
        if(checkDataInsert){
            // show success message
            //clearInputFields();
            Toast.makeText(getContext(), "Student Details Inserted", Toast.LENGTH_SHORT).show();

        }else{
            // show error message
            Toast.makeText(getContext(), "Entry not Inserted", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearInputFields() {
        firstName.getText().clear();
        lastName.getText().clear();
        idNumber.getText().clear();
        gender.getText().clear();
        courseName.getText().clear();
        
    }


}