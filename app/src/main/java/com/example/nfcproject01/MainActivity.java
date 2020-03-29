package com.example.nfcproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nfcproject01.reader.NdefReaderTask;
import com.wang.avi.AVLoadingIndicatorView;

public class MainActivity extends AppCompatActivity {
    private NfcAdapter adapter;
    AVLoadingIndicatorView avLoadingIndicatorView;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    final IntentFilter[] filters = new IntentFilter[]{};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // button = (Button)findViewById(R.id.button);

        // ((AVLoadingIndicatorView)findViewById(R.id.avi)).hide();

        adapter = NfcAdapter.getDefaultAdapter(this);

        if (adapter == null){
            Toast.makeText(this,"No NFC ",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if(!adapter.isEnabled()){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("You need to active NFC");
            builder.setCancelable(true);
            builder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();

            alertDialog.show();


        }
        //onClickButton();


        //testNFC();

    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this,adapter);
        //adapter.enableForegroundDispatch(MainActivity.this,pi,filters,null);
        super.onPause();

    }

    private void stopForegroundDispatch(Activity activity ,NfcAdapter adapter){
        adapter.disableForegroundDispatch(activity);

    }
    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this,adapter);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Toast.makeText(this,"NFC intent Recu ",Toast.LENGTH_LONG).show();
        super.onNewIntent(intent);
    }
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN    );
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }
    private boolean hasNFC (){
        boolean hasNFC = getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
        boolean asAvailable = NfcAdapter.getDefaultAdapter(this).isEnabled();

        return  hasNFC && asAvailable;
    }
    private void testNFC() {
        adapter = NfcAdapter.getDefaultAdapter(this);

        if(adapter!=null){
            Toast.makeText(this,"NFC Existe",Toast.LENGTH_SHORT).show();

            if(adapter.isEnabled()){
                Toast.makeText(this,"NFC disponible",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"NFC non disponible",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"NFC n'existe pas",Toast.LENGTH_LONG).show();
    }
    private  void showWirelessSettings(){
        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
        startActivity(intent);
    }


}
