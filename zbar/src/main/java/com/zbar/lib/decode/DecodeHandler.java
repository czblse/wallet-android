package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.R;
import com.zbar.lib.ZbarManager;


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

		ZbarManager zbarManager = new ZbarManager();
		String result = zbarManager.decode(rotatedData, width, height, true, activity.getX(), activity.getY(), activity.getCropWidth(), activity.getCropHeight());

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
