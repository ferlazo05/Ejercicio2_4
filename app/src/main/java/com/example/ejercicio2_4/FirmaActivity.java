package com.example.ejercicio2_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio2_4.Configuraciones.SQLiteConexion;
import com.example.ejercicio2_4.Configuraciones.Transacciones;
import com.example.ejercicio2_4.Formato.FormatoFirma;
import com.example.ejercicio2_4.Modelado.ModeloFormato;

import java.io.ByteArrayOutputStream;

public class FirmaActivity extends AppCompatActivity {
    FormatoFirma formatoFirma;
    Button btnGuardar, btnLimpiar, btnRegresar;
    EditText TxtDescripcion;
    SQLiteConexion conexion;
    Bitmap imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);
        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE,null,1);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLimpiar();
            }
        });
        TxtDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        formatoFirma = (FormatoFirma) findViewById(R.id.FormatoFirma);
    }

    void dialogLimpiar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FirmaActivity.this);
        builder.setMessage("¿Desea limpiar la firma?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        formatoFirma.nuevoDibujo();
                        TxtDescripcion.setText("");
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {      }
                })
                .show();
    }

    void guardar(){
        if(formatoFirma.getLimpio()){
            showMessage("Debe hacer una firma");
            return;
        }
        if(TxtDescripcion.getText().toString().isEmpty()){
            showMessage("Debe ingresar una descripcion");
            return;
        }
        SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put(Transacciones.DESCRIPCION, TxtDescripcion.getText().toString());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10480);
            Bitmap bitmap = Bitmap.createBitmap(formatoFirma.getWidth(), formatoFirma.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            formatoFirma.draw(canvas);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String img = Base64.encodeToString(bytes,Base64.DEFAULT);
            valores.put(Transacciones.IMAGEN, img);
            Long result = db.insert(Transacciones.NAME_TABLE, Transacciones.ID, valores);
            if (result > 0){
                showMessage("Se registró con éxito!");
                formatoFirma.nuevoDibujo();
                TxtDescripcion.setText(null);
            }else{
                showMessage("Error al registrar");
            }
            db.close();
        }catch (Exception e){
            showMessage("Error al registrar");
            db.close();
        }
    }
    void showMessage(String message){

        Toast.makeText(FirmaActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}