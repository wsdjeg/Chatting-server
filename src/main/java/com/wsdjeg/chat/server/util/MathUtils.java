package com.wsdjeg.chat.server.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class MathUtils {
    public static List<List<Integer>> get_list_by_sum(List<Integer> list, int sum){
        return null;
    }
    public String getResult(List<Integer> list,int sum){
        int min = 1;
        int max = list.size();
        int sumtemp = 0;
        Collections.sort(list);
        List<Integer> prepareList = list;
        for (int i = 0 ; i < prepareList.size();i++ ) {
            sumtemp += prepareList.get(i);
            if (sumtemp > sum) {
                max = i;
                i = prepareList.size();
            }
        }
        sumtemp = 0;
        for (int i = 0 ; i < prepareList.size();i++ ) {
            sumtemp += prepareList.get(prepareList.size()-1-i);
            if (sumtemp >= sum) {
                min = i + 1;
                i = prepareList.size();
            }

        }
        String result = "";
        for (int j = min ; j <= max ; j++) {
            result += getSubListWithnum(list,j,0,sum,"","",0);

        }
        return result;
    }
    /**
     * 从一个List中选取n各元素 所有可能的组合排列
     */
    public String getSubListWithnum(List<Integer> list,int n,int sumTemp,int sum,String result,String results,int index){
        results = "";
        if (n > 1 && n <= list.size()) {
            for (int i = index ; i < list.size() - (n-1); i++) {
                int minSumTemp = 0;
                int maxSumTemp = 0;
                //从list中取n各数,目前已经取一个,还需取n-1个(从listTemp中取),从0 取到n-1-1 即为 < n -1
                for (int i1 = index + 1  ; i1 < index + n - 1; i1++) {
                    minSumTemp += list.get(i1);
                }
                //从 倒数第n-1个取到 最后一个 listTemp.size() -i = n-1
                for (int i1 = list.size() - (n-1) ; i1 < list.size() ; i1++){
                    maxSumTemp += list.get(i1);
                }
                if(sumTemp + list.get(i) + minSumTemp <= sum && sumTemp + list.get(i) + maxSumTemp >= sum ){

                    results += getSubListWithnum(list,n-1,sumTemp + list.get(i),sum,result + list.get(i) + ",",results,i+1);
                }
            }
            return results;
        }else if(n == 1){
            for (int k = index ; k < list.size(); k++) {
                if (sumTemp + list.get(k) ==sum) {
                    //System.out.println(sumTemp+ ","+ list.get(k) +"," +sum + "," +result +"," + results);
                    results += "[" + result  + list.get(k) + "]<br>";
                    //System.out.println(sumTemp+ ","+ list.get(k) +"," +sum + "," +result +"," + results);
                }
            }
            return results;
        }else{
            return "error!";
        }
    }
}

