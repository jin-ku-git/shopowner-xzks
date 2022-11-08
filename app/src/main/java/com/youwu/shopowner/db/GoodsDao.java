package com.youwu.shopowner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.youwu.shopowner.ui.fragment.bean.CommunityBean;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * Created by lzzie
 * Date: 2018-8-10 17:33:43
 */
public class GoodsDao {
    public static final String TAG = "GoodDao";
    // 列定义  id integer primary key,p_name text,bar_code text,p_price text,vip_price text,p_weight integer

    //后台给的原始数据
//    private String image;
//    private String category_id;
//    private String id;
//    private String product_name;
//    private String letters;
//    private List<CommunityBean.SpecsBean> specs;


    private final String[] GOODS_COLUMNS = new String[]{"goods_id_sku","goods_id", "goods_sku", "goods_name",
            "goods_price", "goods_cost_price", "goods_img", "stock", "group_id", "group_name"
            , "group_sort", "group_img", "goods_number", "com_number_state","details","package_id","type","market_price","status"};


    private Context context;
    private DBHelper goodsDBHelper;

    public GoodsDao(Context context) {
        this.context = context;
        goodsDBHelper = new DBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist() {
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = goodsDBHelper.getReadableDatabase();
            // select count(Id) from Goods
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, new String[]{"COUNT(goods_id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    int key = 0;



    /**
     * 初始化数据
     */
    public synchronized void initTable(List<CommunityBean> goodsBeans) {
        if (null == goodsBeans || goodsBeans.isEmpty()) {
            return;
        }

        SQLiteDatabase db = null;


        try {
            db = goodsDBHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < goodsBeans.size(); i++) {
                key = i;
                CommunityBean goodsBean = goodsBeans.get(i);
                if (null != goodsBean) {


                    ContentValues contentValues = new ContentValues();

                    if (goodsBean.getType()==1){//商品
                        contentValues.put("goods_id_sku", goodsBean.getGoods_sku());
                    }else {//套餐
                        contentValues.put("goods_id_sku", goodsBean.getPackage_id()+"");
                    }

                    contentValues.put("goods_id", goodsBean.getGoods_id());
                    contentValues.put("goods_sku", goodsBean.getGoods_sku());
                    contentValues.put("goods_name", goodsBean.getGoods_name());
                    contentValues.put("goods_price", goodsBean.getGoods_price());
                    contentValues.put("goods_cost_price", goodsBean.getGoods_cost_price());
                    contentValues.put("goods_img", goodsBean.getGoods_img());
                    contentValues.put("stock", goodsBean.getStock());
                    contentValues.put("group_id", goodsBean.getGroup_id());
                    contentValues.put("group_name", goodsBean.getGroup_name());
                    contentValues.put("group_sort", goodsBean.getGroup_sort());
                    contentValues.put("group_img", goodsBean.getGroup_img());
                    contentValues.put("goods_number", goodsBean.getGoods_number());
                    contentValues.put("com_number_state", goodsBean.getCom_number_state());
                    contentValues.put("details", goodsBean.getDetails());
                    contentValues.put("package_id", goodsBean.getPackage_id());
                    contentValues.put("type", goodsBean.getType());
                    contentValues.put("market_price", goodsBean.getMarket_price());
                    contentValues.put("status", goodsBean.getStatus());


                    db.insert(goodsDBHelper.GOODS_TABLE_NAME, null, contentValues);

//                    db.execSQL("insert into " +  goodsDBHelper.GOODS_TABLE_NAME + " (Id, GoodsName, Repertory, Price,ImgUrl,Type) values (" + gId + "," + gName +","+ gRepertory + "," + gPrice + "," + gImg + "," + gType + ")");
                }
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {

            Log.e(TAG, "---", e);
        } finally {


            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 获取商品表的大小
     */

    public int getAllGoodsNum() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = goodsDBHelper.getReadableDatabase();
            // select * from Goods
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, null, null, null, null, null);
            return cursor.getCount();
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return 0;
    }

    /**
     * 查询Goods数据库中所有数据
     */
    public List<CommunityBean> getAllDate() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = goodsDBHelper.getReadableDatabase();
            // select * from Goods
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                List<CommunityBean> goodList = new ArrayList<CommunityBean>(cursor.getCount());
                while (cursor.moveToNext()) {
                    goodList.add(parseGoodS(cursor));
                }
                return goodList;
            }
        } catch (Exception e) {
            Log.e(TAG, "getAllData--exception", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }


    /**
     * 根据sku  查询Goods 详情
     */
    public CommunityBean getGoodInfoByGoodSku(String sku) {
        if (TextUtils.isEmpty(sku)) {
            return null;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "sku = ?", new String[]{sku}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (cursor.getCount() > 0) {
                Log.e(TAG, "cursor  count  " + cursor.getCount());
                if (cursor.moveToNext()) {
//                    Log.d("搜索到的商品信息", parseGoodS(cursor).getProduct_name());
                    return parseGoodS(cursor);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

//    public MealsItemBean getMealInfoBymealSku(String sku) {
//        if (TextUtils.isEmpty(sku)) {
//            return null;
//        }
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//        try {
//            db = goodsDBHelper.getReadableDatabase();
//            cursor = db.query(goodsDBHelper.MEAL_TABLE_NAME, MEAL_COLUMNS, "meal_goods_sku = ?", new String[]{sku}, null, null, null);
//            Log.e(TAG, "cursor  " + cursor);
//            if (cursor.getCount() > 0) {
//                Log.e(TAG, "cursor  count  " + cursor.getCount());
//                if (cursor.moveToNext()) {
//                    return paseMealItem(cursor);
//                }
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "", e);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//
//        return null;
//    }



    /**
     * 根据group_id 根据商品类别
     *
     * @return
     */
    public List<CommunityBean> getGoodListByGroupId(String group_id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<CommunityBean> ywGoodBeanList = new ArrayList<>();
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "group_id = ?",
                    new String[]{group_id}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (cursor.getCount() > 0) {
                Log.e("daodata", "cursor  count  " + cursor.getCount());
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    if (cursor.moveToNext()) {
//                        Log.e("11111", i + "--moveToNext:" + JsonHelper.toJSON(parseGoodS(cursor)));
//                        ywGoodBeanList.add(parseGoodS(cursor));
//                    }
//                }

                while (cursor != null && cursor.moveToNext()) {
                    ywGoodBeanList.add(parseGoodS(cursor));
                }
            }


            return ywGoodBeanList;
        } catch (Exception e) {


            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return ywGoodBeanList;
    }

    /**
     * 根据category_id 根据商品类别
     *
     * @return
     */
    public List<CommunityBean> getGoodListByCategoryId(String category_id) {
        KLog.d("category_id:"+category_id);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<CommunityBean> ywGoodBeanList = new ArrayList<>();
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "category_id = ?",
                    new String[]{category_id}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (cursor.getCount() > 0) {
                Log.e("daodata", "cursor  count  " + cursor.getCount());
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    if (cursor.moveToNext()) {
//                        Log.e("11111", i + "--moveToNext:" + JsonHelper.toJSON(parseGoodS(cursor)));
//                        ywGoodBeanList.add(parseGoodS(cursor));
//                    }
//                }

                while (cursor != null && cursor.moveToNext()) {
                    ywGoodBeanList.add(parseGoodS(cursor));
                }
            }


            return ywGoodBeanList;
        } catch (Exception e) {


            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return ywGoodBeanList;
    }



    /**
     * 修改商品对应的套餐sku
     *
     * @param sku
     * @return
     */
    public boolean upGoodsMeals(String sku, List<CommunityBean> goodList) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {

            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "meals_sku = ?", new String[]{sku}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);

            if (null != cursor && cursor.getCount() > 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("meals_sku", "");
                contentValues.put("meals_num", 0);
                db.update(goodsDBHelper.GOODS_TABLE_NAME, contentValues,
                        "meals_sku = ?",
                        new String[]{sku});
//                db.delete(goodsDBHelper.GOODS_TABLE_NAME,"good_id = ?",new String[]{goodBean.getGood_id()});
            }
            for (int i = 0; i < goodList.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("meals_sku", sku);
                contentValues.put("meals_num", goodList.get(i).getGoods_number());
                db.update(goodsDBHelper.GOODS_TABLE_NAME, contentValues,
                        "id = ?",
                        new String[]{goodList.get(i).getGoods_id()+""});
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 修改商品对应的数量
     *
     * @param goods_id
     * @return
     */
    public boolean upGoodsNumber(int goods_id,int Number) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {

            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "goods_id = ?", new String[]{goods_id+""}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);

            if (null != cursor && cursor.getCount() > 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("stock",Number);

                db.update(goodsDBHelper.GOODS_TABLE_NAME, contentValues,
                        "goods_id = ?",
                        new String[]{goods_id+""});
//                db.delete(goodsDBHelper.GOODS_TABLE_NAME,"good_id = ?",new String[]{goodBean.getGood_id()});
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean upGoodsInventory(CommunityBean goodsBean) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {

            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "goods_id = ?", new String[]{goodsBean.getGoods_id()+""}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (null != cursor && cursor.getCount() > 0) {
                ContentValues contentValues = new ContentValues();
//                contentValues.put("inventory", goodsBean.getInventory());
                db.update(goodsDBHelper.GOODS_TABLE_NAME, contentValues,
                        "goods_id = ?",
                        new String[]{goodsBean.getGoods_id()+""});
//                db.delete(goodsDBHelper.GOODS_TABLE_NAME,"good_id = ?",new String[]{goodBean.getGood_id()});
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public boolean updateIfNotExistById(CommunityBean goodsBean) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {

            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "goods_id = ?", new String[]{goodsBean.getGoods_id()+""}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (null != cursor && cursor.getCount() > 0) {
                ContentValues contentValues = new ContentValues();

                contentValues.put("goods_id_sku", goodsBean.getGoods_id_sku());
                contentValues.put("goods_id", goodsBean.getGoods_id());
                contentValues.put("goods_sku", goodsBean.getGoods_sku());
                contentValues.put("goods_name", goodsBean.getGoods_name());
                contentValues.put("goods_price", goodsBean.getGoods_price());
                contentValues.put("goods_cost_price", goodsBean.getGoods_cost_price());
                contentValues.put("goods_img", goodsBean.getGoods_img());
                contentValues.put("stock", goodsBean.getStock());
                contentValues.put("group_id", goodsBean.getGroup_id());
                contentValues.put("group_name", goodsBean.getGroup_name());
                contentValues.put("group_sort", goodsBean.getGroup_sort());
                contentValues.put("group_img", goodsBean.getGroup_img());

                db.update(goodsDBHelper.GOODS_TABLE_NAME, contentValues,
                        "goods_id_sku = ?",
                        new String[]{goodsBean.getGoods_id_sku()+""});
//                db.delete(goodsDBHelper.GOODS_TABLE_NAME,"good_id = ?",new String[]{goodBean.getGood_id()});
                return true;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("goods_id_sku", goodsBean.getGoods_id_sku());
                contentValues.put("goods_id", goodsBean.getGoods_id());
                contentValues.put("goods_sku", goodsBean.getGoods_sku());
                contentValues.put("goods_name", goodsBean.getGoods_name());
                contentValues.put("goods_price", goodsBean.getGoods_price());
                contentValues.put("goods_cost_price", goodsBean.getGoods_cost_price());
                contentValues.put("goods_img", goodsBean.getGoods_img());
                contentValues.put("stock", goodsBean.getStock());
                contentValues.put("group_id", goodsBean.getGroup_id());
                contentValues.put("group_name", goodsBean.getGroup_name());
                contentValues.put("group_sort", goodsBean.getGroup_sort());
                contentValues.put("group_img", goodsBean.getGroup_img());

                db.insert(goodsDBHelper.GOODS_TABLE_NAME, null, contentValues);//执行插入操作
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<CommunityBean> getGoodListByMealSku(String mealsku) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<CommunityBean> ywGoodBeanList = new ArrayList<>();
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(DBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS,
                    "meals_sku = ?",
                    new String[]{mealsku}, null, null, null);
//            Log.e(TAG,"cursor  "+cursor);
            if (cursor.getCount() > 0) {
                Log.e("daodata", "cursor  count  " + cursor.getCount());
                for (int i = 0; i < cursor.getCount(); i++) {
                    if (cursor.moveToNext()) {
                        ywGoodBeanList.add(parseGoodS(cursor));
                    }
                }
            }
            return ywGoodBeanList;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return ywGoodBeanList;
    }


    /**
     * 根据good  name 模糊查询商品数据
     *
     * @return
     */
    public List<CommunityBean> getGoodListByPinYin(String goodInfo) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<CommunityBean> ywGoodBeanList = new ArrayList<>();
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(DBHelper.GOODS_TABLE_NAME, new String[]{GOODS_COLUMNS[0], GOODS_COLUMNS[1],
                            GOODS_COLUMNS[2], GOODS_COLUMNS[3], GOODS_COLUMNS[4], GOODS_COLUMNS[5], GOODS_COLUMNS[6], GOODS_COLUMNS[7],
                            GOODS_COLUMNS[8], GOODS_COLUMNS[9], GOODS_COLUMNS[10]},
                    "letters LIKE ?",
                    new String[]{"%" + goodInfo + "%"}, null, null, null);
//            Log.e(TAG,"cursor  "+cursor);
            if (cursor.getCount() > 0) {
                Log.e("daodata", "cursor  count  " + cursor.getCount());
                for (int i = 0; i < cursor.getCount(); i++) {
                    if (cursor.moveToNext()) {
                        ywGoodBeanList.add(parseGoodS(cursor));
                    }
                }
            }
            return ywGoodBeanList;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return ywGoodBeanList;
    }

    /**
     * 根据条形码  查询Goods 详情
     */
    public CommunityBean getGoodInfoByGoodCode(String goodCode) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "sku = ?", new String[]{goodCode}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (cursor.getCount() > 0) {
                Log.e("daodata", "cursor  count  " + cursor.getCount());
                if (cursor.moveToNext()) {
                    return parseGoodS(cursor);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    public boolean deleteIfExistByGoodId(String goodId) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = goodsDBHelper.getReadableDatabase();
            cursor = db.query(goodsDBHelper.GOODS_TABLE_NAME, GOODS_COLUMNS, "good_id = ?", new String[]{goodId}, null, null, null);
            Log.e(TAG, "cursor  " + cursor);
            if (null != cursor && cursor.getCount() > 0) {
                db.delete(goodsDBHelper.GOODS_TABLE_NAME, "good_id = ?", new String[]{goodId});
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }


    /**
     * 将查找到的数据转换成Order类
     * {"id", "category_id", "image", "product_name", "letters", "specs"};
     */
    private CommunityBean parseGoodS(Cursor cursor) {
        CommunityBean goods = new CommunityBean();
        goods.setGoods_id_sku(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[0])));
        goods.setGoods_id(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[1])));
        goods.setGoods_sku(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[2])));
        goods.setGoods_name(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[3])));
        goods.setGoods_price(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[4])));
        goods.setGoods_cost_price(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[5])));
        goods.setGoods_img(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[6])));
        goods.setStock(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[7])));
        goods.setGroup_id(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[8])));
        goods.setGroup_name(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[9])));
        goods.setGroup_sort(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[10])));
        goods.setGroup_img(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[11])));
        goods.setGoods_number(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[12])));
        goods.setCom_number_state(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[13])));
        goods.setDetails(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[14])));
        goods.setPackage_id(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[15])));
        goods.setType(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[16])));
        goods.setMarket_price(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[17])));
        goods.setStatus(cursor.getInt(cursor.getColumnIndex(GOODS_COLUMNS[18])));



//        goods.setSpecs(MyApplication.gson.fromJson(cursor.getString(cursor.getColumnIndex(GOODS_COLUMNS[5])), new TypeToken<List<CommunityBean.SpecsBean>>() {
//        }.getType()));
        return goods;
    }

    public void deleteAllData() {
        SQLiteDatabase db = null;

        db = goodsDBHelper.getReadableDatabase();
        try {
            db.execSQL("delete from " + goodsDBHelper.GOODS_TABLE_NAME);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}

