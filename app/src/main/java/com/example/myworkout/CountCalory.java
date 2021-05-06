package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CountCalory extends AppCompatActivity {
    String[] activ = { "Малоподвижный образ жизни, спорт отсутствует", "Малая активность, ходьба",
            "Средний уровень активности", "Высокая активность, ежедневные занятия спортом", "Экстремальная активность, занятия с весом"};
    private Spinner spinner;
    private EditText editTextAge,editTextHeight,editTextWeight;
    private Button bRes;
    private TextView textViewRes;
    private RadioButton radioButton, radioButton2;
    private RadioGroup radioGroup;
    public int age,k,G;
    public double weight,height,result,A;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_calory);
        spinner = findViewById(R.id.spinner);
        editTextAge=findViewById(R.id.editTextA);
        editTextHeight=findViewById(R.id.editTextH);
        editTextWeight=findViewById(R.id.editTextW);
        bRes=findViewById(R.id.button);
        textViewRes=findViewById(R.id.textViewResult);
        radioGroup=findViewById(R.id.radioGroup);
        radioButton=findViewById(R.id.radioButtonF);
        radioButton2=findViewById(R.id.radioButtonM);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activ);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        bRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioButtonF:
                                G=2;
                                break;
                            case R.id.radioButtonM:
                                G=1;
                                break;
                            case -1:
                                G=0;
                                break;
                        }
                    }
                });
                AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String)parent.getItemAtPosition(position);
                        System.out.println(item);
                        k = parent.getSelectedItemPosition();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
                spinner.setOnItemSelectedListener(itemSelectedListener);
                System.out.println(k);
                if (k==0)  A = 1.2;
                else if (k==1) A=1.37;
                else if (k==2) A=1.55;
                else if (k==3) A=1.7;
                else if (k==4) A=1.9;

                String heightS=editTextHeight.getText().toString();
                if (heightS.trim().length()!=0){
                    height=Double.parseDouble(heightS);
                }
                if (heightS.isEmpty()){
                    editTextHeight.setError("Введите рост");
                    editTextHeight.requestFocus();
                    return;
                }

                String weightS=editTextWeight.getText().toString();
                System.out.println(weightS);
                if (weightS.trim().length()!=0){
                    weight=Double.parseDouble(weightS);
                }
                if (weightS.isEmpty()){
                    editTextWeight.setError("Введите вес");
                    editTextWeight.requestFocus();
                    return;
                }



                String ageS = editTextAge.getText().toString();
                if (ageS.trim().length()!=0){
                    age=Integer.parseInt(ageS);
                }
                if (ageS.isEmpty()){
                    editTextAge.setError("Введите рост");
                    editTextAge.requestFocus();
                    return;
                }

                if (G==2){
                    result=(9.99*weight+6.25*height-4.92*age-161)*A; System.out.println(result);
                    //System.out.println(result);
                    System.out.println(A);
                    //System.out.println(age);
                    //System.out.println(height);
                    //System.out.println(weight);
                    Toast.makeText(CountCalory.this,"Female",Toast.LENGTH_LONG).show();
                    textViewRes.setText(String.valueOf(Math.ceil(result)));
                }
                else if (G==1){
                    result=(9.99*weight+6.25*height-4.92*age+5)*A;
                    System.out.println(result);
                    System.out.println(A);
                    //System.out.println(age);
                    //System.out.println(height);
                    //System.out.println(weight);


                    Toast.makeText(CountCalory.this,"Male",Toast.LENGTH_LONG).show();
                    textViewRes.setText(String.valueOf(Math.ceil(result)));
                }
                else if (G==0){
                    Toast.makeText(CountCalory.this,"Выберете пол",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}