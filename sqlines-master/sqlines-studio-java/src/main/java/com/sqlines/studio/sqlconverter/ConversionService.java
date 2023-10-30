package com.sqlines.studio.sqlconverter;

import com.sqlines.studio.model.converter.ConversionResult;
import org.springframework.stereotype.Service;

@Service
public interface ConversionService {

    ConversionResult run(String sourceMode,String targetMode, byte[] sourceData, String targetFIleName) throws Exception;
}
