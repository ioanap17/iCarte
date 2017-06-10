package ro.upb.cs.thesis.icarte.listadapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.models.Product;

/**
 * Created by Paun on 10.06.2017.
 */

public class CategoryAdapter extends BaseAdapter{

    List<String> categories;
    LayoutInflater mInflater;
    Context context;

    public CategoryAdapter(ArrayList<String> categories, LayoutInflater inflater, Context context){
        this.categories = categories;
        this.mInflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;
        int currentQuantity = 1;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.category, null);
            item = new ViewItem();

            item.categoryImageView = (ImageView) convertView.findViewById(R.id.ImageViewCategory);

            item.categoryTitle = (TextView) convertView.findViewById(R.id.TextViewCategory);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        final String category = categories.get(position);

        item.categoryImageView.setImageDrawable(getImage(context, context.getResources(), "ic_"+category.toLowerCase()));
        item.categoryTitle.setText(category);

        return convertView;
    }

    public static Drawable getImage(Context c, Resources res, String ImageName) {
        return res.getDrawable(res.getIdentifier(ImageName, "mipmap", c.getPackageName()));
    }

    private class ViewItem {
        public ImageView categoryImageView;
        public TextView categoryTitle;
    }

}
