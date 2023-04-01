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


public class UnitsFragment extends Fragment {
    DbHelper DB;

    EditText unit1, unit2, unit3, unit4, unit5;

    Button unitSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detView = inflater.inflate(R.layout.fragment_units, container, false);
        DB = new DbHelper(getContext());
        setComponents(detView);
        return detView;
    }

    private void setComponents(View view) {
        unit1 = view.findViewById(R.id.unitCode);
        unit2 = view.findViewById(R.id.unitCode2);
        unit3 = view.findViewById(R.id.unitCode3);
        unit4 = view.findViewById(R.id.unitCode4);
        unit5 = view.findViewById(R.id.unitCode5);
        unitSubmit = view.findViewById(R.id.unitSubmit);

        // set button click listener
        unitSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbInsert();
            }
        });
    }

    private String[] getInsertedDetails(){
        String[] UnitsDetails = new String[5];
        UnitsDetails[0] = unit1.getText().toString();
        UnitsDetails[1] = unit2.getText().toString();
        UnitsDetails[2] = unit3.getText().toString();
        UnitsDetails[3] = unit4.getText().toString();
        UnitsDetails[4] = unit5.getText().toString();
        return UnitsDetails;
    }

    private void setDbInsert() {
        String[] unitsDetails = getInsertedDetails();
        Boolean checkInsertData= false;

        checkInsertData = DB.insertUnitsData(unitsDetails[0], unitsDetails[1], unitsDetails[2], unitsDetails[3], unitsDetails[4]);
        if(checkInsertData==true)
            Toast.makeText(getContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();

    }


}