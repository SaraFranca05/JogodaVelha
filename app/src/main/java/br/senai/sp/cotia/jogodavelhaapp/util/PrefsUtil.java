package br.senai.sp.cotia.jogodavelhaapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsUtil {
    public static String getSimboloJog1(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("simbolo_jog1", "X");
    }

    public static String getSimboloJog2(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("simbolo_jog2", "O");
    }

    public static String getNomeJog1(Context context) {
        SharedPreferences nome = PreferenceManager.getDefaultSharedPreferences(context);
        return nome.getString("name_jog1", "Jogador 1");
    }

    public static String getNomeJog2(Context context) {
        SharedPreferences nome = PreferenceManager.getDefaultSharedPreferences(context);
        return nome.getString("name_jog2", "Jogador 2");
    }

    public static void setSimboloJog1(String simbolo, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("simbolo_jog_1", simbolo);
        editor.commit();
    }

    public static void setNomeJog1(String nome, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nome_jog_1", nome);
        editor.commit();
    }
}



