package cn.pear.barcodescanner.encode;


import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Created by Administrator on 2017/5/27.
 *
 */

public class EncodingHandler {
    private static final int BLACK = 0xff000000;  //控制二维码图形的颜色

    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        /**
         * str， 用于传递图形中对应的字符串数据
         * BarcodeFormat.QR_CODE 要生成的图形时二维码图形
         * 后两个参数：生成的图形的宽高
         * encode方法的作用：根据指定的字符传，宽高，以及图形类型
         * 生成一个对应图形矩阵， 在此矩阵中存储图形的相关信息
         */
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        //获取BitMatrix对应的宽高
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //根据宽和高，创建一个数组，稍后用于存储BitMatirx中每一个点上要显示图片像素值
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /**
                 * 根据get方法判断当前点上是否要显示有效图形还是空白
                 */
                //原代码
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
                //阴阳脸图形
//				if (matrix.get(x, y)) {
//					if (x <= width/2) {
//						pixels[y * width + x] = BLACK;
//					} else {
//						pixels[y * width + x] = 0xffff0000;
//					}
//
//				}
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //	设置图形要显示的像素，即让bitmap显示指定数组中的所有图形
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成中心带头像的二维码图形：
     * 1. 向上方一样正常处理图形的生成
     * 2. 需要判断，xy坐标点所处的位置，如果位置是在中心头像意外，
     * 那么就让对应的点上显示黑色或者蓝色等颜色
     * 3. 如果所处的位置是在中心头像区域内，那么就让对应的点上
     * 显示头像图片中的颜色值
     */
    public static Bitmap createQRCode(String str, int widthAndHeight, Bitmap icon, int iconWidth) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        //处理中心图片
        Bitmap logo = Bitmap.createScaledBitmap(icon,iconWidth,iconWidth,true);
        //获取到头像图片的宽高的1/2
        int bitWidth = logo.getWidth()/2;
        int bitHeight = logo.getHeight()/2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((x > width/2-bitWidth && x < width/2 +bitWidth) &&
                        (y>height/2 -bitHeight && y<height/2+bitHeight)){  //作用：判断当前的x，y点的范围是否位于头像范围内
                    //当前x,y点位于头像范围内
                    pixels[y * width + x] = logo.getPixel(x-(width/2-bitWidth),y-(height/2 -bitHeight));

                } else{
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = BLACK;
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    //用于创建条形码的方法
    public static Bitmap createEANCode(String str, int mwidth, int mheight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        /**
         * str， 用于传递图形中对应的字符串数据
         * BarcodeFormat.CODE_128 条形码类型，可以任意类型的128为以下的字符串
         * 后两个参数：生成的图形的宽高
         * encode方法的作用：根据指定的字符传，宽高，以及图形类型
         * 生成一个对应图形矩阵， 在此矩阵中存储图形的相关信息
         *
         */
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, mwidth, mheight);
        //获取BitMatrix对应的宽高
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //根据宽和高，创建一个数组，稍后用于存储BitMatirx中每一个点上要显示图片像素值
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /**
                 * 根据get方法判断当前点上是否要显示有效图形还是空白
                 */
                //原代码
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //	设置图形要显示的像素，即让bitmap显示指定数组中的所有图形
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
