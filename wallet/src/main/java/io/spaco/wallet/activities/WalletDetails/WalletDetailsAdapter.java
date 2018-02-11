package io.spaco.wallet.activities.WalletDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.datas.Address;

/**
 * 钱包主页视图碎片适配器
 * Created by kimi on 2018/1/29.</br>
 */

public class WalletDetailsAdapter extends RecyclerView.Adapter {

    /**
     * 回调接口
     */
    WalletDetailsListener walletDetailsListener;

    public void setWalletDetails(List<Address> walletDetails) {
        this.walletDetails = walletDetails;
    }

    List<Address> walletDetails;

    public WalletDetailsAdapter(List<Address> wallets){
        this.walletDetails = wallets == null ? new ArrayList<Address>() : wallets;
    }

    public void setWalletDetailsListener(WalletDetailsListener walletDetailsListener){
        this.walletDetailsListener = walletDetailsListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(walletDetailsListener == null){
                return;
            }
            int id = v.getId();
            int position = (int) v.getTag();
            int itemViewType = getItemViewType(position);
            if(itemViewType == 0){
                walletDetailsListener.onItemClick(position,walletDetails.get(position));
            }else if(itemViewType == 1){
                walletDetailsListener.onCreateAddress();
            }
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType == 0){
            holder = new WalletDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_details,parent,false));
        }else if(viewType == 1){
            holder = new WalletDetailsExpandViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_details_expand,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == 0){
            Address address = walletDetails.get(position);
            WalletDetailsViewHolder hd = (WalletDetailsViewHolder) holder;
            hd.navigation.setVisibility(position > 0 ? View.GONE : View.VISIBLE);
            hd.content.setTag(position);
            hd.content.setOnClickListener(onClickListener);
            hd.id.setText(address.getAddressId());
            hd.balance.setText(address.getAddresBalance());
            hd.address.setText(address.getAddress());
        }else if(type == 1){
            WalletDetailsExpandViewHolder hd = (WalletDetailsExpandViewHolder) holder;
            hd.createAddress.setTag(position);
            hd.createAddress.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == walletDetails.size() ? 1 : super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return walletDetails.size() + 1;
    }


    /**
     * 钱包对应的holder
     */
    public static class WalletDetailsViewHolder extends RecyclerView.ViewHolder{

        public TextView id,address,skyHours,balance;
        public View navigation,content;

        public WalletDetailsViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            address = itemView.findViewById(R.id.tv_address);
            skyHours = itemView.findViewById(R.id.tv_sky_hours);
            balance = itemView.findViewById(R.id.tv_balance);
            navigation = itemView.findViewById(R.id.navigation);
            content = itemView.findViewById(R.id.content);
        }
    }

    /**
     * 底部扩展对应的holder
     */
    public static class WalletDetailsExpandViewHolder extends RecyclerView.ViewHolder{

        public TextView createAddress;

        public WalletDetailsExpandViewHolder(View itemView) {
            super(itemView);
            createAddress = itemView.findViewById(R.id.tv_create_address);
        }
    }
}
