package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.DataDictionary;
import org.fzu.ybk.entity.DataDictionaryUpdate;
import org.fzu.ybk.entity.MulDataDictionary;
import org.fzu.ybk.service.DataDictionaryService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class DataDictionaryController {

    @Autowired
    DataDictionaryService dataDictionaryService;

    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(DataDictionaryController.class);

    @DeleteMapping(value = "/dataDictionary")
    public String deleteData(@RequestParam(value = "dictName" ,required = true) String dictName,
                             @RequestParam(value = "dataName" ,required = true) String dataName,
                             HttpServletRequest request){
        try{
            return dataDictionaryService.deleteData(dictName,dataName);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/dataDictionary")
    public String updateData(@RequestBody DataDictionaryUpdate dataDictionaryUpdate,
                             HttpServletRequest request){
        try{
            if (dataDictionaryUpdate.getDictName() == null)
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"字典名，数据名不可为空");

            if( dataDictionaryUpdate.getDataName()!=null &&
                    dataDictionaryUpdate.getNewDataName() != null)
                dataDictionaryService.updateValue(dataDictionaryUpdate);

//            if( dataDictionaryUpdate.getDataName()!=null &&
//                    dataDictionaryUpdate.getDataOrder() != null)
//                dataDictionaryService.updateValueOrder(dataDictionaryUpdate);

            if(dataDictionaryUpdate.getNewDictName() != null)
                dataDictionaryService.updateKey(dataDictionaryUpdate);

            return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/updateTextData")
    public String updateTextData(@RequestBody DataDictionaryUpdate dataDictionaryUpdate,
                             HttpServletRequest request){
        try{
            if (dataDictionaryUpdate.getDictName() == null || dataDictionaryUpdate.getDataName() == null)
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"字典名，数据名不可为空");

            else
                dataDictionaryService.updateTextValue(dataDictionaryUpdate);


            return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PostMapping(value = "/dataDictionary")
    public String insertData(@RequestBody DataDictionary dataDictionary, HttpServletRequest request){
        try{
            return dataDictionaryService.insertData(dataDictionary);
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PostMapping(value = "/insertTextData")
    public String insertTextData(@RequestBody DataDictionary dataDictionary, HttpServletRequest request){
        try{
            return dataDictionaryService.insertTextData(dataDictionary);
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PostMapping(value = "/insertAllTextData")
    public String insertTextData(@RequestBody MulDataDictionary muldataDictionary, HttpServletRequest request){
        try{
            return dataDictionaryService.insertAllTextData(muldataDictionary);
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @GetMapping(value = "/dataDictionary")
    public String getData(@RequestParam(value = "getAll" ,required = true) Boolean getAll,
                          @RequestParam(value = "dictName" ,required = false) String dictName,
                          @RequestParam(value = "dataName" ,required = false) String dataName,
                          @RequestParam(value = "page" ,required = true) Long page,
                          @RequestParam(value = "pageSize" ,required = true) Long pageSize,
                          HttpServletRequest request){
        try{
            if (getAll == true || dictName == null)
                return dataDictionaryService.getAllData(page,pageSize);

            if (dataName == null)
                return dataDictionaryService.getDataByDictName(dictName,page,pageSize);
            else
                return dataDictionaryService.
                        getDataByDictNameAndDataName(dictName, dataName, page, pageSize);

        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
    @GetMapping(value = "/textDataget")
    public String getTextData(@RequestParam(value = "dictName" ,required = true) String dictName,
                          @RequestParam(value = "dataName" ,required = true) String dataName,
                          HttpServletRequest request){
        try{
                return dataDictionaryService.
                        getTextDataByDictNameAndDataName(dictName, dataName);

        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



}
