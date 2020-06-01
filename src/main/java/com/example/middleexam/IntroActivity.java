package com.example.middleexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class IntroActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme_Light_NoTitleBar);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //제목 불러오기
        TextView introText = findViewById(R.id.introText);
        //애니메이션 불러오기
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.intro);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //애니메이션이 끝나면
            @Override
            public void onAnimationEnd(Animation animation) {


                Intent intent = new Intent(getApplicationContext(), menuActivity.class);
                //다음 화면으로 넘어갈때 이번화면 지우기(뒤로가기버튼 누를면 바로 나가지게)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //다음 화면으로 넘어갈때 애니메이션 없애기
                overridePendingTransition(0,0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //애니메이션 끝나고 가만히 있기
        anim.setFillAfter(true);
        //텍스트에 애니메이션 적용
        introText.startAnimation(anim);

    }
}
