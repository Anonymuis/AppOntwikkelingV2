package dekoning.jos.appontwikkelingv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MenuItemSettingsFragment extends Fragment {
    public static final String TAG = "Message";
    public final String ACTIVITY_NAME = this.getClass().getSimpleName();

    View rootView;

    Switch switch1;
    Switch switch2;
    Switch switch3;
    boolean switch1State;
    boolean switch2State;
    boolean switch3State;

    String menuItem;
    String menuItemDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        rootView = inflater.inflate(R.layout.fragment_menu_item_settings,container,false);
        initUI();
        return rootView;
    }

    private void initUI(){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        switch1 = (Switch) rootView.findViewById(R.id.switch1);
        switch2 = (Switch) rootView.findViewById(R.id.switch2);
        switch3 = (Switch) rootView.findViewById(R.id.switch3);


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView1, boolean isChecked) {
                switch1State = switch1.isChecked();
                saveSwitchStates();
                setPreference("pref", "stay_logged_in", switch1State);
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView2, boolean isChecked) {
                switch2State = switch2.isChecked();
                saveSwitchStates();
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView3, boolean isChecked) {
                switch3State = switch3.isChecked();
                saveSwitchStates();
            }
        });
        Button logoutButton = rootView.findViewById(R.id.logoutButtonID);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setPreference("pref", "stay_logged_in", false);
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onSaveInstanceState(outState);
        outState.putString("selectedMenuItem",menuItem);

        // Save switch states
        switch1State = switch1.isChecked();
        switch2State = switch2.isChecked();
        switch3State = switch3.isChecked();

        outState.putBoolean("switch1State",switch1.isChecked());
        outState.putBoolean("switch2State",switch2.isChecked());
        outState.putBoolean("switch3State",switch3.isChecked());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            menuItem = savedInstanceState.getString("selectedMenuItem",menuItem);
        }else {
            Log.i(TAG, ACTIVITY_NAME + " " + methodName + " PSSSSSSSSSSSST");

            Bundle bundle = getArguments();
            menuItem = bundle.getString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,"Item1");
            menuItemDescription = getString(getStringId(menuItem));
        }
        restoreSwitchStates();
    }

    @Override
    public void onResume() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onResume();
        if(menuItem.equals("Item0")){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getUser());
            return;
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(menuItem);

    }

    private int getStringId(String menuItem){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        if(menuItem.equals("Item1")){
            return R.string.Item1;
        }else if(menuItem.equals("Item2")){
            return R.string.Item2;
        }else if(menuItem.equals("Item3")){
            return R.string.Item3;
        }else if(menuItem.equals("Item4")){
            return R.string.Item4;
        }else if(menuItem.equals("Item5")){
            return R.string.Item5;
        }else if(menuItem.equals("Item6")){
            return R.string.Item6;
        }else if(menuItem.equals("Item7")){
            return R.string.Item7;
        }else if(menuItem.equals("Item8")){
            return R.string.Item8;
        }else {
            return R.string.Item1;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    private void restoreSwitchStates(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        switch1State = preferences.getBoolean("switch1State", false);
        switch2State = preferences.getBoolean("switch2State", false);
        switch3State = preferences.getBoolean("switch3State", false);
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName + switch1State);

        switch1.setChecked(switch1State);
        switch2.setChecked(switch2State);
        switch3.setChecked(switch3State);
    }
    private void saveSwitchStates(){
        SharedPreferences sharePref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharePref.edit();
        prefEditor.putBoolean("switch1State", switch1State);
        prefEditor.putBoolean("switch2State", switch2State);
        prefEditor.putBoolean("switch3State", switch3State);
        prefEditor.apply(); // Submit to file
    }
    private String getUser(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String value = preferences.getString("username", "defaultValue");
        return value;
    }

    private void setPreference(String file, String identifier, String value){
        SharedPreferences sharePref = this.getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharePref.edit();
        prefEditor.putString(identifier, value);
        prefEditor.apply(); // Submit to file
    }
    private void setPreference(String file, String identifier, boolean value){
        SharedPreferences sharePref = this.getActivity().getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharePref.edit();
        prefEditor.putBoolean(identifier, value);
        prefEditor.apply(); // Submit to file
    }
}
