package com.example.apputnsqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtApellidos, txtNombres, txtIsoPais, txtEdad;
    Autores lstAutores;
    TextView lblTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Autores listaAutores = new Autores(this, "biblioteca.db", 1);
//        Autor r = listaAutores.Create(1, "AMADO", "NERVO", "CL", 100);
//        Autor r = listaAutores.Update(1, "AMADO  EULALIO", "NERVO PAREDES", "EC", 45);
//        Autor r = listaAutores.Read_ById(1);
//        boolean r = listaAutores.Delete(1);
//        Log.println(Log.VERBOSE, "INFO",  "" + r);

        txtID = findViewById(R.id.txtID);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtNombres = findViewById(R.id.txtNombres);
        txtIsoPais = findViewById(R.id.txtIsoPais);
        txtEdad = findViewById(R.id.txtEdad);
        lstAutores = new Autores(this, "biblioteca.db", 1);

        //Leer los datos del diccionario Extra
        Bundle extra = getIntent().getExtras();
        String usuario = extra.getString("usuario");
        String clave = extra.getString("clave");

        lblTitulo = findViewById(R.id.lblTitulo);
        lblTitulo.setText("Bienvenido " + usuario);


    }

    public void cmdCrear_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        String apellidos = txtApellidos.getText().toString();
        String nombres = txtNombres.getText().toString();
        String isoPais = txtIsoPais.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());

        Autor r = lstAutores.Create(id, apellidos, nombres, isoPais, edad);

        if (r != null)
            Toast.makeText(this, "Autor creado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al crear el autor", Toast.LENGTH_LONG).show();

    }

    public void cmdActualizar_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        String apellidos = txtApellidos.getText().toString();
        String nombres = txtNombres.getText().toString();
        String isoPais = txtIsoPais.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());

        Autor r = lstAutores.Update(id, apellidos, nombres, isoPais, edad);

        if (r != null)
            Toast.makeText(this, "Autor actualizado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al actualizar el autor", Toast.LENGTH_LONG).show();

    }

    public void cmdLeer_onClick (View v){
        int id = Integer.parseInt(txtID.getText().toString());
        Autor r = lstAutores.Read_ById(id);

        if (r != null) {
            txtApellidos.setText(r.Apellidos);
            txtNombres.setText(r.Nombres);
            txtIsoPais.setText(r.IsoPais);
            txtEdad.setText("" + r.Edad);
        }
        else
            Toast.makeText(this, "Registro no encontrado", Toast.LENGTH_LONG).show();

    }

    public void cmdEliminar_onClick (View v) {
        int id = Integer.parseInt(txtID.getText().toString());
        boolean r = lstAutores.Delete(id);

        if (r)
        {
            txtID.setText("");
            txtApellidos.setText("");
            txtNombres.setText("");
            txtIsoPais.setText("");
            txtEdad.setText("");
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Error al eliminar el registro", Toast.LENGTH_LONG).show();

    }

    public void cmdRegresar_onClick(View v){
        finish();
    }



}