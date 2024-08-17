package com.sk.projectprocessor.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.processor.SimpleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class XmlUtil {

    private final static XmlMapper mapper = new XmlMapper();
    private final static Logger logger = LoggerFactory.getLogger(SimpleProcessor.class);

    public final static  String STATUS_FILE = "status.xml";
    public final static  int INITIAL_DELAY = 10;
    public final static  int RATE = 5;

    public static ParentInfo read(ParentInfo parentInfo) {
        try {
            File toBeRead = new File(parentInfo.getLocalDir() + File.separator + parentInfo.getRunId() + File.separator + STATUS_FILE);
            return mapper.readValue(toBeRead, ParentInfo.class);
        } catch (Exception ex) {
            ParentInfo parentInfoTemp = parentInfo.clone();
            write(parentInfoTemp);
            return parentInfoTemp;
        }
    }

    public static void write(ParentInfo parentInfo) {
        try {
            File toBeWritten = new File(parentInfo.getLocalDir() + File.separator + parentInfo.getRunId() + File.separator + STATUS_FILE);
            if(!toBeWritten.getParentFile().exists()) {
                toBeWritten.getParentFile().mkdir();
            }
            //mapper.writeValue(toBeWritten, parentInfo);
        } catch (Exception ioe) {
            logger.error("Not able to write status file", ioe);
        }
    }

    public static void registerUpdateStatusJob(ParentInfo parentInfo) {
        ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);
        exector.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                write(parentInfo);
            }
        }, INITIAL_DELAY, RATE, TimeUnit.SECONDS);
    }
}
