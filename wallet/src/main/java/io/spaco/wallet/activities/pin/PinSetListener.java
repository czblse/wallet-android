package io.spaco.wallet.activities.pin;

/**
 * Created by kimi on 2018/1/24.</br>
 */

public interface PinSetListener {

    /**
     * PIN安全码设置成功
     */
    void onPinSetSuccess(String pin);

    /**
     * PIN安全码验证成功
     */
    void onPinSetVerifySuccess(String verifyPin);
}
