package io.spaco.wallet.common;

/**
 * 通用的接口回调
 * Created by kimi on 2018/1/29.</br>
 */

public interface OnCompleteListener<S,F> {

    /**
     * 成功
     * @param s 由泛型确定
     */
    void onSuccess(S s);


    /**
     * 失败
     * @param f
     */
    void onError(F f);
}
