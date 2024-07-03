package com.example.probono_stt;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView voiceText;
    private TextView emotionDisplay;
    private SpeechRecognizer speechRecognizer;

    // 2024-07-03 add code 화면 전환
    private BottomNavigationView bottomNavigationView; // bottom navigation view
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LifeMode lifeMode;
    private OutingMode outingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        lifeMode = new LifeMode();
        outingMode = new OutingMode(this);

        /**
         * voiceText -> "회색 박스" 텍스트 : 음성 인식 내용 텍스트 표시
         * emotionDisplay -> 감성 분석 결과 텍스트 : 추 후 API 사용해서 연동.
         * startVoiceRecognitionButton -> "음성 인식" 클릭 버튼
         */
        voiceText = findViewById(R.id.voiceText);
        emotionDisplay = findViewById(R.id.emotionDisplay); // "노란색 박스" 텍스트
        Button startVoiceRecognitionButton = findViewById(R.id.startVoiceRecognitionButton); // "음성 인식" 버튼

        // 버튼 클릭 시 음성 인식 시작
        startVoiceRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceTask voiceTask = new VoiceTask();
                voiceTask.execute();
            }
        });
    }

    /* private void setFrag(int n) {
        fragmentManager = getSu
    } */

    // 2024-07-02 add code
    // 음성 인식 후
    public class VoiceTask extends AsyncTask<String, Integer, String> {
        String str = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                getVoice();
            } catch (Exception e) {
                // TODO: Handle exception
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL Exception");
            }
        }
    }

    // 2024-07-02 add code
    // 음성 인식 기능 수행 메서드
    private void getVoice() {
        Intent intent = new Intent(); // 음성 인식 Activity 호출
        // 어떠한 언어 모델, 폼 형태를 사용할 것 인지 명시
        // -> 한국어 모델로 설정
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        String language = "ko-KR";
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        // 음성 인식 창 호출
        startActivityForResult(intent, 2);
    }

    // onActivityResult : startActivityForResult 함수가 호출된 뒤 자동으로 호출되는 함수
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String str = result.get(0);
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

            TextView tv = findViewById(R.id.voiceText);
            tv.setText(str);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}