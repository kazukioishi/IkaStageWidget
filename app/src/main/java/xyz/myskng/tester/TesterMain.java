package xyz.myskng.tester;

import xyz.myskng.ikastagewidget.api.StageInfo;
import xyz.myskng.ikastagewidget.model.GachiStage;
import xyz.myskng.ikastagewidget.model.Stage;

import java.util.ArrayList;

public class TesterMain {
    public static void main(String args[]){
        Stage stg = StageInfo.GetRegularStageList();
        System.out.println("レギュラーステージ");
        System.out.println(stg.getMaps()[0]);
        System.out.println(stg.getMaps()[1]);
        System.out.println("ガチマッチステージ");
        Stage stg2 = StageInfo.GetGachiStageList();
        System.out.println(stg2.getMaps()[0]);
        System.out.println(stg2.getMaps()[1]);
        ArrayList<Stage> ar = StageInfo.GetNextRegularStageList();
        ArrayList<GachiStage> ar2 = StageInfo.GetNextGachiStageList();
        System.out.println("complete.");
    }
}
