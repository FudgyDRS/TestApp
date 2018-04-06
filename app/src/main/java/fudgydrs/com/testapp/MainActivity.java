package fudgydrs.com.testapp;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {System.loadLibrary("native-lib");}

    public static String characterName; // character info from textbox
    public static String characterFileName; // file name with attack data
    public static String characterInfo;
    public static String attackName; // attack info from textbox
    public String attackInfo; // attack info shown on-screen
    private SearchObject searchObject;
    public SearchHistory searchHistory;
    //private Activity mActivity = this;
    //private Context mContext = MainActivity.this;
    //private FileUtils finder;// = new FileUtils(mActivity, mContext);// = new FileUtils();
    //private String responseString;
    private String[] temp;
    private Bundle bundle = new Bundle();
    public List<String> attackData = Arrays.asList(new String[8]); // data with attack name

    public static boolean charFlag = false; // Character values set
    public static boolean atkFlag = false; // Attack values set

    private void SetCharacterName(String characterName) {
        MainActivity.characterName = characterName; }

    private void SetAttackName(String attackName) {
        MainActivity.attackName = attackName; }

    private void SetCharacterInfo() {
        TextView char_selected = findViewById(R.id.char_selected);
        char_selected.setText(R.string.default_name_select);
        char_selected.setTextColor(Color.GREEN);
        }
        /*

       Heihachi Frame Data:
       Move: RpLkRk
       Properties:    |||||     Damage: // 15 + 5 + 15 = 35
       (Special)      |||||     -
       Start-Up:      |||||     Block:
       i19            |||||     -
       Hit:           |||||     Counter Hit:
       -              |||||     -
       Notes:
       -
         */


    private void SetAttackInfo(String attack) {
        TextView attack_selected = findViewById(R.id.atk_selected);
        attack_selected.setText(attack);
        attack_selected.setTextColor(Color.GREEN);
    }

    private void SetCharacterInfo(String charName) {
        TextView char_selected = findViewById(R.id.char_selected);
        char_selected.setText(charName);
        char_selected.setTextColor(Color.GREEN);
        }

    public String GetCharacterName() {
        TextView char_name = findViewById(R.id.char_name);
        return char_name.getText().toString();
    }

    public String GetAttackName() {
        TextView atk_name = findViewById(R.id.atk_name);
        return atk_name.getText().toString();
    }

    public String GetAttackInfo() {
        return attackInfo; }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, SearchHistoryActivity.class);
        startActivity(intent);
    }

    private void doButtonAction1() {
        SetCharacterName(GetCharacterName()); // Default set name
        ImageView image = findViewById(R.id.img_selected);
        temp = FileUtils.FindCharacter(MainActivity.characterName);
        if (temp[0].equals("none")) {
            characterFileName = temp[1];
            characterInfo = getString(R.string.error_name_select);
            charFlag = false;
            image.setImageResource(android.R.color.transparent);
        } else {
            characterFileName = temp[0];
            characterInfo = temp[1];
            charFlag = true;
            image.setImageResource(getResources()
                    .getIdentifier(characterFileName, "drawable", getPackageName()));
        }
        SetCharacterInfo(characterInfo);
    }

    private void doButtonAction2(String attack) {
        TextView attack_name = findViewById(R.id.atk_selected);
        SetAttackName(attack);
        if (charFlag) {
            try {
                List<String> json;
                json = FileUtils.FindAttack(GetCharacterName()
                        , GetAttackName(), getAssets());
                if(json != null) {
                    for(int i=0;i<8;i++)
                        attackData.set(i,json.get(i));
                    atkFlag = true;
                } else
                    atkFlag = false;
                SetAttackInfo(atkFlag);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attack_name.setTextColor(Color.GREEN);
        } else {
            SetAttackInfo(getString(R.string.error_attack_select));
            attack_name.setTextColor(Color.GREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            searchHistory = new SearchHistory();
            SetCharacterInfo(); // Default set name info
        } else {
            searchHistory = savedInstanceState.getParcelable("searchHistory");
            searchObject = searchHistory.getSearchObject(0);
            charFlag = true;
            atkFlag = searchObject.getIsFound();
            characterName = searchObject.getCharacter();
            attackName = searchObject.getAttack();
            //--------------------------------------------------------------------------------------
            doButtonAction1();
            //--------------------------------------------------------------------------------------
            doButtonAction2(attackName);
            //--------------------------------------------------------------------------------------
        }

        Button setCharacter = findViewById(R.id.button);
        setCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        doButtonAction1();
                }
            }
        });

        SetAttackInfo(atkFlag); // Default set atk info
        Button setAttack = findViewById(R.id.button2);
        setAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button2:
                        doButtonAction2(GetAttackName()); // Default set attack
                        searchHistory.addSearchObject(new SearchObject(0, atkFlag, characterName, attackName));
                }
            }
        });

        Button viewHistory = findViewById(R.id.button3);
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button3:
                        SearchHistory searchObjects = new SearchHistory(new ArrayList<SearchObject>());
                        SearchObject searchObject = new SearchObject(2, true, "heihachi", "1, 1, 2");
                        SearchObject searchObject2 = new SearchObject(2, false, "jin", "1, 2, 3");
                        searchObjects.addSearchObject(searchObject);
                        searchObjects.addSearchObject(searchObject2);
                        //bundle.putParcelable("search0", searchObject);
                        //bundle.putParcelable("searchHistory", searchObjects);
                        bundle.putParcelable("searchHistory", searchHistory);

                        Intent intent = new Intent(MainActivity.this, SearchHistoryActivity.class);
                        //intent.putExtra("search0", searchObject);
                        intent.putExtras(bundle);
                        startActivity(intent);
                }
            }
        });

        // Set background color
        getWindow().setBackgroundDrawableResource(R.color.color_black);

        // Set attack box text
        TextView atk_name = findViewById(R.id.atk_name);
        atk_name.setText(R.string.default_atk);
        atk_name.setTextColor(Color.GREEN);

        // Set character box text
        TextView char_name = findViewById(R.id.char_name);
        char_name.setText(R.string.default_name);
        char_name.setTextColor(Color.GREEN);

        // Set character box text
        TextView attack_r1c1 = findViewById(R.id.attack_r1c1);
        attack_r1c1.setTextColor(Color.GREEN);
        TextView attack_r1c2 = findViewById(R.id.attack_r1c2);
        attack_r1c2.setTextColor(Color.GREEN);
        TextView attack_r2c1 = findViewById(R.id.attack_r2c1);
        attack_r2c1.setTextColor(Color.GREEN);
        TextView attack_r2c2 = findViewById(R.id.attack_r2c2);
        attack_r2c2.setTextColor(Color.GREEN);
        TextView attack_r3c1 = findViewById(R.id.attack_r3c1);
        attack_r3c1.setTextColor(Color.GREEN);
        TextView attack_r3c2 = findViewById(R.id.attack_r3c2);
        attack_r3c2.setTextColor(Color.GREEN);
        TextView attack_r4c1 = findViewById(R.id.attack_r4c1);
        attack_r4c1.setTextColor(Color.GREEN);
        TextView attack_r4c2 = findViewById(R.id.attack_r4c2);
        attack_r4c2.setTextColor(Color.GREEN);
        TextView attack_r5c1 = findViewById(R.id.attack_r5c1);
        attack_r5c1.setTextColor(Color.GREEN);
        TextView attack_r5c2 = findViewById(R.id.attack_r5c2);
        attack_r5c2.setTextColor(Color.GREEN);
        TextView attack_r6c1 = findViewById(R.id.attack_r6c1);
        attack_r6c1.setTextColor(Color.GREEN);
        TextView attack_r6c2 = findViewById(R.id.attack_r6c2);
        attack_r6c2.setTextColor(Color.GREEN);
        TextView attack_r7c1 = findViewById(R.id.attack_r7c1);
        attack_r7c1.setTextColor(Color.GREEN);
        TextView attack_r7c2 = findViewById(R.id.attack_r7c2);
        attack_r7c2.setTextColor(Color.GREEN);
        TextView attack_r8c1 = findViewById(R.id.attack_r8c1);
        attack_r8c1.setTextColor(Color.GREEN);
    }

    public void SetAttackInfo(boolean flag) {
        TextView attack_selected = findViewById(R.id.atk_selected);
        //attack_selected.setText(R.string.default_atk_select);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(!flag) {
            builder.append(getString(R.string.default_atk_select))
                    .append("\n", new ImageSpan(MainActivity.this, R.drawable.img_f), 0)
                    .append(", ", new ImageSpan(MainActivity.this, R.drawable.img_f), 0)
                    .append("+")
                    .append(" ", new ImageSpan(MainActivity.this, R.drawable.img_12), 0);
            builder.setSpan(new StyleSpan(Color.GREEN), 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //TextView atk_selected = findViewById(R.id.atk_selected);
            attack_selected.setText(builder);
            //atk_selected.setTextColor(Color.GREEN);
        } else if(flag) {
            //attackInfo = "Move: " + attackData.get(0);
            attack_selected.setText(getAttackImg(attackData.get(0)));
            TextView attack_r2c1 = findViewById(R.id.attack_r2c1);
            attack_r2c1.setText(attackData.get(1));
            attack_r2c1.setTextColor(Color.GREEN);
            TextView attack_r2c2 = findViewById(R.id.attack_r2c2);
            attack_r2c2.setText(attackData.get(2));
            attack_r2c2.setTextColor(Color.GREEN);
            TextView attack_r4c1 = findViewById(R.id.attack_r4c1);
            attack_r4c1.setText(attackData.get(3));
            attack_r4c1.setTextColor(Color.GREEN);
            TextView attack_r4c2 = findViewById(R.id.attack_r4c2);
            attack_r4c2.setText(attackData.get(4));
            attack_r4c2.setTextColor(Color.GREEN);
            TextView attack_r6c1 = findViewById(R.id.attack_r6c1);
            attack_r6c1.setText(attackData.get(5));
            attack_r6c1.setTextColor(Color.GREEN);
            TextView attack_r6c2 = findViewById(R.id.attack_r6c2);
            attack_r6c2.setText(attackData.get(6));
            attack_r6c2.setTextColor(Color.GREEN);
            TextView attack_r8c1 = findViewById(R.id.attack_r8c1);
            attack_r8c1.setText(attackData.get(7));
            attack_r8c1.setTextColor(Color.GREEN);
        }
        attack_selected.setTextColor(Color.GREEN);
    }

    private SpannableStringBuilder getAttackImg(String attack_name){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String temp = attack_name;
        builder.append("Move: ").append(attack_name).append('\n');
        int size = attack_name.length();
        while(size != 0){
            if (temp.charAt(0) == 'u') {
                if (size == 1) {
                    Drawable img_u = getDrawable(R.drawable.img_u);
                    assert img_u != null;
                    img_u.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_u), 0);
                    temp = temp.substring(1);
                } else if (temp.charAt(1) == '/') {
                    if (temp.charAt(2) == 'b') {
                        if (temp.charAt(3) == '+' || temp.charAt(3) == ',') {
                            Drawable img_ub = getDrawable(R.drawable.img_ub);
                            assert img_ub != null;
                            img_ub.setBounds(0, 0, 40, 40);
                            builder.append(" ", new ImageSpan(img_ub), 0);
                            temp = temp.substring(3);
                        }
                    } else if (temp.charAt(2) == 'f') {
                        if (temp.charAt(3) == '+' || temp.charAt(3) == ',') {
                            Drawable img_uf = getDrawable(R.drawable.img_uf);
                            assert img_uf != null;
                            img_uf.setBounds(0, 0, 40, 40);
                            builder.append(" ", new ImageSpan(img_uf), 0);
                            temp = temp.substring(3);
                        }
                    }
                } else if (temp.charAt(1) == '+' || temp.charAt(1) == ',' || temp.charAt(1) == ' ') {
                    Drawable img_u = getDrawable(R.drawable.img_u);
                    assert img_u != null;
                    img_u.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_u), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == 'd') {
                if (size == 1) {
                    Drawable img_d = getDrawable(R.drawable.img_d);
                    assert img_d != null;
                    img_d.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_d), 0);
                    temp = temp.substring(1);
                } else if (temp.charAt(1) == '/') {
                    if (temp.charAt(2) == 'b') {
                        if (temp.charAt(3) == '+' || temp.charAt(3) == ',') {
                            Drawable img_db = getDrawable(R.drawable.img_db);
                            assert img_db != null;
                            img_db.setBounds(0, 0, 40, 40);
                            builder.append(" ", new ImageSpan(img_db), 0);
                            temp = temp.substring(3);
                        }
                    } else if (temp.charAt(2) == 'f') {
                        if (temp.charAt(3) == '+' || temp.charAt(3) == ',') {
                            Drawable img_df = getDrawable(R.drawable.img_df);
                            assert img_df != null;
                            img_df.setBounds(0, 0, 40, 40);
                            builder.append(" ", new ImageSpan(img_df), 0);
                            temp = temp.substring(3);
                        }
                    }
                } else if (temp.charAt(1) == '+' || temp.charAt(1) == ',' || temp.charAt(1) == ' ') {
                    Drawable img_d = getDrawable(R.drawable.img_d);
                    assert img_d != null;
                    img_d.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_d), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == 'b') {
                if (size == 1) {
                    Drawable img_b = getDrawable(R.drawable.img_b);
                    assert img_b != null;
                    img_b.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_b), 0);
                    temp = temp.substring(1);
                } else if (temp.charAt(1) == '+' || temp.charAt(1) == ',' || temp.charAt(1) == ' ') {
                    Drawable img_b = getDrawable(R.drawable.img_b);
                    assert img_b != null;
                    img_b.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_b), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == 'f') {
                if (size == 1) {
                    Drawable img_f = getDrawable(R.drawable.img_f);
                    assert img_f != null;
                    img_f.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_f), 0);
                    temp = temp.substring(1);
                } else if (temp.charAt(1) == '+' || temp.charAt(1) == ',' || temp.charAt(1) == ' ') {
                    Drawable img_f = getDrawable(R.drawable.img_f);
                    assert img_f != null;
                    img_f.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_f), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == '1') {
                if (size == 1) {
                    Drawable img_1 = getDrawable(R.drawable.img_1);
                    assert img_1 != null;
                    img_1.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_1), 0);
                    temp = temp.substring(1);
                }
                else if (temp.charAt(1) == '+') {
                    if (temp.charAt(2) == '2') {
                        Drawable img_12 = getDrawable(R.drawable.img_12);
                        assert img_12 != null;
                        img_12.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_12), 0);
                        if (size == 3) {
                            break;
                        }
                        temp = temp.substring(3);
                    } else if (temp.charAt(2) == '3') {
                        Drawable img_13 = getDrawable(R.drawable.img_13);
                        assert img_13 != null;
                        img_13.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_13), 0);
                        if (size == 3) {
                            break;
                        }
                        temp = temp.substring(3);
                    } else if (temp.charAt(2) == '4') {
                        Drawable img_14 = getDrawable(R.drawable.img_14);
                        assert img_14 != null;
                        img_14.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_14), 0);
                        if (size == 3) {
                            break;
                        }
                        temp = temp.substring(3);
                    }
                } else {
                    Drawable img_1 = getDrawable(R.drawable.img_1);
                    assert img_1 != null;
                    img_1.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_1), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == '2') {
                if (size == 1) {
                    Drawable img_2 = getDrawable(R.drawable.img_2);
                    assert img_2 != null;
                    img_2.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_2), 0);
                    temp = temp.substring(1);
                } else if (temp.charAt(1) == '+') {
                    if (temp.charAt(2) == '3') {
                        Drawable img_23 = getDrawable(R.drawable.img_23);
                        assert img_23 != null;
                        img_23.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_23), 0);
                        temp = temp.substring(3);
                    } else if (temp.charAt(2) == '4') {
                        Drawable img_24 = getDrawable(R.drawable.img_24);
                        assert img_24 != null;
                        img_24.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_24), 0);
                        temp = temp.substring(3);
                    }
                } else {
                    Drawable img_2 = getDrawable(R.drawable.img_2);
                    assert img_2 != null;
                    img_2.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_2), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == '3') {
                if (size == 1) {
                    Drawable img_3 = getDrawable(R.drawable.img_3);
                    assert img_3 != null;
                    img_3.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_3), 0);
                    temp = temp.substring(1);
                    //break;
                }
                else if (temp.charAt(1) == '+') {
                    if (temp.charAt(2) == '4') {
                        Drawable img_34 = getDrawable(R.drawable.img_34);
                        assert img_34 != null;
                        img_34.setBounds(0, 0, 40, 40);
                        builder.append(" ", new ImageSpan(img_34), 0);
                        /*if (size == 3) {
                            break;
                        }*/
                        temp = temp.substring(3);
                    }
                } else {
                    Drawable img_3 = getDrawable(R.drawable.img_3);
                    assert img_3 != null;
                    img_3.setBounds(0, 0, 40, 40);
                    builder.append(" ", new ImageSpan(img_3), 0);
                    temp = temp.substring(1);
                }
            } else if (temp.charAt(0) == '4') {
                Drawable img_4 = getDrawable(R.drawable.img_4);
                assert img_4 != null;
                img_4.setBounds(0, 0, 40, 40);
                builder.append(" ", new ImageSpan(img_4), 0);
                temp = temp.substring(1);
            } else {
                builder.append(temp.charAt(0));
                temp = temp.substring(1);
            }
            size = temp.length();
        }
        return builder;
    }
}