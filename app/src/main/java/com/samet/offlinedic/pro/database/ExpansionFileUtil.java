package com.samet.offlinedic.pro.database;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.APKExpansionPolicy;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.DataHolder;

import java.io.BufferedInputStream;
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
    private ProgressBar progressBar;
    private static final byte[] SALT = new byte[]{-46, 65, 30, -128, -103,
            -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
            89};

    public ExpansionFileUtil(Context context) {
        _context = context;
    }

    public void downloadExpensionFile() {
        String deviceId = Settings.Secure.getString(_context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        LicenseCheckerCallback mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        apkEx = new APKExpansionPolicy(_context, new AESObfuscator(SALT,
                "com.samet.offlinedic.pro", deviceId));
        LicenseChecker mChecker = new LicenseChecker(_context, apkEx, _context.getString(R.string.BASE64_PUBLIC_KEY));
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            URL u;
            URLConnection c;
            long total = 0;
            try {
                u = new URL(downloadLink);
                c = u.openConnection();
                c.connect();
                int lenghtOfFile = 8848908;

                String outFileName = DBHelper.DB_PATH + DBHelper.DB_NAME;
                InputStream myInput = new BufferedInputStream(
                        u.openStream());
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    total += length;
                    publishProgress((int) (total * 100 / lenghtOfFile));
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
            progressBar.setProgress(args[0]);
        }

        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            DataHolder.getInstance().dbHelper = new DBHelper(_context);
        }
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            // Should allow user access.
            downloadLink = apkEx
                    .getExpansionURL(APKExpansionPolicy.MAIN_FILE_URL_INDEX);
            progressBar = new ProgressBar(_context, null, android.R.attr.progressBarStyleLarge);
            progressBar.setIndeterminate(false);
            progressBar.setMax(100);
            progressBar.setVisibility(View.VISIBLE);
            DownloadFile downloadFile = new DownloadFile();
            downloadFile.execute();
        }

        public void dontAllow(int policyReason) {
        }

        public void applicationError(int errorCode) {
        }
    }
}
