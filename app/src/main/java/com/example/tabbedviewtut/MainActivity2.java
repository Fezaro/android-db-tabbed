package com.example.tabbedviewtut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText unitID, unitCode;
    Button unitAdd, unitDelete, unitUpdate;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DB = new DbHelper(this);

        unitID = findViewById(R.id.unitID);
        unitCode = findViewById(R.id.unitCode);
        unitAdd = findViewById(R.id.unitAdd);
        unitDelete = findViewById(R.id.unitDelete);
        unitUpdate = findViewById(R.id.unitUpdate);

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

        unitDelete.setOnClickListener(view -> {
            String unitIDTXT = unitID.getText().toString();
            Boolean checkDeleteData = DB.deleteUnitsData(unitIDTXT);
            if(checkDeleteData)
                Toast.makeText(MainActivity2.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity2.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
        });

        unitUpdate.setOnClickListener(view -> {
            String unitIDTXT = unitID.getText().toString();
            String unitCodeTXT = unitCode.getText().toString();
            Boolean checkUpdateData = DB.updateUnitsData(unitIDTXT, unitCodeTXT);
            if(checkUpdateData)
                Toast.makeText(MainActivity2.this, "Entry Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity2.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
        });

    }
}

