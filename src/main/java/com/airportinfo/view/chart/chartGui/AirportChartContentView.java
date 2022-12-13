package com.airportinfo.view.chart.chartGui;

import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.model.Airport;
import com.airportinfo.util.Translator;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.airport.AirportChartView;
import com.airportinfo.view.airport.AirportView;
import com.airportinfo.view.airport.AttributeStatisticCreator;
import com.airportinfo.view.chart.AbstractChartView;
import com.airportinfo.view.chart.HistogramView;
import com.airportinfo.view.chart.PieChartView;
import com.airportinfo.view.content.ContentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AirportChartContentView extends ContentView {
    private AirportController apCon;
    private JComboBox<String> wayCombo;
    private JComboBox<String> attributeCombo;
    private JComboBox<String> rangeCombo;
    private JButton acceptBtn;
    private JPanel chartComboPanel;
    private JPanel chartGUIPanel;
    private AirportView apView;
    private final String[] wayList = {"그래프선택", "막대그래프", "파이그래프"};
    private final String[] attributeList = {"선택", "지역", "국가"};

    private final String[][] rangeList =
            {
                    {"선택", "대양주", "북미", "아시아", "아프리카", "유럽", "중동", "중남미"},
                    { "선택", "UAE", "가나", "가봉", "가이아나", "감비아", "과들루프", "과테말라", "괌", "그라나다", "그리스", "기니", "기니비사우",
                            "나미비아", "나이지리아", "남아프리카", "네덜란드", "네덜란드령 카리브", "네팔" ,"노르웨이", "뉴질랜드", "뉴칼레도니아", "니제르", "니카라과",
                            "대만", "대한민국", "덴마크","도미니카공화국",  "독일",  "라트비아","러시아",  "레바논", "레위니옹",  "루마니아", "룩셈부르크","르완다","리투아니아",
                            "마다가스카르","마요트", "마카오",   "말라위","말레이시아","말리", "멕시코", "모나코",
                            "모로코",
                            "모리셔스",
                            "모리타니아",
                            "모잠비크",
                            "몬테네그로",
                            "몰도바",
                            "몰디브",
                            "몰타",
                            "몽고",
                            "미국",
                            "바베이도스",
                            "바하마",
                            "버마",
                            "버뮤다제도",
                            "베냉",
                            "베네수엘라"
                            ,"베트남"
                            ,"벨기에"
                            ,"벨로루시"
                            ,"보스니아 헤르체고비나"
                            ,"보츠와나"
                            ,"볼리비아"
                            ,"부룬디"
                            ,"부르키나 파소"
                            ,"부탄"
                            ,"북마리아나제도"
                            ,"북마케도니아"
                            ,"불가리아"
                            ,"브라질"
                            ,"사모아"
                            ,"사우디아라비아"
                            ,"상투메프린시페"
                            ,"생바르텔레미"
                            ,"생피에르미클롱"
                            ,"세네갈"
                            ,"세르비아"
                            ,"세이셸"
                            ,"세인트루시아"
                            ,"세인트마틴"
                            ,"세인트헬레나(영국령)"
                            ,"수단"
                            ,"수리남"
                            ,"스리랑카"
                            ,"스웨덴"
                            ,"스위스"
                            ,"스페인"
                            ,"슬로바키아"
                            ,"슬로베니아"
                            ,"시에라리온"
                            ,"신트마르턴"
                            ,"싱가포르"
                            ,"아루바"
                            ,"아르메니아"
                            ,"아르헨티나"
                            ,"아이슬란드"
                            ,"아일랜드"
                            ,"아제르바이잔"
                            ,"알바니아"
                            ,"알제리"
                            ,"앙골라"
                            ,"에스와티니"
                            ,"에스토니아"
                            ,"에위니옹"
                            ,"에콰도르"
                            ,"엘살바드로"
                            ,"영국"
                            ,"오만"
                            ,"오스트레일리아"
                            ,"오스트리아"
                            ,"온두라스"
                            ,"왈리스푸투나"
                            ,"요르단"
                            ,"우간다"
                            ,"우루과이"
                            ,"우즈베키스탄"
                            ,"우크라이나"
                            ,"이디오피아"
                            ,"이라크"
                            ,"이란"
                            ,"이스라엘"
                            ,"이집트"
                            ,"이탈리아"
                            ,"인도"
                            ,"인도네시아"
                            ,"일본"
                            ,"자메이카"
                            ,"잠비아"
                            ,"적도 기니"
                            ,"조지아"
                            ,"중국"
                            ,"중앙아프리카공화국"
                            ,"짐바브웨"
                            ,"체코 공화국"
                            ,"칠레"
                            ,"카메룬"
                            ,"카보베르데"
                            ,"카자흐스탄"
                            ,"카타르"
                            ,"캄보디아"
                            ,"캐나다"
                            ,"케냐"
                            ,"케이맨제도(영국)"
                            ,"코모로"
                            ,"코소보"
                            ,"코스타리카"
                            ,"코코스제도"
                            ,"코트디부아르"
                            ,"콜롬비아"
                            ,"콩고 민주공화국"
                            ,"쿠바"
                            ,"쿠웨이트"
                            ,"쿡 군도"
                            ,"퀴라소"
                            ,"크로아티아"
                            ,"키르기스스탄"
                            ,"키프러스"
                            ,"탄자니아"
                            ,"태국"
                            ,"터키"
                            ,"토고"
                            ,"통가"
                            ,"튀니지"
                            ,"트리니다드토바고"
                            ,"파나마"
                            ,"파라과이"
                            ,"파키스탄"
                            ,"페로 제도"
                            ,"페루"
                            ,"포르투갈"
                            ,"폴란드"
                            ,"푸에르토리코"
                            ,"프랑스"
                            ,"프랑스령기아나"
                            ,"프랑스령폴리네시아"
                            ,"피지"
                            ,"핀란드"
                            ,"필리핀"
                            ,"헝가리"
                            ,"홍콩"
                    }
            };

    private Airport selected;
    public AirportChartContentView(AirportFrame airportFrame) {
        super(airportFrame);
        apCon = airportFrame.getAirportController();
        initComponent();
        initChartComboPanel();

    }

    public void setComponent(AirportView paraApView) {
        apView = paraApView;
        chartGUIPanel.add("West", chartComboPanel);
        chartGUIPanel.add("Center", apView.getPanel());
    }
    public void initComponent() {
        wayCombo = new JComboBox<>(wayList);
        attributeCombo = new JComboBox<>(attributeList);
        rangeCombo = new JComboBox<>();
        acceptBtn = new JButton("적용");
        chartComboPanel = new JPanel();
        chartGUIPanel = new JPanel(new BorderLayout());
    }

    public void initChartComboPanel() {
        chartComboPanel.add(wayCombo);
        chartComboPanel.add(attributeCombo);
        for(String str : rangeList[0])
            rangeCombo.addItem(str);
        attributeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> jcb = (JComboBox)e.getSource();
                int index = jcb.getSelectedIndex();
                rangeCombo.removeAllItems();
                if(index == 1){
                    for(String str : rangeList[0])
                        rangeCombo.addItem(str);
                }
                else if(index == 2) {
                    for (String str : rangeList[1])
                        rangeCombo.addItem(str);
                }
            }
        });
        chartComboPanel.add(rangeCombo);
        chartComboPanel.add(acceptBtn);
    }

    @Override
    public JPanel getPanel() {
        return chartGUIPanel;
    }

    @Override
    public void load() {
        if (!isDisplaying())
            return;
        selected = apCon.getSelectedAirport();


    }
}
