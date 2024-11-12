package com.example.apputnsqlite;

public class Autor {
    public int Id;
    public String Nombres;
    public String Apellidos;
    public String IsoPais;
    public int Edad;

    public Autor(int id, String nombres, String apellidos, String isoPais, int edad) {
        Id = id;
        Nombres = nombres;
        Apellidos = apellidos;
        IsoPais = isoPais;
        Edad = edad;
    }
}
