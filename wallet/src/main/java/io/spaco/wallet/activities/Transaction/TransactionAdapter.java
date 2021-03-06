package io.spaco.wallet.activities.Transaction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.beans.TransactionInfo;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.common.OnItemClickListener;
import io.spaco.wallet.utils.SharePrefrencesUtil;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MainTransactionViewHolder> {

    List<TransactionInfo> transactionInfos;
    OnItemClickListener<TransactionInfo> onItemClickListener;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

    //汇率
    double exchangeCoin = SharePrefrencesUtil.getInstance().getBoolean(Constant.IS_LANGUAGE_ZH)
            ? Double.parseDouble(SharePrefrencesUtil.getInstance().getString(Constant.CNYEXCHAGECOIN))
            : Double.parseDouble(SharePrefrencesUtil.getInstance().getString(Constant.USDEXCHAGECOIN));
    DecimalFormat decimalFormat = new DecimalFormat(".00");

    public TransactionAdapter(List<TransactionInfo> mainTransactionBeanList){
        this.transactionInfos = mainTransactionBeanList == null ? new ArrayList<TransactionInfo>() : mainTransactionBeanList;
    }

    public void setOnItemClickListener( OnItemClickListener<TransactionInfo> onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public List<TransactionInfo> getDatas(){
        return transactionInfos;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(onItemClickListener == null)
                return;
            int position = (int) v.getTag();
            onItemClickListener.onClick(v.getId(),position,transactionInfos.get(position));
        }
    };

    @Override
    public MainTransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainTransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(MainTransactionViewHolder holder, int position) {
        TransactionInfo transactionInfo = transactionInfos.get(position);
        String time = transactionInfo.time;
        Date date = new Date(Long.parseLong(time));
        holder.date.setText(simpleDateFormat.format(date));
        holder.address.setText(transactionInfo.toWallet);
        holder.transaction.setText("-"+transactionInfo.amount);
        holder.flag.setText("  Send "+transactionInfo.coinType);
        holder.itemView.setTag(position);
        holder.address.setTag(position);
        holder.address.setOnClickListener(onClickListener);
        holder.itemView.setOnClickListener(onClickListener);
        double value = Double.valueOf(transactionInfo.amount) * exchangeCoin;
        if (SharePrefrencesUtil.getInstance().getBoolean(Constant.IS_LANGUAGE_ZH))
            holder.balance.setText("- ￥" + decimalFormat.format(value));
        else
            holder.balance.setText("- $" + decimalFormat.format(value));
    }

    @Override
    public int getItemCount() {
        return transactionInfos.size();
    }


    /**
     * viewholder
     */
    public class MainTransactionViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView flag,date,address,transaction,balance;

        public MainTransactionViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            flag = itemView.findViewById(R.id.tv_flag);
            date = itemView.findViewById(R.id.tv_date);
            address = itemView.findViewById(R.id.tv_address);
            transaction = itemView.findViewById(R.id.tv_transaction);
            balance = itemView.findViewById(R.id.tv_balance);
        }
    }
}
