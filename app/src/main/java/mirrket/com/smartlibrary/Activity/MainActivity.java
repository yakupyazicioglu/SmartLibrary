package mirrket.com.smartlibrary.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import mirrket.com.smartlibrary.Fragment.AyarlarFragment;
import mirrket.com.smartlibrary.Fragment.FavorilerFragment;
import mirrket.com.smartlibrary.Fragment.FragmentDrawer;
import mirrket.com.smartlibrary.Fragment.KitapEkleFragment;
import mirrket.com.smartlibrary.Fragment.KutuphaneFragment;
import mirrket.com.smartlibrary.Fragment.RafEkleFragment;
import mirrket.com.smartlibrary.Fragment.RaflarFragment;
import mirrket.com.smartlibrary.R;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create) {
            View menuItemView = findViewById(R.id.action_create);
            showPopupMenu(menuItemView);
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Geliştirme aşamasında=)", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showPopupMenu (View view){

        final String item = (String) view.getTag();

        final PopupMenu popup = new PopupMenu(this, view, Gravity.CENTER);
        popup.getMenuInflater().inflate(R.menu.menu_create, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_create_kitap:
                        KitapEkleFragment fragment1 = new KitapEkleFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment1);
                        getSupportActionBar().setTitle("Kitap Ekle");
                        fragmentTransaction.commit();
                        break;
                    case R.id.action_create_raf:
                        RafEkleFragment fragment2 = new RafEkleFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.container_body, fragment2);
                        getSupportActionBar().setTitle("Raf Ekle");
                        fragmentTransaction1.commit();
                        break;
                    default:

                }
                return false;
            }
        });

        popup.show();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new KutuphaneFragment();
                title = getString(R.string.title_tumkitaplar);
                break;
            case 1:
                fragment = new RaflarFragment();
                title = getString(R.string.title_rafekle);
                break;
            case 2:
                fragment = new FavorilerFragment();
                title = getString(R.string.title_favoriler);
                break;
            case 3:
                fragment = new AyarlarFragment();
                title = getString(R.string.title_ayarlar);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(title);
        }
    }
}