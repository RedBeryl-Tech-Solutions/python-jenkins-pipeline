package com.sqlines.studio.sqlconverter;


import com.sqlines.studio.model.converter.ConversionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;


@RestController
public class ConversionController {

    private final Logger log = LoggerFactory.getLogger(ConversionController.class);


    @Autowired
    private ConversionService conversionService ;

    @Autowired
    public void setConversionService(ConversionService conversionService){
        this.conversionService = conversionService;
    }

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public ConversionController() {

    }

    //    @GetMapping("/result")
    //    @ResponseBody
    @GetMapping("/convert")
    public Object runConversion(
        @RequestParam("sourceMode") String sourceMode,
        @RequestParam("targetMode") String targetMode,
        @RequestParam("sourceData") String sourceData,
        @RequestParam("targetFileName") String targetFileName
    ) {

        log.info("targetFileName in ConversionController class ==> " + targetFileName);

        try {
            // Convert sourceData to a byte array if needed
            byte[] sourceDataBytes = sourceData.getBytes();

            ConversionResult result = conversionService.run(sourceMode, targetMode, sourceDataBytes, targetFileName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            String errorMessage = "An error occurred during the conversion process.";
            log.error(errorMessage, e);
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}



//@RestController
//@RequestMapping("/api")
//public class ConversionController {
//
// ConversionService conversionService = null;
//
//    public ConversionController(){
//    }
//
//    public ConversionController(ConversionService conversionService) {
//        this.conversionService = conversionService;
//    }
//
//    @GetMapping("/run")
//    public ResponseEntity<ConversionResult> runConversion(@RequestParam String sourceMode,@RequestParam String targetMode,
//                                                          @RequestParam byte[] sourceData,@RequestParam String targetFileName){
//        try{
//            ConversionResult result= conversionService.run(sourceMode,targetMode,sourceData,targetFileName);
//            return ResponseEntity.ok(result);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
// }
