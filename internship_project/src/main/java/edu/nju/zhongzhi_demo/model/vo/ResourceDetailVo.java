package edu.nju.zhongzhi_demo.model.vo;

import edu.nju.zhongzhi_demo.enums.ResourceType;

public class ResourceDetailVo {

    public int id;
    public String name;
    public ResourceType resourceType;
    public String deptName;
    public String auditDeptName;
    public String auditorName;
    public String resrcStatus;
    public String reviewTime;
    public boolean approved = false;
}
