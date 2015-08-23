package course.example.dailyselfie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by javikkal on 8/20/2015.
 */
public class ImageListAdapter extends BaseAdapter{
    private ArrayList<SelfiePlaceHolder> list = new ArrayList<>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public  ImageListAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        SelfiePlaceHolder curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater
                    .inflate(R.layout.activity_main, parent, false);
            holder.imagePreview = (ImageView) newView.findViewById(R.id.selfie_preview_image);
            holder.imageName = (TextView) newView.findViewById(R.id.image_file_name);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.imagePreview.setImageBitmap(curr.getBitmapPreview(holder.imagePreview.getMaxHeight(),
                holder.imagePreview.getMaxWidth() ));
        holder.imageName.setText(curr.getImageName());

        return newView;
    }

    static class ViewHolder {

        ImageView imagePreview;
        TextView imageName;
    }

    public void add(SelfiePlaceHolder listItem) {
        list.add(listItem);
        notifyDataSetChanged();
    }

    public boolean remove(int position){
       SelfiePlaceHolder item = (SelfiePlaceHolder) getItem(position);
        if (item != null){
            File f = new File(item.getFullImagePath());
            if (f != null && f.exists()){
                f.delete();
            }
            list.remove(position);
            notifyDataSetChanged();
        }
        return true;
    }
}
