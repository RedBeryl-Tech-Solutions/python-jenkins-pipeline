package com.sqlines.studio.sqlconverter;

import com.sqlines.studio.model.ResourceLoader;
import com.sqlines.studio.model.converter.CmdModes;
import com.sqlines.studio.model.converter.ConversionResult;
import com.sqlines.studio.model.converter.ConverterImpl;
import com.sqlines.studio.model.coreprocess.Arguments;
import com.sqlines.studio.model.coreprocess.CoreProcessRunner;
import com.sqlines.studio.model.coreprocess.CoreProcessRunnerImp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class ConversionServiceimp implements ConversionService {

    private static String sourceMode = "PostgreSQL";
    private static String targetMode = "MySQL";
    private static String sourceFilePath = "C:/Users/Acer/Downloads/sourcefile.txt";
    private static String targetFileName = "tab1_MySQL.sql"; //C:\Users\Acer\sqlines\tab1_MySQL.sql

    public static void main(String arg[]) throws Exception {
        new ConversionServiceimp().getConversionResult(sourceMode, targetMode, sourceFilePath, targetFileName);
    }

    private ConversionResult getConversionResult(String sourceMode, String targetMode, String sourceFilePath, String targetFileName) throws Exception {

        // Map<String, String> cmdModesMap = new HashMap<String, String>();
        // CmdModes cmdModes = new CmdModes(cmdModesMap);

        CoreProcessRunner coreProcess = new CoreProcessRunnerImp();

        ConverterImpl c = new ConverterImpl(new CmdModes(ResourceLoader.loadCmdModes()), coreProcess);

        ConversionResult cr = c.run((String) sourceMode, (String) targetMode, (String) sourceFilePath, (String) targetFileName);
        return cr;
    }

    @Override
    public ConversionResult run(String sourceMode, String targetMode, byte[] sourceData, String targetFileName) throws Exception {
        //return null;
        System.out.println("sourceMode ==> " + sourceMode);
        System.out.println("targetMode ==> " + targetMode);
        System.out.println("sourceData ==> " + sourceData);
        System.out.println("targetFileName ==> " + targetFileName);

        ConversionResult conversionResult = new ConversionServiceimp().getConversionResult(sourceMode, targetMode, sourceFilePath, targetFileName);
        return conversionResult;
    }


    //ConversionResult run(String sourceMode,String targetMode, byte[] sourceData, String targetFIleName)


    }

