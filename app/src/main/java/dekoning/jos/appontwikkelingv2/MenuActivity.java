package dekoning.jos.appontwikkelingv2;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MenuActivity extends AppCompatActivity implements FragmentActionListener{
    public static final String TAG = "Message";
    public final String ACTIVITY_NAME = this.getClass().getSimpleName();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Select the correct XML file for this activity
        fragmentManager = getSupportFragmentManager(); // Create a new fragment manager

        // Check if the phone is in portrait or landscape orientation
        if(findViewById(R.id.activity_menu_portrait)!= null){

            // Check if this is the first time this view is created
            if(savedInstanceState==null){
                addMenuItemsFragment(); // Add new fragment to display the menu
            }else{
                addMenuItemDescriptionFragment(savedInstanceState.getString("selectedMenuItem",null));
            }
        }else if (findViewById(R.id.activity_menu_landscape)!=null){
            addMenuItemsFragment();
            if(savedInstanceState==null){
                addMenuItemDescriptionFragment(R.id.fragmentContainerSelectedItem,"Item1");
            }else{
                addMenuItemDescriptionFragment(R.id.fragmentContainerSelectedItem,savedInstanceState.getString("selectedMenuItem","Item0"));
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onSaveInstanceState(outState);
        //Log.i("OrintationChange","MainActivity onSaveInstanceState");
        outState.putString("selectedMenuItem", selectedMenuItem);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onRestoreInstanceState(savedInstanceState);
        selectedMenuItem = savedInstanceState.getString("selectedMenuItem","Item1");
    }

    private void addMenuItemsFragment(){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        fragmentTransaction = fragmentManager.beginTransaction();

        MenuFragment menuListFragment=new MenuFragment();
        menuListFragment.setFragmentActionListener(this);

        fragmentTransaction.add(R.id.fragmentContainerListViewMenu,menuListFragment);
        fragmentTransaction.commit();
    }

    private void addMenuItemDescriptionFragment(String menuItem){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        fragmentTransaction = fragmentManager.beginTransaction();

        // ------- Begin
        if (menuItem.equalsIgnoreCase(getUser())) {
            MenuItemSettingsFragment menuItemSettingsFragment = new MenuItemSettingsFragment();

            Bundle bundle = new Bundle();
            bundle.putString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,menuItem);
            menuItemSettingsFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragmentContainerListViewMenu,menuItemSettingsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return;
        }
        // ------- End

        MenuItemDescriptionFragment menuDescriptionFragment = new MenuItemDescriptionFragment();

        Bundle bundle=new Bundle();
        bundle.putString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,menuItem);
        menuDescriptionFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainerListViewMenu,menuDescriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addMenuItemDescriptionFragment(int fragmentContainerId, String menuItem){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        fragmentTransaction = fragmentManager.beginTransaction();

        // ------- Begin
        Log.i(TAG, "menuItem: " + menuItem);
        if (menuItem.equalsIgnoreCase(getUser())) {
            MenuItemSettingsFragment menuItemSettingsFragment = new MenuItemSettingsFragment();

            Bundle bundle = new Bundle();
            bundle.putString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,menuItem);
            menuItemSettingsFragment.setArguments(bundle);

            fragmentTransaction.replace(fragmentContainerId,menuItemSettingsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return;
        }
        // ------- End

        MenuItemDescriptionFragment menuDescriptionFragment = new MenuItemDescriptionFragment();


        Bundle bundle =new Bundle();
        bundle.putString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,menuItem);
        menuDescriptionFragment.setArguments(bundle);

        fragmentTransaction.replace(fragmentContainerId,menuDescriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onMenuSelected(String itemMenu) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        selectedMenuItem = itemMenu;

        // Add the correct fragment to the menu
        if(findViewById(R.id.activity_menu_landscape)==null){
            addMenuItemDescriptionFragment(itemMenu);
        }else {
            addMenuItemDescriptionFragment(R.id.fragmentContainerSelectedItem,itemMenu);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i("OrintationChange", "landscape");

        }else if(newConfig.orientation== Configuration.ORIENTATION_PORTRAIT){
            Log.i("OrintationChange", "portrait");
        }
    }
    private String getUser(){
        SharedPreferences preferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String value = preferences.getString("username", "defaultValue");
        return value;
    }
}