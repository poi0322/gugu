package com.example.gugu.ui.home;

import android.graphics.drawable.Drawable;

public class HomeListItem {
    private Drawable NoticeImage;
    private String NoticeTitle;

    public Drawable getNoticeImage() { return NoticeImage; }

    public void setNoticeImage(Drawable noticeImage) {
        this.NoticeImage = noticeImage;
    }

    public String getNoticeTitle() { return NoticeTitle; }

    public void setNoticeTitle(String noticeTitle) {
        this.NoticeTitle = noticeTitle;
    }


}
