package com.marcos.project1andorid2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.marcos.project1andorid2.R;
import com.marcos.project1andorid2.Util.Auth;
import com.marcos.project1andorid2.model.Usuario;

public class CadastroActivity extends AppCompatActivity {
    Usuario usuario;
    FirebaseAuth loginAutenticacao;
    EditText edtNome,edtEmail,edtSenha;
    Button botaoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();
    }

    private void inicializar(){
        edtNome = findViewById(R.id.etName);
        edtEmail = findViewById(R.id.etMail);
        edtSenha = findViewById(R.id.etPassword);
        botaoCadastro = findViewById(R.id.btnCadastro);
    }

    public void validaCampo(View view){
        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        if (!nome.isEmpty()){
            if (!email.isEmpty()) {
                if (!senha.isEmpty()){

                    //criar Cadastro
                    usuario = new Usuario();

                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    cadastrarUsuario();


                }else{
                    Toast.makeText(this, "Insira uma Senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Insira um Email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Insira um Nome", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarUsuario() {
        loginAutenticacao = Auth.autenticacao();
        loginAutenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Cadastro criado com Sucesso", Toast.LENGTH_SHORT).show();
                    paginaLogin();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao= "A senha deve conter no mínimo 6 digitos";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao="Email invalido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao="Esta conta já existe";
                    }catch (Exception e){
                        excecao="Erro ao cadastrar o usuario"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void paginaLogin() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}