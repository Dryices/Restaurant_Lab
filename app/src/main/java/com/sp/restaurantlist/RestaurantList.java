package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sp.com.restaurantlist.R;

public class RestaurantList extends AppCompatActivity {
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private List<Restaurant> model = new ArrayList<Restaurant>();
    private RestaurantAdapter adapter=null;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        restaurantName = findViewById(R.id.restaurant_name);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);
        restaurantTypes = findViewById(R.id.restaurant_types);
        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        list = findViewById(R.id.restaurants);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            //String combineStr = nameStr + "\n" + addressStr + "\n" + telStr + "\n" + restType;
            //Toast.makeText(v.getContext(),combineStr,Toast.LENGTH_LONG).show();
            Restaurant restaurant = new Restaurant();
            restaurant.setName(nameStr);
            restaurant.setAddress(addressStr);
            restaurant.setTelephone(telStr);
            restaurant.setRestaurantType(restType);
            //Pass the record to ArrayAdapter
            //It will update the ListArray and the ListView
            adapter.add(restaurant);

            String combineStr = nameStr + "\n" +addressStr+ "\n" + telStr + "\n" + restType;
            Toast.makeText(v.getContext(), combineStr, Toast.LENGTH_LONG).show();

        }
    };
    static class RestaurantHolder {
        private TextView restName= null;
        private TextView addr= null;
        private TextView icon= null;
        RestaurantHolder(View row){
            restName=row.findViewById(R.id.restName);
            addr=row.findViewById(R.id.restAdr);
            icon=row.findViewById(R.id.icon);
        }
        void populateFrom(Restaurant r){
            restName.setText(r.getName());
            addr.setText(r.getAddress());
            if(r.getRestaurantType().equals("Chinese")) {
                icon.setImageResource(R.drawable.ball_red);
            }
            else if(r.getRestaurantType().equals("Western"))
                icon.setImageResource(R.drawable.ball_yellow);
            else {
                icon.setImageResource(R.drawable.ball_green);
            }
        }
    }
     class RestaurantAdapter extends ArrayAdapter<Restaurant>{
        RestaurantAdapter(){super (RestaurantList.this,R.layout.row,model);}
        @Override
         public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView;
            RestaurantHolder holder;
            if(row==null){
                LayoutInflater inflater = getLayoutInflater();
                row=inflater.inflate(R.layout.row, parent,false);
                holder=new RestaurantHolder(row);
                row.setTag(holder);
            }else {
                holder=(RestaurantHolder)row.getTag();
            }
                holder.populateFrom(model.get(position));
                return (row);
            }
        }
     }


