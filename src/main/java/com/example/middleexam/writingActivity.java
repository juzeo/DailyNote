package com.example.middleexam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.part3_8.DBHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class writingActivity extends AppCompatActivity {
    TextView currentTheme;
    TextView content;
    boolean fileReadPermission;
    boolean fileWritePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //메뉴창에서 필요한 정보 가져오기
        fileReadPermission=((menuActivity)menuActivity.context).fileReadPermission;
        fileWritePermission=((menuActivity)menuActivity.context).fileWritePermission;
        setContentView(R.layout.activity_writing);
        currentTheme= findViewById(R.id.TodayTheme);
        String ClickTheme=((menuActivity)menuActivity.context).ClickTheme;
        String todayTheme = ((menuActivity)menuActivity.context).todayTheme;

        //Editor에 focus주고 키보드 열기
        content = findViewById(R.id.editText);
        content.requestFocus();

        InputMethodManager keybord = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keybord.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        //주제 이름 바꿔주기
            currentTheme.setText("\n"+ClickTheme+"\n");
            //저장된 파일 받아오기
           File file= new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()+"/myApp/"+currentTheme.getText().toString()+".txt");
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();
                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                    buffer.append("\n");
                }
                content.setText(buffer.toString());
                reader.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

//            DBHelper helper=new DBHelper(this);
//            SQLiteDatabase db=helper.getReadableDatabase();
//            Cursor cursor=  db.rawQuery("select title,content from tb_memo order by _id desc limit 1",null);
//
//            while(cursor.moveToNext()){
//                String theme = cursor.getString(cursor.getColumnIndex("title"));
//
//                currentTheme.setText(cursor.getString(0));
//                content.setText(cursor.getString(1));
////                Toast.makeText(getApplicationContext(),cursor.getString(0)+cursor.getString(1),Toast.LENGTH_LONG).show();
////                Toast.makeText(getApplicationContext(),
////                      cursor.getString(1), Toast.LENGTH_SHORT).show();
//            }


        Toolbar toolbar=findViewById(R.id.WritingToolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setTitle("");
    }
    //나가면
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
            super.onBackPressed();
            content=findViewById(R.id.editText);
            String theme = (currentTheme.getText()).toString();

            if(fileWritePermission&&fileWritePermission) {
                FileWriter writer;
                try{
                    String dirPath= Environment.getExternalStorageDirectory()
                            .getAbsolutePath()+"/myApp";

                    File dir = new File(dirPath);

                    if(!dir.exists()){
                        dir.mkdir();

                    }

                    File file = new File(dir +"/"+theme+".txt");

                    file.delete();
                    file.createNewFile();

                      writer = new FileWriter(file,true);

                    writer.write(content.getText().toString());
                    writer.flush();
                    writer.close();
                    Toast.makeText(getApplicationContext(),"저장이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    showToast(e.getMessage());
                    e.printStackTrace();
                    e.toString();
                    e.getMessage();
                }
            }else{
            }

//                DBHelper helper = new DBHelper(this);
//                SQLiteDatabase db = helper.getWritableDatabase();
//
//
//                db.execSQL("INSERT into tb_memo (data, title,content) VALUES (?,?,?)",
//                new String[]{time,theme,Content});
            finish();

        }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }
    float initX;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            initX=event.getRawX();

        }
        else if(event.getAction()==MotionEvent.ACTION_UP){
            float diffx=initX-event.getRawX();
            if(diffx>30){
                onBackPressed();
            }
        }
        return  true;
    }
}