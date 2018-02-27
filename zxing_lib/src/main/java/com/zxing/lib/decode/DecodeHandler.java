package com.zxing.lib.decode;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.zxing.lib.CaptureActivity;
import com.zxing.lib.R;

import java.util.Hashtable;


final class DecodeHandler extends Handler {

	CaptureActivity activity = null;

	DecodeHandler(CaptureActivity activity) {
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message message) {
		if(message == null)return;
		if (message.what == R.id.decode) {
			decode((byte[]) message.obj, message.arg1, message.arg2);

		} else if (message.what == R.id.quit) {
			Looper.myLooper().quit();

		}
	}

	private void decode(byte[] data, int width, int height) {
		byte[] rotatedData = new byte[data.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = data[x + y * width];
		}
		int tmp = width;// Here we are swapping, that's the difference to #11
		width = height;
		height = tmp;
		String result = null;

		try {
			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
			hints.put(DecodeHintType.ALLOWED_LENGTHS, Boolean.TRUE);
			PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(rotatedData,
					width,
					height,
					activity.getX(), activity.getY(),
					activity.getCropWidth(),
					activity.getCropHeight(),
					false);
			BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
			result = new QRCodeReader().decode(bitmap1, hints).getText();
			Log.d("qrcode",result + "  result");
		}catch (Exception e){
			e.printStackTrace();
		}
		if (result != null) {
			if(null != activity.getHandler()){
				Message msg = new Message();
				msg.obj = result;
				msg.what = R.id.decode_succeeded;
				activity.getHandler().sendMessage(msg);
			}
		} else {
			if (null != activity.getHandler()) {
				activity.getHandler().sendEmptyMessage(R.id.decode_failed);
			}
		}
	}

}
