package chuc.project.doanmonngonmoingay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import chuc.project.doanmonngonmoingay.MyArrayAdapter.ViewHolder;
import com.commonsware.cwac.endless.EndlessAdapter;

public class MenuListActivity extends Activity {
	private static String catalogId = null;
	private static Context context = null;
	public static ArrayList<String> listSearch = new ArrayList<String>();
	MonNgonDB db;
	ArrayAdapter<MonNgonModel> adapter;
	ArrayList<MonNgonModel> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_list);

		context = this.getApplicationContext();
		Bundle bundle = this.getIntent().getExtras();
		catalogId = bundle.getString("catalogId");

		if (catalogId.equals("1")) {
			setTitle("Thực đơn ngày thường");
		} else if (catalogId.equals("2")) {
			setTitle("Thực đơn đặc biệt");
		} else if (catalogId.equals("3")) {
			setTitle("Thực đơn cho bé");
		}
		final ProgressDialog dialog = ProgressDialog.show(this, "",
				"Đang tải dữ liệu ...", true);
		final ListView listViewMonNgon = (ListView) findViewById(R.id.listViewMonNgon);
		new Thread(new Runnable() {
			public void run() {

				processFolderData();
				MenuListActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						lists = new ArrayList<MonNgonModel>();
						for (int i = 0; i <= 10; i++) {
							lists.add(getMonAnModel(catalogId).get(i));
						}
						listViewMonNgon.setAdapter(new DemoAdapter(lists));
					}
				});
				listViewMonNgon
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								ViewHolder holder = (ViewHolder) view.getTag();
								Bundle bundle = new Bundle();
								bundle.putInt("catalogId", holder.catalogId);
								bundle.putInt("id", holder.id);
								bundle.putString("title", holder.title);
								bundle.putString("image", holder.image);
								bundle.putString("video", holder.video);
								bundle.putString("description",
										holder.description);
								bundle.putString("nguyenlieu",
										holder.nguyenlieu);
								bundle.putString("content", holder.content);
								bundle.putString("tips", holder.tips);
								bundle.putString("favorite", holder.favorite);
								Intent newIntent = new Intent(context,
										MainActivity.class);
								newIntent.putExtras(bundle);
								startActivityForResult(newIntent, 0);
							}
						});
				dialog.dismiss();

			}
		}).start();

		db = new MonNgonDB(this);
		final YeuThichModel search = new YeuThichModel();
		final EditText edtSearch = (EditText) findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				ConvertUnsigned crtUn = new ConvertUnsigned();
				List<MonNgonModel> danhsach = getMonAnModel(catalogId);
				List<MonNgonModel> i = new ArrayList<MonNgonModel>();
				if (s != null && s.toString().length() > 0) {
					for (int index = 0; index < danhsach.size(); index++) {
						MonNgonModel si = danhsach.get(index);
						if (crtUn.ConvertStringURI(si.getTitle().toLowerCase())
								.contains(s.toString().toLowerCase()) == true) {
							i.add(si);
						}
					}
				}
				else i = danhsach;
				adapter = new MyArrayAdapter(MenuListActivity.this, i);
				listViewMonNgon.setAdapter(adapter);
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

	}

	private void processFolderData() {
		try {
			String storagePath = Environment.getExternalStorageDirectory()
					.getPath();
			if ((storagePath != null) && (!"".equals(storagePath))) {
				File dirAndroid = new File(storagePath + "/Android/");
				if (!dirAndroid.exists()) {
					dirAndroid.mkdirs();
				}
				File dirAndroidData = new File(storagePath + "/Android/Data/");
				if (!dirAndroidData.exists()) {
					dirAndroidData.mkdirs();
				}
				File dirAndroidDataHrs = new File(storagePath
						+ "/Android/Data/chuc.project.doanmonngonmoingay.mnmn");
				if (!dirAndroidDataHrs.exists()) {
					dirAndroidDataHrs.mkdirs();
				}
				File dirAndroidDataHrsImages = new File(
						storagePath
								+ "/Android/Data/chuc.project.doanmonngonmoingay/images");
				if (!dirAndroidDataHrsImages.exists()) {
					dirAndroidDataHrsImages.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<MonNgonModel> getMonAnModel(String catalogId) {
		MonNgonDB monNgon = new MonNgonDB(this);
		List<MonNgonModel> list = monNgon.getLists(catalogId);
		return list;
	}

	class DemoAdapter extends EndlessAdapter {
		DemoAdapter(ArrayList<MonNgonModel> list) {
			super(MenuListActivity.this, new MyArrayAdapter(
					MenuListActivity.this, list), R.layout.pending);
		}

		@Override
		protected boolean cacheInBackground() throws Exception {
			// SystemClock.sleep(10000); // pretend to do work

			if (getWrappedAdapter().getCount() < getMonAnModel(catalogId)
					.size()) {
				return (true);
			}

			throw new Exception("Gadzooks!");
		}

		@Override
		protected void appendCachedData() {
			if (getWrappedAdapter().getCount() < getMonAnModel(catalogId)
					.size()) {
				@SuppressWarnings("unchecked")
				ArrayAdapter<MonNgonModel> a = (ArrayAdapter<MonNgonModel>) getWrappedAdapter();

				for (int i = 0; i < 10; i++) {
					if (a.getCount() < getMonAnModel(catalogId).size())
						a.add(getMonAnModel(catalogId).get(a.getCount()));
				}
				Log.d("MON_NGON", "" + a.getCount());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_list, menu);
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
		if (id == R.id.back) {
			finish();
			Intent intent = new Intent(getApplicationContext(),
					MenuCatalogActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
