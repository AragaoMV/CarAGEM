package com.marcos.project1andorid2.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserCheck {
    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = Auth.autenticacao();
        return usuario.getCurrentUser();
    }
}
