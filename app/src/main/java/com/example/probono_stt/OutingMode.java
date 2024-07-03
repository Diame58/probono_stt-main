package com.example.probono_stt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

public class OutingMode extends Fragment{
    private Interpreter tflite;

    // 생성자에서 모델 로드
    public OutingMode(Context context) {
        try {
            tflite = new Interpreter(loadModelFile(context, "my_model.tflite"));
            Log.d("ModelLoad", "Model is loaded successfully.");
            // 모델 메타데이터 출력
            if (tflite != null) {
                Log.d("ModelLoad", "Input count: " + tflite.getInputTensorCount());
                Log.d("ModelLoad", "Output count: " + tflite.getOutputTensorCount());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // 모델 파일 로드 메서드
    private MappedByteBuffer loadModelFile(Context context, String modelName) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelName);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 예시: 모델을 사용하여 추론 수행
    public void runInference() {
        if (tflite != null) {
            // 여기에서 입력 데이터를 준비하고 tflite.run()을 호출하여 모델을 실행
        }
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.outing_mode, container, false);
        return view;
    }
}
