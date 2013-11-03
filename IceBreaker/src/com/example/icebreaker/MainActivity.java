package com.example.icebreaker;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	SocialAuthAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button login = (Button) findViewById(R.id.btnLinkedin);

		adapter = new SocialAuthAdapter(new ResponseListener());
		adapter.authorize(getApplicationContext(), Provider.LINKEDIN);
		//adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.enable(login);
	}

	/**
	 * Listens Response from Library
	 * 
	 */
	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			Log.d("OAuth", "Authentication Successful");
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("OAuth", "Provider Name = " + providerName);
			Toast.makeText(MainActivity.this, providerName + " connected", Toast.LENGTH_LONG).show();
			
			//Show profile view
			//Fragment newFragment = new ProfileFragment();
			//FragmentTransaction transaction = getFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack
			//transaction.replace(R.id.fragment_container, newFragment);
			//transaction.addToBackStack(null);

			// Commit the transaction
			//transaction.commit();
			
			
			
			adapter.getUserProfileAsync(new ProfileDataListener());
			
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("OAuth", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("OAuth", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("OAuth", "Dialog Closed by pressing Back Key");
		}

	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(MainActivity.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(MainActivity.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {}
	}

	private final class ProfileDataListener implements SocialAuthListener<Profile> {
		@Override
		public void onError(SocialAuthError arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onExecute(String arg0, Profile arg1) {
			// TODO Auto-generated method stub
			Log.d("ProfileInfo", "Receiving Data");
			Profile profileMap = arg1;
			
			Log.d("ProfileInfo",  "Validate ID         = " + profileMap.getValidatedId());
			Log.d("ProfileInfo",  "First Name          = " + profileMap.getFirstName());
			Log.d("ProfileInfo",  "Last Name           = " + profileMap.getLastName());
			Log.d("ProfileInfo",  "Email               = " + profileMap.getEmail());
			Log.d("ProfileInfo",  "Country                  = " + profileMap.getCountry());
			Log.d("ProfileInfo",  "Language                 = " + profileMap.getLanguage());
			Log.d("ProfileInfo",  "Location                 = " + profileMap.getLocation());
			Log.d("ProfileInfo",  "Profile Image URL  = " + profileMap.getProfileImageURL());
		}
	}
}