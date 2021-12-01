package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.RadioGroup;

public class Order extends AppCompatActivity {
    Toolbar t ;
    RadioGroup r;
    Fragment f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        t=findViewById(R.id.order_act_tbar);
        t.setTitle("Orders activity");
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        r=findViewById(R.id.radio_group_order_activity);
        f=new ProgessOrderFragement();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_order_activity,f).commit();
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.in_progress_btn:
                        f=new ProgessOrderFragement();
                        break;
                    case R.id.completed_btn:
                        f=new CompletedOrderFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_order_activity,f).commit();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}