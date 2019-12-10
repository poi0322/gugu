package com.example.gugu.ui.guide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gugu.R;
import com.example.gugu.ui.home.NoticeListItem;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    private ArrayList<GuideListItem> guideListItems = new ArrayList<GuideListItem>();

    public ListItemAdapter() { };

    @Override
    public int getCount() { return guideListItems.size(); }

    @Override
    public View getView(int position, View gview, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (gview == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gview = inflater.inflate(R.layout.guide_listitem, parent, false);
        }
        ImageView guideimage = (ImageView) gview.findViewById((R.id.guideimage));
        TextView guideetext = (TextView) gview.findViewById((R.id.guidetitle));

        GuideListItem gListitem = guideListItems.get(position);

        guideimage.setImageDrawable(gListitem.getGuideimage());
        guideetext.setText(gListitem.getGuidetitle());
        return gview;
    }

    @Override
    public GuideListItem getItem(int position) {
        return guideListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public void addItem(Drawable guideimage, String guidetitle) {
        GuideListItem gListItem = new GuideListItem();

        gListItem.setGuideimage(guideimage);
        gListItem.setGuidetitle(guidetitle);

        guideListItems.add(gListItem);

    }
}
