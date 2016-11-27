package com.minimalviking.tidetest.home.barList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minimalviking.tidetest.R;
import com.minimalviking.tidetest.data.Result;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
	private List<Result> items;

	public ResultsAdapter(@NonNull List<Result> items) {
		this.items = items;
	}

	public void updateItems(@NonNull List<Result> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.row_bar_list, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public void onBindViewHolder(ResultsAdapter.ViewHolder holder, int position) {
		Result result = items.get(position);
		holder.title.setText(result.getName());
		holder.subtitle.setText("Distance: " + result.getFormattedDistanceToTheUser());
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		public TextView title;
		public TextView subtitle;

		public ViewHolder(final View itemView) {
			super(itemView);
			this.title = (TextView) itemView.findViewById(R.id.row_bar_title);
			this.subtitle = (TextView) itemView.findViewById(R.id.row_bar_subTitle);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post(items.get(getAdapterPosition()));
				}
			});
		}

	}

}
