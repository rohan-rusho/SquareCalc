package com.easyes.countpro.tool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText heightInput, weightInput, optionalInput;
    Button calculateButton, shareButton;
    TextView resultText;

    String lastResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        optionalInput = findViewById(R.id.optionalInput);

        calculateButton = findViewById(R.id.calculateButton);
        shareButton = findViewById(R.id.shareButton);
        resultText = findViewById(R.id.resultText);

        // Initially hide the share button
        shareButton.setVisibility(View.GONE);

        calculateButton.setOnClickListener(v -> calculateSquare());
        shareButton.setOnClickListener(v -> shareResult());
    }

    private void calculateSquare() {
        String heightStr = heightInput.getText().toString();
        String weightStr = weightInput.getText().toString();
        String depthStr = optionalInput.getText().toString();

        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {
            resultText.setText("Please enter both height and weight.");
            shareButton.setVisibility(View.GONE);
            return;
        }

        try {
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            String result;
            if (TextUtils.isEmpty(depthStr)) {
                double area = height * weight;
                result = "Area: " + area;
            } else {
                double depth = Double.parseDouble(depthStr);
                double volume = height * weight * depth;
                result = "Volume: " + volume;
            }
            resultText.setText(result);
            lastResult = result;
            shareButton.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            resultText.setText("Invalid input. Please enter valid numbers.");
            shareButton.setVisibility(View.GONE);
        }
    }

    private void shareResult() {
        if (!TextUtils.isEmpty(lastResult)) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "My Square Finder result: " + lastResult);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
}