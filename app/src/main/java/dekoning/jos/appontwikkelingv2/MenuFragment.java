package dekoning.jos.appontwikkelingv2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MenuFragment extends Fragment {
    public static final String TAG = "Message";
    public final String ACTIVITY_NAME = this.getClass().getSimpleName();

    View rootView;
    ListView listViewMenu;
    ArrayAdapter<String> menuItemsAdapter;
    Context context;
    String[] menuItems = {"Item0", "Item0", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8"};


    FragmentActionListener fragmentActionListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        rootView = inflater.inflate(R.layout.fragment_listview_menu,container,false);
        initUI();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);


        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            fragmentActionListener = (MenuActivity)getActivity();
        }

    }

    @Override
    public void onResume() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        super.onResume();
        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name)+"->Select Menu");
    }

    public void setFragmentActionListener(FragmentActionListener fragmentActionListener){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        this.fragmentActionListener = fragmentActionListener;
    }

    private void initUI(){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.i(TAG, ACTIVITY_NAME + " " + methodName);

        context  = getContext();

        /*
        menuItems = getResources().getStringArray(R.array.menuItems); // Get the list view items from the resources
        listViewMenu = (ListView)rootView.findViewById(R.id.listViewMenu); // Get reference to list view

        menuItemsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,menuItems);
        listViewMenu.setAdapter(menuItemsAdapter);
        */
        ///*////--- begin
        // Menu items

        menuItemsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menuItems);

        listViewMenu = (ListView) rootView.findViewById(R.id.listViewMenu); // Get reference to list view
        listViewMenu.setAdapter(menuItemsAdapter);
      // */ ////--- end


        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                Log.i(TAG, ACTIVITY_NAME + " " + methodName);

                if (fragmentActionListener != null){
                    fragmentActionListener.onMenuSelected(menuItems[i]);
                }
            }
        });
    }
    private String getUser(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String value = preferences.getString("username", "defaultValue");
        return value;
    }
}