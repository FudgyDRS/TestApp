package fudgydrs.com.testapp;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class FileUtils extends MainActivity {
    public static String[] FindCharacter(String charName) {
        String[] data = new String[2];
        switch (charName) {
            case "akuma":
                data[0] = "akuma";
                data[1] = "Akuma Frame Data";
                break;
            case "alisa":
                data[0] = "alisa";
                data[1] = "Alisa Frame Data";
                break;
            case "asuka":
                data[0] = "asuka";
                data[1] = "Asuka Frame Data";
                break;
            case "bob":
                data[0] = "bob";
                data[1] = "Bob Frame Data";
                break;
            case "bryan":
                data[0] = "bryan";
                data[1] = "Bryan Frame Data";
                break;
            case "claudio":
                data[0] = "claudio";
                data[1] = "Claudio Frame Data";
                break;
            case "devil jin":
            case "dvj":
            case "devil_jin":
                data[0] = "devil_jin";
                data[1] = "Devil Jin Frame Data";
                break;
            case "dragunov":
            case "drag":
                data[0] = "dragunov";
                data[1] = "Dragunov Frame Data";
                break;
            case "eddy":
                data[0] = "eddy";
                data[1] = "Eddy Frame Data";
                break;
            case "eliza":
                data[0] = "eliza";
                data[1] = "Eliza Frame Data";
                break;
            case "feng":
                data[0] = "feng";
                data[1] = "Feng Frame Data";
                break;
            case "geese":
                data[0] = "geese";
                data[1] = "Geese Frame Data";
                break;
            case "gigas":
                data[0] = "gigas";
                data[1] = "Gigas Frame Data";
                break;
            case "heihachi":
            case "hei":
                data[0] = "heihachi";
                data[1] = "Heihachi Frame Data";
                break;
            case "hwoarang":
            case "hwo":
                data[0] = "hwoarang";
                data[1] = "Hwoarang Frame Data";
                break;
            case "jack":
            case "jack7":
            case "jack_7":
            case "jack 7":
                data[0] = "jack_7";
                data[1] = "Jack 7 Frame Data";
                break;
            case "jin":
                data[0] = "jin";
                data[1] = "Jin Frame Data";
                break;
            case "josie":
                data[0] = "josie";
                data[1] = "Josie Frame Data";
                break;
            case "katarina":
                data[0] = "katarina";
                data[1] = "Katarina Frame Data";
                break;
            case "kazumi":
                data[0] = "kazumi";
                data[1] = "Kazumi Frame Data";
                break;
            case "kazuya":
            case "kaz":
                data[0] = "kazuya";
                data[1] = "Kazuya Frame Data";
                break;
            case "king":
                data[0] = "king";
                data[1] = "King Frame Data";
                break;
            case "kuma":
                data[0] = "kuma";
                data[1] = "Kuma Frame Data";
                break;
            case "lars":
                data[0] = "lars";
                data[1] = "Lars Frame Data";
                break;
            case "law":
                data[0] = "law";
                data[1] = "Law Frame Data";
                break;
            case "lee":
                data[0] = "lee";
                data[1] = "Lee Frame Data";
                break;
            case "leo":
                data[0] = "leo";
                data[1] = "Leo Frame Data";
                break;
            case "lili":
                data[0] = "lili";
                data[1] = "Lili Frame Data";
                break;
            case "lucky chloe":
            case "lucky_chloe":
            case "lucky":
            case "chloe":
                data[0] = "lucky_chloe";
                data[1] = "Lucky Chloe Frame Data";
                break;
            case "master raven":
            case "master_raven":
            case "raven":
                data[0] = "master_raven";
                data[1] = "Master Raven Frame Data";
                break;
            case "miguel":
            case "mig":
                data[0] = "miguel";
                data[1] = "Miguel Frame Data";
                break;
            case "nina":
                data[0] = "nina";
                data[1] = "Nina Frame Data";
                break;
            case "noctis":
                data[0] = "noctis";
                data[1] = "Noctis Frame Data";
                break;
            case "paul":
            case "conehead":
                data[0] = "paul";
                data[1] = "Paul Frame Data";
                break;
            case "shaheen":
                data[0] = "shaheen";
                data[1] = "Shaheen Frame Data";
                break;
            case "steve":
                data[0] = "steve";
                data[1] = "Steve Frame Data";
                break;
            case "xiaoyu":
            case "ling":
                data[0] = "xiaoyu";
                data[1] = "Xiaoyu Frame Data";
                break;
            case "yoshimitsu":
            case "yoshi":
                data[0] = "yoshimitsu";
                data[1] = "Yoshimitsu Frame Data";
                break;
            default:
                data[0] = "none";
                data[1] = "";
        }
        return new String[]{data[0], data[1]};
    }

    public static List<String> FindAttack(String charName, String attack, AssetManager mngr) throws IOException {
        return readJSONFromAsset(charName, attack, mngr);
    }

    private static List<String> readJSONFromAsset(String charFile, String attack, AssetManager mngr) throws IOException {
        List<String> attackInfo = Arrays.asList(new String[8]);
        try {
            mngr.open(charFile + ".json");
            String json = readFile(charFile + ".json", mngr); // **File has been read as string!!!**
            JSONObject obj = new JSONObject(json);
            JSONArray attack_list = obj.getJSONArray("attack_list");
            int i = 0, max = attack_list.length();
            while (i < max) {
                if (attack_list.getJSONObject(i).getString("attack_name").equals(attack)) {
                    attackInfo.set(0, attack_list.getJSONObject(i).getString("attack_name"));
                    attackInfo.set(1, attack_list.getJSONObject(i).getString("property"));
                    attackInfo.set(2, attack_list.getJSONObject(i).getString("damage"));
                    attackInfo.set(3, attack_list.getJSONObject(i).getString("startup"));
                    attackInfo.set(4, attack_list.getJSONObject(i).getString("block"));
                    attackInfo.set(5, attack_list.getJSONObject(i).getString("hit"));
                    attackInfo.set(6, attack_list.getJSONObject(i).getString("counter_hit"));
                    attackInfo.set(7, attack_list.getJSONObject(i).getString("notes"));
                    MainActivity.atkFlag = true;
                    break;
                }
                i++;
                MainActivity.atkFlag = false;
            }
        } catch (JSONException e) { e.printStackTrace(); }
        if (atkFlag)
            return attackInfo;
        return null;
    }


    private static String readFile(String fileName, AssetManager mngr) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(mngr.open(fileName)
                , "UTF-8"));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            content.append(line);
        return content.toString();
    }
}