package mirrket.com.smartlibrary.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;

/**
 * Created by yy on 27.05.2016.
 */
public class KitapBilgiFragment extends Fragment {
    DatabaseHelper databaseHelper;
    TextView kitap;
    TextView yazar;
    TextView sayfa;
    ImageView read;
    CheckBox che;
    TextView not;
    int id;
    String rea;

    public KitapBilgiFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.kitap_ozet, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kitap = (TextView) view.findViewById(R.id.kitapdi);
        yazar = (TextView) view.findViewById(R.id.yazardi);
        sayfa = (TextView) view.findViewById(R.id.sayfadi);
        read = (ImageView) view.findViewById(R.id.readdi);
        not = (TextView) view.findViewById(R.id.notdi);

        Bundle arguments=getArguments();
        id = arguments.getInt("id", 0);

        HashMap<String, String> map = databaseHelper.kitapDetay(id);

        kitap.setText(map.get("kitap"));
        yazar.setText(map.get("yazar"));
        sayfa.setText(map.get("sayfa"));
        //che.setChecked(Boolean.parseBoolean((map.get("read"))));
        rea = map.get("read").toString();
        if(rea.equals("false"))
        read.setVisibility(view.GONE);
        not.setText(map.get("notlar"));


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
