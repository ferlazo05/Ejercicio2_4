package com.example.ejercicio2_4.Configuraciones;

public class Transacciones {

    public static final String NAME_DATABASE = "Ejercicio2_4";
    public static final String NAME_TABLE = "firmas";
    public static final String ID = "id";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";

    public static final String CREATE_TABLE_FIRMAS = "CREATE TABLE "+ NAME_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            DESCRIPCION + " TEXT, " +
            IMAGEN + " BLOB)";

    public static final String DROP_TABLE_FIRMAS = "DROP TABLE IF EXISTS "+ NAME_TABLE;
}