package io.spaco.wallet;

import org.junit.Test;

import io.spaco.wallet.api.Const;
import io.spaco.wallet.utils.DES;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEnrypt(){
        String text = "333333";
        String password = Const.DESKey;
        try {
            String result = DES.encryptDES(text, password);
            System.out.println("加密结果"  + result);

            result = DES.decryptDES(result, password);
            System.out.println("原始数据"  + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}