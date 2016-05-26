package mirrket.com.smartlibrary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;

/**
 * Created by yy on 14.05.2016.
 */
public class KitapDuzenle extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper databaseHelper;
    EditText kitapb;
    EditText yazarb;
    EditText sayfab;
    Spinner spinnerb;
    CheckBox readb;
    EditText notb;
    Button kitapDuzenleb;
    List<String> listspinner = new ArrayList<String>();
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_kitap_duzenle);
        listspinner = databaseHelper.tumraflar();
        databaseHelper = new DatabaseHelper(this);

        kitapb = (EditText) findViewById(R.id.kitapd);
        yazarb = (EditText) findViewById(R.id.yazard);
        sayfab = (EditText) findViewById(R.id.sayfad);
        spinnerb = (Spinner) findViewById(R.id.spinnerrafd);
        readb = (CheckBox) findViewById(R.id.readd);
        notb = (EditText) findViewById(R.id.notd);
        kitapDuzenleb = (Button) findViewById(R.id.duzenled);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listspinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerb.setAdapter(dataAdapter);

        Bundle arguments=getIntent().getExtras();
        id = arguments.getInt("id", 0);

        HashMap<String, String> map = databaseHelper.kitapDetay(id);


        kitapb.setText(map.get("kitap"));
        yazarb.setText(map.get("yazar"));
        sayfab.setText(map.get("sayfa"));
        spinnerb.setSelection(Integer.parseInt(map.get("raf")));
        readb.setChecked(Boolean.parseBoolean((map.get("read"))));
        notb.setText(map.get("notlar"));


        kitapDuzenleb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kitap,yazar,sayfa,raf,read,not;
                kitap = kitapb.getText().toString();
                yazar = yazarb.getText().toString();
                sayfa = sayfab.getText().toString();
                raf = String.valueOf(spinnerb.getSelectedItem());
                if(readb.isChecked()){
                    read = String.valueOf(true);
                }
                else
                {
                    read = String.valueOf(false);
                }
                not = notb.getText().toString();

                if(kitap.matches("") || yazar.matches("") || sayfa.matches("")){
                    Toast.makeText(getApplication(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseHelper db = new DatabaseHelper(getApplication());
                    db.kitapDuzenle(kitap, yazar, sayfa, raf,read, not, id);
                    db.close();
                    Toast.makeText(getApplication(), "Kitabınız Basarıyla Düzenlendi.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
