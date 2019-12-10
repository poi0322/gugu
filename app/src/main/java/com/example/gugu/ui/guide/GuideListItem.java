package com.example.gugu.ui.guide;

import android.graphics.drawable.Drawable;

public class GuideListItem {
    private Drawable guideimage;
    private String guidetitle;


    public Drawable getGuideimage() { return this.guideimage; }

    public void setGuideimage(Drawable guideImage) { guideimage = guideImage; }

    public String getGuidetitle() { return this.guidetitle; }

    public void setGuidetitle(String guidet) { guidetitle = guidet; }
}
