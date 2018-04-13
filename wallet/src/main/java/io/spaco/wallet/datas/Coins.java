package io.spaco.wallet.datas;

import java.util.ArrayList;

/**
 * Created by jinyang.zheng on 2018/2/22.
 */

public class Coins {
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    private String name;
    private String url;

    public static ArrayList<Coins> GetAllCoins(){
        ArrayList<Coins> result = new ArrayList<>();
        result.add(BuildSkyInfo());
        result.add(BuildSpoInfo());
        return result;
    }

    private static Coins BuildSkyInfo(){
        Coins coins = new Coins();
        coins.name = "skycoin";
        coins.url = "i.spo.network:6420";
        return coins;
    }

    private static Coins BuildSpoInfo(){
        Coins coins = new Coins();
        coins.name = "spocoin";
        coins.url = "i.spo.network:8620";
        return coins;
    }

    @Override
    public String toString() {
        return name;
    }
}
