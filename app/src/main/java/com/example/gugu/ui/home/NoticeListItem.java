package com.example.gugu.ui.home;

import android.graphics.drawable.Drawable;

public class NoticeListItem {
    private Drawable noticeimage;
    private String noticetitle;


    public Drawable getNoticeImage() { return this.noticeimage; }

    public void setNoticeImage(Drawable noticeImage) { noticeimage = noticeImage; }

    public String getNoitceTitle() { return this.noticetitle; }

    public void setNoticeTitle(String noticet) {
        noticetitle = noticet;
    }
}
