package com.example.passwordkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import java.util.concurrent.Executor;
import android.content.Intent;

import com.example.passwordkeeper.R;


public class BootActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Button accessButton = findViewById(R.id.accessButton);

        if(Build.VERSION.SDK_INT < 24){

            //Compatibility only with Android 7.0 or greater

            Toast.makeText(this, "Smartphone not supported, you need to have at least Android 7.0", Toast.LENGTH_SHORT).show();
            return;


        }
        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if fingerprint, start the procedure

                if(hasFingerPrint()){
                    //ask the user to bio-authenticate

                    performBiomAuthentication();



                }


            }
        });
    }


    public boolean hasFingerPrint(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                return true;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                Toast.makeText(this, "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                Toast.makeText(this, "Biometric features are currently unavailable.", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                Toast.makeText(this, "The user hasn't associated " +
                        "any biometric credentials with their account.", Toast.LENGTH_SHORT).show();
                break;

        }

        return false;

    }

    public void performBiomAuthentication(){

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(BootActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                               CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                     BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                //authentication done, now start new activity

                Intent intent = new Intent(getBaseContext().getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);





    }


}
