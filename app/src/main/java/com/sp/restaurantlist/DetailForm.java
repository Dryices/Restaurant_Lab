package com.sp.restaurantlist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import sp.com.restaurantlist.R;

public class DetailForm extends AppCompatActivity {
    boolean isAllFieldsChecked = false;
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private RestaurantHelper helper =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_form);
        restaurantName = findViewById(R.id.restaurant_name);
        restaurantTypes = findViewById(R.id.restaurant_types);

        buttonSave =findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);

        helper=new RestaurantHelper(this);


    }
    @Override
    protected  void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {
                //To read data from restaurantName EditText
                String nameStr = restaurantName.getText().toString();
                String addressStr = restaurantAddress.getText().toString();
                String telStr = restaurantTel.getText().toString();
                String restType = "";
                //To read selection of restaurantTypes RadioGroup
                switch (restaurantTypes.getCheckedRadioButtonId()) {
                    case R.id.chinese:
                        restType = "Chinese";
                        break;
                    case R.id.western:
                        restType = "Western";
                        break;
                    case R.id.indian:
                        restType = "Indian";
                        break;
                    case R.id.indonesian:
                        restType = "Indonesian";
                        break;
                    case R.id.korean:
                        restType = "Korean";
                        break;
                    case R.id.japanese:
                        restType = "Japanese";
                        break;
                    case R.id.thai:
                        restType = "Thai";
                        break;
                }
                helper.insert(nameStr, addressStr, telStr, restType);

                //To close current Activity class and exit
                finish();
            }
        }
    };

    private boolean CheckAllFields() {
        boolean check =true;
        if (restaurantName.length() == 0) {
            restaurantName.setError("This field is required");
            check =false;
        }
        if (restaurantAddress.length() == 0) {
            restaurantAddress.setError("This field is required");
            check=false;
        }
        if (restaurantTel.length() == 0) {
            restaurantTel.setError("This field is required");
            check=false;
        }

        // after all validation return true.
        return check;
    }

}


