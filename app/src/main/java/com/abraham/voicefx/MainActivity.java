package com.abraham.voicefx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.topjohnwu.superuser.Shell;

import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TextView logView;
    EditText customCommandInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logView = findViewById(R.id.logView);
        customCommandInput = findViewById(R.id.customCommandInput);

        Button startPulse = findViewById(R.id.startPulse);
        Button stopPulse = findViewById(R.id.stopPulse);
        Button chillVoice = findViewById(R.id.chillVoice);
        Button robotVoice = findViewById(R.id.robotVoice);
        Button womanVoice = findViewById(R.id.womanVoice);
        Button manVoice = findViewById(R.id.manVoice);
        Button kidVoice = findViewById(R.id.kidVoice);
        Button girlVoice = findViewById(R.id.girlVoice);
        Button customFilter = findViewById(R.id.customFilter);

        startPulse.setOnClickListener(v -> runOptimized("pulseaudio --start --exit-idle-time=-1 --realtime=yes"));

        stopPulse.setOnClickListener(v -> runOptimized("pulseaudio --kill"));

        chillVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch +1200"));

        robotVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch -500 tremolo 40 40"));

        womanVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch +400"));

        manVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch -400"));

        kidVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch +800"));

        girlVoice.setOnClickListener(v -> runOptimized("sox -q -d -d pitch +600"));

        customFilter.setOnClickListener(v -> {
            String userCommand = customCommandInput.getText().toString();
            if (!userCommand.isEmpty()) {
                runOptimized(userCommand);
            }
        });

        Button startService = findViewById(R.id.startService);
        startService.setOnClickListener(v -> startForegroundService(new Intent(MainActivity.this, VoiceService.class)));

        Button stopService = findViewById(R.id.stopService);
        stopService.setOnClickListener(v -> stopService(new Intent(MainActivity.this, VoiceService.class)));
    }

    private void runOptimized(String command) {
        new Thread(() -> {
            Shell.Result result = Shell.su(command).exec();
            String output = result.getOut().toString() + "\n" + result.getErr().toString();
            runOnUiThread(() -> logView.setText(output));
        }).start();
    }
}
