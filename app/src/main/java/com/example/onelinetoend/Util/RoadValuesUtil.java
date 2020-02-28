package com.example.onelinetoend.Util;

import com.example.onelinetoend.Model.Bean.Bean_Road;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadValuesUtil {

    public final static List<List<Bean_Road>> roadValuesList = new CopyOnWriteArrayList<>();


    static {
        {//关卡1
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(3,3,new Integer[]{6,3,0,1,4,7,8}));
            list.add(new Bean_Road(3,3,new Integer[]{4,7,8,5,2,1,0}));
            list.add(new Bean_Road(3,3,new Integer[]{0,1,4,3,6,7,8}));
            list.add(new Bean_Road(3,4,new Integer[]{9,5,6,7,3,2,1,0,4,8}));
            list.add(new Bean_Road(3,4,new Integer[]{5,1,2,6,7,11,10,9,8,4}));
            list.add(new Bean_Road(3,4,new Integer[]{5,4,0,1,2,3,7,6,10,9}));
            list.add(new Bean_Road(3,4,new Integer[]{0,1,2,3,7,11,10,9,8}));
            list.add(new Bean_Road(3,4,new Integer[]{6,2,3,7,11,10,9,8,4}));
            list.add(new Bean_Road(3,4,new Integer[]{8,4,5,1,2,3,7,11,10}));
            list.add(new Bean_Road(3,4,new Integer[]{1,2,3,7,6,10,9,8}));
            roadValuesList.add(list);
        }

        {//关卡2
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(3,4,new Integer[]{8,4,0,1,2,6,7,3}));
            list.add(new Bean_Road(3,5,new Integer[]{9,4,3,2,1,6,5,10,11,12,7,8,13}));
            list.add(new Bean_Road(3,5,new Integer[]{0,1,2,3,4,9,14,13,12,7,6,5,10}));
            list.add(new Bean_Road(3,5,new Integer[]{9,14,13,12,7,6,11,10,5,0,1,2,3}));
            list.add(new Bean_Road(3,5,new Integer[]{9,4,3,2,1,0,5,6,7,12,13,14}));
            list.add(new Bean_Road(3,5,new Integer[]{11,6,5,0,1,2,3,4,9,14,13,12}));
            list.add(new Bean_Road(3,5,new Integer[]{3,2,1,0,5,6,7,12,13,8,9,14}));
            list.add(new Bean_Road(3,5,new Integer[]{9,4,3,2,1,6,11,12,7,8,13}));
            list.add(new Bean_Road(3,5,new Integer[]{12,13,14,9,4,3,8,7,6,1,0}));
            list.add(new Bean_Road(3,5,new Integer[]{9,14,13,12,7,8,3,2,1,0,5}));
            list.add(new Bean_Road(3,5,new Integer[]{7,8,3,4,9,14,13,12,11,10}));
            list.add(new Bean_Road(3,5,new Integer[]{4,3,2,1,0,5,6,11,12,13}));
            list.add(new Bean_Road(3,5,new Integer[]{5,0,1,6,11,12,13,8,3,2}));
            list.add(new Bean_Road(3,6,new Integer[]{13,7,8,14,15,9,10,11,5,4,3,2,1,0,6,12}));
            list.add(new Bean_Road(3,6,new Integer[]{11,5,4,3,2,1,7,6,12,13,14,8,9,15,16,17}));
            list.add(new Bean_Road(3,6,new Integer[]{17,11,5,4,10,9,3,2,1,7,6,12,13,14}));
            list.add(new Bean_Road(3,6,new Integer[]{5,4,10,11,17,16,15,14,13,7,1,0,6,12}));
            list.add(new Bean_Road(3,6,new Integer[]{10,16,15,14,13,12,6,7,1,2,3,4,5}));
            list.add(new Bean_Road(3,6,new Integer[]{9,15,16,17,11,5,4,3,2,1,7,13,12}));
            list.add(new Bean_Road(3,6,new Integer[]{8,7,6,0,1,2,3,4,5,11,17,16,15}));
            list.add(new Bean_Road(3,6,new Integer[]{6,7,1,2,8,9,3,4,10,16,17,11}));
            list.add(new Bean_Road(3,6,new Integer[]{3,4,10,11,17,16,15,14,8,7,13,12}));
            list.add(new Bean_Road(3,6,new Integer[]{12,6,7,8,2,3,9,10,16,17,11,5}));
            list.add(new Bean_Road(3,6,new Integer[]{1,7,6,12,13,14,8,2,3,4,10}));
            list.add(new Bean_Road(3,6,new Integer[]{8,2,3,4,10,9,15,14,13,7,6}));
            list.add(new Bean_Road(3,6,new Integer[]{7,6,12,13,14,15,9,3,4,10,16}));
            list.add(new Bean_Road(4,3,new Integer[]{4,1,2,5,8,7,10,9,6,3}));
            list.add(new Bean_Road(4,3,new Integer[]{9,6,7,10,11,8,5,4,1,0}));
            list.add(new Bean_Road(4,3,new Integer[]{7,10,9,6,3,0,1,2,5,8}));
            list.add(new Bean_Road(4,3,new Integer[]{10,9,6,3,0,1,2,5,8}));
            roadValuesList.add(list);
        }

        {//关卡3
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(4,4,new Integer[]{6,2,1,0,4,5,9,8,12,13,14,15,11,7}));
            list.add(new Bean_Road(4,4,new Integer[]{15,11,7,6,2,1,0,4,8,12,13,9,10,14}));
            list.add(new Bean_Road(4,4,new Integer[]{4,0,1,5,9,8,12,13,14,15,11,10,6,2}));
            list.add(new Bean_Road(4,4,new Integer[]{13,12,8,9,5,4,0,1,2,6,7,11,15}));
            list.add(new Bean_Road(4,4,new Integer[]{10,14,13,12,8,4,0,1,5,6,2,3,7}));
            list.add(new Bean_Road(4,4,new Integer[]{11,15,14,10,6,5,9,8,4,0,1,2,3}));
            list.add(new Bean_Road(4,4,new Integer[]{4,8,12,13,14,15,11,10,9,5,6,2}));
            list.add(new Bean_Road(4,4,new Integer[]{9,5,4,8,12,13,14,10,11,7,6,2}));
            list.add(new Bean_Road(4,4,new Integer[]{4,8,12,13,14,10,11,7,6,2,1,5}));
            list.add(new Bean_Road(4,4,new Integer[]{5,4,8,9,10,6,2,3,7,11,15}));
            list.add(new Bean_Road(4,4,new Integer[]{0,1,5,9,13,14,10,6,7,11,15}));
            list.add(new Bean_Road(4,4,new Integer[]{6,10,11,15,14,13,12,8,4,0,1}));
            list.add(new Bean_Road(4,4,new Integer[]{0,4,8,9,5,1,2,6,7,11}));
            list.add(new Bean_Road(4,4,new Integer[]{0,4,5,1,2,6,7,11,10,14}));
            list.add(new Bean_Road(4,4,new Integer[]{13,9,8,4,0,1,2,3,7,11}));
            list.add(new Bean_Road(4,5,new Integer[]{16,11,10,5,0,1,6,7,2,3,4,9,8,13,12,17,18,19}));
            list.add(new Bean_Road(4,5,new Integer[]{15,10,11,16,17,12,13,18,19,14,9,4,3,2,7,6,1,0}));
            list.add(new Bean_Road(4,5,new Integer[]{6,5,0,1,2,7,8,3,4,9,14,19,18,13,12,11,16,15}));
            list.add(new Bean_Road(4,5,new Integer[]{10,15,16,17,12,7,6,5,0,1,2,3,4,9,14,19,18}));
            list.add(new Bean_Road(4,5,new Integer[]{0,1,2,3,8,9,14,19,18,13,12,7,6,5,10,15,16}));
            list.add(new Bean_Road(4,5,new Integer[]{13,8,3,2,1,6,5,10,15,16,11,12,17,18,19,14,9}));
            list.add(new Bean_Road(4,5,new Integer[]{17,12,7,6,11,16,15,10,5,0,1,2,3,8,13,18}));
            list.add(new Bean_Road(4,5,new Integer[]{2,1,0,5,10,15,16,11,6,7,12,13,14,9,4,3}));
            list.add(new Bean_Road(4,5,new Integer[]{16,15,10,11,6,5,0,1,2,3,4,9,8,13,12,17}));
            list.add(new Bean_Road(4,5,new Integer[]{5,0,1,6,11,12,7,8,13,14,19,18,17,16,15}));
            list.add(new Bean_Road(4,5,new Integer[]{16,15,10,5,6,11,12,13,8,3,4,9,14,19,18}));
            list.add(new Bean_Road(4,5,new Integer[]{13,12,7,2,1,6,5,10,11,16,17,18,19,14,9}));
            list.add(new Bean_Road(4,5,new Integer[]{4,9,14,13,12,7,2,1,0,5,6,11,16,15}));
            list.add(new Bean_Road(4,5,new Integer[]{9,8,3,2,7,6,11,10,15,16,17,18,19,14}));
            list.add(new Bean_Road(4,5,new Integer[]{8,9,14,19,18,17,16,15,10,11,12,7,6,1}));
            roadValuesList.add(list);
        }

        {//关卡4
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(5,5,new Integer[]{0,5,10,15,20,21,16,11,6,1,2,3,8,9,14,13,12,17,22,23,18,19,24}));
            list.add(new Bean_Road(5,5,new Integer[]{17,12,13,14,9,4,3,2,7,6,1,0,5,10,11,16,15,20,21,22,23,24,19}));
            list.add(new Bean_Road(5,5,new Integer[]{3,4,9,8,7,2,1,0,5,6,11,10,15,20,21,16,17,12,13,14,19,24,23}));
            list.add(new Bean_Road(5,5,new Integer[]{17,22,23,24,19,18,13,14,9,4,3,2,7,6,1,0,5,10,11,16,15,20}));
            list.add(new Bean_Road(5,5,new Integer[]{10,5,0,1,6,7,2,3,4,9,14,19,24,23,18,13,12,17,16,15,20,21}));
            list.add(new Bean_Road(5,5,new Integer[]{13,12,7,8,9,4,3,2,1,0,5,10,11,16,15,20,21,22,17,18,19,24}));
            list.add(new Bean_Road(5,5,new Integer[]{11,6,1,0,5,10,15,16,21,22,23,18,13,12,7,2,3,8,9,14,19}));
            list.add(new Bean_Road(5,5,new Integer[]{8,9,4,3,2,1,0,5,10,11,6,7,12,13,18,19,24,23,22,21,16}));
            list.add(new Bean_Road(5,5,new Integer[]{6,11,10,5,0,1,2,3,4,9,8,13,14,19,24,23,18,17,22,21,20}));
            list.add(new Bean_Road(5,5,new Integer[]{17,12,11,10,5,0,1,2,3,4,9,14,13,18,19,24,23,22,21,16}));
            list.add(new Bean_Road(5,5,new Integer[]{4,3,8,9,14,19,24,23,22,17,12,7,6,1,0,5,10,15,20,21}));
            list.add(new Bean_Road(5,5,new Integer[]{23,24,19,18,17,22,21,16,15,10,11,6,7,8,9,4,3,2,1,0}));
            list.add(new Bean_Road(5,5,new Integer[]{16,11,6,1,0,5,10,15,20,21,22,17,12,7,8,13,18,23,24}));
            list.add(new Bean_Road(5,5,new Integer[]{14,19,24,23,22,17,16,15,10,5,0,1,6,11,12,13,8,3,4}));
            list.add(new Bean_Road(5,5,new Integer[]{9,14,19,24,23,18,17,22,21,20,15,10,11,6,1,2,7,8,3}));
            list.add(new Bean_Road(5,5,new Integer[]{5,0,1,2,3,4,9,14,13,18,23,22,17,16,11,10,15,20}));
            list.add(new Bean_Road(5,5,new Integer[]{10,5,6,7,12,13,8,3,4,9,14,19,18,23,22,21,20,15}));
            list.add(new Bean_Road(5,5,new Integer[]{5,0,1,6,11,16,21,22,17,12,7,2,3,4,9,14,19,24}));
            list.add(new Bean_Road(5,5,new Integer[]{13,12,7,2,1,0,5,10,15,16,21,22,23,24,19,14,9}));
            list.add(new Bean_Road(5,5,new Integer[]{4,3,8,7,6,11,12,13,14,19,24,23,22,17,16,15,20}));
            list.add(new Bean_Road(5,5,new Integer[]{14,19,18,13,12,17,22,21,20,15,16,11,6,7,8,3,4}));
            list.add(new Bean_Road(5,5,new Integer[]{12,17,18,13,8,3,2,7,6,1,0,5,10,15,20,21}));
            list.add(new Bean_Road(5,5,new Integer[]{14,19,24,23,18,17,12,7,8,3,2,1,6,5,10,11}));
            list.add(new Bean_Road(5,5,new Integer[]{5,6,11,10,15,20,21,22,23,18,17,12,13,8,3,4}));
            list.add(new Bean_Road(5,5,new Integer[]{24,19,18,17,22,21,20,15,10,11,12,7,8,3,4}));
            list.add(new Bean_Road(5,5,new Integer[]{8,7,2,1,0,5,10,15,16,21,22,23,18,17,12}));
            list.add(new Bean_Road(5,5,new Integer[]{3,8,13,14,19,24,23,18,17,22,21,16,15,10,5}));
            list.add(new Bean_Road(5,6,new Integer[]{20,19,13,7,8,9,15,21,22,28,29,23,17,16,10,11,5,4,3,2,1,0,6,12,18,24,25,26}));
            list.add(new Bean_Road(5,6,new Integer[]{17,11,5,4,3,2,1,0,6,12,18,24,25,19,20,21,27,28,29,23,22,16,10,9,15,14,13,7}));
            list.add(new Bean_Road(5,6,new Integer[]{17,11,5,4,10,9,3,2,1,0,6,12,18,24,25,26,20,19,13,7,8,14,15,21,22,28,29,23}));
            roadValuesList.add(list);
        }

        {//关卡5
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(5,6,new Integer[]{6,0,1,7,8,2,3,4,10,16,15,21,20,19,13,12,18,24,25,26,27,28,22,23,17,11,5}));
            list.add(new Bean_Road(5,6,new Integer[]{23,29,28,22,21,27,26,20,14,13,19,25,24,18,12,6,7,1,2,8,9,15,16,17,11,10,4}));
            list.add(new Bean_Road(5,6,new Integer[]{17,11,10,4,3,2,8,9,15,16,22,23,29,28,27,26,20,19,25,24,18,12,13,7,1,0}));
            list.add(new Bean_Road(5,6,new Integer[]{20,14,8,2,1,0,6,12,18,19,25,26,27,21,15,9,3,4,5,11,10,16,17,23,29,28}));
            list.add(new Bean_Road(5,6,new Integer[]{1,0,6,7,13,14,15,9,8,2,3,4,5,11,17,23,29,28,22,21,27,26,20,19,18,24}));
            list.add(new Bean_Road(5,6,new Integer[]{16,22,28,29,23,17,11,5,4,10,9,3,2,8,7,13,14,20,26,25,24,18,12,6,0}));
            list.add(new Bean_Road(5,6,new Integer[]{22,16,15,9,3,4,5,11,17,23,29,28,27,26,20,19,25,24,18,12,6,0,1,2,8}));
            list.add(new Bean_Road(5,6,new Integer[]{14,13,7,6,12,18,24,25,26,27,21,15,9,10,16,22,28,29,23,17,11,5,4,3,2}));
            list.add(new Bean_Road(5,6,new Integer[]{18,12,6,0,1,2,8,7,13,14,20,19,25,26,27,28,22,16,10,4,5,11,17,23}));
            list.add(new Bean_Road(5,6,new Integer[]{20,14,8,7,13,19,18,12,6,0,1,2,3,9,10,4,5,11,17,23,22,28,27,26}));
            list.add(new Bean_Road(5,6,new Integer[]{27,26,20,19,25,24,18,12,6,0,1,2,8,9,3,4,5,11,10,16,17,23,29,28}));
            list.add(new Bean_Road(5,6,new Integer[]{23,17,11,10,9,15,16,22,21,27,26,20,19,25,24,18,12,6,0,1,7,8,2}));
            list.add(new Bean_Road(5,6,new Integer[]{24,18,12,6,0,1,7,13,19,20,21,27,28,29,23,22,16,17,11,10,4,3,2}));
            list.add(new Bean_Road(5,6,new Integer[]{12,6,7,1,2,8,14,13,19,20,21,15,9,10,16,17,23,29,28,27,26,25,24}));
            list.add(new Bean_Road(5,6,new Integer[]{11,17,23,29,28,22,21,20,26,25,24,18,19,13,14,15,9,3,2,1,0,6}));
            list.add(new Bean_Road(5,6,new Integer[]{24,18,19,20,14,13,7,1,2,8,9,15,16,10,4,5,11,17,23,29,28,27}));
            list.add(new Bean_Road(5,6,new Integer[]{24,18,19,25,26,20,14,8,7,1,2,3,4,10,16,22,28,29,23,17,11,5}));
            list.add(new Bean_Road(5,6,new Integer[]{4,5,11,10,9,3,2,1,7,6,12,18,19,25,26,27,28,22,16,15,14}));
            list.add(new Bean_Road(5,6,new Integer[]{22,23,17,16,10,11,5,4,3,9,8,14,20,21,27,26,25,19,13,7,6}));
            list.add(new Bean_Road(5,6,new Integer[]{3,4,5,11,10,9,8,2,1,0,6,7,13,12,18,19,25,26,20,14,15}));
            list.add(new Bean_Road(5,6,new Integer[]{4,5,11,10,9,8,14,13,7,6,12,18,19,25,26,20,21,27,28,29}));
            list.add(new Bean_Road(5,6,new Integer[]{0,6,12,13,7,8,14,20,26,27,28,22,21,15,9,3,4,10,11,5}));
            list.add(new Bean_Road(5,6,new Integer[]{26,27,28,22,21,20,19,13,12,6,0,1,7,8,9,10,16,17,11,5}));
            list.add(new Bean_Road(5,6,new Integer[]{15,14,8,7,1,2,3,4,10,16,17,23,29,28,27,21,20,26,25}));
            list.add(new Bean_Road(5,6,new Integer[]{24,25,19,13,7,1,2,8,9,3,4,5,11,17,16,15,21,27,28}));
            list.add(new Bean_Road(5,6,new Integer[]{17,23,22,28,27,21,15,9,3,2,1,0,6,12,13,14,20,26,25}));
            list.add(new Bean_Road(5,6,new Integer[]{14,8,9,15,16,17,11,5,4,3,2,1,0,6,12,13,19,20}));
            list.add(new Bean_Road(5,6,new Integer[]{29,23,17,16,10,4,3,9,15,21,27,26,20,14,13,7,1,0}));
            list.add(new Bean_Road(5,6,new Integer[]{17,16,22,23,29,28,27,21,15,14,13,12,6,0,1,2,3,4}));
            list.add(new Bean_Road(5,6,new Integer[]{27,26,25,24,18,12,6,0,1,7,8,9,10,11,17,23,22}));
            roadValuesList.add(list);
        }

        {//关卡6
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(6,4,new Integer[]{0,4,8,12,16,20,21,17,13,9,5,6,2,3,7,11,10,14,15,19,18,22}));
            list.add(new Bean_Road(6,4,new Integer[]{4,0,1,5,6,2,3,7,11,10,14,15,19,23,22,21,20,16,17,13,12,8}));
            list.add(new Bean_Road(6,4,new Integer[]{5,1,0,4,8,12,16,20,21,17,13,9,10,14,18,22,23,19,15,11,7,3}));
            list.add(new Bean_Road(6,4,new Integer[]{9,5,1,0,4,8,12,13,14,10,6,2,3,7,11,15,19,18,17,16,20}));
            list.add(new Bean_Road(6,4,new Integer[]{8,12,16,20,21,22,23,19,18,17,13,14,10,9,5,6,7,3,2,1,0}));
            list.add(new Bean_Road(6,4,new Integer[]{4,0,1,5,6,2,3,7,11,15,19,23,22,21,17,18,14,10,9,8,12}));
            list.add(new Bean_Road(6,4,new Integer[]{20,21,17,16,12,8,4,0,1,5,9,10,6,2,3,7,11,15,19,23}));
            list.add(new Bean_Road(6,4,new Integer[]{12,16,20,21,22,23,19,18,17,13,14,10,9,8,4,5,1,2,6,7}));
            list.add(new Bean_Road(6,4,new Integer[]{10,6,5,1,0,4,8,12,16,17,21,22,23,19,18,14,15,11,7,3}));
            list.add(new Bean_Road(6,4,new Integer[]{8,4,5,9,10,6,2,3,7,11,15,19,18,14,13,12,16,20,21}));
            list.add(new Bean_Road(6,4,new Integer[]{3,2,1,5,6,7,11,15,14,13,12,16,20,21,17,18,22,23,19}));
            list.add(new Bean_Road(6,4,new Integer[]{18,17,16,20,21,22,23,19,15,11,10,6,2,1,0,4,8,9,5}));
            list.add(new Bean_Road(6,4,new Integer[]{2,1,0,4,8,12,16,20,21,22,18,19,15,11,10,6,7,3}));
            list.add(new Bean_Road(6,4,new Integer[]{20,21,22,23,19,18,17,16,12,8,9,10,11,7,6,2,1,0}));
            list.add(new Bean_Road(6,4,new Integer[]{8,12,13,9,5,4,0,1,2,6,7,11,15,19,18,17,16,20}));
            list.add(new Bean_Road(6,4,new Integer[]{4,8,12,13,9,5,6,2,3,7,11,15,19,18,17,21,20}));
            list.add(new Bean_Road(6,4,new Integer[]{0,1,5,9,13,17,21,22,23,19,18,14,10,6,2,3,7}));
            list.add(new Bean_Road(6,4,new Integer[]{14,18,22,23,19,15,11,10,9,13,12,8,4,0,1,2,3}));
            list.add(new Bean_Road(6,4,new Integer[]{5,1,2,3,7,6,10,11,15,19,18,22,21,20,16,12}));
            list.add(new Bean_Road(6,4,new Integer[]{12,8,9,13,17,21,22,18,19,15,11,10,6,5,1,0}));
            list.add(new Bean_Road(6,4,new Integer[]{18,22,21,17,16,12,8,9,13,14,10,6,7,3,2,1}));
            list.add(new Bean_Road(6,4,new Integer[]{14,18,19,23,22,21,20,16,12,8,9,5,1,2,3}));
            list.add(new Bean_Road(6,4,new Integer[]{11,7,6,5,1,0,4,8,9,13,17,18,22,23,19}));
            list.add(new Bean_Road(6,4,new Integer[]{10,9,5,4,0,1,2,3,7,11,15,14,18,19,23}));
            list.add(new Bean_Road(6,4,new Integer[]{3,7,6,2,1,5,9,8,12,16,17,13,14,18}));
            list.add(new Bean_Road(6,4,new Integer[]{21,22,23,19,15,14,13,9,10,6,5,1,0,4}));
            list.add(new Bean_Road(6,4,new Integer[]{11,15,14,10,6,5,4,8,9,13,12,16,17,21}));
            list.add(new Bean_Road(6,5,new Integer[]{21,16,15,20,25,26,27,22,17,18,23,28,29,24,19,14,13,8,7,12,11,6,5,0,1,2,3,4}));
            list.add(new Bean_Road(6,5,new Integer[]{12,7,2,1,6,5,10,15,20,25,26,21,22,27,28,29,24,19,14,9,4,3,8,13,18,17,16,11}));
            list.add(new Bean_Road(6,5,new Integer[]{6,1,0,5,10,15,20,25,26,21,16,11,12,7,2,3,4,9,8,13,14,19,24,23,18,17,22,27}));
            roadValuesList.add(list);
        }

        {//关卡7
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(6,5,new Integer[]{18,17,12,7,6,1,2,3,4,9,8,13,14,19,24,29,28,23,22,27,26,25,20,21,16,15,10}));
            list.add(new Bean_Road(6,5,new Integer[]{25,20,21,26,27,22,17,16,11,10,5,0,1,6,7,2,3,4,9,8,13,14,19,18,23,28,29}));
            list.add(new Bean_Road(6,5,new Integer[]{3,4,9,14,13,18,17,16,11,6,7,2,1,0,5,10,15,20,25,26,27,22,23,28,29,24,19}));
            list.add(new Bean_Road(6,5,new Integer[]{27,22,21,26,25,20,15,16,11,10,5,6,1,2,3,4,9,8,7,12,13,18,19,24,23,28}));
            list.add(new Bean_Road(6,5,new Integer[]{24,19,14,9,4,3,2,1,0,5,6,7,8,13,18,17,16,15,20,25,26,21,22,27,28,23}));
            list.add(new Bean_Road(6,5,new Integer[]{9,8,3,2,7,6,1,0,5,10,11,16,15,20,21,26,27,22,23,18,13,14,19,24,29,28}));
            list.add(new Bean_Road(6,5,new Integer[]{27,26,25,20,21,16,15,10,5,0,1,6,11,12,13,18,23,28,29,24,19,14,9,4,3}));
            list.add(new Bean_Road(6,5,new Integer[]{26,27,28,29,24,19,14,13,18,23,22,21,20,15,16,11,10,5,0,1,6,7,8,3,4}));
            list.add(new Bean_Road(6,5,new Integer[]{1,6,5,10,11,16,21,20,25,26,27,28,29,24,23,18,13,14,9,4,3,2,7,12,17}));
            list.add(new Bean_Road(6,5,new Integer[]{0,5,10,11,12,7,2,3,4,9,8,13,14,19,24,23,28,27,26,25,20,21,22,17}));
            list.add(new Bean_Road(6,5,new Integer[]{22,27,28,29,24,23,18,13,8,9,4,3,2,7,6,5,10,11,12,17,16,21,20,25}));
            list.add(new Bean_Road(6,5,new Integer[]{17,12,11,6,1,0,5,10,15,16,21,26,27,22,23,28,29,24,19,18,13,8,9,4}));
            list.add(new Bean_Road(6,5,new Integer[]{1,0,5,10,15,16,11,6,7,8,3,4,9,14,19,24,23,22,21,26,27,28,29}));
            list.add(new Bean_Road(6,5,new Integer[]{15,10,5,0,1,6,7,2,3,8,9,14,13,18,19,24,23,28,27,22,21,26,25}));
            list.add(new Bean_Road(6,5,new Integer[]{28,29,24,23,22,27,26,21,20,15,10,5,6,11,12,17,18,19,14,13,8,3,2}));
            list.add(new Bean_Road(6,5,new Integer[]{25,26,21,22,23,24,19,18,17,12,13,8,7,6,11,10,5,0,1,2,3,4}));
            list.add(new Bean_Road(6,5,new Integer[]{28,23,18,19,14,13,12,17,22,27,26,21,20,15,10,11,6,5,0,1,2,3}));
            list.add(new Bean_Road(6,5,new Integer[]{13,18,19,24,29,28,23,22,27,26,25,20,15,10,11,6,1,2,7,8,9,4}));
            list.add(new Bean_Road(6,5,new Integer[]{23,28,29,24,19,18,17,12,11,16,21,20,15,10,5,0,1,6,7,8,9}));
            list.add(new Bean_Road(6,5,new Integer[]{12,17,22,23,28,27,26,21,20,15,10,5,6,1,2,3,8,13,14,9,4}));
            list.add(new Bean_Road(6,5,new Integer[]{6,1,2,7,8,13,18,17,16,11,10,15,20,21,22,27,28,29,24,19,14}));
            list.add(new Bean_Road(6,5,new Integer[]{12,13,8,7,2,1,6,5,10,15,16,21,22,23,24,29,28,27,26,25}));
            list.add(new Bean_Road(6,5,new Integer[]{10,15,16,11,6,7,8,9,14,13,18,19,24,23,28,27,22,21,26,25}));
            list.add(new Bean_Road(6,5,new Integer[]{15,10,5,0,1,2,7,12,17,22,21,20,25,26,27,28,29,24,19,18}));
            list.add(new Bean_Road(6,5,new Integer[]{1,0,5,10,15,20,25,26,27,22,17,18,19,14,9,8,7,12,11}));
            list.add(new Bean_Road(6,5,new Integer[]{3,4,9,14,13,12,7,6,5,10,15,20,21,26,27,22,17,18,23}));
            list.add(new Bean_Road(6,5,new Integer[]{24,19,18,17,16,21,20,15,10,11,6,5,0,1,2,7,8,3,4}));
            list.add(new Bean_Road(6,5,new Integer[]{6,5,10,15,16,11,12,7,8,13,18,17,22,27,28,23,24,29}));
            list.add(new Bean_Road(6,5,new Integer[]{4,9,14,13,12,17,18,23,28,27,22,21,20,15,16,11,6,5}));
            list.add(new Bean_Road(6,5,new Integer[]{16,15,10,11,6,7,8,3,4,9,14,19,18,23,28,27,22,21}));
            roadValuesList.add(list);
        }

        {//关卡8
            List<Bean_Road> list = new CopyOnWriteArrayList<>();
            list.add(new Bean_Road(6,6,new Integer[]{29,23,17,11,5,4,3,2,1,0,6,12,18,19,13,7,8,14,15,16,22,28,34,33,32,26,25,31,30}));
            list.add(new Bean_Road(6,6,new Integer[]{18,24,30,31,25,19,20,14,8,9,15,16,22,28,27,33,34,35,29,23,17,11,5,4,3,2,1,0,6}));
            list.add(new Bean_Road(6,6,new Integer[]{13,12,6,0,1,7,8,2,3,9,15,14,20,19,25,24,30,31,32,33,27,21,22,16,17,23,29,35,34}));
            list.add(new Bean_Road(6,6,new Integer[]{2,8,7,6,12,13,19,20,26,25,24,30,31,32,33,34,28,29,23,17,11,10,16,22,21,15,9,3}));
            list.add(new Bean_Road(6,6,new Integer[]{35,34,33,27,28,22,23,17,11,5,4,3,2,1,0,6,7,13,12,18,24,30,31,25,19,20,14,15}));
            list.add(new Bean_Road(6,6,new Integer[]{3,9,8,7,1,0,6,12,13,19,25,24,30,31,32,26,27,21,22,16,10,11,17,23,29,28,34,35}));
            list.add(new Bean_Road(6,6,new Integer[]{18,19,25,26,32,33,34,28,29,23,17,11,5,4,3,2,8,9,10,16,15,21,20,14,13,12,6}));
            list.add(new Bean_Road(6,6,new Integer[]{4,10,11,17,16,22,23,29,35,34,33,32,26,27,21,20,14,8,2,1,7,6,12,18,24,25,19}));
            list.add(new Bean_Road(6,6,new Integer[]{20,14,8,2,1,0,6,12,18,24,25,26,32,33,27,28,22,16,10,4,5,11,17,23,29,35,34}));
            list.add(new Bean_Road(6,6,new Integer[]{33,27,28,34,35,29,23,22,16,17,11,10,9,3,2,8,14,13,19,18,24,30,31,25,26,32}));
            list.add(new Bean_Road(6,6,new Integer[]{28,29,23,22,21,27,33,32,26,20,14,15,9,10,11,5,4,3,2,1,0,6,7,13,19,18}));
            list.add(new Bean_Road(6,6,new Integer[]{10,4,3,2,8,7,1,0,6,12,13,19,25,24,30,31,32,33,27,21,15,16,17,23,29,35}));
            list.add(new Bean_Road(6,6,new Integer[]{3,4,10,11,17,16,15,14,8,2,1,7,6,12,13,19,18,24,25,26,32,33,27,28,29}));
            list.add(new Bean_Road(6,6,new Integer[]{18,12,6,0,1,7,8,2,3,4,10,9,15,14,13,19,25,31,32,33,34,35,29,28,22}));
            list.add(new Bean_Road(6,6,new Integer[]{29,35,34,28,27,21,15,16,17,11,5,4,3,2,1,7,6,12,18,24,30,31,25,19,13}));
            list.add(new Bean_Road(6,6,new Integer[]{25,31,32,26,20,14,8,7,6,0,1,2,3,4,5,11,17,16,22,21,27,28,34,35}));
            list.add(new Bean_Road(6,6,new Integer[]{9,15,16,17,23,29,35,34,33,27,26,20,19,25,31,30,24,18,12,13,14,8,2,1}));
            list.add(new Bean_Road(6,6,new Integer[]{34,33,27,21,20,26,25,31,30,24,18,12,6,0,1,7,13,14,8,9,10,11,17,23}));
            list.add(new Bean_Road(6,6,new Integer[]{32,26,25,19,20,14,8,7,1,2,3,9,15,16,10,11,17,23,29,35,34,28,27}));
            list.add(new Bean_Road(6,6,new Integer[]{4,5,11,17,23,29,28,27,33,32,26,25,24,18,19,20,21,15,14,8,7,1,0}));
            list.add(new Bean_Road(6,6,new Integer[]{21,15,9,10,16,22,23,17,11,5,4,3,2,1,7,6,12,18,24,25,19,13,14}));
            list.add(new Bean_Road(6,6,new Integer[]{0,1,2,8,14,13,12,18,24,25,26,32,33,34,35,29,28,22,16,10,4,5}));
            list.add(new Bean_Road(6,6,new Integer[]{1,0,6,7,8,2,3,9,15,14,20,19,18,24,25,31,32,26,27,21,22,16}));
            list.add(new Bean_Road(6,6,new Integer[]{30,24,18,19,25,31,32,33,34,35,29,28,27,21,22,16,15,14,8,7,6,0}));
            list.add(new Bean_Road(6,6,new Integer[]{30,24,18,12,13,19,25,26,27,28,34,35,29,23,22,21,15,14,8,2,1}));
            list.add(new Bean_Road(6,6,new Integer[]{9,15,21,20,26,27,28,34,33,32,31,25,24,18,12,13,14,8,2,1,0}));
            list.add(new Bean_Road(6,6,new Integer[]{5,4,10,16,17,23,22,21,27,26,20,19,25,31,30,24,18,12,6,7,8}));
            list.add(new Bean_Road(6,6,new Integer[]{25,19,20,21,27,28,22,23,17,11,10,16,15,9,3,2,1,0,6,12}));
            list.add(new Bean_Road(6,6,new Integer[]{27,28,34,33,32,31,30,24,18,19,13,14,20,21,15,16,17,11,5,4}));
            list.add(new Bean_Road(6,6,new Integer[]{4,5,11,10,9,15,16,22,28,34,33,27,26,20,14,13,12,6,0,1}));
            roadValuesList.add(list);
        }
    }

}
