package com.graction.developer.zoocaster.Method;

import android.util.Log;
import android.view.View;

import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JeongTaehyun on 2018-03-14.
 */

/*
 * Adapter 별 공통 메소드 관리
 */

public class CommonMethodManager {
    private static final CommonMethodManager instance = new CommonMethodManager();

    private CommonMethodManager() {
    }

    public static CommonMethodManager getInstance() {
        return instance;
    }

    public void favoriteRemove(String address) {
        DataBaseStorage.dataBaseHelper.delete(DataBaseStorage.Table.TABLE_FAVORITE
                , DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS + "=?"
                , new String[]{address});
    }

    public void favoriteAdd(FavoriteTable favoriteTable) {
        DataBaseStorage.dataBaseHelper.insert(DataBaseStorage.Table.TABLE_FAVORITE, favoriteTable);
    }
}
