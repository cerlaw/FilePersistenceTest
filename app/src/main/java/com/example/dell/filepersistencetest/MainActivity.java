package com.example.dell.filepersistencetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText) findViewById(R.id.edit);
        String string = load();
        if(!TextUtils.isEmpty(string)){
            edit.setText(string);
            edit.setSelection(string.length());
            Toast.makeText(this,"Restoring succeeded!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputString = edit.getText().toString();
        save(inputString);
    }

    public void save(String string){
        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try{
            fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(
                    fileOutputStream));
            writer.write(string);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String load(){
        FileInputStream in = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            in = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String reader = "";
            while((reader = bufferedReader.readLine()) != null){
                stringBuilder.append(reader);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}
