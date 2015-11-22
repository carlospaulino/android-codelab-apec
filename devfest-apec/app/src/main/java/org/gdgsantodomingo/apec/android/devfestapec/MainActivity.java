package org.gdgsantodomingo.apec.android.devfestapec;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.gdgsantodomingo.apec.android.devfestapec.model.User;
import org.gdgsantodomingo.apec.android.devfestapec.service.ServiceHelper;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		showFollowers();
	}

	private void showFollowers(){
		ServiceHelper.listFollowers("octocat").enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(Response<List<User>> response, Retrofit retrofit) {
				mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
				mRecyclerView.setAdapter(new SimpleRecyclerViewAdapter(MainActivity.this, response.body()));
			}

			@Override
			public void onFailure(Throwable t) {
				Snackbar.make(mRecyclerView, getString(R.string.net_error_msg), Snackbar.LENGTH_LONG).setAction("Action", null).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
