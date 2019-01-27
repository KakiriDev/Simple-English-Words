package com.kakiridev.simpleenglishwords;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class SpeechAttack extends Activity implements View.OnClickListener {


    private TextView mText;
    private SpeechRecognizer sr;
    private static final String TAG = "VoiceRecognition";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_attack);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Button speakButton = (Button) findViewById(R.id.toggleButton1);
        mText = (TextView) findViewById(R.id.textView1);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());



    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(false);
            progressBar.setMax(10);
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
            progressBar.setProgress((int) rmsdB);
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            mText.setText("error " + error);

            String mError = "";

            // TODO Auto-generated method stub
            switch (error) {
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    mError = " network timeout";
                    //startListening();
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    mError = " network" ;
                    //toast("Please check data bundle or network settings");
                    return;
                case SpeechRecognizer.ERROR_AUDIO:
                    mError = " audio";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    mError = " server";
                    //startListening();
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    mError = " client";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    mError = " speech time out" ;
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    mError = " no match" ;
                    //startListening();

                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    mError = " recogniser busy" ;
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    mError = " insufficient permissions" ;
                    break;

            }
            Log.i(TAG,  "Error: " +  error + " - " + mError);

            //startSR();
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            mText.setText("results: "+String.valueOf(data.size()));
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
    public void onClick(View v) {
        if (v.getId() == R.id.toggleButton1) {
            startRecord();
        }
    }

    private void startRecord(){

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
            sr.startListening(intent);
            Log.i("111111","11111111");

    }
}