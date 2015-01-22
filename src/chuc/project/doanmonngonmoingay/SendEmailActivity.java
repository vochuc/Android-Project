package chuc.project.doanmonngonmoingay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SendEmailActivity extends Activity {

	Button buttonSend, buttonDong;
	EditText textTo;
	EditText textMessage;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_send_email);
 
		textTo = (EditText) findViewById(R.id.editTextTo);
		buttonSend = (Button) findViewById(R.id.buttonSend);
		buttonDong = (Button) findViewById(R.id.buttonDong);
		
		
		Bundle bundle = this.getIntent().getExtras();
		final String title = bundle.getString("title");
		final String video = bundle.getString("video");
		final String description = bundle.getString("description");
		final String nguyenlieu = bundle.getString("nguyenlieu");
		final String content = bundle.getString("content");
		final String tips = bundle.getString("tips");
		
		
		textTo.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
					 final String to = textTo.getText().toString();
					  final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
					  if (to.equals("")){
						  Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ email",Toast.LENGTH_SHORT).show();
					  }
					  else if (to.matches(emailPattern) && to.length() > 0)
					          { 
					        	  String subject = "Món Ngon: "+title;
								  String message="";
								  if (description.equals(null) || description.equals("") || description.equals(" ")){
									  message = "Nguyên liệu:\n "+nguyenlieu+"\n Chế Biến:\n "
							                     +content+"\n Mách Nhỏ:\n"+tips+"Link Youtube:\n"+video;
								  }
								  else {
								   message = "Mô tả:\n "+description+"\n Nguyên liệu:\n "+nguyenlieu+"\n Chế Biến:\n "
								                     +content+"\n Mách Nhỏ:\n"+tips+"Link Youtube:\n"+video;
								  }
					 
								  Intent email = new Intent(Intent.ACTION_SEND);
								  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
								  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
								  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
								  email.putExtra(Intent.EXTRA_SUBJECT, subject);
								  email.putExtra(Intent.EXTRA_TEXT, message);

								  //need this to prompts email client only
								  email.setType("message/rfc822");
								  startActivity(Intent.createChooser(email, "Choose an Email client :"));
								}
					          else
					          {
					               Toast.makeText(getApplicationContext(),"Địa chỉ email không đúng",Toast.LENGTH_SHORT).show();
					          }
				}
				
				return false;
			}
		});
		buttonSend.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			  final String to = textTo.getText().toString();
			  final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
			  if (to.equals("")){
				  Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ email",Toast.LENGTH_SHORT).show();
			  }
			  else
			      if (to.matches(emailPattern) && to.length() > 0)
			          { 
			        	  String subject = "Món Ngon: "+title;
						  String message="";
						  if (description.equals(null) || description.equals("") || description.equals(" ")){
							  message = "Nguyên liệu:\n "+nguyenlieu+"\n Chế Biến:\n "
					                     +content+"\n Mách Nhỏ:\n"+tips+"Link Youtube:\n"+video;
						  }
						  else {
						   message = "Mô tả:\n "+description+"\n Nguyên liệu:\n "+nguyenlieu+"\n Chế Biến:\n "
						                     +content+"\n Mách Nhỏ:\n"+tips+"Link Youtube:\n"+video;
						  }
			 
						  Intent email = new Intent(Intent.ACTION_SEND);
						  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
						  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
						  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
						  email.putExtra(Intent.EXTRA_SUBJECT, subject);
						  email.putExtra(Intent.EXTRA_TEXT, message);

						  //need this to prompts email client only
						  email.setType("message/rfc822");
						  startActivity(Intent.createChooser(email, "Choose an Email client :"));
						}
			          else
			          {
			               Toast.makeText(getApplicationContext(),"Địa chỉ email không đúng",Toast.LENGTH_SHORT).show();
			          }
			}
		
		});
		buttonDong.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
