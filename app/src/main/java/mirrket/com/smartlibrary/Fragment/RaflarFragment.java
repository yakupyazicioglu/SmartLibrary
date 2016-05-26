package mirrket.com.smartlibrary.Fragment;

import android.content.Context;
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

import mirrket.com.smartlibrary.Adapter.ExpandableListAdapter;
import mirrket.com.smartlibrary.Database.DatabaseHelper;
import mirrket.com.smartlibrary.R;
import mirrket.com.smartlibrary.Utils.AlertDialogUtils;


public class RaflarFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DatabaseHelper databaseHelper;
    ListView raflar;
    ArrayAdapter<String> adapter;
    int raf_idler[];
    ArrayList<String> raf_liste;
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_raflar, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //raflar = (ListView)view.findViewById(R.id.expandableListViewRaf);
        expListView = (ExpandableListView)view.findViewById(R.id.expandableListViewRaf);
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                idb = position;
                ArrayList<AlertDialogUtils.AlertDialogItem> items = new ArrayList<AlertDialogUtils.AlertDialogItem>();
                items.add( new AlertDialogUtils.AlertDialogItem(getString(R.string.alert_item_sil),mRafSil) );
                AlertDialogUtils.showContextDialogue(getActivity(),"", items);
                return false;
            }
        });


        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Açıldı", Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Kapandı", Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /*public void showPopupMenu (View view){

        final String item = (String) view.getTag();

        PopupMenu popup = new PopupMenu(getActivity(), view, Gravity.RIGHT);
        popup.getMenuInflater().inflate(R.menu.menu_popupraf, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_duzenle:
                        Intent intent = new Intent(getActivity(), RafDuzenle.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_sil:
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        db.rafSil(raf_liste.get(idb));

                        RaflarFragment fragment = new RaflarFragment();
                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.replace(R.id.container_body, fragment);
                        tr.commit();
                        break;

                    default:

                }
                return false;
            }
        });

        popup.show();
    }*/

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

    private Runnable mRafSil = new Runnable() {
        @Override
        public void run() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.rafSil(raf_liste.get(idb));

            RaflarFragment fragment = new RaflarFragment();
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container_body, fragment);
            tr.commit();
        }
    };



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
