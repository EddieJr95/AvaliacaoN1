package com.example.avaliacaon1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DecoderActivity extends AppCompatActivity {

    EditText inputTexto;
    Button btnCodificar, btnDecodificar, bVoltar;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decoder_activity);

        inputTexto = findViewById(R.id.inputTexto);
        btnCodificar = findViewById(R.id.btnCodificar);
        btnDecodificar = findViewById(R.id.btnDecodificar);
        bVoltar = findViewById(R.id.bVoltar);
        resultado = findViewById(R.id.resultado);

        btnCodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoOriginal = inputTexto.getText().toString();
                String textoCodificado = codificarTexto(textoOriginal, 12);
                resultado.setText(textoCodificado);
                esconderTeclado();
            }
        });

        btnDecodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoCodificado = resultado.getText().toString();
                String textoDecodificado = decodificarTexto(textoCodificado, 12);
                resultado.setText(textoDecodificado);
                esconderTeclado();
            }
        });

        resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Texto Copiado", resultado.getText());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(DecoderActivity.this, "Texto copiado", Toast.LENGTH_SHORT).show();
            }
        });

        bVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String codificarTexto(String texto, int chave) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            char novoCaracter;

            if (Character.isLetter(caracter)) {
                int base = Character.isUpperCase(caracter) ? 'A' : 'a';
                novoCaracter = (char) (((caracter - base + chave) % 26) + base);
            } else {
                novoCaracter = caracter;
            }
            resultado.append(novoCaracter);
        }

        return resultado.toString();
    }

    private String decodificarTexto(String texto, int chave) {
        return codificarTexto(texto, 26 - chave);
    }

    private void esconderTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
