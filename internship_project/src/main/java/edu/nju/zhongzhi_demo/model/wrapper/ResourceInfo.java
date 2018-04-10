package edu.nju.zhongzhi_demo.model.wrapper;

import edu.nju.zhongzhi_demo.entity.ResrcApi;
import edu.nju.zhongzhi_demo.entity.ResrcCmpt;
import edu.nju.zhongzhi_demo.entity.ResrcData;

import java.util.ArrayList;
import java.util.List;

public class ResourceInfo {

    public ResourceInfo(){
        this.resrcApiList = new ArrayList<>();
        this.resrcCmptList = new ArrayList<>();
        this.resrcDataList = new ArrayList<>();
    }

    public List<ResrcCmpt> resrcCmptList;
    public List<ResrcData> resrcDataList;
    public List<ResrcApi> resrcApiList;
}
