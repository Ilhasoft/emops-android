package in.ureport.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.FirebaseRecyclerAdapter;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;

import br.com.ilhasoft.support.tool.EditTextValidator;
import in.ureport.R;
import in.ureport.managers.MissionManager;
import in.ureport.models.Mission;
import in.ureport.network.MissionServices;
import in.ureport.views.holders.MissionViewHolder;

/**
 * Created by john-mac on 5/12/16.
 */
public class MissionsFragment extends Fragment {

    private static final int MIN_SIZE = 4;
    private static final int CODE_MAX_LENGTH = 6;

    private EditTextValidator validator;
    private MissionServices services;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_missions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObjects();
        setupView(view);
    }

    private void setupObjects() {
        validator = new EditTextValidator();
        services = new MissionServices();
    }

    private void setupView(View view) {
        RecyclerView missionsList = (RecyclerView) view.findViewById(R.id.missionsList);

        missionsList.setHasFixedSize(true);
        missionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerAdapter<Mission, MissionViewHolder> missionAdapter = new FirebaseRecyclerAdapter<Mission, MissionViewHolder>(
                  Mission.class, R.layout.item_mission
                , MissionViewHolder.class, MissionServices.getMissionsInformationsReference()) {
            @Override
            protected void populateViewHolder(MissionViewHolder missionViewHolder, Mission mission, int index) {
                mission.setKey(getRef(index).getKey());
                missionViewHolder.itemView.setOnClickListener(view1 -> selectMission(mission));
                missionViewHolder.bindView(mission);
            }
        };
        missionsList.setAdapter(missionAdapter);

        FloatingActionButton createMissionButton = (FloatingActionButton) view.findViewById(R.id.createMissionButton);
        createMissionButton.setOnClickListener(onAddMissionClickListener);
    }

    private void selectMission(Mission mission) {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(getContext())
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setBackgroundColor(android.R.color.white)
                .setMenu(R.menu.menu_mission)
                .setItemClickListener(item -> {
                    switch (item.getId()) {
                        case R.id.remove:
                            removeMission(mission); break;
                        case R.id.edit:
                            displayMissionDialog(mission);
                    }
                })
                .createDialog();
        dialog.show();
    }

    private void removeMission(Mission mission) {
        if(MissionManager.getGlobalMission().equals(mission)) {
            Toast.makeText(getContext(), R.string.error_message_remove_global, Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.message_remove_mission_confirmation)
                    .setPositiveButton(R.string.confirm_neutral_dialog_button, (dialog, which) -> services.removeMission(mission))
                    .setNegativeButton(R.string.cancel_dialog_button, null)
                    .show();
        }
    }

    private View.OnClickListener onAddMissionClickListener = v -> {
        displayMissionDialog(null);
    };

    private void displayMissionDialog(Mission mission) {
        boolean update = mission != null;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_create_mission, null);

        final EditText codeInput = (EditText) view.findViewById(R.id.code);
        codeInput.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(CODE_MAX_LENGTH)});
        codeInput.setVisibility(update ? View.GONE : View.VISIBLE);
        codeInput.setText(update ? mission.getKey() : null);

        final EditText nameInput = (EditText) view.findViewById(R.id.name);
        nameInput.setText(update ? mission.getName() : null);

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setMessage(R.string.message_message_information)
                .setView(view)
                .setPositiveButton(getString(R.string.confirm_neutral_dialog_button), null)
                .setNegativeButton(getString(R.string.cancel_dialog_button), null)
                .create();

        alertDialog.setOnShowListener((DialogInterface dialog) -> {
            Button buttonPosition = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPosition.setOnClickListener(button -> {
                if(validator.validateSizeMulti(MIN_SIZE-1, getString(R.string.error_message_mission_required), codeInput, nameInput)) {
                    String code = codeInput.getText().toString();
                    String name = nameInput.getText().toString();

                    services.saveMission(new Mission(code, name));
                    dialog.dismiss();
                }
            });
        });
        alertDialog.show();
    }
}
