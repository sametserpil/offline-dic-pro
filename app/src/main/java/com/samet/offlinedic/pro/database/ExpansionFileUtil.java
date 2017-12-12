package com.samet.offlinedic.pro.database;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.APKExpansionPolicy;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.samet.offlinedic.pro.IDownloadListener;
import com.samet.offlinedic.pro.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class ExpansionFileUtil {
    private APKExpansionPolicy apkEx;
    private Context _context;
    private String downloadLink;
    private IDownloadListener downloadListener;
    private static final byte[] SALT = new byte[]{-46, 65, 30, -128, -103,
            -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
            89};

    public ExpansionFileUtil(Context context, IDownloadListener downloadListener) {
        _context = context;
        this.downloadListener = downloadListener;
    }

    public void downloadExpensionFile() {
        String deviceId = Settings.Secure.getString(_context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        LicenseCheckerCallback mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        apkEx = new APKExpansionPolicy(_context, new AESObfuscator(SALT,
                _context.getPackageName(), deviceId));
        apkEx.resetPolicy();
        LicenseChecker mChecker = new LicenseChecker(_context, apkEx, _context.getString(R.string.BASE64_PUBLIC_KEY));
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            URL u;
            URLConnection c;
            try {
                u = new URL(downloadLink);
                c = u.openConnection();
                c.connect();
                String outFileName = DBHelper.DB_PATH + DBHelper.DB_NAME;
                File dbFolder = new File(DBHelper.DB_PATH);
                if (!dbFolder.exists()) {
                    dbFolder.mkdir();
                }
                InputStream myInput = new BufferedInputStream(
                        u.openStream());
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException e) {
                Log.e(_context.getString(R.string.app_name), "" + e.getMessage());
            }
            return null;
        }

        public void onProgressUpdate(Integer... args) {

        }

        protected void onPostExecute(String result) {
            downloadListener.onDownloadComplete();
        }
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            // Should allow user access.
            downloadLink = apkEx
                    .getExpansionURL(APKExpansionPolicy.MAIN_FILE_URL_INDEX);
            DownloadFile downloadFile = new DownloadFile();
            downloadFile.execute();
        }

        public void dontAllow(int policyReason) {
        }

        public void applicationError(int errorCode) {
        }
    }
}
