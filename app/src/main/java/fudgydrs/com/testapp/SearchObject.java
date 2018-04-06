package fudgydrs.com.testapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchObject implements Parcelable {

    private int id;
    private void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    private boolean isFound;
    private void setIsFound(boolean isFound) { this.isFound = isFound; }
    public boolean getIsFound() { return this.isFound; }

    private String character;
    private void setCharacter(String character) { this.character = character; }
    public String getCharacter() { return this.character; }

    private String attack;
    private void setAttack(String attack) { this.attack = attack; }
    public String getAttack() { return this.attack; }

    public SearchObject() {
        setId(99);
        setIsFound(false);
        setCharacter("");
        setAttack("");
    }

    public SearchObject(int id, boolean isFound, String character, String attack) {
        setId(id);
        setIsFound(isFound);
        setCharacter(character);
        setAttack(attack);
    }

    @Override
    public String toString() {
        return String.format("%1$-" + 10 + "s", String.valueOf(isFound))
                + String.format("%1$-" + 20 + "s", this.getCharacter())
                + String.format("%1$-" + 20 + "s", this.getAttack());
    }

    protected SearchObject(Parcel in) {
        setId(in.readInt());
        setIsFound(in.readByte() != 0);
        setCharacter(in.readString());
        setAttack(in.readString());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isFound ? 1 : 0));
        dest.writeString(character);
        dest.writeString(attack);
    }

    public static final Parcelable.Creator<SearchObject> CREATOR = new
            Parcelable.Creator<SearchObject>() {
                public SearchObject createFromParcel(Parcel source) {
                    return new SearchObject(source);
                }
                @Override
                public SearchObject[] newArray(int size) {
                    return new SearchObject[size];
                }
            };
}
