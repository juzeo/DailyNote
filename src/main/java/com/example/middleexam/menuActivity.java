package com.example.middleexam;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.part3_8.DBHelper;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class menuActivity extends AppCompatActivity {

    public String ClickTheme; //누른 주제
    public String todayTheme; //오늘의 주제
    public static Context context;

    //퍼미션
    public boolean fileReadPermission;
    public boolean fileWritePermission;


    Random r = new Random();



//    class themeList {
//        String data;
//        String theme;
//    }

    //RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //오늘 날짜 얻기
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");

        String Date = simpleDateFormat.format(date);

        context = this;



//        SharedPreferences prefs = getPreferences(context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//
//
//        if(prefs.contains(Date)==false){//겹치지 않으면
//
//
//            editor.putString(Date,CheckTheme());  //오늘 날짜에 랜덤한 주제 넣기
//            editor.apply();
//            editor.commit(); //닫기
//        }


        //하단 글 목록
//        RecyclerView recyclerView = findViewById(R.id.recycler) ;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
//        layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//
//

        //오늘의 주제 설정
        TextView theme = findViewById(R.id.OnDayTheme); //오늘의 주제
        ListView listView = findViewById(R.id.customList);
        ArrayList<ThemeList> themeListArrayList = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select data, title from tb_memo", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        boolean insertable = true;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(Date)) {
                insertable = false;
                theme.setText( cursor.getString(1));
            }

        }
        if (insertable== true) {
            theme.setText(CheckTheme());//오늘의 주제 텍스트 바꾸기
            todayTheme = theme.getText().toString();
            db.execSQL("insert into tb_memo(data,title) values(?,?)",
                    new String[]{Date, todayTheme});
        }


        cursor = db.rawQuery("select data, title from tb_memo", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        //저장된 값 불러오기
        while (cursor.moveToNext()) {
            ThemeList list = new ThemeList("", "");
            list.data = cursor.getString(0);
            list.theme = cursor.getString(1);
            themeListArrayList.add(list);
            //Toast.makeText(getApplicationContext(),list.data,Toast.LENGTH_LONG).show();

        }
        Collections.reverse(themeListArrayList);
        db.close();
        Adapter adapter = new Adapter(this, R.layout.block, themeListArrayList);
        listView.setAdapter(adapter);
//        ListView listView=findViewById(R.id.customList);
//        ArrayList<ThemeList> themeListArrayList= new ArrayList<>();
//        //모든 저장 값 가져오기
//        Map<String,?> keys = prefs.getAll();
//        Iterator<String> iterator=keys.keySet().iterator();
//
//        while(iterator.hasNext()){
//            String key = iterator.next();
//            String value = keys.get(key).toString();
//            themeListArrayList.add(new ThemeList(key, value));
//            Toast.makeText(getApplicationContext(),key,Toast.LENGTH_LONG).show();
//        }
//
//        //Collections.reverse(themeListArrayList);
//
//        Adapter adapter=new Adapter(this,R.layout.block,themeListArrayList);
//        listView.setAdapter(adapter);


        //퍼미션 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            fileReadPermission = true;

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

            fileWritePermission = true;

        }
        if (!fileReadPermission || !fileWritePermission) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }


    }

    //글쓰기 버튼
    public void onClick(View v) {
        //클릭한 주제로  글쓰기 가능하게 이동
        TextView theme = findViewById(R.id.OnDayTheme);
        ClickTheme = theme.getText().toString();
        Intent intent = new Intent(getApplicationContext(), writingActivity.class);

        startActivity(intent);

    }

    //ListView 클릭
    public void pastClick(View v) {
        //클릭한 주제로  글쓰기 가능하게 이동
        TextView theme = v.findViewWithTag("theme");
        ClickTheme = theme.getText().toString();
        Intent intent = new Intent(getApplicationContext(), writingActivity.class);

        startActivity(intent);
    }

    public String CheckTheme() {
        //주제 가져오기
        Resources resources = getResources();
        String[] themes = resources.getStringArray(R.array.themes);
        //랜덤한 주제
        String RandomTheme = themes[r.nextInt(themes.length - 1)];
        //저장된 정보 불러오기
////        SharedPreferences prefs = getPreferences(context.MODE_PRIVATE);
////        ArrayList<ThemeList> themeListArrayList = new ArrayList<>();
////        Map<String, ?> keys = prefs.getAll();
////        Iterator<String> iterator = keys.keySet().iterator();
////        while (iterator.hasNext()) {
////            String key = iterator.next();
////            String value = keys.get(key).toString();
////            themeListArrayList.add(new ThemeList(key, value));
////        }
        DBHelper helper= new DBHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select data, title from tb_memo", null);

        ArrayList<ThemeList> themeListArrayList= new ArrayList<>();
        while(cursor.moveToNext()){
            ThemeList list = new ThemeList("","");
            list.data=cursor.getString(0);
            list.theme=cursor.getString(1);
            themeListArrayList.add(list);
        }

//        //겹치면 다시 랜덤한 걸로
        for (int i = 0; i < themeListArrayList.size(); i++) {
            //Toast.makeText(getApplicationContext(),RandomTheme,Toast.LENGTH_SHORT).show();
            if (RandomTheme.equals(themeListArrayList.get(i).theme)) {

                i = -1;
                RandomTheme = themes[r.nextInt(themes.length - 1)];
            }

        }
        return RandomTheme;
    }

    @Override
    public void onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("이야깃거리를 종료하시겠습니까?\n");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.show();


    }


}
