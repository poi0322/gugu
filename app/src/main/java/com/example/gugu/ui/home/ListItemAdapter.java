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

    private ArrayList<NoticeListItem> noticeListItems = new ArrayList<NoticeListItem>();

    public ListItemAdapter(){};
    @Override
    public int getCount() {return noticeListItems.size(); }

    @Override
    public View getView(int position, View nview, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (nview == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            nview = inflater.inflate(R.layout.home_llistitem, parent, false);
        }
        ImageView noticeimage = (ImageView)nview.findViewById((R.id.noticeimage));
        TextView noticetext = (TextView)nview.findViewById((R.id.noticetitle));

        NoticeListItem nListitem = noticeListItems.get(position);

        noticeimage.setImageDrawable(nListitem.getNoticeImage());
        noticetext.setText(nListitem.getNoitceTitle());
        return nview;
    }

    @Override
    public NoticeListItem getItem(int position) {
        return noticeListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public void addItem(Drawable noticeimage, String noticetitle) {
        NoticeListItem nListItem = new NoticeListItem();

        nListItem.setNoticeImage(noticeimage);
        nListItem.setNoticeTitle(noticetitle);

        noticeListItems.add(nListItem);
    }
}
