package ex14.stories2.com.ex14.CustomStoreListView;

import android.graphics.drawable.Drawable;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class EachStoreListViewItem {
    Drawable eachStoreIcon;
    String eachStoreTitle, eachStoreSubTitle;

    public void SetEachStoreIcon(Drawable eachStoreIcon) {
        this.eachStoreIcon = eachStoreIcon;
    }

    public void SetEachStoreTitle(String eachStoreTitle) {
        this.eachStoreTitle = eachStoreTitle;
    }

    public void SetEachStoreSubTitle(String eachStoreSubTitle) {
        this.eachStoreSubTitle = eachStoreSubTitle;
    }

    public Drawable GetEachStoreIcon() {
        return eachStoreIcon;
    }

    public String GetEachStoreTitle() {
        return eachStoreTitle;
    }

    public String GetEachStoreSubTitle() {
        return eachStoreSubTitle;
    }
}
