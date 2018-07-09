package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    TextView tvDate;
    TextView tvBMI;
    Button btnCalculate;
    Button btnReset;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etHeight=findViewById(R.id.editTextHeight);
        etWeight=findViewById(R.id.editTextWeight);
        tvDate=findViewById(R.id.textViewDate);
        tvBMI=findViewById(R.id.textViewBMI);
        btnCalculate=findViewById(R.id.buttonCalculate);
        btnReset=findViewById(R.id.buttonReset);
        tvResult=findViewById(R.id.textViewresult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                String strWeight = etWeight.getText().toString();
                String strHeight = etHeight.getText().toString();
                float floatWeight = Float.parseFloat(strWeight);
                float floatHeight = Float.parseFloat(strHeight);
                float bmi = floatWeight/floatHeight/floatHeight;
                String strBMI = String.valueOf(bmi);
                tvBMI.setText("Last Calculated BMI: "+strBMI);
                tvDate.setText("Last Calculated Date: "+datetime);
                if(bmi<18.5){
                    tvResult.setText("You are underweight");
                }else if(bmi>=18.5 && bmi<24.9){
                    tvResult.setText("Your BMI is normal");
                }else if(bmi>=24.9 && bmi <29.9){
                    tvResult.setText("You are overweight");

                }else if(bmi>=29.9){

                    tvResult.setText("You are obese");
                }
            }
        });
        final SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etHeight.setText(null);
                etWeight.setText(null);
                tvBMI.setText("Last Calculated BMI: 0.0");
                tvDate.setText("Last Calculated Date:");
                tvResult.setText("");
                prefs.edit().clear().commit();


            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!etWeight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty()) {
            Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH) + 1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);
            String strWeight = etWeight.getText().toString();
            String strHeight = etHeight.getText().toString();
            String result = tvResult.getText().toString();
            float floatWeight = Float.parseFloat(strWeight);
            float floatHeight = Float.parseFloat(strHeight);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putFloat("Weight", floatWeight);
            prefEdit.putFloat("Height", floatHeight);
            prefEdit.putString("Date", datetime);
            prefEdit.putString("Result", result);
            prefEdit.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float weight = prefs.getFloat("Weight",0.0f);
        float height = prefs.getFloat("Height",0.1f);
        String strDate = prefs.getString("Date","");
        String result = prefs.getString("Result","");
        float bmi = weight/height/height;
        String strBMI = String.valueOf(bmi);
        tvBMI.setText("Last Calculated BMI: "+strBMI);
        tvDate.setText("Last Calculated Date: "+strDate);
        tvResult.setText(result);





    }
}
