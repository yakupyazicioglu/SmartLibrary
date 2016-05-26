package mirrket.com.smartlibrary.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import mirrket.com.smartlibrary.Activity.MainActivity;
import mirrket.com.smartlibrary.Adapter.ListViewAdapter;
import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;
import mirrket.com.smartlibrary.Utils.AlertDialogUtils;


/**
 * Created by yy on 30.04.2016.
 */
public class KutuphaneFragment extends Fragment {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "kitap";
    public static final String KEY_AUTHOR = "yazar";
    public static final String KEY_PAGE = "sayfa";
    DatabaseHelper databaseHelper;
    ListView lv;
    ListViewAdapter lazyAdapter;
    ArrayList<HashMap<String, String>> kitap_liste;
    ArrayList<HashMap<String, String>> booksList = new ArrayList<HashMap<String, String>>();
    String kitap_idler[];
    int idb;

    public KutuphaneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
        kitap_liste = databaseHelper.tumkitaplar();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_kutuphane, container, false);
        lv = (ListView) rootView.findViewById(R.id.tumkitaplar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(kitap_liste.size()==0){//kitap listesi bossa
            Toast.makeText(getActivity(), "Kütüphanenizde kitap bulunmamaktadır.", Toast.LENGTH_LONG).show();
        }else {
            //kitap_adlari = new String[kitap_liste.size()]; // kitap adlar�n� tutucam�z string arrayi olusturduk.
            //kitap_yazarlari = new String[kitap_liste.size()]; // kitap yazarları tutucam�z string arrayi olusturduk.
            kitap_idler = new String [kitap_liste.size()]; // kitap id lerini tutucam�z string arrayi olusturduk.
            for (int i = 0; i < kitap_liste.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID, kitap_liste.get(i).get("_id"));
                map.put(KEY_NAME, kitap_liste.get(i).get("kitap"));
                map.put(KEY_AUTHOR, kitap_liste.get(i).get("yazar"));

                booksList.add(map);

                kitap_idler[i] = kitap_liste.get(i).get("_id");
                //kitap_adlari[i] = kitap_liste.get(i).get("kitap");
                //kitap_yazarlari[i] = kitap_liste.get(i).get("yazar");


            }

            lazyAdapter = new ListViewAdapter(getActivity(), booksList);

            lv.setAdapter(lazyAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                {
                    idb=arg2;
                    ArrayList<AlertDialogUtils.AlertDialogItem> items = new ArrayList<AlertDialogUtils.AlertDialogItem>();
                    items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_favekle), mKitapFavEkle) );
                    items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_duzenle), mKitapDuzenle) );
                    items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_sil),mKitapSil) );
                    AlertDialogUtils.showContextDialogue(getActivity(),"", items);
                }

            });
        }
    }

    private Runnable mKitapDuzenle = new Runnable() {
        @Override
        public void run() {
            Bundle args = new Bundle();
            //args.putInt("id", Integer.parseInt(KEY_ID));
            args.putInt("id", Integer.parseInt(kitap_idler[idb]));

            KitapDuzenleFragment fragment = new KitapDuzenleFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            fragment.setArguments(args);
            tr.commit();

        }
    };

    private Runnable mKitapSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.kitapSil(Integer.parseInt(kitap_idler[idb]));
            Intent intent1 = new Intent(getActivity(), MainActivity.class);
            startActivity(intent1);
            Toast.makeText(getActivity(),"Kitap Silindi!!", Toast.LENGTH_SHORT).show();
        }
    };

    private Runnable mKitapFavEkle = new Runnable() {
        @Override
        public void run() {
            String kitap_adi = kitap_liste.get(idb).get("kitap");
            String kitap_yazari = kitap_liste.get(idb).get("yazar");
            String kitap_sayfasi = kitap_liste.get(idb).get("sayfa");
            Toast.makeText(getActivity(),"Favorilere Eklendi!!", Toast.LENGTH_SHORT).show();

            databaseHelper.favEkle(kitap_adi,kitap_yazari,kitap_sayfasi);
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
