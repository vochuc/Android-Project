package chuc.project.doanmonngonmoingay;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<MonNgonModel> {
	private List<MonNgonModel> list;
	private final Activity context;
	private ImageLoader imageLoader;
	private Filter myFilter;
	
	public MyArrayAdapter(Activity context, List<MonNgonModel> list) {
		super(context, R.layout.item_list_menu, list);
		this.context = context;
		this.list = list;
		imageLoader = new ImageLoader(context.getApplicationContext());

	}

	static class ViewHolder {
		protected TextView textViewTitle;
		protected TextView textViewDesc;
		protected ImageView imageViewMonNgon;
		protected int id;
		protected int catalogId;
		protected String image;
		protected String title;
		protected String description;
		protected String nguyenlieu;
		protected String content;
		protected String tips;
		protected String video;
		protected String favorite;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.item_list_menu, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.textViewTitle = (TextView) view
					.findViewById(R.id.textViewTitle);
			viewHolder.textViewDesc = (TextView) view
					.findViewById(R.id.textViewDesc);
			viewHolder.imageViewMonNgon = (ImageView) view
					.findViewById(R.id.imageViewMonNgon);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.textViewTitle.setText(list.get(position).getTitle());
		holder.textViewDesc.setText(list.get(position).getDescription());
		holder.id = list.get(position).getId();
		holder.image = list.get(position).getImage();
		holder.title = list.get(position).getTitle();
		holder.description = list.get(position).getDescription();
		holder.nguyenlieu = list.get(position).getNguyenlieu();
		holder.content = list.get(position).getContent();
		holder.tips = list.get(position).getTips();
		holder.video = list.get(position).getVideo();
		holder.favorite = String.valueOf(list.get(position).getFavorite());
		imageLoader.DisplayImage(list.get(position).getImage(),
				holder.imageViewMonNgon);
		return view;
	}
	
}
