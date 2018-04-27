package com.haier.cases;


import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.haier.anno.Iuri;
import com.haier.anno.ServiceKey;
import com.haier.enums.ContentTypeEnum;
import com.haier.enums.EnvEnum;
import com.haier.enums.HttpTypeEnum;
import com.haier.enums.RequestMethodTypeEnum;
import com.haier.mapper.TcaseMapper;
import com.haier.mytest.MyTest;
import com.haier.po.*;
import com.haier.util.DBUtil;
import com.haier.util.TestPropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@ServiceKey("User")
public class UserTest {
    private static Log log = LogFactory.getLog(MyTest.class);
    private String envKey;
    private int envKeyId;
    private String serviceKey;
    private int serviceKeyId;
    private String httpType;
    private String host;
    private String db;

    //无参构造器初始化环境信息
    public UserTest() {
        this.envKey = TestPropertiesUtil.getValue("test.env");
        this.envKeyId = EnvEnum.getId(envKey);
        this.serviceKey = this.getClass().getAnnotation(ServiceKey.class).value();
        //获取serviceId
        try {
            this.serviceKeyId = DBUtil.getServiceKeyId(serviceKey);
        } catch (IOException e) {
            log.error("获取ServiceKey时IO异常");
            log.error(e);
            return;
        }
        //获取host,db,httpType信息
        List<TenvdetailCustom> tenvdetailCustomList = null;
        try {
            tenvdetailCustomList = DBUtil.getTenvdetailCustomList(serviceKey, envKey);
        } catch (IOException e) {
            log.error(e);
        }
        if (tenvdetailCustomList != null) {
            TenvdetailCustom tenvdetailCustom = tenvdetailCustomList.get(0);//默认拿第一条记录,如果数据库有多条记录,证明数据库中存在脏数据
            //得到Host信息
            this.host = tenvdetailCustom.getHostinfo();
            //得到数据库信息
            this.db = tenvdetailCustom.getDbinfo();
            //得到请求类型ID
            int _httpType = tenvdetailCustom.getTservice().getHttptype();
            //请求类型ID转换成String
            this.httpType = HttpTypeEnum.getValue(_httpType);
        } else {
            log.error("根据serviceKey:" + serviceKey + "和envKey:" + envKey + "未获取到环境信息");
            return;
        }


    }

    @BeforeClass
    public void beforeClass() {

    }

    @Iuri("/loginFacade/generateCode")
    @Test(groups = "loginFacade", dataProvider = "getCaseWithObject", description = "验证码生成接口测试")
    public void loginFacade_generateCode_test(int requestMethod, String uri, String param, Integer paramType, Integer responseType, String expected, String assertType) {
        String url = httpType + "://" + host + uri;//组装请求地址
        Header[] headers= HttpHeader.custom().other("content-type", ContentTypeEnum.getValue(paramType)+";charset=utf-8").build();

        HttpConfig httpConfig= HttpConfig.custom().headers(headers).url(url).encoding("utf-8");
        //根据paramType配置请求参数类型
        if(ContentTypeEnum.getValue(paramType).equalsIgnoreCase("application/json")){
            httpConfig.json(param);
        }else if (ContentTypeEnum.getValue(paramType).equalsIgnoreCase("application/x-www-form-urlencoded")){
            //do application/x-www-form-urlencoded post
            //...
        }

        //根据requestMethod发送不同方式的请求

    }

    @DataProvider(name = "getCaseWithIterator")
    public Iterator<Object[]> getData() throws IOException {
        List<Object[]> ret = new ArrayList<Object[]>();
        SqlSession sqlSession = DBUtil.getSqlSession();
        TcaseMapper mapper = sqlSession.getMapper(TcaseMapper.class);
        TcaseExample tcaseExample = new TcaseExample();
        TcaseExample.Criteria criteria = tcaseExample.createCriteria();
        criteria.andIidEqualTo(1);//待传参实现
        List<Tcase> list = mapper.selectByExample(tcaseExample);
        //做一个形式转换,要求返回Iterator<Object[]>
        for (Object o : list) {
            ret.add(new Object[]{o});
        }
        return ret.iterator();
    }


    @DataProvider(name = "getCaseWithObject")
    public Object[][] getCase(Method method) {
        String uri = method.getAnnotation(Iuri.class).value();
        if (Objects.isNull(uri)) {
            log.error("测试方法:" + method.getName() + " 无@Iuri注解或者注解为空");
            return null;
        }
        //获取用例数据ti表与tcase表联合查询,过滤ti.status,ti.serviceId,ti.iuri,tcase.status,tcase.env,
        List<TcaseCustom> tcaseCustoms = null;
        try {
            tcaseCustoms = DBUtil.getTcaseCustomList(serviceKeyId, uri, envKeyId);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        if (tcaseCustoms == null) {
            return null;
        }
        //声明一个二维数组,行数为case的数量,列数为传进来的测试方法的参数的个数
        Object[][] o = new Object[tcaseCustoms.size()][method.getParameterCount()];

        for (int i = 0; i < tcaseCustoms.size(); i++) {
            TcaseCustom c = tcaseCustoms.get(i);
            o[i] = new Object[]{
                    new Integer(c.getTi().getIrequestmethod()),
                    new String(c.getTi().getIuri()),
                    new String(c.getRequestparam()),
                    new Integer(c.getTi().getIparamtype()),
                    new Integer(c.getTi().getIresponsetype()),
                    new String(c.getExpected()),
                    new String(c.getAsserttype())
            };
        }
        return o;
    }

}
