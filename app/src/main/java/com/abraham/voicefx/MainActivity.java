package com.abraham.voicefx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.topjohnwu.superuser.Shell;
import com.topjohnwu.superuser.internal.ShellImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText commandInput;
    private Button executeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandInput = findViewById(R.id.command_input);
        executeButton = findViewById(R.id.execute_button);

        // Inicializar libsu
        Shell.enableVerboseLogging = true;
        if (!Shell.isAppGrantedRoot()) {
            Toast.makeText(this, "⚠️ No hay acceso root", Toast.LENGTH_SHORT).show();
        }

        executeButton.setOnClickListener(v -> runShellCommand());
    }

    private void runShellCommand() {
        String command = commandInput.getText().toString().trim();

        if (command.isEmpty()) {
            Toast.makeText(this, "Escribe un comando", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar el comando con root
        List<String> output = Shell.cmd(command).exec().getOut();

        // Mostrar el resultado
        StringBuilder result = new StringBuilder();
        for (String line : output) {
            result.append(line).append("\n");
        }

        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }
}