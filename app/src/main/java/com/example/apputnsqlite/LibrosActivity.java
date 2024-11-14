package com.example.apputnsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibrosActivity extends AppCompatActivity {

    EditText txtID, txtTitulo, txtIdAutor, txtIsbn, txtAnioPublicacion, txtRevision, txtNroHojas;
    Libros lstLibros;
    TextView lblTitulo;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_libros);

        // Ajuste para márgenes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_libros);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_libros) {
                return true;
            } else if (itemId == R.id.navigation_autores) {
                startActivity(new Intent(this, AutoresActivity.class));
                overridePendingTransition(0, 0); // Sin animación
                finish();
                return true;
            }
            return false;
        });

        // Inicializar los elementos de la interfaz
        txtID = findViewById(R.id.txtID);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtIdAutor = findViewById(R.id.txtIdAutor);
        txtIsbn = findViewById(R.id.txtIsbn);
        txtAnioPublicacion = findViewById(R.id.txtAnioPublicacion);
        txtRevision = findViewById(R.id.txtRevision);
        txtNroHojas = findViewById(R.id.txtNroHojas);
        lstLibros = new Libros(this, "biblioteca.db", 1);

        // Leer los datos del diccionario Extra
        Bundle extra = getIntent().getExtras();
        String usuario = extra.getString("usuario");

        lblTitulo = findViewById(R.id.lblTitulo);
        lblTitulo.setText("Bienvenido " + usuario);
    }

    public void cmdCrear_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        String titulo = txtTitulo.getText().toString();
        int idAutor = Integer.parseInt(txtIdAutor.getText().toString());
        String isbn = txtIsbn.getText().toString();
        int anioPublicacion = Integer.parseInt(txtAnioPublicacion.getText().toString());
        int revision = Integer.parseInt(txtRevision.getText().toString());
        int nroHojas = Integer.parseInt(txtNroHojas.getText().toString());

        Libro r = lstLibros.Create(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);

        if (r != null)
            Toast.makeText(this, "Libro creado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al crear el libro", Toast.LENGTH_LONG).show();
    }

    public void cmdActualizar_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        String titulo = txtTitulo.getText().toString();
        int idAutor = Integer.parseInt(txtIdAutor.getText().toString());
        String isbn = txtIsbn.getText().toString();
        int anioPublicacion = Integer.parseInt(txtAnioPublicacion.getText().toString());
        int revision = Integer.parseInt(txtRevision.getText().toString());
        int nroHojas = Integer.parseInt(txtNroHojas.getText().toString());

        Libro r = lstLibros.Update(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);

        if (r != null)
            Toast.makeText(this, "Libro actualizado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al actualizar el libro", Toast.LENGTH_LONG).show();
    }

    public void cmdLeer_onClick(View v){
        int id = Integer.parseInt(txtID.getText().toString());
        Libro r = lstLibros.Read_ById(id);

        if (r != null) {
            txtTitulo.setText(r.Titulo);
            txtIdAutor.setText(String.valueOf(r.IdAutor));
            txtIsbn.setText(r.Isbn);
            txtAnioPublicacion.setText(String.valueOf(r.AnioPublicacion));
            txtRevision.setText(String.valueOf(r.Revision));
            txtNroHojas.setText(String.valueOf(r.NroHojas));
        } else {
            Toast.makeText(this, "Registro no encontrado", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdEliminar_onClick(View v) {
        int id = Integer.parseInt(txtID.getText().toString());
        boolean r = lstLibros.Delete(id);

        if (r) {
            txtID.setText("");
            txtTitulo.setText("");
            txtIdAutor.setText("");
            txtIsbn.setText("");
            txtAnioPublicacion.setText("");
            txtRevision.setText("");
            txtNroHojas.setText("");
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al eliminar el registro", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdRegresar_onClick(View v){
        finish();
    }
}
