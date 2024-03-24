package com.example.prueba_pf_mysqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba_pf_mysqlite.db.DbContactos;

public class NuevoActivity extends AppCompatActivity{
	EditText txtNombre;
	EditText txtTelefono;
	EditText txtEmail;
	Button btnGuarda;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo);
		txtNombre = findViewById(R.id.txtNombre);
		txtTelefono = findViewById(R.id.txtTelefono);
		txtEmail = findViewById(R.id.txtEmail);
		btnGuarda.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				DbContactos dbContactos = new DbContactos(NuevoActivity.this);
				long id = dbContactos.insertaContacto(txtNombre.getText().toString(),
						txtTelefono.getText().toString(), txtEmail.getText().toString());
				if(id > 0){
					Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
					limpiar();
				}else{
					Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void limpiar(){
		txtNombre.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
	}
}