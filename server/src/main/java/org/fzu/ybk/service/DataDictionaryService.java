package org.fzu.ybk.service;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.DataDictionary;
import org.fzu.ybk.entity.DataDictionaryUpdate;
import org.fzu.ybk.exception.DataDictionaryException;
import org.fzu.ybk.mapper.DataDictionaryMapper;
import org.fzu.ybk.utils.DateFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 考虑：把软删除独立作为一个泛型类，反射读取is_deleted两个属性，供所有类调用
* */
@Service
public class DataDictionaryService {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private DateFormater dateFormater;

    private final Logger logger = LoggerFactory.getLogger(DataDictionaryService.class);

    private void deleteDictKey(String dictName) throws DataDictionaryException {
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null)
            throw new DataDictionaryException("数据字典无此键，删除失败");
        dataDictionaryMapper.deleteDictKeyByDictId(keyId);
    }

    private void deleteDictValue(String dictName,String dataName) throws DataDictionaryException{
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null)
            throw new DataDictionaryException("数据字典无此键，删除失败");
        Long ValueId = dataDictionaryMapper.
                getDictValueByDictIdAndDataName(keyId,dataName);
        if (ValueId == null )
            throw new DataDictionaryException("数据字典中，该键下无此类数据，删除失败");

        dataDictionaryMapper.deleteDictValueByDataId(ValueId);
    }

    private void insertDictKey(String dictName) throws DataDictionaryException{
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null){
            dataDictionaryMapper.insertDictKey(dictName,dateFormater.getDate(),false);
        }
        else {
                throw new DataDictionaryException("数据字典已存在此键");
        }
    }


    private void insertDictValue(String dictName, String dataName) throws DataDictionaryException{
        String dateStr = dateFormater.getDate();
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null){
            dataDictionaryMapper.insertDictKey(dictName,dateStr,false);
            keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        }

        Long dataOrder = dataDictionaryMapper.getDictValueNumber(keyId) + 1;

        Long valueId = dataDictionaryMapper.
                getDictValueByDictIdAndDataName(keyId,dataName);

        if (valueId == null){
            dataDictionaryMapper.insertDictValue(keyId,dataName,
                    dataOrder,dateStr,false);
        }
        else
            throw new DataDictionaryException("数据字典该键下已存在此类型");
    }



    private void updateDictKey(String dictName,String newDictName) throws DataDictionaryException{
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null ){
            throw new DataDictionaryException("数据字典无此键");
        }

        //判断新值是否会重复
        Long keyId2 = dataDictionaryMapper.
                getDictKeyByDictName(newDictName);
        if (keyId2 == null)
            dataDictionaryMapper.updateDictKeyByDictId(keyId,newDictName);
        else
            throw new DataDictionaryException("数据字典已存在被修改后的键");


    }

    private void updateDictValue(String dictName,String dataName, String newDataName) throws DataDictionaryException{
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null )
            throw new DataDictionaryException("数据字典无此键");

        Long valueId = dataDictionaryMapper.
                getDictValueByDictIdAndDataName(keyId,dataName);
        if (valueId == null )
            throw new DataDictionaryException("数据字典该键下无此类型");

        //判断新值是否会重复
        Long valueId2 = dataDictionaryMapper.
                getDictValueByDictIdAndDataName(keyId,newDataName);
        if (valueId2 == null){
            dataDictionaryMapper.updateDictValueByDataId(valueId,newDataName);
        }

        else
            throw new DataDictionaryException("数据字典该键已存在此类型值");
    }

    private void updateDictValueOrder(String dictName,String dataName, Long newOrder)
            throws DataDictionaryException{

        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null )
            throw new DataDictionaryException("数据字典无此键");

        Long valueId = dataDictionaryMapper.
                getDictValueByDictIdAndDataName(keyId,dataName);
        if (valueId == null )
            throw new DataDictionaryException("该键下无此类型");

        dataDictionaryMapper.updateDictValueOrderByDataId(valueId,newOrder);

    }

    // delimiter of soft operation based on mapper


    public String deleteData(String dictName, String dataName ) throws Exception{
        this.deleteDictValue(dictName,dataName);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String insertData(DataDictionary dataDictionary) throws Exception{

        if (dataDictionary.getDataName() == null || dataDictionary.getDictName() == null){
            throw new DataDictionaryException("字典、数据Name不能为空！");
        }
        this.insertDictValue(dataDictionary.getDictName(),dataDictionary.getDataName());
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"插入成功");
    }

    public String updateKey(DataDictionaryUpdate dataDictionaryUpdate) throws Exception{

        String oldDictName = dataDictionaryUpdate.getDictName();
        String newDictName = dataDictionaryUpdate.getNewDictName();
        if (oldDictName == null || newDictName == null){
            throw new DataDictionaryException("字典名，新字典名不能为空");
        }
        this.updateDictKey(oldDictName,newDictName);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }

    public String updateValue(DataDictionaryUpdate dataDictionaryUpdate) throws Exception{
        String dictName = dataDictionaryUpdate.getDictName();
        String oldDataName = dataDictionaryUpdate.getDataName();
        String newDataName = dataDictionaryUpdate.getNewDataName();
        if (dictName == null || oldDataName == null || newDataName == null){
            throw new DataDictionaryException("字典名，数据名，新数据名不能为空");
        }
        this.updateDictValue(dictName,oldDataName,newDataName);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }

    public String updateValueOrder(DataDictionaryUpdate dataDictionaryUpdate) throws Exception{
        String dictName = dataDictionaryUpdate.getDictName();
        String dataName = dataDictionaryUpdate.getDataName();
//        String newDataName = dataDictionaryUpdate.getNewDataName();
        Long newOrder = dataDictionaryUpdate.getDataOrder();
        if (dictName == null || dataName == null || newOrder == null){
            throw new DataDictionaryException("字典名，数据名，新数据序不能为空");
        }
        this.updateDictValueOrder(dictName,dataName,newOrder);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }


    public String getAllData(Long page,Long pageSize) throws Exception{
        Long offset = (page-1)*pageSize;

        List<DataDictionary> res = dataDictionaryMapper.getAllDataFromDataDictionary(pageSize,offset);
        if (res.size() == 0){
            throw new DataDictionaryException("数字字典下无数据");
        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",res);
    }

    public String getDataByDictName(String dictName,Long page,Long pageSize) throws Exception{
        Long offset = (page-1)*pageSize;
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null )
            throw new DataDictionaryException("数据字典无此键");
        List<DataDictionary> res = dataDictionaryMapper.
                getDictValuesByDictId(keyId,pageSize,offset);
        if (res.size() == 0){
            throw new DataDictionaryException("该键下无数据");
        }
        //为查询到的value赋key值
        for (int i=0;i<res.size();++i){
            res.get(i).setDictName(dictName);
        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",res);
    }

    public String getDataByDictNameAndDataName(String dictName, String dataName,Long page,Long pageSize )
            throws Exception{

        Long offset = (page-1)*pageSize;
        Long keyId = dataDictionaryMapper.getDictKeyByDictName(dictName);
        if (keyId == null )
            throw new DataDictionaryException("数据字典无此键");

        List<DataDictionary> res = dataDictionaryMapper.
                getDictValuesByDictIdAndDataNameLikely(keyId,dataName,pageSize,offset);
        if (res.size() == 0){
            throw new DataDictionaryException("该字典下未匹配到数据");
        }
        //为查询到的value赋key值
        for (int i=0;i<res.size();++i){
            res.get(i).setDictName(dictName);
        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",res);
    }

}
