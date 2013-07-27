package com.androidtitlan.gdgbootcamp;

import android.app.ProgressDialog;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidtitlan.gdgbootcamp.fragment.GooglePlusFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends FragmentActivity implements
		OnConnectionFailedListener, ConnectionCallbacks, OnClickListener {

	private PlusClient.Builder plusClient = null;
	private static final int REQUEST_CODE = 9000;
	private PlusClient client;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		plusClient = new PlusClient.Builder(this, this, this);
		plusClient.setScopes(Scopes.PLUS_LOGIN);
		plusClient.setVisibleActivities(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity");
		client = plusClient.build();

		findViewById(R.id.button_sign_in).setOnClickListener(this);
		dialog = new ProgressDialog(this);

			getIntent().getAction();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (client.isConnected()) {
			client.disconnect();
		}
	}

	@Override
	public void onClick(View arg0) {

		if (arg0.getId() == R.id.button_sign_in) {
			dialog.setMessage("Sign in...");
			if (!client.isConnected()) {
				client.connect();
				dialog.show();
			} else {
				if (client.isConnected() && dialog.isShowing()) {
					dialog.dismiss();
					client.clearDefaultAccount();
					client.disconnect();
				}
			}
		}

	}

	@Override
	public void onConnected(Bundle connectionHint) {

		if (client.isConnected() && dialog.isShowing()) {
			dialog.cancel();
			Person person = client.getCurrentPerson();
			addPlusFragment(person.getDisplayName(), client,plusClient);
		}
	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(MainActivity.this, REQUEST_CODE);
			} catch (SendIntentException e) {

				client.disconnect();
				client.connect();
			}
		}
	}

	private void addPlusFragment(String user, PlusClient client,PlusClient.Builder builder) {

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container,
				GooglePlusFragment.newInstance(user, client,builder));
		transaction.commit();

	}
}
