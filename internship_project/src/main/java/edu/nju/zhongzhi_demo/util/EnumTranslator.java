package edu.nju.zhongzhi_demo.util;

import edu.nju.zhongzhi_demo.enums.*;

import java.sql.Timestamp;

public class EnumTranslator {

    public static String translate(WorkOrderStatus workOrderStatus){
        switch (workOrderStatus) {
            case wait_review:
                return "待审核";
            case processed:
                return "已处理";
        }
        return "";
    }


    public static String translate(WorkOrderReviewResult workOrderReviewResult){
        if(workOrderReviewResult == null){
            return "暂未审核";
        }

        switch (workOrderReviewResult) {
            case all_pass:
                return "全部通过";
            case all_deny:
                return "全部否决";
            case part_pass:
                return "部分通过";
        }
        return "";

    }

    public static String translate(ResourceStatus resourceStatus){

        if(resourceStatus == null){
            return "暂未审核";
        }

        switch (resourceStatus) {
            case approved:
                return "通过";
            case rejected:
                return "未通过";

        }
        return "";
    }

    public static String translate(Timestamp reviewTime){
        if(reviewTime == null){
            return "暂未审核";
        }
        return DateHelper.TimestampToString(reviewTime);
    }

    public static String translate(DataType dataType){
        switch (dataType) {
            case relation_db:
                return "关系数据库";
            case doc_db:
                return "文档数据库";
        }
        return "";
    }

    public static String translate(ApiLevel apiLevel){
        switch (apiLevel) {
            case privatel:
                return "私有";
            case publicl:
                return "公开";
        }
        return "";
    }

}

