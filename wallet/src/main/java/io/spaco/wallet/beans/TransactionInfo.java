package io.spaco.wallet.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class TransactionInfo implements Serializable {

    public Status status;
    public String time;
    public Txn txn;

    public static class Status implements Serializable{
        public boolean confirmed;
        public boolean unconfirmed;
        public String height;
        public String block_seq;
        public boolean unknown;

        /**
         * 获取交易状态
         * @return
         */
        public String getStatusValues(){
            if(confirmed){
                return "已完成";
            }else if(unconfirmed){
                return "未校验";
            }else if(unknown){
                return "未知的";
            }else{
                return "";
            }
        }
    }

    public static class Txn implements Serializable{
        public String length;
        public String type;
        public String txid;
        public String inner_hash;
        public String timestamp;
        public List<String> sigs;
        public  List<String> inputs;
        public  List<Outputs>  outputs;

        public static class Outputs implements Serializable{
            public String uxid;
            public String dst;
            public  String coins;
            public String hours;
        }
    }

}
