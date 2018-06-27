package com.haier.service.impl;

import com.haier.po.*;
import com.haier.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2018/6/26 16:24
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class RunServiceImpl implements RunService {
    @Autowired
    TserviceService tserviceService;
    @Autowired
    TenvService tenvService;
    @Autowired
    TenvdetailService tenvdetailService;
    @Autowired
    TiService tiService;
    @Autowired
    TcaseService tcaseService;

    @Override
    public Tservice getTservice(Integer serviceId) {
        return tserviceService.selectOne(serviceId);
    }

    @Override
    public Tenv getTenv(Integer tenvId) {
        return tenvService.selectOne(tenvId);
    }

    @Override
    public Tenvdetail getTenvdetail(Integer serviceId, Integer envId) {
        Tenvdetail tenvdetail = new Tenvdetail();
        tenvdetail.setServiceid(serviceId);
        tenvdetail.setEnvid(envId);
        List<Tenvdetail> tenvdetailList = tenvdetailService.selectByCondition(tenvdetail);
        //正常情况有且只会返回一个tenvdetail,如果返回多个,则取第一个
        if (tenvdetailList != null && tenvdetailList.size() > 0) {
            return tenvdetailList.get(0);
        }
        return null;
    }

    @Override
    public Ti getTi(Integer serviceId, String iUri) {
        Ti ti=new Ti();
        ti.setServiceid(serviceId);
        ti.setIuri(iUri);
        List<Ti> tis = tiService.selectByCondition(ti);
        if (tis != null && tis.size() > 0) {
            return tis.get(0);
        }
        return null;
    }

    @Override
    public List<Tcase> getTcase(Integer iId, Integer envId, String caseDesigner) {
        Tcase tcase=new Tcase();
        tcase.setIid(iId);
        tcase.setEnvid(envId);
        tcase.setAuthor(caseDesigner);
        return tcaseService.selectByCondition(tcase);
    }
}
