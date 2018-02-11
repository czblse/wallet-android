package io.spaco.wallet.activities.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import io.spaco.wallet.R;
import io.spaco.wallet.beans.TransactionInfo;
import io.spaco.wallet.common.OnItemClickListener;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class MainTransactionAdapter extends RecyclerView.Adapter<MainTransactionAdapter.MainTransactionViewHolder> {

    List<TransactionInfo> transactionInfos;
    OnItemClickListener<TransactionInfo> onItemClickListener;

    public MainTransactionAdapter(List<TransactionInfo> mainTransactionBeanList){
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
            onItemClickListener.onClick(position,transactionInfos.get(position));
        }
    };

    @Override
    public MainTransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainTransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(MainTransactionViewHolder holder, int position) {
        TransactionInfo transactionInfo = transactionInfos.get(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onClickListener);
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
