package cn.pear.cartoon.tools;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by liuliang on 2017/6/22.
 */

public class StringUtil {
    private final static String TAG = "StringUtil";

    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str)|| (str != null && "".equals(str.trim()))) {
            return true;
        }
        return false;
    }

    public static String substringAndAddPrefix(String str, int toCount,String more)
    {
        int reInt = 0;
        String reStr = "";
        if (str == null)
            return "";
        char[] tempChar = str.toCharArray();
        for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
            String s1 = str.valueOf(tempChar[kk]);
            byte[] b = s1.getBytes();
            reInt += b.length;
            reStr += tempChar[kk];
        }
        if (toCount == reInt || (toCount == reInt - 1))
            reStr = more+reStr;
        return reStr;
    }

    /**
     * MD5 Encryption
     *
     * @param str
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, e.toString());
            return null;
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.toString());
            return null;
        }

        final byte[] byteArray = messageDigest.digest();

        final StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        // 6-bit encryption, 9 to 25
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }
}
