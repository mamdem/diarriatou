package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng position = new LatLng(14.685087847867663, -17.46741572666577); // Coordonnées de la Tour Eiffel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.addMarker(new MarkerOptions().position(position).title("Tour Eiffel"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 40)); // Zoom de 10 sur la position

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                Cursor cursor = db.getAllLocations();

                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE));
                        @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE));
                        @SuppressLint("Range") String userInput = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_INPUT));

                        LatLng latLng = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(userInput));

                    } while (cursor.moveToNext());
                    cursor.close();
                }

                db.close();

                // Ajoutez des actions supplémentaires pour la caméra si nécessaire ici
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Entrez le nom du lieu")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String userInput = input.getText().toString();

                                // Insérer les données dans SQLite
                                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                                boolean isInserted = dbHelper.insertLocation(latLng.latitude, latLng.longitude, userInput);

                                if(isInserted) {
                                    Toast.makeText(MainActivity.this, "Location saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Failed to save location", Toast.LENGTH_SHORT).show();
                                }

                                // Ajouter un marqueur avec le texte de l'utilisateur
                                googleMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title(userInput));
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}