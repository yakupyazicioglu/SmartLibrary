package mirrket.com.smartlibrary.Fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;


public class RafEkleFragment extends Fragment {

    DatabaseHelper databaseHelper;
    EditText rafedit;
    Button rafekle;


    public RafEkleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rafekle, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rafedit = (EditText)view.findViewById(R.id.raf_edit);
        rafekle = (Button) view.findViewById(R.id.raf_ekle);

        rafekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rafedit.getText().toString().trim().length()==0)
                    Toast.makeText(getActivity(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                else
                {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    String ID= "_id";
                    String FK= "fk";
                    String KITAP = "kitap";
                    String YAZAR = "yazar";
                    String SAYFA = "sayfa";
                    String CREATE_TABLE_SHELF = "CREATE TABLE " + rafedit.getText().toString() + "("
                            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + KITAP + " TEXT,"
                            + YAZAR + " TEXT,"
                            + SAYFA + " TEXT,"
                            + FK + " INTEGER NOT NULL,"
                            + " FOREIGN KEY ("+FK+") REFERENCES "+databaseHelper.TABLE_ALLBOOK+"("+ID+"));";
                    db.execSQL(CREATE_TABLE_SHELF);
                    rafedit.setText("");
                    Toast.makeText(getActivity(), "Raf eklenmiştir.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
