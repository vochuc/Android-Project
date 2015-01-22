package chuc.project.doanmonngonmoingay;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
    
	public static boolean checkConn(Context ctx) {
		ConnectivityManager conMgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null)
			return false;
		if (!i.isConnected())
			return false;
		if (!i.isAvailable())
			return false;
		return true;
	}
	
	public static String readFileData(String sFilename) {
		String storagePath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(storagePath + "/Android/Data/vn.softech.mnmn/" + sFilename);
		StringBuilder text = new StringBuilder();
		if (file.exists()) {
			try {
			    BufferedReader br = new BufferedReader(new FileReader(file));
			    String line;
			    while ((line = br.readLine()) != null) {
			        text.append(line);
			        text.append('\n');
			    }
			}
			catch (IOException e) {
			    e.printStackTrace();
			}
		}
		return text.toString();
	}
	
	public static void writeFileDataSave(String sFilename, String sBody) {
		String storagePath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(storagePath + "/Android/Data/vn.softech.mnmn/" + sFilename);
//		StringBuilder text = new StringBuilder();
		try {
			FileWriter writer = new FileWriter(file);
	        writer.append(sBody);
	        writer.flush();
	        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}