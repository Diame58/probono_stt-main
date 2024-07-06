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

    public OutingMode(Context context) {
        try {
            tflite = new Interpreter(loadModelFile(context, "my_model.tflite"));
            Log.d("ModelLoad", "Model is loaded successfully.");

            if (tflite != null) {
                Log.d("ModelLoad", "Input count: " + tflite.getInputTensorCount());
                Log.d("ModelLoad", "Output count: " + tflite.getOutputTensorCount());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private MappedByteBuffer loadModelFile(Context context, String modelName) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelName);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private float[][] runInference(float[] inputData) {
        // 모델의 출력 데이터 크기를 미리 정의해야 합니다.
        float[][] outputData = new float[1][NUMBER_OF_OUTPUT_CLASSES];
        tflite.run(inputData, outputData);
        return outputData;
    }

    public void runInference() {
        if (tflite != null) {
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
