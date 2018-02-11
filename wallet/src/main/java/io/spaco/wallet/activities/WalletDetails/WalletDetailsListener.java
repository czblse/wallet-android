package io.spaco.wallet.activities.WalletDetails;

import io.spaco.wallet.datas.Address;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public interface WalletDetailsListener {

    /**
     * item点击回调
     * @param position
     * @param bean
     */
    void onItemClick(int position, Address bean);

    /**
     * 创建新地址
     */
    void onCreateAddress();
}
