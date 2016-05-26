package mirrket.com.smartlibrary.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import mirrket.com.smartlibrary.Adapter.ListViewAdapter;
import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;
import mirrket.com.smartlibrary.Utils.AlertDialogUtils;


/**
 * Created by yy on 30.04.2016.
 */
public class FavorilerFragment extends Fragment {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "kitap";
    public static final String KEY_AUTHOR = "yazar";
    public static final String KEY_PAGE = "sayfa";
    DatabaseHelper databaseHelper;
    ListView lv;
    ListViewAdapter lazyAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> fav_kitap_liste;
    ArrayList<HashMap<String, String>> booksList = new ArrayList<HashMap<String, String>>();
    String fav_kitap_adlari[];
    String fav_kitap_idler[];
    int idb;

    public FavorilerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(getActivity());
        fav_kitap_liste = databaseHelper.favorikitaplar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favoriler, container, false);
        lv = (ListView) rootView.findViewById(R.id.favkitaplar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(fav_kitap_liste.size()==0){//kitap listesi bossa
            Toast.makeText(getActivity(), "Favori kitabınız bulunmamaktadır.", Toast.LENGTH_LONG).show();
        }else {
            fav_kitap_adlari = new String[fav_kitap_liste.size()];
            fav_kitap_idler = new String[fav_kitap_liste.size()];
            for (int i = 0; i < fav_kitap_liste.size(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID, fav_kitap_liste.get(i).get("_id"));
                map.put(KEY_NAME, fav_kitap_liste.get(i).get("kitap"));
                map.put(KEY_AUTHOR, fav_kitap_liste.get(i).get("yazar"));

                booksList.add(map);

                fav_kitap_idler[i] = fav_kitap_liste.get(i).get("_id");
                fav_kitap_idler[i] = String.valueOf(Integer.parseInt(fav_kitap_liste.get(i).get("_id")));

            }

            lazyAdapter = new ListViewAdapter(getActivity(), booksList);
            lv.setAdapter(lazyAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    idb=arg2;
                    ArrayList<AlertDialogUtils.AlertDialogItem> items = new ArrayList<AlertDialogUtils.AlertDialogItem>();
                    items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_sil),mKitapSil) );
                    AlertDialogUtils.showContextDialogue(getActivity(),"", items);

                }

            });
        }
    }


    private Runnable mKitapSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.favSil(Integer.parseInt(fav_kitap_idler[idb]));
            FavorilerFragment fragment = new FavorilerFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            tr.commit();
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
