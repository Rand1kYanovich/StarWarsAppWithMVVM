package com.example.starwarsappwithmvvm.util

import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

class ListUtil {

    companion object {
        fun equalsObjects(changeableList: ArrayList<FullInfoCard>, valueList: ArrayList<FullInfoCard>):ArrayList<FullInfoCard> {
            valueList.removeAll(changeableList)
            for (i in 0 until changeableList.size)
                for (j in 0 until valueList.size)
                    if (changeableList[i].name == valueList[j].name) changeableList[i] = valueList[j]
            return changeableList


        }
    }
}