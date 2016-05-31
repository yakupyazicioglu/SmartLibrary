package mirrket.com.smartlibrary.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mirrket.com.smartlibrary.Activity.MainActivity;
import mirrket.com.smartlibrary.Adapter.ExpandableListAdapter;
import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;
import mirrket.com.smartlibrary.Utils.AlertDialogUtils;


public class RaflarFragment extends Fragment {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "kitap";
    public static final String KEY_AUTHOR = "yazar";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    ArrayList<HashMap<String, String>> kitap_liste;
    HashMap<String, List<String>> listDataChild;
    ArrayList<HashMap<String, String>> booksList = new ArrayList<HashMap<String, String>>();
    DatabaseHelper databaseHelper;
    ListView raflar;
    ArrayAdapter<String> adapter;
    int raf_idler[];
    String kitap_idler[];
    ArrayList<String> raf_liste;
    int idraf;
    int idb;
    int idchild;
    int idgroup;

    public RaflarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(getActivity());
        raf_liste = databaseHelper.tumraflar();
        kitap_liste = databaseHelper.tumkitaplar();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_raflar, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        expListView = (ExpandableListView)view.findViewById(R.id.expandableListViewRaf);
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                idraf = position;
                if(idraf==0)
                    Toast.makeText(getActivity(), "Bu raf üzerinde işlem yapamazsınız!!", Toast.LENGTH_LONG).show();
                else{
                    ArrayList<AlertDialogUtils.AlertDialogItem> items = new ArrayList<AlertDialogUtils.AlertDialogItem>();
                    items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_sil),mRafSil) );
                    AlertDialogUtils.showContextDialogue(getActivity(),"", items);
                }

                return true;
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                idb = (int) id;
                idgroup = groupPosition;
                idchild = childPosition;
                ArrayList<AlertDialogUtils.AlertDialogItem> items = new ArrayList<AlertDialogUtils.AlertDialogItem>();
                items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_sil),mKitapSil) );
                //items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_raf_item_sil),mKitapRafSil) );
                AlertDialogUtils.showContextDialogue(getActivity(),"", items);


                /*Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT).show();*/
                return true;
            }
        });
    }


    private Runnable mRafSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.rafSil(raf_liste.get(idraf));
            Toast.makeText(getActivity(),"Raf Silindi!!", Toast.LENGTH_SHORT).show();

            RaflarFragment fragment = new RaflarFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            tr.commit();
        }
    };

    private Runnable mKitapSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.kitapSil(Integer.parseInt(kitap_idler[idb]));
            Toast.makeText(getActivity(),"Kitap Silindi!!", Toast.LENGTH_SHORT).show();

            RaflarFragment fragment = new RaflarFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            tr.commit();
        }
    };

    private Runnable mKitapRafSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.kitapRafSil(String.valueOf(raf_liste.get(idraf)),idchild);
            Toast.makeText(getActivity(),"Kitap Silindi!!", Toast.LENGTH_SHORT).show();

            RaflarFragment fragment = new RaflarFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            tr.commit();
        }
    };

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader = databaseHelper.tumraflar();
        raf_idler = new int[listDataHeader.size()];

        for(int i=0; i<listDataHeader.size();i++)
        {
            //raf_idler[i] = Integer.parseInt(listDataHeader.get(i));
            listDataChild.put(listDataHeader.get(i),databaseHelper.rafKitaplar(String.valueOf(listDataHeader.get(i))));
        }

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
