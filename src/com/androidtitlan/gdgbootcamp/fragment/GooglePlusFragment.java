package com.androidtitlan.gdgbootcamp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidtitlan.gdgbootcamp.R;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusOneButton;
import com.google.android.gms.plus.PlusShare;

public class GooglePlusFragment extends Fragment {

	private Button post_button;
	private EditText text_post;
	private TextView user;
	private PlusOneButton plusOneButton;
	private PlusClient client;
	private PlusClient.Builder plusClientBuilder;

	public static GooglePlusFragment newInstance(String user_name,
			PlusClient plusClient, PlusClient.Builder builder) {
		GooglePlusFragment fragment = new GooglePlusFragment();
		Bundle bundle = new Bundle();
		bundle.putString("username", user_name);
		fragment.setArguments(bundle);
		fragment.client = plusClient;
		fragment.plusClientBuilder = builder;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup group = (ViewGroup) inflater.inflate(R.layout.activity_post,
				container, false);
		post_button = (Button) group.findViewById(R.id.button1);
		text_post = (EditText) group.findViewById(R.id.editText1);
		user = (TextView) group.findViewById(R.id.user_connected);
		plusOneButton = (PlusOneButton) group
				.findViewById(R.id.plus_one_button);
		return group;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String user_name = getArguments().getString("username");
		user.setText("User: " + user_name);

		if (client.isConnected()) {
			plusClientBuilder.clearScopes();
			plusOneButton.initialize(client,
					"http://mexicocity.startupweekend.org/",
					0);
		}

		post_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String message = text_post.getText().toString();
				Intent shareIntent = new PlusShare.Builder(getActivity())
						.setType("text/plain")
						.setText(message)
						.setContentUrl(
								Uri.parse("http://mexicocity.startupweekend.org/"))
						.getIntent();

				startActivityForResult(shareIntent, 0);
			}
		});

	}

}
