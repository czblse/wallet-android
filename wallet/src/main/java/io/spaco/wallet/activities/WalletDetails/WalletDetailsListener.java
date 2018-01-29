package io.spaco.wallet.activities.WalletDetails;

import io.spaco.wallet.beans.WalletDetailsBean;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public interface WalletDetailsListener {

    /**
     * item点击回调
     * @param position
     * @param bean
     */
    void onItemClick(int position, WalletDetailsBean bean);

    /**
     * 创建新地址
     */
    void onCreateAddress();
}
