package zhou.com.snailbook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.SpineReference;
import zhou.com.snailbook.ui.fragment.EPubReaderFragment;

/**
 * Created by zhou on 2018/1/28.
 */


public class EPubReaderAdapter extends FragmentPagerAdapter {

    private List<SpineReference> mSpineReferences;
    private Book mBook;
    private String mEpubFileName;
    private boolean mIsSmilAvailable;

    public EPubReaderAdapter(FragmentManager fm, List<SpineReference> spineReferences,
                             Book book, String epubFilename, boolean isSmilAvilable) {
        super(fm);
        this.mSpineReferences = spineReferences;
        this.mBook = book;
        this.mEpubFileName = epubFilename;
        this.mIsSmilAvailable = isSmilAvilable;
    }

    @Override
    public Fragment getItem(int position) {
        return EPubReaderFragment.newInstance(position, mBook, mEpubFileName, mIsSmilAvailable);
    }

    @Override
    public int getCount() {
        return mSpineReferences.size();
    }

}