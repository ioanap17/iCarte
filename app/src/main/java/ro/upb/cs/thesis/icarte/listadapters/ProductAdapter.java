package ro.upb.cs.thesis.icarte.listadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;


public class ProductAdapter extends BaseAdapter {

    private List<Product> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;
    private boolean mShowQuantity;
    private boolean mShowButtons;

    public ProductAdapter(List<Product> list, LayoutInflater inflater, boolean showCheckbox, boolean showQuantity, boolean showButtons) {
        mProductList = list;
        mInflater = inflater;
        mShowCheckbox = showCheckbox;
        mShowQuantity = showQuantity;
        mShowButtons = showButtons;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
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
            convertView = mInflater.inflate(R.layout.item, null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView.findViewById(R.id.ImageViewItem);

            item.productTitle = (TextView) convertView.findViewById(R.id.TextViewItem);

            item.productAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);

            item.productPrice = (TextView) convertView.findViewById(R.id.plist_price_text);

            item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            item.productQuantity = (TextView) convertView.findViewById(R.id.quantity);

            item.minusButton = (ImageView) convertView.findViewById(R.id.cart_minus_img);

            item.plusButton = (ImageView) convertView.findViewById(R.id.cart_plus_img);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        final Product curProduct = mProductList.get(position);

        item.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.title);
        item.productAuthor.setText("de " + curProduct.author);
        item.productPrice.setText(String.valueOf(curProduct.price) + " RON");

        if(!mShowCheckbox) {
            item.productCheckbox.setVisibility(View.GONE);
        } else {
            if(curProduct.selected == true)
                item.productCheckbox.setChecked(true);
            else
                item.productCheckbox.setChecked(false);
        }

        // Show the quantity in the cart or not
        if (mShowQuantity) {
            currentQuantity = ShoppingCartHelper.getProductQuantity(curProduct);
            item.productQuantity.setText(String.valueOf(currentQuantity));
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        }

        if (mShowButtons){
            item.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = ShoppingCartHelper.getProductQuantity(curProduct) - 1;
                    if(quantity >= 0) {
                        item.productQuantity.setText(String.valueOf(quantity));
                        ShoppingCartHelper.setQuantity(curProduct, quantity);
                    }
                }
            });
            item.plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = ShoppingCartHelper.getProductQuantity(curProduct) + 1;
                    item.productQuantity.setText(String.valueOf(quantity));
                    ShoppingCartHelper.setQuantity(curProduct, quantity);
                }
            });
        }else{
            item.minusButton.setVisibility(View.GONE);
            item.plusButton.setVisibility(View.GONE);
        }

        return convertView;
    }


    private class ViewItem {
        ImageView productImageView;
        TextView productTitle, productAuthor;
        CheckBox productCheckbox;
        TextView productQuantity, productPrice;
        ImageView plusButton, minusButton;
    }

}

