package cn.pear.cartoon.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author yanggf
 *
 */
public class Utils {	

	public static final String FORCE_UPDATE = "forceupdate";
	public static final String CACHE_UPDATE = "cacheupdate";
	public static final String NULL = "null";


	/**
	 * 判断是否有存储卡，有返回TRUE，否则FALSE
	 * 
	 * @return
	 */
	public static boolean isSDcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isNetworkUri(Uri uri) {
		boolean isUri = false;
		try {
			if (!StringUtil.isEmpty(uri.toString())) {
				
				String scheme =uri.getScheme();
				if (scheme != null && (scheme.contains("http")||scheme.contains("www")
						||scheme.contains("rtsp")||scheme.contains("mms"))) {
					isUri = true;
				} else {
					isUri = false;
				}			
			}
		} catch (Exception e) {
			return isUri;
		}
		return isUri;	
	}
	
	
	public static boolean isEmpty(String str) {
		if (TextUtils.isEmpty(str) || Utils.NULL.equals(str)
				|| (str != null && "".equals(str.trim()))) {
			return true;
		}
		return false;
	}

	public static boolean  getFirstStartForceUpdateVaule(Context context) {
		   if (null == context)
			   return false;
		   SharedPreferences sharedPreferences = context.getSharedPreferences(FORCE_UPDATE, context.MODE_PRIVATE);
		   return sharedPreferences.getBoolean(CACHE_UPDATE, false);
	   }
	   
		 public static void setIsFirstStartForceUpdate (boolean flag ,Context context) {
			   if (null == context)
				   return;
			   SharedPreferences sharedPreferences = context.getSharedPreferences(FORCE_UPDATE, context.MODE_PRIVATE );
			   Editor edit = sharedPreferences.edit();
			   edit.putBoolean(CACHE_UPDATE, flag);
			   edit.commit();
		   } 
		 
			
		 /**
		  * 判断用户输入的内容是是否为数字
		  * 2013-5-16下午2:11:02
		  * @param str
		  * @return
		  */
	public static boolean isNumber(String str) {
		try {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher match = pattern.matcher(str);
			return match.matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	public static boolean isMailAddress (String str) {
		try {
			Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
	        Matcher matcher = pattern.matcher(str);
	        return matcher.matches();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isQQNumber (String str) {
		try {
			int length = str.length();
			return ((!str.startsWith("0"))&&isNumber(str) && (length >= 5 && length < 11));
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean isPhoneNumber (String str) {
		try {
			Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
			Matcher matcher = pattern.matcher(str);
			return (isNumber(str) &&str.length() == 11 &&  matcher.matches());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Detect whether there are network
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isCheckNetAvailable(Context context) {
		boolean isCheckNet = false;
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo mobNetInfoActivity = connectivityManager
					.getActiveNetworkInfo();
			if (mobNetInfoActivity != null && mobNetInfoActivity.isAvailable()) {
				isCheckNet = true;
				return isCheckNet;
			} else {
				isCheckNet = false;
				return isCheckNet;
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return isCheckNet;
	}

	public static String getFileName(String uri) {
		String name = uri;
		if (name != null) {
			String[] content = name.split("/");
			if (content != null && content.length > 1) {
				name = content[content.length - 1];
			}
		}
		return name;
	} 
	
	public static boolean isCheckUriByM3u8(Context context, Uri uri) {
		boolean isUri = false;
		try {
			if (uri != null) {
				if (uri.toString() != null && uri.toString().toLowerCase().contains("m3u8")
						||uri.toString().contains("rtsp")) {
					isUri = true;
				} else {
					isUri = false;
				}
			
			}
		} catch (Exception e) {
			return isUri;
		}
		return isUri;

	}
	/**
	 * Detect whether the network address
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static boolean checkUri(Context context, Uri uri) {
		boolean isUri = false;
		try {
			if (uri != null) {
				if (uri.getScheme().contains("http")
						|| uri.getScheme().equals("rtsp")
						|| uri.getScheme().equals("mms")
						) {
					isUri = true;
				} else {
					isUri = false;
				}
//				LogUtil.i(TAG, "---checkUri()--getScheme()==" + uri.getScheme());
			}
			return isUri;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUri;

	}

		public static boolean isCMWAP(Context context) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info == null || !info.isAvailable()) { 
				return false;
			} else if (info.getTypeName().equals("WIFI")) {
				return false;
			} else if (info.getTypeName() != null
					&& info.getTypeName().equals("MOBILE")
					&& info.getExtraInfo() != null
					&& info.getExtraInfo().toLowerCase().equals("cmwap")) {

				return true; 
			}
			return false;
		}
	/**
	 * @author donggx
	 * 
	 * @param view
	 * 
	 * http://stackoverflow.com/questions/4611822/java-lang-outofmemoryerror-bitmap-size-exceeds-vm-budget
	 */
	public static void unbindDrawables(View view) {
		try {
			if (null == view) {
				return;
			}
			if (view.getBackground() != null) {
				view.getBackground().setCallback(null);
			}
			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
				((ViewGroup) view).removeAllViews();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Access to device resolution and density information to determine its to
	 * belong aphone or apad criterion here is to determine the resolution and
	 * dpi comprehensive comparison, about the standard Greater than 800x480 for
	 * the pad is less than for the phone
	 */
	public static String getDeviceType(Context context) {
		if (null == context) {
			return "";
		}
		String deviceType = "";

		DisplayMetrics dm = new DisplayMetrics();
		if (null != dm) {
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			double x = Math.pow(dm.widthPixels, 2);
			double y = Math.pow(dm.heightPixels, 2);
			double screenInches = Math.sqrt(x + y) / (160 * dm.density);

			if (2 < screenInches && 5 > screenInches) {
				deviceType = "aphone";
			} else {
				deviceType = "apad";
			}
		}
		return deviceType;
	}

	public static void netNoPlayeDialog(Context context) {// 退出确认
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setTitle("提示");
		ad.setMessage("暂时只支持android_2.1以上系统");
		ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 退出按钮
					
					public void onClick(DialogInterface dialog, int i) {
					}
				});
		
		ad.show();// 显示对话框
	}

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	public static int generateViewId() {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			for (; ; ) {
				final int result = sNextGeneratedId.get();
				// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
				int newValue = result + 1;
				if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
				if (sNextGeneratedId.compareAndSet(result, newValue)) {
					return result;
				}
			}
		} else {
			return View.generateViewId();
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	public static float getDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();

		float density = dm.density;        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
//		int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120/160/240/320）
		return density;
	}


	public  static  void sendMessage(int code , Handler handler , String message){
		Message msg = Message.obtain();
		msg.obj = message;
		msg.what = code;
		handler.sendMessage(msg);
	}

	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
