package com.kakiridev.simpleenglishwords.SpeechAttack;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.KnownWord;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;


public class SpeechAttack extends Activity implements RecognitionListener {

    private TextView returnedText, wordTV;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VR_Error";
    private TextToSpeech tts;
    private AudioManager manager;
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_attack);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

        returnedText = findViewById(R.id.textView1);
        wordTV = findViewById(R.id.Tile);

        //UNMUTE
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setStreamMute(AudioManager.STREAM_MUSIC, false);

        word = randWord();
        setWordTV();
        sayWordEN(word);
        initSpeechToText();

        //speech.stopListening();
    }

    private void initSpeechToText(){
        Log.i(LOG_TAG, "initSpeechToText");
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        speech.startListening(recognizerIntent);
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    /**
     * @Override protected void onPause() {
     * super.onPause();
     * <p>
     * if (speech != null) {
     * speech.destroy();
     * Log.i(LOG_TAG, "destroy");
     * }
     * <p>
     * }
     **/
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
       // speech.stopListening();
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);


        if (errorMessage.equals("No match")) {
            //manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            speech.startListening(recognizerIntent);
        } else if (errorMessage.equals("No speech input")) {

        }else if (errorMessage.equals("RecognitionService busy")) {

        }

        returnedText.setText(errorMessage);
       //
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.d(LOG_TAG, "onPartialResults");
        String next = "Next";
        String repeat = "Repeat";
        ArrayList<String> matches = arg0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches) {
            // returnedText.setText(text);
            text += result + "\n";
        }
        Log.d(LOG_TAG, "onPartialResultss - " + text);

            if (text.toLowerCase().contains(next.toLowerCase())) { /** NEXT **/
                Log.d(LOG_TAG, "onPartialResults - ...");
                returnedText.setText("Dobrze " + next + "   " + word);

                word = randWord();
                setWordTV();
                sayWordEN(word);

            } else if (text.toLowerCase().contains(repeat.toLowerCase())){ /** REPEAT **/
                Log.d(LOG_TAG, "onPartialResults - REPEAT");

                sayWordEN(word);
                returnedText.setText("Dobrze " + repeat);
            //    speech.startListening(recognizerIntent);
            } else if (text.toLowerCase().contains(word.getnazwaEn().toLowerCase())){ /** WORD **/
                Log.d(LOG_TAG, "onPartialResults - WORD");
                word = randWord();
                setWordTV();
                returnedText.setText("Dobrze " + word.getnazwaEn().toLowerCase());
            } else {
                Log.d(LOG_TAG, "onPartialResults - ELSE");
//                speech.startListening(recognizerIntent);
//                Log.d(LOG_TAG, "onPartialResults - StartListener");
            }


    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }


    @Override
    public void onResults(Bundle results) {
//        Log.i(LOG_TAG, "onResults");
//        String next = "Next";
//        String repeat = "Repeat";
//        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        String text = "";
//        for (String result : matches) {
//            // returnedText.setText(text);
//            text += result + "\n";
//        }
//
//        if (text.toLowerCase().contains(next.toLowerCase())) { /** NEXT **/
//            returnedText.setText("Dobrze " + next + "   " + word);
//            //speech.stopListening();
//            word = randWord();
//            setWordTV();
//            sayWordEN(word);
//            speech.startListening(recognizerIntent);
//            returnedText.setText("Dobrze " + next + "   " + word);
//        } else if (text.toLowerCase().contains(repeat.toLowerCase())){ /** REPEAT **/
//            //speech.stopListening();
//            sayWordEN(word);
//            speech.startListening(recognizerIntent);
//            returnedText.setText("Dobrze " + repeat);
//        } else { /** WORD **/
//            //speech.stopListening();
//            word = randWord();
//            setWordTV();
//            returnedText.setText("Źle");
//            speech.startListening(recognizerIntent);
//        }
//        //returnedText.setText(text);
    }


    @Override
    public void onRmsChanged(float rmsdB) {
      //  Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }


    private void sayWordEN(Word word){
        Log.d(LOG_TAG, "sayWordEN");

      //  speech.stopListening();
        tts = new TextToSpeech(this, ttsInitResult -> {
            if (TextToSpeech.SUCCESS == ttsInitResult) {
                String textToSay = word.getnazwaEn();
                tts.setLanguage(Locale.US);
                tts.speak(textToSay, TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    private Word randWord() {
        Log.d(LOG_TAG, "randWord");
        int wordListSize = Constatus.KNOWN_WORD_LIST.size();

        int randPercent;
        Word randKnownWord;
        int randScore;
        do {
            randKnownWord = Constatus.KNOWN_WORD_LIST.get(new Random().nextInt(wordListSize));
            randPercent = new Random().nextInt(100);
            randScore = randKnownWord.getscore();
        } while (randScore > randPercent);


        /**
         * add timer to word 10s
         * check speak word
         * if 3x false set score -10
         * if correct set score +10
         *
         * say NEXT to next wordand -10 score
         */
        return randKnownWord;
    }

    private void setWordTV(){
        Log.d(LOG_TAG, "setWordTV");
        wordTV.setText(word.getnazwaEn());
    }


}