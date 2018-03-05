package io.spaco.wallet.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author: lh on 2018/1/23 11:17.
 * Email:luchefg@gmail.com
 * Description: 钱包Api接口
 */

public interface ApiService {

    @GET("v1/ticker/skycoin/?convert=CNY")
    Observable<String> getCNYcoinExchange();

    @GET("v1/ticker/skycoin/?convert=USD")
    Observable<String> getUSDcoinExchange();

}
