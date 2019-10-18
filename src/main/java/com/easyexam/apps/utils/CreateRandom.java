package com.easyexam.apps.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//生成随机数的工具类
public class CreateRandom {
    public static List<Object> getList(List oldlist, Integer needNum){
        Random random = new Random();
        ArrayList<Object> list = new ArrayList<>();
        int size = oldlist.size();
        if (needNum > size){
            return null;
        }
        for (int i = 0; i < needNum; i++) {
            int ranInt = random.nextInt(size--);
            Object remove = oldlist.remove(ranInt);
            list.add(remove);
        }
        return list;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(9);
        list.add(2);
        list.add(8);
        list.add(3);
        //List<Integer> list1 = CreateRandom.getList(new ArrayList<Integer>(), 2);
//        for (int i =0; i<list1.size(); i++){
//            System.out.println(list1.get(i));
//        }
    }

}
