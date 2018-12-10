package com.example.blue.shoppinglistorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<String> stringCategoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringCategoryList = Arrays.asList(getResources().getStringArray(R.array.category));

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

        DisplayList();

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i,1);
            }
        });

        // Allows the user to delete an item on the list by tapping its name
        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Creates an Alert Dialogue Box asking the user if they want to delete the item
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + groceries.get(position).name);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteItem(positionToRemove);
                        DisplayList();
                    }});
                adb.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Grocery grocery = new Grocery(data.getStringExtra("txtName"), 
                data.getStringExtra("category"), 
                Float.parseFloat(data.getStringExtra("txtPrice")));
        groceries.add(grocery);

        DisplayList();
        }
        
        void DisplayList(){
        OrganizeList();
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
            TextView txtTotal = findViewById(R.id.txtTotal);
            EditText txtPercentage = findViewById(R.id.txtPercentage);
            NumberFormat currency = NumberFormat.getCurrencyInstance();

            Float totalPrice = CalculateTotal();
            Float taxPercentage = Float.parseFloat(txtPercentage.getText().toString());
            Float totalWithTax = totalPrice + (totalPrice * taxPercentage / 100);
            txtTotal.setText(currency.format(totalWithTax));
        }

        void OrganizeList(){
        List<Grocery> tempList = new ArrayList<>();
            for (String s :
                    stringCategoryList) {
                for (Grocery g:
                        groceries ) {
                    if(g.category.equals(s)){
                        tempList.add(g);
                    }
                }
            }

            groceries.clear();
            groceries.addAll(tempList);
            tempList.clear();
        }

        void DeleteItem(int item){
            groceries.remove(item);
        }

        float CalculateTotal(){
            Float total = 0.0f;
            for (Grocery g :
                    groceries) {
                total += g.price;
            }
            return total;
        }

    }

