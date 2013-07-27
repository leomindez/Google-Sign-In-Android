package com.androidtitlan.gdgbootcamp;

import com.google.android.gms.plus.PlusShare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent shareIntent = new PlusShare.Builder(PostActivity.this)
						.setType("text/plain")
						.setText("Google Plus Sign In Success by Leon")
						.setContentUrl(
								Uri.parse("https://developers.google.com/+/"))
						.getIntent();

				startActivityForResult(shareIntent, 0);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}

}
