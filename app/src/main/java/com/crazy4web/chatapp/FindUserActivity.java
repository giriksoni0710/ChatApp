package com.crazy4web.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private RecyclerView muserList;
    private RecyclerView.LayoutManager muserListManager;
    private RecyclerView.Adapter muserListAdapter;

    // We need to lists: one userlist to update the recycler view and contact list to get all the contacts
    ArrayList<userObject> userList, contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);


        // We need to lists: one userlist to update the recycler view and contact list to get all the contacts
        contactList = new ArrayList<>();
        userList = new ArrayList<>();


        // Initializing the Recycler view
        initializeRecyclerView();

        getContactList();

    }

    //Getting the information of the contacts that we have Read Access to

    private void getContactList(){

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, null);

    //We will add the contact names to the userList using userObject class
        while(phones.moveToNext()){

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.d("name",name+":"+number);
            userObject mcontact = new userObject(name,number);

            contactList.add(mcontact);

            muserListAdapter.notifyDataSetChanged();


        }

    }

    //fetching country ISO to identify the iso code for a country

    private String getCountryISO(){

        String iso = null;


        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        if(telephonyManager.getNetworkCountryIso()!=null){
            if(!telephonyManager.getNetworkCountryIso().toString().equals("")){

                iso = telephonyManager.getNetworkCountryIso().toString();

            }

        }

        return iso;

    }

    //populating list of users in the recylerView

    private void initializeRecyclerView() {

        muserList = findViewById(R.id.userList);

        muserList.setNestedScrollingEnabled(false);
        muserList.setHasFixedSize(false);

        muserListManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

        muserList.setLayoutManager(muserListManager);

        //setting an Adapter to set the data

        muserListAdapter = new userlistAdapter(userList);

        muserList.setAdapter(muserListAdapter);

        getPermissions();
    }


    //now getting the read and write contact permission from the user
    private void getPermissions() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1);

        }

    }
}
