package com.nsu.ccfit.nsuschedule;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.nsu.ccfit.nsuschedule.data.DataController;
import com.nsu.ccfit.nsuschedule.data.wrappers.TimeIntervalData;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;

public class UserDialogFragment extends DialogFragment {

    private DataViewModel dataViewModel;
    private TimeIntervalData timeIntervalData;
    private boolean[] checkedItemsArray = {true, false, false};

    public UserDialogFragment(DataViewModel dataViewModel, TimeIntervalData timeIntervalData) {
        this.timeIntervalData = timeIntervalData;
        this.dataViewModel = dataViewModel;
        if (timeIntervalData.getUserSettingsData().isNotificationsAllowed()) {
            checkedItemsArray[0] = true;
        } else {
            checkedItemsArray[0] = false;
        }

        if (timeIntervalData.getUserSettingsData().isAlarmAllowed()) {
            checkedItemsArray[1] = true;
        } else {
            checkedItemsArray[1] = false;
        }

        if (timeIntervalData.getUserSettingsData().isVisible()) {
            checkedItemsArray[2] = false;
        } else {
            checkedItemsArray[2] = true;
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] settingsArray = {"Уведомления", "Будильник", "Скрыть пару"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Настройки пары")
                .setMultiChoiceItems(settingsArray, checkedItemsArray,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                DataController dataController = new DataController(getActivity().getFilesDir());
                                try {
                                    if (checkedItemsArray[0]) {
                                        System.out.println(dataController
                                                .changeIsNotificationsAllowedByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , true)
                                        );
                                    } else {
                                        System.out.println(dataController
                                                .changeIsNotificationsAllowedByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , false)
                                        );
                                    }

                                    if (checkedItemsArray[1]) {
                                        System.out.println(dataController
                                                .changeIsAlarmsAllowedByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , true)
                                        );
                                    } else {
                                        System.out.println(dataController
                                                .changeIsAlarmsAllowedByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , false)
                                        );
                                    }

                                    if (checkedItemsArray[2]) {
                                        System.out.println(dataController
                                                .changeIsVisibleByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , false)
                                        );
                                        //скрыть пару
                                    } else {
                                        System.out.println(dataController
                                                .changeIsVisibleByHash(
                                                        timeIntervalData
                                                                .getNsuServerData()
                                                                .getHash()
                                                        , true)
                                        );
                                    }
                                    dataViewModel.loadData();
                                }catch (IOException | ParserException i) {
                                    i.printStackTrace();
                                }
                            }
                        })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }
}
