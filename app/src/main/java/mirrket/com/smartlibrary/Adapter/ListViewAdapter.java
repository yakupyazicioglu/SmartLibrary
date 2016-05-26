package mirrket.com.smartlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import mirrket.com.smartlibrary.Fragment.KutuphaneFragment;
import mirrket.com.smartlibrary.R;

public class ListViewAdapter extends BaseAdapter {

    KutuphaneFragment libraryFragment;
    Context context;
    //private Activity activity;
    //private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    String[] kitap_adi;
    String[] yazar_adi;
    private static LayoutInflater inflater=null;
    
    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> d) {
        //fragment = a;
        this.context = context;
        data=d;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        //TextView title = (TextView)vi.findViewById(R.id.title); // title
        //TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView kitap_adi_textview = (TextView)vi.findViewById(R.id.kitap_adi);
        TextView yazar_adi_textview = (TextView)vi.findViewById(R.id.yazar_adi);

        HashMap<String, String> book = new HashMap<String, String>();
        book = data.get(position);
        
        // Setting all values in listview
        //kitap_adi_textview.setText(book.get(kitap_adi));
        kitap_adi_textview.setText(book.get(libraryFragment.KEY_NAME));
        //yazar_adi_textview.setText(book.get(yazar_adi));
        yazar_adi_textview.setText(book.get(libraryFragment.KEY_AUTHOR));

        //kitap_adi_textview.setText(kitap_adi[position]);
        //yazar_adi_textview.setText(yazar_adi[position]);
        return vi;
    }
}