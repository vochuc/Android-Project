package chuc.project.doanmonngonmoingay;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {
	String title, image;
	String description, nguyenlieu, content, tips, video;
	int id, catalogId;
	ArrayList<MonNgonModel> list = new ArrayList<MonNgonModel>();
	CheckBox chkFavorite;
	MonNgonDB db;
	YeuThichModel yeuthich;
	long insertCout;
	int deleteCout;
	List<YeuThichModel> danhsach;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		ImageLoader imageLoader = new ImageLoader(getApplicationContext());
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Bundle bundle = this.getIntent().getExtras();
		id = bundle.getInt("id");
		catalogId = bundle.getInt("catalogId");
		title = bundle.getString("title");
	    image = bundle.getString("image");
		video = bundle.getString("video");
		description = bundle.getString("description");
		nguyenlieu = bundle.getString("nguyenlieu");
		content = bundle.getString("content");
		tips = bundle.getString("tips");
		
		nguyenlieu = nguyenlieu.replaceAll(" ", "1234");
		nguyenlieu = nguyenlieu.replaceAll("\\Wn", "\n");
		nguyenlieu = nguyenlieu.replaceAll("1234", " ");
		
		content = content.replaceAll(" ", "1234");
		content = content.replaceAll("\\Wng", "-Ng");
		content = content.replaceAll("\\Wn", "\n");
		content = content.replaceAll("1234", " ");
		
		tips = tips.replaceAll(" ", "1234");
		tips = tips.replaceAll("\\Wn", "\n");
		tips = tips.replaceAll("1234", " ");
		
		final String youtubeId = video.replaceAll("http://www.youtube.com/embed/", "");
		TextView titleTextView = (TextView) findViewById(R.id.txtTitle);
		titleTextView.setText(title);
		
		ImageView imgHome = (ImageView) findViewById(R.id.imgHome);
		imgHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
				Intent intent1 = new Intent(getApplicationContext(),MenuCatalogActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent1.putExtra("EXIT", true);
				startActivity(intent1);
			}
		});
		ImageView imgSendEmail = (ImageView) findViewById(R.id.imgSendEmail);
		imgSendEmail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
	        	bundle.putString("title", title);
	        	bundle.putString("video", video);
	        	bundle.putString("description", description);
	        	bundle.putString("nguyenlieu", nguyenlieu);
	        	bundle.putString("content", content);
	        	bundle.putString("tips", tips);
	        	Intent newIntent = new Intent(getApplicationContext(), SendEmailActivity.class);
	        	newIntent.putExtras(bundle);
	        	startActivityForResult(newIntent, 0);
			}
		});
	    db = new MonNgonDB(this);
		yeuthich = new YeuThichModel();
		yeuthich.setId(id);
		yeuthich.setTitle(title);
		yeuthich.setImage(image);

		chkFavorite = (CheckBox) findViewById(R.id.chkFavorite);
		danhsach = db.getListsFavorite();
		boolean remember= false;
		for (int i=0;i<danhsach.size();i++){
			if(danhsach != null && danhsach.get(i).getTitle().equals(title)){
				remember = true;
				break;
			}
			else {
				remember = false;
			}
		}
		if (remember == true){
			chkFavorite.setChecked(true);
		}
		else {
			chkFavorite.setChecked(false);
		}
		chkFavorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if (isChecked) {
					insertCout = db.insertToFavorite(yeuthich);
				}
				else {
					deleteCout = db.deleteFavorite(id);
				}
			}
		});

		ImageView imageViewDetailDesc = (ImageView) findViewById(R.id.imageViewDetailDesc);
		imageLoader.DisplayImage(image, imageViewDetailDesc);
		ImageButton imageButtonYoutube = (ImageButton) findViewById(R.id.imageButtonYoutube);
		imageButtonYoutube.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("vnd.youtube://" + youtubeId));
				intent.putExtra("VIDEO_ID", youtubeId);
				startActivity(intent);
			}
		});
		TextView textViewDesc = (TextView) findViewById(R.id.textViewDesc);
		textViewDesc.setText(description);
		TextView textViewNguyenLieu = (TextView) findViewById(R.id.textViewNguyenLieu);
		TextView textViewCheBien = (TextView) findViewById(R.id.textViewCheBien);
		TextView textViewMachNho = (TextView) findViewById(R.id.textViewMachNho);
		
	    textViewNguyenLieu.setText(nguyenlieu);
	    textViewCheBien.setText(content);
	    textViewMachNho.setText(tips);
	    
		// Lấy Tabhost id ra trước (cái này của built - in android
		final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
		// gọi lệnh setup
		tab.setup();
		TabHost.TabSpec spec;
		// Tạo tab1
		spec = tab.newTabSpec("t1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Nguyên liệu");
		tab.addTab(spec);
		// Tạo tab2
		spec = tab.newTabSpec("t2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Chế Biến");
		tab.addTab(spec);
		// Tạo tab3
		spec = tab.newTabSpec("t3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Mách nhỏ");
		tab.addTab(spec);
		// Thiết lập tab mặc định được chọn ban đầu là tab 0
		tab.setCurrentTab(0);
	}
}
