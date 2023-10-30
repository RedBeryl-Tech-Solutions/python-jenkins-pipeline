/*
 * Copyright (c) 2021 SQLines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sqlines.studio.model.converter;

import com.sqlines.studio.model.coreprocess.Arguments;
import com.sqlines.studio.model.coreprocess.CoreProcessRunner;

import com.sqlines.studio.sqlconverter.ConversionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConverterImpl implements Converter {
    private static final Logger logger = LogManager.getLogger(ConverterImpl.class);

    private CmdModes cmdModes;

    /*@Autowired
    public void setCoreProcess(CoreProcessRunner coreProcess){
        this.coreProcess = coreProcess;
    }*/


    private CoreProcessRunner coreProcess;

    /*@Autowired
    public void setCoreProcess(CoreProcessRunner coreProcess){
        this.coreProcess = coreProcess;
    }*/


    /**
     * Constructs a new Converter with the specified command line modes
     * and sqlines core program.
     *
     * @param cmdModes command line modes
     * @param coreProcess sqlines command line program
     */
    public ConverterImpl(CmdModes cmdModes, CoreProcessRunner coreProcess) {
        this.cmdModes = cmdModes;
        this.coreProcess = coreProcess;
    }

    @Override
    public ConversionResult run(String sourceMode, String targetMode,
                                String sourceFilePath, String targetFileName) throws Exception {
        String logFilePath = "";
        try {
            logFilePath = getLogFilePath();
            String targetPath = getTargetFilePath(targetFileName, targetMode);
            System.out.println("targetPath ==> " + targetPath);


            //targetPath = "C:\\Users\\ganes\\sqlines\\tab1_MySQL.sql";
            //targetPath =

            Arguments arguments = Arguments.builder()
                    .withSourceMode(getCmdMode(sourceMode))
                    .withTargetMode(getCmdMode(targetMode))
                    .withSourceFilePath(sourceFilePath)
                    .withTargetFilePath(targetPath)
                    .withLogFilePath(logFilePath)
                    .build();

            String conversionResponseString = coreProcess.runAndWait(arguments);
            System.out.println("conversionResponseString ==> " + conversionResponseString);

            return getTargetData(targetPath);
        } finally {
            clean(sourceFilePath, logFilePath);
        }
    }

    private String getLogFilePath() {
        String path = "";
        try {
            File file = File.createTempFile("sqlines-log", ".tmp");
            logger.info("Created log file: " + file.getAbsolutePath());
            path = file.getAbsolutePath();
        } catch (Exception e) {
            logger.error("Cannot create log file: " + e.getMessage());
        }

        return path;
    }

    private String getCmdMode(String rowMode) {
        if (cmdModes == null) {
            // Handle the case where cmdModes is not initialized
            return "defaultMode"; // or another appropriate default value this i enterend ann
        }
        String cmdMode = cmdModes.get(rowMode);
        if (cmdMode == null) {
            String errorMsg = "No config file in application resources: " +
                "source-modes.txt or target-modes.txt";
            throw new IllegalStateException(errorMsg);
        }
        return cmdMode;
    }



//    private String getCmdMode(String rowMode) {
//        String cmdMode = cmdModes.get(rowMode);
//        if (cmdMode == null) {
//            String errorMsg = "No config file in application resources: " +
//                    "source-modes.txt or target-modes.txt";
//            throw new IllegalStateException(errorMsg);
//        }
//        return cmdMode;
//    }  --->original code

    private String getTargetFilePath(String fileName, String targetMode) throws Exception {
         String path = buildTargetFilePath(fileName, targetMode);
        if (fileExists(path)) {
            return path;
        } else {
            return createTargetFile(path);
        }
    }

    private String buildTargetFilePath(String fileName, String targetMode) {
        System.out.println("In ConverterImpl : fileName ==> " + fileName + ", targetMode ==> " + targetMode);
        System.out.println("model.curr-dir ==> " + System.getProperty("model.curr-dir"));



        //return new StringBuilder(System.getProperty("model.curr-dir"))
        return new StringBuilder("C:\\Users\\Acer\\Downloads")
                .append("/")
                .append(fileName.replaceAll("\\s+",""))
                .append("_")
                .append(targetMode)
                .append(".sql")
                .toString();
    }

    private boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }


    private String createTargetFile(String path) throws IOException {
        try {
            // Replace forward slashes with backslashes for Windows file path
            path = path.replace("/", "\\");
            File targetFile = new File(path);
            if (targetFile.exists()) {
                logger.info("Target file already exists: " + targetFile.getAbsolutePath());
            } else {
                boolean success = targetFile.createNewFile();
                if (success) {
                    logger.info("Created target file: " + targetFile.getAbsolutePath());
                } else {
                    throw new IOException("Cannot create target file: " + targetFile.getAbsolutePath());
                }
            }

            return targetFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Cannot create target file: " + path, e);
        }
    }

//    private String createTargetFile(String path) throws IOException {
//        try {
//            File targetFile = new File(path);
//            boolean success = targetFile.createNewFile();
//            if (success) {
//                logger.info("Created target file: " + targetFile.getAbsolutePath());
//            } else {
//                throw new IOException("Cannot create target file: " + targetFile.getAbsolutePath());
//            }
//
//            return targetFile.getAbsolutePath();
//        } catch (Exception e) {
//            throw new IOException("Cannot create target file: " + path, e);
//        }
//    }

    private ConversionResult getTargetData(String targetFilePath) throws Exception {
        File file = new File(targetFilePath);
        try (FileInputStream stream = new FileInputStream(file)) {
            String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            return new ConversionResult(data, targetFilePath);
        }
    }

    private void clean(String sourceFilePath, String logFilePath) {

        String tempDir = System.getProperty("java.io.tmpdir");
        if (sourceFilePath.startsWith(tempDir)) {
        }
    }

//    public void deleteLogFile(String path) {
//        try {
//            File file = new File(path);
//            boolean success = file.delete();
//            if (success) {
//                logger.info("Log file deleted: " + path);
//            } else {
//                logger.error("Cannot delete log file: " + path);
//            }
//        } catch (Exception e) {
//            logger.error("Cannot delete log file: " + e.getMessage());
//        }
//    }

//    public void deleteSourceFile(String path) {
//        try {
//            File file = new File(path);
//            boolean success = file.delete();
//            if (success) {
//                logger.info("Source file deleted: " + path);
//            } else {
//                logger.error("Cannot delete source file: " + path);
//            }
//        } catch (Exception e) {
//            logger.error("Cannot delete source file: " + e.getMessage());
//        }
//    }

    @Override
    public ConversionResult run(String sourceMode, String targetMode,
                                byte[] sourceData, String targetFileName) throws Exception {
        String sourceFilePath = createTempSourceFile(sourceData);
        return run(sourceMode, targetMode, sourceFilePath, targetFileName);
    }

    private String createTempSourceFile(byte[] data) throws Exception {
        try {
            File file = File.createTempFile("sqlines-temp", ".tmp");
            writeToFile(file, data);
            logger.info("Created temporary source file: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new IOException("Cannot create temporary source file", e);
        }
    }

    private void writeToFile(File file, byte[] data) throws Exception {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(data);
        }
    }
}
