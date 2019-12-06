package com.example.gugu.ui.home;



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

    private ArrayList<HomeListItem> homeListItems = new ArrayList<>();

    @Override
    public int getCount() {
        return homeListItems.size();
    }

    @Override
    public HomeListItem getItem(int position) {
        return homeListItems.get(position);
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
            convertView = inflater.inflate(R.layout.home_llistitem, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView noticeimage = (ImageView) convertView.findViewById(R.id.noticeimage);
        TextView noticetitle = (TextView) convertView.findViewById(R.id.listTitle);



        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        HomeListItem mListItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        noticeimage.setImageDrawable(mListItem.getNoticeImage());
        noticetitle.setText(mListItem.getNoticeTitle());


        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        //참고 : https://mailmail.tistory.com/6
        return convertView;
    }

    public void addItem(Drawable noticeimage, String listTitle) {

        HomeListItem mListItem = new HomeListItem();

        //HomeListItem에 아이템 세팅하기
        mListItem.setNoticeImage(noticeimage);
        mListItem.setNoticeTitle(listTitle);

        homeListItems.add(mListItem);
    }
}
