package mirrket.com.smartlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import mirrket.com.smartlibrary.Fragment.KutuphaneFragment;
import mirrket.com.smartlibrary.R;
import mirrket.com.smartlibrary.Utils.ImageLoader;

public class ListViewAdapter extends BaseAdapter {

    KutuphaneFragment libraryFragment;
    Context context;
    //private Activity activity;
    //private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> d) {
        //fragment = a;
        this.context = context;
        data=d;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context.getApplicationContext());
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
        ImageView read_image = (ImageView)vi.findViewById(R.id.readimage);

        HashMap<String, String> book = new HashMap<String, String>();
        book = data.get(position);
        
        // Setting all values in listview
        kitap_adi_textview.setText(book.get(libraryFragment.KEY_NAME));
        yazar_adi_textview.setText(book.get(libraryFragment.KEY_AUTHOR));
        //imageLoader.DisplayImage(book.get(libraryFragment.KEY_READ), read_image);

        return vi;
    }
}