package io.spaco.wallet.activities.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.datas.Wallet;

/**
 * 钱包主页视图碎片适配器
 * Created by kimi on 2018/1/29.</br>
 */

public class MainWalletAdapter extends RecyclerView.Adapter {

    /**
     * 回调接口
     */
    MainWalletListener mainWalletListener;

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    List<Wallet> wallets;

    public MainWalletAdapter(List<Wallet> wallets){
        this.wallets = wallets == null ? new ArrayList<Wallet>() : wallets;
    }

    public void setMainWalletListener(MainWalletListener mainWalletListener){
        this.mainWalletListener = mainWalletListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(mainWalletListener == null){
                return;
            }
            int id = v.getId();
            int position = (int) v.getTag();
            int itemViewType = getItemViewType(position);
            if(itemViewType == 0){
                mainWalletListener.onItemClick(position,wallets.get(position));
//            }else if(itemViewType == 1){
//                if(id == R.id.tv_new_wallet){
//                    mainWalletListener.onCreateWallet();
//                }else if(id == R.id.tv_import_wallet){
//                    mainWalletListener.onImportWallet();
//                }
            }
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType == 0){
            holder = new MainWalletViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_wallet,parent,false));
//        }else if(viewType == 1){
//            holder = new MainWalletExpandViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_wallet_expand,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == 0){
            MainWalletViewHolder hd = (MainWalletViewHolder) holder;
            hd.navigation.setVisibility(position > 0 ? View.GONE : View.VISIBLE);
            hd.content.setTag(position);
            hd.content.setOnClickListener(onClickListener);
            Wallet wallet = wallets.get(position);
            hd.walletName.setText(wallet.getWalletName());
            hd.skyHours.setText(wallet.getCoinHour());
            hd.balance.setText(wallet.getBalance());

//        }else if(type == 1){
//            MainWalletExpandViewHolder hd = (MainWalletExpandViewHolder) holder;
//            hd.importWallet.setTag(position);
//            hd.importWallet.setOnClickListener(onClickListener);
//            hd.createWallet.setTag(position);
//            hd.createWallet.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == wallets.size() ? 1 : super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }


    /**
     * 钱包对应的holder
     */
    public static class MainWalletViewHolder extends RecyclerView.ViewHolder{

        public TextView walletName,skyHours,balance;
        public View navigation,content;

        public MainWalletViewHolder(View itemView) {
            super(itemView);
            walletName = itemView.findViewById(R.id.tv_wallet_name);
            skyHours = itemView.findViewById(R.id.tv_sky_hours);
            balance = itemView.findViewById(R.id.tv_balance);
            navigation = itemView.findViewById(R.id.navigation);
            content = itemView.findViewById(R.id.content);
        }
    }

    /**
     * 底部扩展对应的holder
     */
    public static class MainWalletExpandViewHolder extends RecyclerView.ViewHolder{

        public TextView createWallet,importWallet;

        public MainWalletExpandViewHolder(View itemView) {
            super(itemView);
            createWallet = itemView.findViewById(R.id.tv_new_wallet);
            importWallet = itemView.findViewById(R.id.tv_import_wallet);
        }
    }
}
