package com.abraham.voicefx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.topjohnwu.superuser.Shell;

public class MainActivity extends AppCompatActivity {

    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elementos de la interfaz
        logView = findViewById(R.id.logView);
        EditText customCommandInput = findViewById(R.id.customCommandInput);

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

        // Acciones por botón
        startPulse.setOnClickListener(v -> runCommand("pulseaudio --start"));
        stopPulse.setOnClickListener(v -> runCommand("pulseaudio --kill"));
        chillVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav pitch 500"));
        robotVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav synth 0.3 square 100"));
        womanVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav pitch 300"));
        manVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav pitch -200"));
        kidVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav pitch 700"));
        girlVoice.setOnClickListener(v -> runCommand("sox -t alsa default output.wav pitch 400"));

        customFilter.setOnClickListener(v -> {
            String command = customCommandInput.getText().toString();
            if (!command.isEmpty()) {
                runCommand(command);
            } else {
                Toast.makeText(this, "Escribe un comando", Toast.LENGTH_SHORT).show();
            }
        });

        startService.setOnClickListener(v -> runCommand("am start-foreground-service com.abraham.voicefx/.VoiceService"));
        stopService.setOnClickListener(v -> runCommand("am stopservice com.abraham.voicefx/.VoiceService"));
    }

    private void runCommand(String command) {
        Shell.Result result = Shell.cmd(command).exec();
        String output = String.join("\n", result.getOut());
        String error = String.join("\n", result.getErr());

        logView.setText("Salida:\n" + output + "\n\nError:\n" + error);
    }
}