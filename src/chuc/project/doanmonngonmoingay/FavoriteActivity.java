package chuc.project.doanmonngonmoingay;

import java.util.ArrayList;
import java.util.List;

import chuc.project.doanmonngonmoingay.FavoriteAdapter.ViewHolderFavorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteActivity extends Activity {
	MonNgonModel monan;
	ListView lvFavorite;
	ArrayAdapter<YeuThichModel> adapter1;
	private static Context context = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_favorite);
		context = this.getApplicationContext();
		 lvFavorite = (ListView) findViewById(R.id.lvFavorite);
		final ArrayAdapter<YeuThichModel>adapter = new FavoriteAdapter(FavoriteActivity.this,getMonAnModel());
		lvFavorite.setAdapter(adapter);
		
		EditText edtSearch = (EditText) findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				ConvertUnsigned crtUn = new ConvertUnsigned();
				List<YeuThichModel> danhsach = getMonAnModel();
				List<YeuThichModel> i = new ArrayList<YeuThichModel>();
				if (s != null && s.toString().length() > 0) {
					for (int index = 0; index < danhsach.size(); index++) {
						YeuThichModel si = danhsach.get(index);
						if (crtUn.ConvertStringURI(si.getTitle().toLowerCase())
								.contains(s.toString().toLowerCase()) == true) {
							i.add(si);
						}
					}
				}
				else i = danhsach;
				adapter1 = new FavoriteAdapter(FavoriteActivity.this, i);
				lvFavorite.setAdapter(adapter1);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final MonNgonDB db = new MonNgonDB(this);
		
		lvFavorite.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	ViewHolderFavorite holder = (ViewHolderFavorite)view.getTag();
            	Bundle bundle = new Bundle();
            	
            	monan = getMonAn(String.valueOf(holder.id));
            	bundle.putInt("id", holder.id);
            	bundle.putString("title", holder.title);
            	bundle.putString("image", holder.image);
            	bundle.putInt("catalogId", monan.getCatalog_id());
            	bundle.putString("video", monan.getVideo());
            	bundle.putString("description", monan.getDescription());
            	bundle.putString("nguyenlieu", monan.getNguyenlieu());
            	bundle.putString("content", monan.getContent());
            	bundle.putString("tips", monan.getTips());
            	bundle.putInt("favorite", monan.getFavorite());
            	Intent newIntent = new Intent(context, MainActivity.class);
            	newIntent.putExtras(bundle);
            	startActivityForResult(newIntent, 0);
            }
        });
		ImageView imgDelete = (ImageView) findViewById(R.id.imgDelete);
		imgDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int row = db.deleteAll();
				lvFavorite.setAdapter(null);
				Log.d("MON NGON MOI NGAY", ""+row);
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ArrayAdapter<YeuThichModel>adapter = new FavoriteAdapter(FavoriteActivity.this,getMonAnModel());
		lvFavorite.setAdapter(adapter);
	}

	private List<YeuThichModel> getMonAnModel() {
		MonNgonDB monNgon = new MonNgonDB(this);
		List<YeuThichModel> list = monNgon.getListsFavorite();
		return list;
	}
	private MonNgonModel getMonAn(String id) {
		MonNgonDB monNgon = new MonNgonDB(this);
		MonNgonModel list = monNgon.getMonAnById(id);
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite, menu);
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
