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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.marcos.project1andorid2.R;
import com.marcos.project1andorid2.Util.Auth;
import com.marcos.project1andorid2.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    EditText loginMail,loginSenha;
    private FirebaseAuth auteticacao;
    Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auteticacao = Auth.autenticacao();
        inicializarDados();
    }
    public void inicializarDados(){
        loginMail = findViewById(R.id.etLoginMail);
        loginSenha = findViewById(R.id.etLoginPassword);
        botaoLogin = findViewById(R.id.btnLogin);
    }
    public void validaLogin(View view){
        String email = loginMail.getText().toString();
        String senha = loginSenha.getText().toString();
            if (!email.isEmpty()) {
                if (!senha.isEmpty()){
                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    
                    logar(usuario);
                }else{
                    Toast.makeText(this, "Insira uma Senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Insira um Email", Toast.LENGTH_SHORT).show();
            }
        }

    private void logar(Usuario usuario) {
        auteticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    entrar();
                }else {
                    String excecao="";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Email não cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e ){
                        excecao = "Email ou senha incorreto";
                    }catch (Exception e){
                        excecao = "Erro ao entrar "+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAuth = auteticacao.getCurrentUser();
        if (usuarioAuth!=null){
            entrar();
        }
    }

    public void criarCadastro(View view){
        Intent i = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(i);
    }

    private void entrar(){
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
    }

}