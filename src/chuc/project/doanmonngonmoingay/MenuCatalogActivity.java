package chuc.project.doanmonngonmoingay;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuCatalogActivity extends ActionBarActivity {
	Button btNgayThuong, btDacBiet, btChoBe, btYeuthich, btGioiThieu, btThoat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_catalog);
		
		btNgayThuong = (Button) findViewById(R.id.btNgayThuong);
		btDacBiet = (Button) findViewById(R.id.btDacBiet);
		btChoBe = (Button) findViewById(R.id.btChoBe);
		btYeuthich = (Button) findViewById(R.id.btFavorite);
		btGioiThieu = (Button) findViewById(R.id.btGioiThieu);
		btThoat = (Button) findViewById(R.id.btThoat);
		
		btNgayThuong.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btNgayThuong));
				Bundle bundle = new Bundle();
				bundle.putString("catalogId", "1");
				Intent intent = new Intent(getApplicationContext(), MenuListActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		btDacBiet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btDacBiet));
				Bundle bundle = new Bundle();
				bundle.putString("catalogId", "2");
				Intent intent = new Intent(getApplicationContext(), MenuListActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		btChoBe.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btChoBe));
				Bundle bundle = new Bundle();
				bundle.putString("catalogId", "3");
				Intent intent = new Intent(getApplicationContext(), MenuListActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		btYeuthich.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btFavorite));
				Intent intent = new Intent(getApplicationContext(),FavoriteActivity.class);
				startActivity(intent);
			}
		});
		btGioiThieu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btGioiThieu));
				Bundle bundle = new Bundle();
				bundle.putString("catalogId", "1");
				Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		btThoat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YoYo.with(Techniques.FadeIn)
                .duration(1)
                .playOn(findViewById(R.id.btThoat));
				finish();
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_catalog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
