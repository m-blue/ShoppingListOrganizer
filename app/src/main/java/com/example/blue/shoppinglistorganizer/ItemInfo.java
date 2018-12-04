package com.example.blue.shoppinglistorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ItemInfo extends AppCompatActivity {

    EditText txtName;
    EditText txtPrice;
    Button btnAdd;
    Spinner spnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        final EditText txtName = findViewById(R.id.txtName);
        final EditText txtPrice = findViewById(R.id.txtPrice);
        final Button btnAdd = findViewById(R.id.btnAdd);
        final Spinner spnCategory = findViewById(R.id.spnCategory);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckData()){
                    Intent mainScreen = new Intent(ItemInfo.this, MainActivity.class);
                    mainScreen.putExtra("txtName", txtName.getText().toString());
                    mainScreen.putExtra("txtPrice", txtPrice.getText().toString());
                    mainScreen.putExtra("category", spnCategory.getSelectedItem().toString());

                    setResult(RESULT_OK, mainScreen);
                    finish();
                }


            }
        });
    }
    boolean CheckData(){

        EditText txtName = findViewById(R.id.txtName);
        if(txtName.getText().toString().isEmpty()){
            txtName.setError("Invalid Name");
            txtName.requestFocus();
            return false;
        }

        EditText txtPrice = findViewById(R.id.txtPrice);
        if(txtPrice.getText().toString().isEmpty()){
            txtPrice.setError("Invalid Price");
            txtPrice.requestFocus();
            return false;
        }
        return true;
    }
}
