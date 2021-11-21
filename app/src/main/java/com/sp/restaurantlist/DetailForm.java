package com.sp.restaurantlist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import sp.com.restaurantlist.R;

public class DetailForm extends AppCompatActivity {
    boolean isAllFieldsChecked = false;
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private RestaurantHelper helper = null;
    private String restaurantID = "";

    private TextView location = null;
    private GPSTracker gpsTracker;
    private double latitude = 0.0d;
    private double longitude = 0.0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_form);
        restaurantName = findViewById(R.id.restaurant_name);
        restaurantTypes = findViewById(R.id.restaurant_types);

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);
        helper = new RestaurantHelper(this);

        location = findViewById(R.id.location);
        gpsTracker = new GPSTracker(DetailForm.this);

        restaurantID=getIntent().getStringExtra("ID");
        if (restaurantID!=null){
            load();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
        gpsTracker.stopUsingGPS();
    }

    private void load() {
        Cursor c = helper.getById(restaurantID);
        c.moveToFirst();
        restaurantName.setText(helper.getRestaurantName(c));
        restaurantAddress.setText(helper.getRestaurantAddress(c));
        restaurantTel.setText(helper.getRestaurantTel(c));

        if (helper.getRestaurantType(c).equals("Chinese")) {
            restaurantTypes.check(R.id.chinese);
        } else if (helper.getRestaurantType(c).equals("Western")) {
            restaurantTypes.check(R.id.western);
        } else if (helper.getRestaurantType(c).equals("Indian")) {
            restaurantTypes.check(R.id.indian);
        } else if (helper.getRestaurantType(c).equals("Indonesian")) {
            restaurantTypes.check(R.id.indonesian);
        } else if (helper.getRestaurantType(c).equals("Korean")) {
            restaurantTypes.check(R.id.korean);
        } else if (helper.getRestaurantType(c).equals("Japanese")) {
            restaurantTypes.check(R.id.japanese);
        } else {
            restaurantTypes.check(R.id.thai);
        }

        latitude=helper.getLatitude(c);
        longitude= helper.getLongitude(c);
        location.setText(String.valueOf(latitude)+", "+String.valueOf(longitude));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        new MenuInflater(this).inflate(R.menu.details_option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==R.id.get_location){
            if(gpsTracker.canGetLocation()){
                latitude=gpsTracker.getLatitude();
                longitude=gpsTracker.getLongitude();
                location.setText(String.valueOf(latitude)+", "+String.valueOf(longitude));
                // \n is for new line
                Toast.makeText(getApplicationContext(),"Your Location is = \nLat: "+ latitude
                        + "\nLong: " +longitude, Toast.LENGTH_LONG).show();
            }
            return (true);
        }
        return super.onOptionsItemSelected(item);
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
                if (restaurantID==null) {
                    helper.insert(nameStr, addressStr, telStr, restType, latitude, longitude);
                }else {
                    helper.update(restaurantID, nameStr, addressStr, telStr, restType,latitude,longitude);
                }
                //To close current Activity class and exit
                finish();
            }
        }
    };

    private boolean CheckAllFields() {
        boolean check = true;
        if (restaurantName.length() == 0) {
            restaurantName.setError("This field is required");
            check = false;
        }
        if (restaurantAddress.length() == 0) {
            restaurantAddress.setError("This field is required");
            check = false;
        }
        if (restaurantTel.length() == 0) {
            restaurantTel.setError("This field is required");
            check = false;
        }
        // after all validation return true.
        return check;
    }

}


