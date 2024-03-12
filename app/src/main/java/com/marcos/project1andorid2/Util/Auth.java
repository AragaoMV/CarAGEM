package com.marcos.project1andorid2.Util;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {
    private static FirebaseAuth auth;

    public static FirebaseAuth autenticacao(){
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
