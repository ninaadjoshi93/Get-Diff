package com.ninaad.gitdiff.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.SingleGitListItemBinding;
import com.ninaad.gitdiff.models.GDGitPR;

import java.util.List;

/**
 * Created by ninaad on 2/25/19.
 */
public class GDPullRequestsAdapter extends RecyclerView.Adapter<GDPullRequestsAdapter.GDPullRequestsViewHolder>{
    private static final String TAG = GDPullRequestsAdapter.class.getName();
    private List<GDGitPR> pullRequestsList;
    private LayoutInflater layoutInflater;
    private ClickListener clickListener;

    public GDPullRequestsAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public GDPullRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SingleGitListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.single_git_list_item,
                viewGroup, false);
        return new GDPullRequestsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GDPullRequestsViewHolder holder, int i) {
        holder.binding.setMPullRequest(pullRequestsList.get(i));
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return pullRequestsList.size();
    }

    public void setPullRequestsList(List<GDGitPR> gitPRList) {
        if (null == this.pullRequestsList){
            this.pullRequestsList = gitPRList;
            notifyItemRangeInserted(0, gitPRList.size());
        } else {
            this.pullRequestsList = gitPRList;
        }
    }

    public interface ClickListener {
        void onItemClicked(View view, GDGitPR pullRequestObject);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    class GDPullRequestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final SingleGitListItemBinding binding;

        GDPullRequestsViewHolder(SingleGitListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onItemClicked(view, pullRequestsList.get(position));
        }
    }

}
