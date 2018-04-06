package fudgydrs.com.testapp;

import android.graphics.Color;
import android.os.Parcelable;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchHistoryActivity extends AppCompatActivity {

    ArrayList<SearchObject> searchObjects;
    private SearchHistory searchHistory;
    private SearchObject searchObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        getWindow().setBackgroundDrawableResource(R.color.color_black);
        if(savedInstanceState == null) {
            searchHistory = new SearchHistory();
        } else {
            searchHistory = savedInstanceState.getParcelable("searchHistory");
        }
        Bundle bundle = getIntent().getExtras();
        //String hello = bundle.getString("Hello"); // → World
        searchHistory = bundle.getParcelable("searchHistory");
        int size = searchHistory.getSize();
        String searchText = "";
        for(int i=0; i<size; i++) {
            searchText = searchText.concat(searchHistory.getSearchObject(i).toString() + '\n');
        }
        TextView search_item = findViewById(R.id.search_item);
        //int id = searchObject.getId();
        //boolean isFound = searchObject.getIsFound();
        //String character = searchObject.getCharacter();
        //String attack = searchObject.getAttack();
        search_item.setText(searchText);
        search_item.setTextColor(Color.GREEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("search0", searchObjects);
    }
}
