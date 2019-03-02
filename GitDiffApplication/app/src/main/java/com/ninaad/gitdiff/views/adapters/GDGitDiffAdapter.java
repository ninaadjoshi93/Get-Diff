package com.ninaad.gitdiff.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.SingleGitDiffLineItemBinding;
import com.ninaad.gitdiff.models.GDGitDiffLine;

import java.util.List;

/**
 * Created by ninaad on 3/2/19.
 */
public class GDGitDiffAdapter extends RecyclerView.Adapter<GDGitDiffAdapter.GDGitDiffViewHolder> {


    private static final String TAG = GDGitDiffAdapter.class.getName();
    private List<GDGitDiffLine> gitLineList;
    private LayoutInflater layoutInflater;

    public GDGitDiffAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public GDGitDiffAdapter.GDGitDiffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SingleGitDiffLineItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout
                        .single_git_diff_line_item,
                viewGroup, false);
        GDGitDiffAdapter.GDGitDiffViewHolder holder = new GDGitDiffAdapter.GDGitDiffViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GDGitDiffAdapter.GDGitDiffViewHolder holder, int i) {
        holder.binding.setGitLine(gitLineList.get(i));
        holder.binding.gitDiffLineTv.setBackgroundColor(gitLineList.get(i).getGitColor());
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return gitLineList.size();
    }

    public void setGitLineList(List<GDGitDiffLine> gitLineList) {
        if (null == this.gitLineList){
            this.gitLineList = gitLineList;
            notifyItemRangeInserted(0, gitLineList.size());
        } else {
            this.gitLineList = gitLineList;
        }
    }

    class GDGitDiffViewHolder extends RecyclerView.ViewHolder{

        final SingleGitDiffLineItemBinding binding;

        public GDGitDiffViewHolder(SingleGitDiffLineItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
