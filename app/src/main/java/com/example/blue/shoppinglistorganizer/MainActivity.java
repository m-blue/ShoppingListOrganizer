package com.example.blue.shoppinglistorganizer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView nameList;
    ListView categoryList;
    ListView priceList;

    ArrayAdapter<String> nameAdapter;
    ArrayAdapter<String> catAdapter;
    ArrayAdapter<String> priceAdapter;

    Button btnNew;
    List<Grocery> groceries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameList = findViewById(R.id.nameList);
        categoryList = findViewById(R.id.categoryList);
        priceList = findViewById(R.id.priceList);

        btnNew = findViewById(R.id.btnNew);

        nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        nameList.setAdapter(nameAdapter);

        catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        categoryList.setAdapter(catAdapter);

        priceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        priceList.setAdapter(priceAdapter);

        final Intent i = new Intent(MainActivity.this, ItemInfo.class);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i,1);
            }
        });

/*        String newString = null;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                newString = null;
            }else{
                newString = extras.getString("txtName");
            }
        }
        arrayAdapter.add(newString);
        arrayAdapter.notifyDataSetChanged();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(MainActivity.this,"Cin th Re",Toast.LENGTH_LONG).show();
        Grocery grocery = new Grocery(data.getStringExtra("txtName"), 
                data.getStringExtra("category"), 
                Float.parseFloat(data.getStringExtra("txtPrice")));
        groceries.add(grocery);

        String newString = data.getStringExtra("txtName");
        String newString2 = data.getStringExtra("category");
        DisplayList();

        }
        
        void DisplayList(){
        nameAdapter.clear();
        priceAdapter.clear();
        catAdapter.clear();

            for (Grocery g :
                    groceries) {
                nameAdapter.add(g.name);
                catAdapter.add(g.category);
                if(g.price == null){
                    g.price = 0.0f;
                }
                priceAdapter.add((g.price).toString());
            }
        }
    }

