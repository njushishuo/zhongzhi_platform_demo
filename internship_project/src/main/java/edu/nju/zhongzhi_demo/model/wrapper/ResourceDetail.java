package edu.nju.zhongzhi_demo.model.wrapper;


import edu.nju.zhongzhi_demo.model.vo.ResrcApiDetailVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcCmptDetailVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcDataDetailVo;

import java.util.ArrayList;
import java.util.List;

public class ResourceDetail {
    public ResourceDetail(){
        this.resrcApiList = new ArrayList<>();
        this.resrcCmptList = new ArrayList<>();
        this.resrcDataList = new ArrayList<>();
    }

    public List<ResrcCmptDetailVo> resrcCmptList;
    public List<ResrcDataDetailVo> resrcDataList;
    public List<ResrcApiDetailVo> resrcApiList;
}
