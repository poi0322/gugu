package com.example.gugu.ui.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gugu.R;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {

    private ArrayList<MapListItem> mapListItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mapListItems.size();
    }

    @Override
    public MapListItem getItem(int position) {
        return mapListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();


        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.map_listitem, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView listImage = (ImageView) convertView.findViewById(R.id.listImage);
        TextView listTitle = (TextView) convertView.findViewById(R.id.listTitle);
        TextView listProfile = (TextView) convertView.findViewById(R.id.listProfile);
        ImageView list_active_sup = (ImageView) convertView.findViewById(R.id.list_active_sup);
        ImageView list_bath_sup = (ImageView) convertView.findViewById(R.id.list_bath_sup);
        ImageView list_toilet_sup = (ImageView) convertView.findViewById(R.id.list_toilet_sup);
        ImageView list_clean_sup = (ImageView) convertView.findViewById(R.id.list_clean_sup);


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MapListItem mListItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        listImage.setImageDrawable(mListItem.getListImage());
        listTitle.setText(mListItem.getListTitle());
        listProfile.setText(mListItem.getListProfile());
        list_active_sup.setImageDrawable(mListItem.getList_active_sup());
        list_bath_sup.setImageDrawable(mListItem.getList_bath_sup());
        list_toilet_sup.setImageDrawable(mListItem.getList_toilet_sup());
        list_clean_sup.setImageDrawable(mListItem.getList_clean_sup());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        //참고 : https://mailmail.tistory.com/6
        return convertView;
    }

    public void addItem(Drawable listImage, String listTitle, String listProfile,
                        Drawable list_active_sup, Drawable list_bath_sup,
                        Drawable list_toilet_sup, Drawable list_clean_sup) {

        MapListItem mListItem = new MapListItem();

        //MapListItem에 아이템 세팅하기
        mListItem.setListImage(listImage);
        mListItem.setListTitle(listTitle);
        mListItem.setListProfile(listProfile);
        mListItem.setList_active_sup(list_active_sup);
        mListItem.setList_bath_sup(list_bath_sup);
        mListItem.setList_toilet_sup(list_toilet_sup);
        mListItem.setList_clean_sup(list_clean_sup);

        mapListItems.add(mListItem);
    }
}
