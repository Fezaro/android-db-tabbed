package com.example.tabbedviewtut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText unitID, unitCode;
    Button unitAdd;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DB = new DbHelper(this);

        unitID = findViewById(R.id.unitID);
        unitCode = findViewById(R.id.unitCode);
        unitAdd = findViewById(R.id.unitAdd);

        unitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String unitIDTXT = unitID.getText().toString();
                String unitCodeTXT = unitCode.getText().toString();
                Boolean checkInsertData = DB.insertUnitData(unitIDTXT, unitCodeTXT);
                if(checkInsertData)
                    Toast.makeText(MainActivity2.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity2.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

