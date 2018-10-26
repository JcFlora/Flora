package com.jc.flora.apps.scene.qrcode.delegate;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

/**
 * Created by Shijincheng on 2018/10/25.
 */

public class QrcodeCreateDelegate {

    /** 黑点颜色 */
    private static final int BLACK = 0xFF000000;
    /** 白色 */
    private static final int WHITE = 0xFFFFFFFF;

    // 二维码的宽度和高度
    private int mQrcodeWith = 200, mQrcodeHeight = 200;

    /** 配置集合 */
    private HashMap<EncodeHintType, Object> mEncodeHints = new HashMap<>();
    /** 编码配置 */
    private String mEncodeHintCharacterSet = "utf-8";
    /** 容错配置，系统默认是L，这里修改为，容错率高一点，容易扫得出来 */
    private ErrorCorrectionLevel mEncodeHintErrorCorrection = ErrorCorrectionLevel.M;
    /** 设置白色边距值，默认是4，这里改成0 */
    private int mEncodeHintMargin = 0;

    public QrcodeCreateDelegate() {
        mEncodeHints.put(EncodeHintType.CHARACTER_SET, mEncodeHintCharacterSet);
        mEncodeHints.put(EncodeHintType.ERROR_CORRECTION, mEncodeHintErrorCorrection);
        mEncodeHints.put(EncodeHintType.MARGIN, mEncodeHintMargin);
    }

    public void setWidthAndHeight(int width, int height){
        if(width <= 0 || height <= 0){
            return;
        }
        mQrcodeWith = width;
        mQrcodeHeight = height;
    }

    public void setEncodeHints(HashMap<EncodeHintType, Object> hints) {
        mEncodeHints = hints;
    }

    public void setEncodeHintCharacterSet(String characterSet) {
        mEncodeHintCharacterSet = characterSet;
        if(mEncodeHints != null){
            mEncodeHints.put(EncodeHintType.CHARACTER_SET, mEncodeHintCharacterSet);
        }
    }

    public void setEncodeHintErrorCorrection(ErrorCorrectionLevel errorCorrection) {
        mEncodeHintErrorCorrection = errorCorrection;
        if(mEncodeHints != null){
            mEncodeHints.put(EncodeHintType.ERROR_CORRECTION, mEncodeHintErrorCorrection);
        }
    }

    public void setEncodeHintMargin(int margin) {
        mEncodeHintMargin = margin;
        if(mEncodeHints != null){
            mEncodeHints.put(EncodeHintType.MARGIN, mEncodeHintMargin);
        }
    }

    /**
     * 生成一个二维码
     * @param content   二维码的内容
     * @return
     */
    public Bitmap createQrCode(String content) {
        try {
            //参数分别是内容，编码格式，宽，长，配置集合。
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, mQrcodeWith, mQrcodeHeight, mEncodeHints);

            //解析成像素点
            int[] pixels = new int[mQrcodeWith * mQrcodeHeight];
            for (int y = 0; y < mQrcodeHeight; y++) {
                for (int x = 0; x < mQrcodeWith; x++) {
                    pixels[y * mQrcodeWith + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
                }
            }

            //用上述像素点生成bitmap
            return Bitmap.createBitmap(pixels, 0, mQrcodeWith, mQrcodeWith, mQrcodeHeight, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以图片合成方式生成带logo二维码
     * 需要注意，使用这种方式生成，二维码的容错率一定要设置的较高，
     * 否则会因为图片挡住二维码导致无法扫描成功
     * @param content
     * @param logoBitmap
     * @return
     */
    public Bitmap addLogoToQrCode(String content, Bitmap logoBitmap){
        return addLogoToQrBitmap(createQrCode(content), logoBitmap);
    }

    /**
     * 以图片合成方式生成带logo二维码
     * 需要注意，使用这种方式生成，二维码的容错率一定要设置的较高，
     * 否则会因为图片挡住二维码导致无法扫描成功
     * @param qrBitmap
     * @param logoBitmap
     * @return
     */
    private Bitmap addLogoToQrBitmap(Bitmap qrBitmap, Bitmap logoBitmap) {
        //只是将logo加在原先的二维码上即可
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        //递归设置缩放比例。使logo长宽都在二维码的1/5以内。
        float scaleSize = 1.0f;
        while ((logoWidth / scaleSize) > (mQrcodeWith / 5) || (logoHeight / scaleSize) > (mQrcodeHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;

        //图片合成
        Bitmap blankBitmap = Bitmap.createBitmap(mQrcodeWith, mQrcodeHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        // 画二维码
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        // 保存二维码底图
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 缩放画布到sx倍大小
        canvas.scale(sx, sx, mQrcodeWith / 2, mQrcodeHeight / 2);
        // 画logo图
        canvas.drawBitmap(logoBitmap, (mQrcodeWith - logoWidth) / 2, (mQrcodeHeight - logoHeight) / 2, null);
        // 合成
        canvas.restore();
        return blankBitmap;
    }

    /**
     * 像素合成方式，生成带LOGO的二维码
     * 虽然在容错级别较低时，比图片合成方式的容易扫，但有可能无法进行识别(比如微信的识别图中二维码)
     * 不推荐使用此方法生成
     * @param content
     * @param logoBitmap
     * @return
     */
    public Bitmap createQrcodeWithLogo(String content, Bitmap logoBitmap) {
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();
        int logoHaleWidth = mQrcodeWith / 5;
        int logoHaleHeight = mQrcodeHeight / 5;

        // 将logo图片按matrix设置的信息缩放
        Matrix m = new Matrix();
        float sx = (float) logoHaleWidth / logoWidth;
        float sy = (float) logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);

        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        try {
            // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
            BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, mQrcodeWith, mQrcodeHeight, mEncodeHints);
            int halfW = mQrcodeWith / 2;
            int halfH = mQrcodeHeight / 2;
            // 二维矩阵转为一维像素数组,也就是一直横着排了
            int[] pixels = new int[mQrcodeWith * mQrcodeHeight];
            for (int y = 0; y < mQrcodeHeight; y++) {
                for (int x = 0; x < mQrcodeWith; x++) {
                    // 取值范围,可以画图理解下
                    // halfW + newLogoWidth / 2 - (halfW - newLogoWidth / 2) = newLogoWidth
                    // halfH + newLogoHeight / 2 - (halfH - newLogoHeight) = newLogoHeight
                    if (x > halfW - newLogoWidth / 2&& x < halfW + newLogoWidth / 2
                            && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
                        // 记录图片每个像素信息
                        // halfW - newLogoWidth / 2 < x < halfW + newLogoWidth / 2
                        // --> 0 < x - halfW + newLogoWidth / 2 < newLogoWidth
                        // halfH - newLogoHeight / 2  < y < halfH + newLogoHeight / 2
                        // -->0 < y - halfH + newLogoHeight / 2 < newLogoHeight
                        // 刚好取值newLogoBitmap。getPixel(0-newLogoWidth,0-newLogoHeight);
                        pixels[y * mQrcodeWith + x] = newLogoBitmap.getPixel(
                                x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                    } else {
                        pixels[y * mQrcodeWith + x] = matrix.get(x, y) ? BLACK: WHITE;// 设置信息
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(mQrcodeWith, mQrcodeHeight, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap,具体参考api
            bitmap.setPixels(pixels, 0, mQrcodeWith, 0, 0, mQrcodeWith, mQrcodeHeight);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
