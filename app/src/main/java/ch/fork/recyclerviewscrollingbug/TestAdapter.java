package ch.fork.recyclerviewscrollingbug;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 25.01.17.
 */

public class TestAdapter extends RecyclerView.Adapter  {

    private final List<Integer> currentList = new ArrayList<>();
    private final List<Integer> oldList = new ArrayList<>();
    private final Context context;

    public TestAdapter(Context context) {

        this.context = context;
        currentList.add(0);
        currentList.add(1);
        currentList.add(2);
        currentList.add(3);
        currentList.add(4);
        currentList.add(5);
        currentList.add(6);
        currentList.add(7);
        currentList.add(8);
        currentList.add(9);

        oldList.clear();
        oldList.addAll(currentList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new TestViewHolder(
                LayoutInflater.from(context)
                              .inflate(android.R.layout.simple_list_item_1, parent,
                                       false), this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText("position " + currentList.get(position));
        ((TestViewHolder)holder).position = position;
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    public void clicked(int pos) {
        Integer remove = currentList.remove(pos);
        currentList.add(remove);

        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return currentList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition)
                              .equals(currentList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }
        })
                .dispatchUpdatesTo(this);

        oldList.clear();
        oldList.addAll(currentList);
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        int position;

        public TestViewHolder(View inflate, final TestAdapter listener) {
            super(inflate);

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clicked(position);
                }
            });
        }

    }

}
