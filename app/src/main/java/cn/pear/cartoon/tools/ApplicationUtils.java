package cn.pear.cartoon.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.pear.cartoon.R;

/**
 * Created by liuliang on 2017/6/28.
 */

public class ApplicationUtils {
    /**
     * Display a standard yes / no dialog.
     * @param context The current context.
     * @param icon The dialog icon.
     * @param title The dialog title.
     * @param message The dialog message.
     * @param onYes The dialog listener for the yes button.
     */
    //--弹出对话框，提示确认或者取消，确认则删除，取消则不删除
    public static void showYesNoDialog(Context context, int icon, int title, int message, DialogInterface.OnClickListener onYes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setIcon(icon);
        builder.setTitle(context.getResources().getString(title));
        builder.setMessage(context.getResources().getString(message));

        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(context.getResources().getString(R.string.Commons_Yes), onYes);
        builder.setNegativeButton(context.getResources().getString(R.string.Commons_No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String getVerName(Context mContext){
        try {
            PackageManager packageManager = mContext.getApplicationContext().getPackageManager();
            PackageInfo packageInfo =packageManager.getPackageInfo(mContext.getApplicationContext().getPackageName(),0);
            String ver_name =packageInfo.versionName;
            return ver_name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
