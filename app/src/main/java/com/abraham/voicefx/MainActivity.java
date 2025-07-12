package com.abraham.voicefx;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.topjohnwu.superuser.Shell;

public class MainActivity extends AppCompatActivity {

    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startPulse = findViewById(R.id.startPulse);
        Button stopPulse = findViewById(R.id.stopPulse);
        Button chillVoice = findViewById(R.id.chillVoice);
        Button robotVoice = findViewById(R.id.robotVoice);
        Button womanVoice = findViewById(R.id.womanVoice);
        Button manVoice = findViewById(R.id.manVoice);
        Button kidVoice = findViewById(R.id.kidVoice);
        Button girlVoice = findViewById(R.id.girlVoice);
        Button customFilter = findViewById(R.id.customFilter);
        Button startService = findViewById(R.id.startService);
        Button stopService = findViewById(R.id.stopService);
        EditText customCommandInput = findViewById(R.id.customCommandInput);
        logView = findViewById(R.id.logView);

        startPulse.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/pulseaudio --start --exit-idle-time=-1"));
        stopPulse.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/pulseaudio --kill"));

        chillVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null pitch 1000"));
        robotVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null synth sin 440 gain -n"));
        womanVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null pitch 400"));
        manVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null pitch -400"));
        kidVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null pitch 700"));
        girlVoice.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/sox -t pulseaudio default -t pulseaudio null pitch 500"));

        customFilter.setOnClickListener(v -> {
            String customCmd = customCommandInput.getText().toString();
            if (!customCmd.isEmpty()) runCommand("/data/data/com.termux/files/usr/bin/" + customCmd);
        });

        startService.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/pactl load-module module-null-sink sink_name=voicefx"));
        stopService.setOnClickListener(v -> runCommand("/data/data/com.termux/files/usr/bin/pactl unload-module module-null-sink"));
    }

    private void runCommand(String command) {
        String result = Shell.cmd(command).exec().getOut().toString();
        logView.setText(result);
    }
}