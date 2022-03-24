package com.example.ejercicio2_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ejercicio2_4.Adapter.AdapterFormat;
import com.example.ejercicio2_4.Configuraciones.SQLiteConexion;
import com.example.ejercicio2_4.Configuraciones.Transacciones;
import com.example.ejercicio2_4.Modelado.ModeloFormato;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    SQLiteConexion conexion;
    RecyclerView recyclerView;
    List<ModeloFormato> formatoList;
    List<String> formatoListString;
    Button btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnRegresar = (Button) findViewById(R.id.btnRegresarLista);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        formatoList = new ArrayList<>();
        formatoListString = new ArrayList<>();
        getSignaturessForDatabase();
        AdapterFormat adapter = new AdapterFormat(formatoList);
        recyclerView.setAdapter(adapter);
    }
    private void getSignaturessForDatabase() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        ModeloFormato modeloFormato = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.NAME_TABLE,null);

        while (cursor.moveToNext()){
            modeloFormato = new ModeloFormato();
            modeloFormato.id = cursor.getInt(0);
            modeloFormato.descripcion = cursor.getString(1);
            modeloFormato.imagen = cursor.getString(2);
            formatoList.add(modeloFormato);
            formatoListString.add(modeloFormato.descripcion);
        }
    }
}