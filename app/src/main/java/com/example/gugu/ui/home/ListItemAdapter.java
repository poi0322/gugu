package com.example.gugu.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gugu.R;
import com.example.gugu.ui.map.MapListItem;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {

    private ArrayList<NoticeListItem> noticeListItems = new ArrayList<>();

    @Override
    public int getCount() {return noticeListItems.size(); }

    @Override
    public NoticeListItem getItem(int position) {
        return noticeListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }





    @Override
    public View getView(int position, View mview, ViewGroup parent) {
        Context context = parent.getContext();

        if (mview == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview = inflater.inflate(R.layout.home_llistitem, parent, false);
        }
        ImageView noticeimage = (ImageView)mview.findViewById((R.id.noticeimage));
        TextView noticetext = (TextView)mview.findViewById((R.id.noticetitle));

        NoticeListItem nListitem = getItem(position);

        noticeimage.setImageDrawable(nListitem.getNoticeImage());
        noticetext.setText(nListitem.getNoitceTitle());
        return mview;
    }

    public void addItem(Drawable noticeimage, String noticetitle) {
        int image = R.drawable.santa;
        int title = R.string.app_name;

        NoticeListItem nListItem = new NoticeListItem();

        nListItem.setNoticeImage(noticeimage);
        nListItem.setNoticeTitle(noticetitle);


        noticeListItems.add(nListItem);
    }
}
