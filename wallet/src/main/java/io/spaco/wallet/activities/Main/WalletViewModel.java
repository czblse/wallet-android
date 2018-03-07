package io.spaco.wallet.activities.Main;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.spaco.wallet.api.ApiService;
import io.spaco.wallet.api.RetrofitService;
import io.spaco.wallet.datas.Address;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.datas.WalletManager;
import mobile.Mobile;

/**
 * 钱包控制层
 * Created by kimi on 2018/2/12.</br>
 */

public class WalletViewModel {

    /**
     * 所有钱包的总金额之和
     */
    public static double totalBalance = 0.0d;
    public static double totalHours = 0.0d;

    /**
     * 所有的本地钱包
     */
    public static List<Wallet> wallets;

    /**
     * 查询所有的本地钱包
     *
     * @return
     */
    public Observable<List<Wallet>> getAllWallets() {
        return Observable.create(new ObservableOnSubscribe<List<Wallet>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Wallet>> emitter) throws Exception {
                emitter.onNext(WalletManager.getInstance().getAllWallet());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<List<Wallet>, List<Wallet>>() {
                    @Override
                    public List<Wallet> apply(List<Wallet> wallets) throws Exception {
                        totalBalance = 0.00d;
                        totalHours = 0.0d;
                        for (Wallet wallet : wallets) {
                            String walletBalanceJson = Mobile.getWalletBalance(wallet.getWalletType(), wallet.getWalletID());
                            double walletBalance = Wallet.getBalanceFromRawData(walletBalanceJson);
                            double hours = Wallet.getHoursFromRawData(walletBalanceJson);
                            totalBalance += walletBalance;
                            totalHours += walletBalance * hours;
                            wallet.setBalance(String.valueOf(walletBalance));
                            wallet.setHours(String.valueOf(hours));
                            wallet.setCoinHour(String.valueOf(walletBalance * hours));
                        }
                        return wallets;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取当前钱包已使用的所有地址
     *
     * @param walletType 钱包类型
     * @param walletID   钱包id
     * @return
     */
    public Observable<List<Address>> getAllAddressByWalletId(final String walletType, final String walletID) {
        return Observable.create(new ObservableOnSubscribe<List<Address>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Address>> emitter) throws Exception {
                emitter.onNext(WalletManager.getInstance().getAddressesByWalletId(walletType, walletID));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 备份钱包,根据钱包id获取钱包种子
     * @param walletId
     * @return
     */
    public Observable<String> getWalletSeed(final String walletId){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String seed = Mobile.getSeed(walletId);
                emitter.onNext(seed);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除钱包
     * @param wallet
     * @return
     */
    public Observable<Boolean> deleteWallet(final Wallet wallet){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                WalletManager.getInstance().removeWallet(wallet);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取人民币的汇率
     */
    public Observable<String> getCNYcoinExchange(){
        return RetrofitService.getInstance().retrofit.create(ApiService.class)
                .getCNYcoinExchange().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 获取USD的汇率
     */
    public Observable<String> getUSDcoinExchange(){
        return RetrofitService.getInstance().retrofit.create(ApiService.class)
                .getUSDcoinExchange().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
