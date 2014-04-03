package com.shyam.rollcall;

/**
 * Created by shyamsundar on 3/30/2014.
 */

import java.util.ArrayList;

import android.app.*;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {
    SharedPreferences somedata;
    public static String file="MyShPre";

    private final static int ALERT_DIALOG1=1;
    private final static int ALERT_DIALOG2=2;
    private final static int ALERT_DIALOG3=3;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        somedata=getSharedPreferences(file, MODE_PRIVATE);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Take
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Add
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Import
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Edit
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Remove
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // class
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        // attendance
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        // export
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TakeFragment();
                break;
            case 1:
                fragment = new AddFragment();
                break;
            case 2:
                fragment = new ImportFragment();
                break;
            case 3:
                fragment = new EditFragment();
                break;
            case 4:
                fragment = new RemoveFragment();
                break;
            case 5:
                fragment = new ClassFragment();
                break;
            case 6:
                fragment = new AttendanceFragment();
                break;
            case 7:
                fragment = new ExportFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("Main", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //change password and dialogs -------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuchange, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_changePass:
                Main.this.showDialog(ALERT_DIALOG2);
                return true;
            case R.id.action_help:

                return true;
            case R.id.action_about:
                Main.this.showDialog(ALERT_DIALOG1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        Dialog dialog=null;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        switch(id)
        {
            case ALERT_DIALOG1:
                b.setTitle("Roll Call");
                b.setMessage("Designed & Developed By : \n \t \t Shyam Sundar Choudhary \n \t \t -CSE sophomore at IITR");
                b.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                b.setCancelable(false);
                dialog=b.create();
                break;
            case ALERT_DIALOG2:
                b.setTitle("Change Password");
                LayoutInflater inflater = this.getLayoutInflater();
                final View view=inflater.inflate(R.layout.change_password, null) ;
                b.setView(view)
                        // Inflate and set the layout for the dialog
                        // Pass null as the parent view because its going in the dialog layout
                        // Add action buttons
                        .setPositiveButton("Signin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        EditText usr = (EditText) view.findViewById(R.id.username);
                                        EditText pswd = (EditText) view.findViewById(R.id.password);
                                        if(TextUtils.isEmpty(usr.getText().toString())||TextUtils.isEmpty(pswd.getText().toString()))
                                        {
                                            Toast.makeText(getApplicationContext(), "Empty Username or Password.", Toast.LENGTH_LONG).show();
                                            dialog.cancel();}
                                        else {
                                            if(somedata.getString("username",null).equals(usr.getText().toString())&&(somedata.getString("password",null).equals(pswd.getText().toString())))
                                            { dialog.cancel();
                                                Main.this.showDialog(ALERT_DIALOG3);}
                                            else {
                                                Toast.makeText(getApplicationContext(), "Wrong Username or Password.\nTry Again.", Toast.LENGTH_LONG).show();
                                                dialog.cancel();
                                            }
                                        }
                                    }
                                }
                        )
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                b.setCancelable(false);
                dialog=b.create();
                break;
            case ALERT_DIALOG3:
                b.setTitle("Change Password");
                LayoutInflater inflater1 = this.getLayoutInflater();
                final View view1=inflater1.inflate(R.layout.change_conf, null) ;
                b.setView(view1)
                        // Inflate and set the layout for the dialog
                        // Pass null as the parent view because its going in the dialog layout
                        // Add action buttons
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText usr = (EditText) view1.findViewById(R.id.username);
                                EditText pswd = (EditText) view1.findViewById(R.id.password);
                                EditText pswdConf = (EditText) view1.findViewById(R.id.confirm);
                                if (TextUtils.isEmpty(pswd.getText().toString())||TextUtils.isEmpty(usr.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Password or Username Can't Be Null.", Toast.LENGTH_LONG).show();
                                else if (TextUtils.isEmpty(pswdConf.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please Confirm Password.", Toast.LENGTH_LONG).show();
                                else if (pswd.getText().toString().equals(pswdConf.getText().toString()) == false)
                                    Toast.makeText(getApplicationContext(), "Both The Passwords Are Not Same.", Toast.LENGTH_LONG).show();
                                else {
                                    SharedPreferences.Editor editor=somedata.edit();
                                    editor.putString("username", usr.getText().toString());
                                    editor.putString("password", pswd.getText().toString());
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(), "Password Successfully Changed.", Toast.LENGTH_LONG).show();
                                }

                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                b.setCancelable(false);
                dialog=b.create();
                break;
            default:
        }
        return dialog;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
