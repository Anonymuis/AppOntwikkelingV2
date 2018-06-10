package dekoning.jos.appontwikkelingv2;

import android.os.Bundle;

public class MenuItem {

    private String displayName = "Placeholder";
    private int layoutFileID;
    private Object mItem;
    private Bundle bundle;

    public MenuItem(){
    }

    public Object getmItem() {
        return mItem;
    }

    public void setmItem(Object mItem) {
        this.mItem = mItem;
    }
    public void setArguments(Bundle bundle){
        this.bundle = bundle;
    }
    public Bundle getArguments(){
        return this.bundle;
    }




    public int getLayoutFileID(){
        return this.layoutFileID;
    }
    public void setLayoutFileID(int layoutFileID){
        this.layoutFileID = layoutFileID;
    }

    public String getDisplayName() {
        return this.displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

