package fudgydrs.com.testapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SearchHistory implements Parcelable {

    private List<SearchObject> searchObjects = new ArrayList<>();
    public void addSearchObject(SearchObject object) {
        searchObjects.add(0, object);
        while(searchObjects.size() > 30)
            removeSearchObject(30);
    }
    private void removeSearchObject(int index) { searchObjects.remove(index); }
    private void setSearchObjects(List<SearchObject> objects) { this.searchObjects = objects; }
    public SearchObject getSearchObject(int index) { return this.searchObjects.get(index); }
    public List<SearchObject> getSearchObjects() { return this.searchObjects; }
    public int getSize() { return this.searchObjects.size(); }

    public SearchHistory() {}
    public SearchHistory(List<SearchObject> searchObjects) {
        setSearchObjects(searchObjects);
    }

    protected SearchHistory(Parcel in) {
        //searchObjects = new ArrayList<>();
        in.readList(searchObjects, SearchObject.class.getClassLoader());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(searchObjects);
    }

    public static final Parcelable.Creator<SearchHistory> CREATOR = new
            Parcelable.Creator<SearchHistory>() {
                public SearchHistory createFromParcel(Parcel source) {
                    return new SearchHistory(source);
                }
                @Override
                public SearchHistory[] newArray(int size) {
                    return new SearchHistory[size];
                }
            };
}
