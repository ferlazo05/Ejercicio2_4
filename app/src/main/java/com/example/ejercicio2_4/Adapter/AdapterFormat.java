package com.example.ejercicio2_4.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio2_4.R;
import com.example.ejercicio2_4.Modelado.ModeloFormato;

import java.util.List;

public class AdapterFormat extends RecyclerView.Adapter<AdapterFormat.ViewHolder> {

    private List<ModeloFormato> modeloFormato;

    public AdapterFormat(List<ModeloFormato> modeloFormato) {
        this.modeloFormato = modeloFormato;
    }

    @NonNull
    @Override
    public AdapterFormat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new AdapterFormat.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFormat.ViewHolder holder, int position) {
        holder.setData(modeloFormato.get(position));
    }

    @Override
    public int getItemCount() {
        return modeloFormato.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView Imagen;
        TextView TvDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Imagen =  itemView.findViewById(R.id.imageViewCardView);
            TvDescripcion = itemView.findViewById(R.id.textDescripcionCardView);
        }

        void setData(ModeloFormato signature){
            TvDescripcion.setText(signature.descripcion);
            Imagen.setImageBitmap(decodeImage(signature.imagen));
        }
    }

    //Formato de imagen
    private static Bitmap decodeImage(String encodedImage){
        byte[] bytes = android.util.Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
