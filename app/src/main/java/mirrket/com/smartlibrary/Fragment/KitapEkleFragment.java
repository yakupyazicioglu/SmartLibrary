package mirrket.com.smartlibrary.Fragment;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;


/**
 * Created by yy on 30.04.2016.
 */
public class KitapEkleFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    DatabaseHelper databaseHelper;
    EditText kitap;
    EditText yazar;
    EditText sayfa;
    Spinner spinner;
    CheckBox read;
    EditText notlar;
    Button kitapEkle;
    List<String> listspinner = new ArrayList<String>();
    //ImageButton fav;

    public KitapEkleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//klavye butonu yukarı atmasın diye
        databaseHelper=new DatabaseHelper(getActivity());
        listspinner = databaseHelper.tumraflar();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Kitap Ekle");
        kitap = (EditText) view.findViewById(R.id.kitap);
        yazar = (EditText) view.findViewById(R.id.yazar);
        sayfa = (EditText) view.findViewById(R.id.sayfa);
        spinner = (Spinner)view.findViewById(R.id.spinnerraf);
        read = (CheckBox) view.findViewById(R.id.read);
        notlar = (EditText) view.findViewById(R.id.not);
        kitapEkle = (Button) view.findViewById(R.id.ekle);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listspinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        kitapEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                if(kitap.getText().toString().trim().length() == 0 || yazar.getText().toString().trim().length() == 0 || sayfa.getText().toString().trim().length() == 0)
                    Toast.makeText(getActivity(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                else{
                    try {
                        ContentValues values = new ContentValues();
                        String durum;

                        values.put("kitap", kitap.getText().toString());
                        values.put("yazar",yazar.getText().toString());
                        values.put("sayfa",sayfa.getText().toString());
                        values.put("raf",String.valueOf(spinner.getSelectedItem()));
                        if(read.isChecked()){
                            durum = String.valueOf(true);
                            values.put("read",durum);
                        }
                        else
                        {
                            durum = String.valueOf(false);
                            values.put("read",durum);
                        }
                        values.put("notlar",notlar.getText().toString());

                        db.insert(DatabaseHelper.TABLE_ALLBOOK, null, values);
                        /*databaseHelper.rafaEkle(kitap.getText().toString(),yazar.getText().toString(),
                                sayfa.getText().toString(),String.valueOf(spinner.getSelectedItem()));*/

                        Toast.makeText(getActivity(), "Kitap eklenmiştir.", Toast.LENGTH_SHORT).show();
                        Log.v("DBTEST", "KAYIT EKLENDİ");
                    }catch (Exception e) {
                        Log.e("DBTEST", e.toString());
                    }finally{
                        db.close();
                    }

                    yazar.setText("");
                    sayfa.setText("");
                    kitap.setText("");
                    //spinner.setSelection(0);
                    read.setChecked(false);
                    notlar.setText("");
                }

                //fav.setBackgroundResource(R.drawable.star);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitap_ekle, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
