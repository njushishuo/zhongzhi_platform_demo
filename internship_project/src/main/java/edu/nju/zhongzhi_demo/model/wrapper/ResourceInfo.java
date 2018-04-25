package edu.nju.zhongzhi_demo.model.wrapper;

import edu.nju.zhongzhi_demo.model.vo.ResrcApiVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcCmptVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcDataVo;
import java.util.ArrayList;
import java.util.List;

public class ResourceInfo {

    public ResourceInfo(){
        this.resrcApiList = new ArrayList<>();
        this.resrcCmptList = new ArrayList<>();
        this.resrcDataList = new ArrayList<>();
    }

    public List<ResrcCmptVo> resrcCmptList;
    public List<ResrcDataVo> resrcDataList;
    public List<ResrcApiVo> resrcApiList;
}
