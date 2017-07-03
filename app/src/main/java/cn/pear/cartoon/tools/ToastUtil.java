package cn.pear.cartoon.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuliang on 2017/6/23.
 */

public class ToastUtil {
    private static Toast mToast = null;

    public static void showLongToast(Context context, int id) {
        if (null != context) {
            if (null == mToast) {
                mToast = Toast.makeText(context, id, Toast.LENGTH_LONG);
            } else {
                mToast.setText(id);
            }
            mToast.show();
        }
    }

    public static void showLongToast(Context context, String content) {
        if (null != context) {
            if (null == mToast) {
                mToast = Toast.makeText(context, content, Toast.LENGTH_LONG);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }

    public static void showShortToast(Context context, int id) {
        if (null != context) {
            if (null == mToast) {
                mToast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(id);
            }
            mToast.show();
        }
    }

    public static void showShortToast(Context context, String content) {
        if (null != context) {
            if (null == mToast) {
                mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }
}
