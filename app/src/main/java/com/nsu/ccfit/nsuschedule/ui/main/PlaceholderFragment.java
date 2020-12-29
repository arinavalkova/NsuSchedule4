package com.nsu.ccfit.nsuschedule.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nsu.ccfit.nsuschedule.DataViewModel;
import com.nsu.ccfit.nsuschedule.R;
import com.nsu.ccfit.nsuschedule.data.wrappers.TimeIntervalData;
import com.nsu.ccfit.nsuschedule.scheduleabstract.Parity;
import com.nsu.ccfit.nsuschedule.scheduleabstract.ScheduleItem;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private DataViewModel dataViewModel;
    int index = 1;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
//        dataViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView listView = root.findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    dataViewModel.showWindow(getActivity().getSupportFragmentManager(), index, position);
                } catch (ParserException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        LiveData<ScheduleItem[]> scheduleLiveData = dataViewModel.getSchedule(index);
        final Adapter adapter = new Adapter(getActivity());
//        final TextView textView = root.findViewById(R.id.section_label);
//        dataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        scheduleLiveData.observe(getViewLifecycleOwner(), new Observer<ScheduleItem[]>() {
            @Override
            public void onChanged(ScheduleItem[] schedule) {
                adapter.setSchedule(schedule);
            }
        });
        listView.setAdapter(adapter);
        return root;
    }

    class Adapter extends ArrayAdapter<ScheduleItem> {
        ScheduleItem[] schedule = null;
        LayoutInflater layoutInflater;

        public Adapter(@NonNull Context context) {
            super(context, R.layout.schedule_item);
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setSchedule(ScheduleItem[] schedule) {
            this.schedule = schedule;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (schedule == null) return 0;
            return schedule.length;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View item = layoutInflater.inflate(R.layout.schedule_item, parent, false);
            TextView description = item.findViewById(R.id.descriptionText);
            TextView location = item.findViewById(R.id.locationText);
            TextView summary = item.findViewById(R.id.summaryText);
            TextView time = item.findViewById(R.id.timeText);
            TextView parity = item.findViewById(R.id.parityText);

            ScheduleItem scheduleItem = schedule[position];

            if (scheduleItem.getDescription().trim().equals("-")) {
                description.setVisibility(View.GONE);
            } else {
                description.setText(scheduleItem.getDescription());
            }
            location.setText(scheduleItem.getLocation());
            summary.setText(scheduleItem.getSummary());
            time.setText(scheduleItem.getStartTime() + " - " + scheduleItem.getEndTime());
            switch (scheduleItem.getParity()) {
                case ALL:
//                    parity.setText("");
                    parity.setVisibility(View.GONE);
                    break;
                case ODD:
                    parity.setText("нечетная");
                    break;
                case EVEN:
                    parity.setText("четная");
            }
            return item;
        }
    }

}