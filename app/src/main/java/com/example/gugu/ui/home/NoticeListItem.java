package com.example.gugu.ui.home;

import android.graphics.drawable.Drawable;

public class NoticeListItem {
    private Drawable noticeimage;
    private String noticetitle;


    public Drawable getNoticeImage() { return noticeimage; }

    public void setNoticeImage(Drawable noticeImage) { this.noticeimage = noticeImage; }

    public String getNoitceTitle() { return noticetitle; }

    public void setNoticeTitle(String noticetitle) {
        this.noticetitle = noticetitle;
    }
}
