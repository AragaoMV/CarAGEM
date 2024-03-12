package com.marcos.project1andorid2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.marcos.project1andorid2.R;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText marca, modelo, ano, cor, placa, nomeProprietario, obs;
    Button btnCadastraCarro, btnCancelaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        marca = findViewById(R.id.etMarca);
        modelo = findViewById(R.id.etModelo);
        ano = findViewById(R.id.etAno);
        cor = findViewById(R.id.etCor);
        placa = findViewById(R.id.etPlaca);
        nomeProprietario = findViewById(R.id.etNomeProprietario);
        obs = findViewById(R.id.etObs);
        btnCadastraCarro = findViewById(R.id.btnCadastraCarro);
        btnCancelaCadastro = findViewById(R.id.btnCancela);

        btnCadastraCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                finish();
            }
        });
        btnCancelaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("modelo", modelo.getText().toString());
        map.put("marca", marca.getText().toString());
        map.put("ano", ano.getText().toString());
        map.put("cor", cor.getText().toString());
        map.put("placa", placa.getText().toString());
        map.put("nomeProprietario", nomeProprietario.getText().toString());
        map.put("obs", obs.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("cars").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddActivity.this, "Carro cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddActivity.this, "Erro ao cadastrar o carro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}