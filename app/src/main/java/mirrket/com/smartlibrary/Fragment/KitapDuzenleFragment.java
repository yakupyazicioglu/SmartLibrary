package mirrket.com.smartlibrary.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.HashMap;
import java.util.List;

import mirrket.com.smartlibrary.Activity.MainActivity;
import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;


/**
 * Created by yy on 05.05.2016.
 */

public class KitapDuzenleFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    DatabaseHelper databaseHelper;
    EditText kitapd;
    EditText yazard;
    EditText sayfad;
    Spinner spinnerd;
    CheckBox readd;
    EditText notd;
    Button kitapDuzenled;
    List<String> listspinner = new ArrayList<String>();
    int id;

    public KitapDuzenleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//klavye butonu yukarı atmasın diye
        databaseHelper=new DatabaseHelper(getActivity());
        listspinner = databaseHelper.tumraflar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitap_duzenle, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);super.onViewCreated(view, savedInstanceState);
        kitapd = (EditText) view.findViewById(R.id.kitapd);
        yazard = (EditText) view.findViewById(R.id.yazard);
        sayfad = (EditText) view.findViewById(R.id.sayfad);
        spinnerd = (Spinner) view.findViewById(R.id.spinnerrafd);
        readd = (CheckBox) view.findViewById(R.id.readd);
        notd = (EditText) view.findViewById(R.id.notd);
        kitapDuzenled = (Button) view.findViewById(R.id.duzenled);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listspinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerd.setAdapter(dataAdapter);

        Bundle arguments=getArguments();
        id = arguments.getInt("id", 0);

        HashMap<String, String> map = databaseHelper.kitapDetay(id);


        kitapd.setText(map.get("kitap"));
        yazard.setText(map.get("yazar"));
        sayfad.setText(map.get("sayfa"));
        //spinnerd.setSelection(Integer.valueOf(map.get("raf")));
        readd.setChecked(Boolean.parseBoolean((map.get("read"))));
        notd.setText(map.get("notlar"));


        kitapDuzenled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitap,yazar,sayfa,raf,read,not;
                kitap = kitapd.getText().toString();
                yazar = yazard.getText().toString();
                sayfa = sayfad.getText().toString();
                raf = String.valueOf(spinnerd.getSelectedItem());
                if(readd.isChecked()){
                    read = String.valueOf(true);
                }
                else
                {
                    read = String.valueOf(false);
                }
                not = notd.getText().toString();

                if(kitap.matches("") || yazar.matches("") || sayfa.matches("")){
                    Toast.makeText(getActivity(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }else{
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    db.kitapDuzenle(kitap, yazar, sayfa, raf, read, not, id);
                    db.rafaEkle(kitap,yazar,sayfa,raf);
                    db.close();
                    Toast.makeText(getActivity(), "Kitabınız Basarıyla Düzenlendi.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    db.close();
                }

            }
        });

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
