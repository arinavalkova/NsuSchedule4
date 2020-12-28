package com.nsu.ccfit.nsuschedule.data.controllers.server;

import com.nsu.ccfit.nsuschedule.data.common.Copyier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class NSUServerDataController {

    //Собственность Арины, переделывать только с ее разрешения, использовать можно без ее разрешения

    private final static String NSU_SCHEDULE_FILE = "nsu_schedule_file4.ics";
    private final static String BACK_UP_FILE = "nsu_schedule_back_up.ics";
    private File nsuScheduleData;
    private File backUpScheduleFile;

    public NSUServerDataController(File filesDir) {
        this.nsuScheduleData = new File(filesDir, NSU_SCHEDULE_FILE);
        this.backUpScheduleFile = new File(filesDir, BACK_UP_FILE);
    }

    public boolean loadData(final String scheduleUrl) throws IOException {
        if (!nsuScheduleData.exists()) {
            nsuScheduleData.createNewFile();
        }
        if (!backUpScheduleFile.exists()) {
            backUpScheduleFile.createNewFile();
        }
        final CallBack callBack = new CallBack();
        try {
            new Copyier().copyFileTo(nsuScheduleData, backUpScheduleFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Thread loadingFromNSUServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(scheduleUrl);
                    ReadableByteChannel rbc = Channels.newChannel(url.openConnection().getInputStream());

                    FileOutputStream nsuScheduleFOS = new FileOutputStream(nsuScheduleData);
                    nsuScheduleFOS.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    nsuScheduleFOS.close();

                    rbc.close();
                    callBack.callback(true);
                } catch (IOException e) {
                    callBack.callback(false);
                    e.printStackTrace();
                }
            }
        });
        loadingFromNSUServerThread.start();
        try {
            loadingFromNSUServerThread.join();
        } catch (InterruptedException e) {
            callBack.callback(false);
            e.printStackTrace();
        }
        return callBack.getResult();
    }

    public File getNSUScheduleFile() {
        return nsuScheduleData;
    }

    public File getBackUpScheduleFile() {
        return backUpScheduleFile;
    }
}
