package dekoning.jos.appontwikkelingv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MenuItemDescriptionFragment extends Fragment {
    public static final String TAG = "Message";
    public final String ACTIVITY_NAME = this.getClass().getSimpleName();

    View rootView;
    TextView textViewMenuDescription;


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



        rootView = inflater.inflate(R.layout.fragment_menu_item_description,container,false);
        initUI();
        return rootView;
    }

    private void initUI(){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        textViewMenuDescription = (TextView)rootView.findViewById(R.id.textViewMenuDescription);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onSaveInstanceState(outState);
        outState.putString("selectedMenuItem",menuItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            menuItem = savedInstanceState.getString("selectedMenuItem",menuItem);
            menuItemDescription = getString(getStringId(menuItem));
        }else {
            Bundle bundle = getArguments();
            menuItem = bundle.getString(FragmentActionListener.KEY_SELECTED_MENU_ITEM,"Item1");
            menuItemDescription = getString(getStringId(menuItem));
        }


    }

    @Override
    public void onResume() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(menuItem);
        textViewMenuDescription.setText(menuItemDescription);
        textViewMenuDescription.setTextColor(Color.argb(255,0,0,0));
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
    private String getUser(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String value = preferences.getString("key", "defaultValue");
        return value;
    }
}
