package com.edwinkato.bucketlist.ui.bucketLists;

import com.edwinkato.bucketlist.data.model.BucketList;

import java.util.List;

/**
 * Created by edwinkato on 10/18/17.
 */

public interface BucketListContract {
    interface View {
        void showLoading();
        void hideLoading();
        void displayBucketlists(List<BucketList> bucketLists);
        void showError();
    }

    interface Presenter {
        void setView(BucketListContract.View view);
        void getAndDisplayucketlists();
        void pullToRefreshAssignments();
    }
}
