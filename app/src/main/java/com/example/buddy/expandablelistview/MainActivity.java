package com.example.buddy.expandablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    String itemclicked,header;
    HashMap<String, List<String>> listDataChild;
    List<GetDataAdapter> getDataAdapterList;
    int position=0;


    DatabaseHelper myDB;

   // ProgressBar progressBar;
    // List<GetDataAdapter> GetDataAdapter1;

    String GET_JSON_DATA_HTTP_URL = "http://192.168.1.101/Student.php";
    //String JSON_ID = "id";
    String JSON_StudentName = "Stu_Name";
    String JSON_StudentAddress = "Stu_Address";
    String JSON_StudentPhone = "Stu_Phone";


    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.exp_listview1);
        // preparing list data

        JSON_DATA_WEB_CALL();
        preparedlist();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setDividerHeight(2);
        expListView.setGroupIndicator(null);
        expListView.setClickable(true);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        "Group Clicked " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                header=listDataHeader.get(groupPosition);
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition),Toast.LENGTH_SHORT).show();
                return false;
            }

        });


    }
    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //progressBar.setVisibility(View.GONE);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){
        myDB=new DatabaseHelper(this);

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                //  GetDataAdapter2.setId(json.getInt(JSON_ID));

                GetDataAdapter2.setName(json.getString(JSON_StudentName));

                GetDataAdapter2.setAddress(json.getString(JSON_StudentAddress));

                GetDataAdapter2.setPhone(json.getInt(JSON_StudentPhone));
                getDataAdapterList.add(GetDataAdapter2);
                myDB.insertData(GetDataAdapter2);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            //GetDataAdapter1.add(GetDataAdapter2);



        }
    }


    private void preparedlist()
    {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data

        listDataHeader.add("Business");
        listDataHeader.add("Entertainment");
        listDataHeader.add("Health");
        listDataHeader.add("Markets");
        listDataHeader.add("Media");
        listDataHeader.add("Internet");
        listDataHeader.add("Sports");
        listDataHeader.add("Technology");
        listDataHeader.add("Special Interest");


        List<String> Business= new ArrayList<>();
        List<String> Entertainment=new ArrayList<String>();
        List<String> Health=new ArrayList<String>();
        List<String> Markets=new ArrayList<String>();
        List<String> Media=new ArrayList<String>();
        List<String> Internet=new ArrayList<String>();
        List<String> Sports=new ArrayList<String>();
        List<String> Technology=new ArrayList<String>();
        List<String> Interest=new ArrayList<String>();


        /*List<String> Business=new ArrayList<String>();
        Business.add("Breaking Business News");
        Business.add("International Business News");
	*//*  	Business.add("sectors");
	  	Business.add("Latest Financial News");
	  	*//*
        List<String> Entertainment=new ArrayList<String>();
        Entertainment.add("Arts");
        Entertainment.add("Books");
        Entertainment.add("Breaking Entertaiment");
        Entertainment.add("Movies");
        Entertainment.add("Music");
        Entertainment.add("Television");
        Entertainment.add("Weried");

        List<String> Health=new ArrayList<String>();
        Health.add("Aging News");
        Health.add("Breaking Health News");
        Health.add("Cancer News");
        Health.add("Fitness News");
        Health.add("Healthcare News");
        Health.add("Medical News");
        Health.add("Natural Health News");
        Health.add("Physcology News");
        Health.add("Public Health News");

        List<String> Markets=new ArrayList<String>();
        Markets.add("Breaking Financial Market News");
        Markets.add("Stock Markets News");
        Markets.add("Commodities News");
        Markets.add("Foreign Exchange News");
        Markets.add("Tech Markets News");
        Markets.add("UK Market News");
        Markets.add("US Market News");
        Markets.add("World Market News");

        List<String> Media=new ArrayList<String>();
        Media.add("Breaking Media News");
        Media.add("Broadcasting News");
        Media.add("Journalism News");
        Media.add("Newspapers News");
        Media.add("TV News");

        List<String> Internet=new ArrayList<String>();
        Internet.add("Breaking Internet News");
        Internet.add("Domain Names News");
        Internet.add("Internet Marketing News");
        Internet.add("Search Engines News");
        Internet.add("Viruses News");
        Internet.add("Website Development News");


        List<String> Sports=new ArrayList<String>();
        Sports.add("Atheletics News");
        Sports.add("Baseball News");
        Sports.add("BasketBall News");
        Sports.add("Boxing News");
        Sports.add("Breaking Sports News");
        Sports.add("circket News");
        Sports.add("Soccer News");
        Sports.add("Swimming News");
        Sports.add("Tennins News");
        Sports.add("wrestling News");



        List<String> Technology=new ArrayList<String>();
        Technology.add("Breaking Technology News");
        Technology.add("Communication News");
        Technology.add("Computer Games News");
        Technology.add("Computer News");
        Technology.add("Software News");
        Technology.add("Database News");
        Technology.add("Viruses News");
        Technology.add("Wireless News");
        Technology.add("Telecom News");

        List<String> Interest=new ArrayList<String>();
        Interest.add("");*/

//        myDB=new DatabaseHelper(this);
//
//        Cursor res=myDB.getAllData();
//        res.moveToFirst();
//        do{
////
          //  while(position<getDataAdapterList.size()){
              final GetDataAdapter getDataAdapter = getDataAdapterList.get(position);
              Business.add(getDataAdapter.getName());
              Entertainment.add(getDataAdapter.getName());
              Health.add(getDataAdapter.getAddress());
              Markets.add(getDataAdapter.getAddress());
              Media.add(String.valueOf(getDataAdapter.getPhone()));
              Internet.add(String.valueOf(getDataAdapter.getPhone()));
//              Sports.add(res.getString(2));
//              Technology.add(res.getString(1));
//              Interest.add(res.getString(2));
            //  position++;
         //}
        //listDataChild.put(listDataHeader.get(1), Entertainment);
//        listDataChild.put(listDataHeader.get(2), Health);
//        l
//        }while (res.moveToNext());

        listDataChild.put(listDataHeader.get(0), Business); // Header, Child data
        listDataChild.put(listDataHeader.get(3), Markets);
        listDataChild.put(listDataHeader.get(4), Media);
        listDataChild.put(listDataHeader.get(5), Internet);
        listDataChild.put(listDataHeader.get(6), Sports);
        listDataChild.put(listDataHeader.get(7), Technology);
        listDataChild.put(listDataHeader.get(8), Interest);
    }

}
