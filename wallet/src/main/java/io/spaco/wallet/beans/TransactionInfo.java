package io.spaco.wallet.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class TransactionInfo implements Serializable {

    Status status;
    String time;
    Txn txn;

    public static class Status implements Serializable{
        String confirmed;
        String unconfirmed;
        String height;
        String block_seq;
        String unknown;
    }

    public static class Txn implements Serializable{
        String length;
        String type;
        String txid;
        String inner_hash;
        String timestamp;
        List<String> sigs;
        List<String> inputs;
        List<Outputs>  outputs;

        public static class Outputs implements Serializable{
            String uxid;
            String dst;
            String coins;
            String hours;
        }
    }

}
