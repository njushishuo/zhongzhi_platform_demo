package edu.nju.zhongzhi_demo.model.para;

import edu.nju.zhongzhi_demo.entity.ResrcApi;
import edu.nju.zhongzhi_demo.entity.ResrcCmpt;
import edu.nju.zhongzhi_demo.entity.ResrcData;

import java.util.List;

public class WorkOrderPara {
    public Integer appId;
    public Integer userId;
    public List<ResrcCmpt> cmptList;
    public List<ResrcData> dataList;
    public List<ResrcApi> apiList;
    public String description;
}
