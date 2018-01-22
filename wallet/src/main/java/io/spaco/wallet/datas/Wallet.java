package io.spaco.wallet.datas;

import java.security.SecureRandom;
import java.util.List;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
/**
 * Created by zjy on 2018/1/22.
 */

public class Wallet {
    //生成助记词的种子长度
    public static final int SEED_ENTROPY_DEFAULT = 192;
    public static final int SEED_ENTROPY_EXTRA = 256;

    public static String generateMnemonicString(int entropyBitsSize) {
        List<String> mnemonicWords = Wallet.generateMnemonic(entropyBitsSize);
        return mnemonicToString(mnemonicWords);
    }

    public static String mnemonicToString(List<String> mnemonicWords) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mnemonicWords.size(); i++) {
            if (i != 0) sb.append(' ');
            sb.append(mnemonicWords.get(i));
        }
        return sb.toString();
    }

    public static List<String> generateMnemonic(int entropyBitsSize) {
        byte[] entropy;
        entropy = new byte[entropyBitsSize / 8];

        SecureRandom sr = new SecureRandom();
        sr.nextBytes(entropy);

        return bytesToMnemonic(entropy);
    }

    static List<String> bytesToMnemonic(byte[] bytes) {
        List<String> mnemonic;
        try {
            mnemonic = MnemonicCode.INSTANCE.toMnemonic(bytes);
        } catch (MnemonicException.MnemonicLengthException e) {
            throw new RuntimeException(e); // should not happen, we have 16bytes of entropy
        }
        return mnemonic;
    }
}
