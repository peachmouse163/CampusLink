package com.example.campuslink.link;

import android.util.Log;

import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.NewsModel;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.QuesToAnModel;
import com.example.campuslink.model.SignModel;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.model.User;
import com.example.campuslink.model.VoluModel;

import java.util.ArrayList;
import java.util.List;

public class LinkToData {

    private static final String TAG = "LinkToData";

    public static ArrayList<Preview> getTrans(String string){
        ArrayList<Preview> tranModels = new ArrayList<>();

        if (string != null){
            String[] bigString = string.split("~");

            for (String midString:
                    bigString) {
                String[] mString = midString.split(";");
                if (mString.length>1){
                    //String tranId, String infoNo,String tranMoney, String tranPlace,  String tranDatetime, String tranPic,String tranTitle, String tranContent, String tranState
                    TranModel model = new TranModel(mString[0],mString[1],mString[2],mString[3],mString[4],mString[5],mString[6],mString[7],mString[8]);
                    tranModels.add(model);
                }
            }
        }

        return tranModels;
    }

    public static ArrayList<QuesToAnModel> getQuestions(String string){
        ArrayList<QuesToAnModel> quesModels = new ArrayList<>();

        if (string != null){
            String[] bigString = string.split("~");

            for (String midString:
                    bigString) {
                String[] mString = midString.split(";");
                if (mString.length>1){
                    QuesToAnModel model = new QuesToAnModel(mString[0],mString[1],mString[2],mString[3],mString[4],mString[5],mString[6],mString[7],mString[8]);
                    quesModels.add(model);
                }
            }
        }

        return quesModels;
    }

    public static User getUser(String string){
        User user = new User();
        if (string!=null){
            String[] strings = string.split(";");
            user.setInfoNo(Integer.parseInt(strings[0]));
            user.setInfoName(strings[1]);
            user.setInfoPhone(strings[2]);
            user.setInfoDepartment(strings[3]);
            user.setInfoZy(strings[4]);
            user.setInfoYear(strings[5]);
            user.setInfoClass(strings[6]);
            user.setInfoRefresh(strings[7]);
            user.setInfoPassword(strings[8]);
            LoginViewModel.user.setInfoIdentity(Integer.parseInt(strings[9]));

            //仅logcat检查登录账户数据
            for (String s :
                    strings) {
                Log.d("LinkToData", "getUser: "+s);
            }
        }else {
            Log.d("LinkToData", "getUser: string == null");
        }
        return user;
    }

    public static ArrayList<NewsModel> getNews(String string){
        ArrayList<NewsModel> newsModels = new ArrayList<>();
        if (string != null){
            String[] bigString = string.split("-");

            for (String midString:
                    bigString) {
                Log.d(TAG, "getNewsBigString: "+midString);
                String[] mString = midString.split(";");
                for (String s :
                        mString) {
                    Log.d(TAG, "getNewsMString: "+s);
                }
                NewsModel newsModel = new NewsModel(mString[0],mString[1],mString[2],mString[3],mString[4],mString[5],mString[6]);
                newsModels.add(newsModel);
            }
        }

        return newsModels;
    }

    public static ArrayList<Preview> getThin(String string){
        ArrayList<Preview> thinModels = new ArrayList<>();
        if (string != null){
            String[] bigString = string.split("~");

            for (String midString:
                    bigString) {

                String[] mString = midString.split(";");

                if (mString.length == 10){
                    //6;食堂;null;门禁卡丢了;2024-03-22 11:20:00;12001260;0;;在食堂丢失了手表;0
                    //thinNo,thinTitle,thinDate,infoNo,thinAttribute,thinPlace,thinPhone,thinPic,thinContent,thinState

                    ThinModel thinModel = new ThinModel(mString[0],mString[1],mString[2],mString[3],mString[4],mString[5],mString[6],mString[7],mString[8],mString[9]);
                    thinModels.add(thinModel);
                }else
                    Log.d(TAG, "getThin: 有数据分解错误！");

            }
        }

        return thinModels;
    }

    public static ArrayList<Preview> getVolu(String string){
        ArrayList<Preview> voluModels = new ArrayList<>();
        if (string != null){
            String[] bigString = string.split("~");

            for (String midString:
                    bigString) {

                String[] mString = midString.split(";");
                for (String s :
                        mString) {
                    Log.d(TAG, "getVolu: "+s);
                }
                if (mString.length == 12){
                    VoluModel voluModel = new VoluModel(mString[0],mString[1],mString[2],mString[3],mString[4],mString[5],mString[6],mString[7],mString[8],mString[9],mString[10],mString[11]);
                    voluModels.add(voluModel);
                }else
                    Log.d(TAG, "getVolu: 有数据分解错误！");

            }
        }

        return voluModels;
    }

    public static ArrayList<CommunicationModel> getCom(String string){
        ArrayList<CommunicationModel> comModels = new ArrayList<>();

        if (string != null){
            String[] bigString = string.split("~");

            for (String midString:
                    bigString) {

                String[] mString = midString.split(";");

                if (mString.length == 5){
                    CommunicationModel comModel = new CommunicationModel(mString[0],mString[1],mString[2],mString[3],mString[4]);
                    comModels.add(comModel);
                }else
                    Log.d(TAG, "getCom: 有数据分解错误！");

            }
        }

        return comModels;
    }

}
