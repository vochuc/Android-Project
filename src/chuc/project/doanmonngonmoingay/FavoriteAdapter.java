package chuc.project.doanmonngonmoingay;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteAdapter extends ArrayAdapter<YeuThichModel> {
	private  List<YeuThichModel> list;
	private final Activity context;
	private ImageLoader imageLoader;
	
	public FavoriteAdapter(Activity context, List<YeuThichModel> list) {
		super(context,R.layout.item_list_favorite,list);
		this.context = context;
		this.list = list;
		imageLoader = new ImageLoader(context.getApplicationContext());
		
	}
	
	static class ViewHolderFavorite{
		protected TextView textViewTitle;
		protected ImageView imageViewMonNgon;
		protected int id;
		protected String image;
		protected String title ;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null){
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.item_list_favorite, null);
			ViewHolderFavorite viewHolder = new ViewHolderFavorite();
			viewHolder.textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
			viewHolder.imageViewMonNgon = (ImageView) view.findViewById(R.id.imageViewMonNgon);
			view.setTag(viewHolder);
		}
		else {
			view = convertView;
		}
		
		ViewHolderFavorite holder = (ViewHolderFavorite)view.getTag();
		holder.id = list.get(position).getId();
		holder.textViewTitle.setText(list.get(position).getTitle());
		holder.image = list.get(position).getImage();
		holder.title = list.get(position).getTitle();
		imageLoader.DisplayImage(list.get(position).getImage(), holder.imageViewMonNgon);
		return view;
	}

}

