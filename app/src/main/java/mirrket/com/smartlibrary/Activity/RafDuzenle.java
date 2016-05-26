package mirrket.com.smartlibrary.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;


/**
 * Created by yy on 14.05.2016.
 */
public class RafDuzenle extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText raf;
    Button rafDuzenle;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_raf_duzenle);

        raf = (EditText) findViewById(R.id.raf_edit);
        rafDuzenle = (Button) findViewById(R.id.raf_kaydet);

        databaseHelper = new DatabaseHelper(this);

        Bundle arguments=getIntent().getExtras();
        id = arguments.getInt("id", 0);

        //List<String> listDataHeader = databaseHelper.rafDetay(id);
        HashMap<String, String> map = databaseHelper.rafDetay(id);

        raf.setText(map.get("id"));

        rafDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(raf.getText().toString().trim().length()==0)
                    Toast.makeText(getApplication(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                else
                {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    String ID= "_id";
                    String CREATE_TABLE_SHELF = "CREATE TABLE " + raf.getText().toString() + "("
                            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")";
                    db.execSQL(CREATE_TABLE_SHELF);
                    raf.setText("");
                    Toast.makeText(getApplicationContext(),"Raf Düzenlendi!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
