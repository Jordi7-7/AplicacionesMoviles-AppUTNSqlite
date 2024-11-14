package com.example.apputnsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AutoresActivity extends AppCompatActivity {

    EditText txtID, txtApellidos, txtNombres, txtIsoPais, txtEdad;
    Autores lstAutores;
    TextView lblTitulo;
    TableLayout tb_libros;
    Libros librosDB;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_autores);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Marcar el elemento Autores como seleccionado
        bottomNavigationView.setSelectedItemId(R.id.navigation_autores);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_libros) {
                Intent intent = new Intent(AutoresActivity.this, LibrosActivity.class);
                intent.putExtra("usuario", "NombreUsuario"); // Asegúrate de pasar valores reales aquí
                intent.putExtra("clave", "ClaveUsuario");    // Asegúrate de pasar valores reales aquí
                startActivity(intent);
                overridePendingTransition(0, 0); // Sin animación
                finish(); // Finaliza la actividad actual
                return true;
            } else if (itemId == R.id.navigation_autores) {
                return true; // Ya estamos en esta actividad
            }
            return false;
        });


        lstAutores = new Autores(this, "biblioteca.db", 1);
        librosDB = new Libros(this, "biblioteca.db", 1);

        Autor r = lstAutores.Create(1, "AMADO", "NERVO", "CL", 100);
        Autor r1 = lstAutores.Create(2, "JORDI", "NERVO", "CL", 100);
        Autor r2 = lstAutores.Create(3, "NOSE", "NERVO", "CL", 100);

        //        Autor r = listaAutores.Update(1, "AMADO  EULALIO", "NERVO PAREDES", "EC", 45);
//        Autor r = listaAutores.Read_ById(1);
//        boolean r = listaAutores.Delete(1);
//        Log.println(Log.VERBOSE, "INFO",  "" + r);

        txtID = findViewById(R.id.txtID);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtNombres = findViewById(R.id.txtNombres);
        txtIsoPais = findViewById(R.id.txtIsoPais);
        txtEdad = findViewById(R.id.txtEdad);

//        //Leer los datos del diccionario Extra
//        Bundle extra = getIntent().getExtras();
//        String usuario = extra.getString("usuario");
//        String clave = extra.getString("clave");
//
//        lblTitulo = findViewById(R.id.lblTitulo);
//        lblTitulo.setText("Bienvenido " + usuario);


    }

    public void cmdCrear_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        String apellidos = txtApellidos.getText().toString();
        String nombres = txtNombres.getText().toString();
        String isoPais = txtIsoPais.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());

        Autor r = lstAutores.Create(id,  nombres,apellidos, isoPais, edad);

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

        Autor r = lstAutores.Update(id, nombres,apellidos ,isoPais, edad);

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

    public void llenarTablaLibros(int idAutor){
        if (tb_libros.getChildCount() > 1) {
            tb_libros.removeViews(1, tb_libros.getChildCount() - 1);
        }

        Libro[] listaLibros = librosDB.Read_ByAutor(idAutor);

        // Agregar las filas de los libros
        for (Libro libro : listaLibros) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            // Crear celdas para cada columna
            TextView tvId = crearCelda(String.valueOf(libro.Id));
            tvId.setVisibility(View.GONE);
            tvId.setPadding(0,0,0,0);
            TextView tvTitulo = crearCelda(libro.Titulo);
            TextView tvAnio = crearCelda(String.valueOf(libro.AnioPublicacion));
            // Agregar celdas a la fila
            row.addView(tvId);
            row.addView(tvTitulo);
            row.addView(tvAnio);

            // Agregar la fila a la tabla
            tb_libros.addView(row);
        }
    }

    private TextView crearCelda(String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(8, 8, 8, 8);
        textView.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return textView;
    }


    public void cmdRegresar_onClick(View v){
        finish();
    }



}