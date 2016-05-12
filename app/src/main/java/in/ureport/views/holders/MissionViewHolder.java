package in.ureport.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.ureport.R;
import in.ureport.models.Mission;

/**
 * Created by john-mac on 5/12/16.
 */
public class MissionViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public MissionViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bindView(Mission mission) {
        title.setText(mission.getName());
    }

}
