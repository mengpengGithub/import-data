package com.mp.service;


import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.stream.Stream;

public class GenrateSqlService {


    public static void main(String[] args) throws IOException {

        String path ="C:\\Users\\RYX\\Desktop\\连连撞库-2019-08-05\\";
        //FileWriter writer = new FileWriter(path + "success_20190805.txt");
        FileWriter writer = new FileWriter(path + "fail_20190805.txt");
        System.out.println("开始生成文件");
        try (Stream<String> stream  = Files.lines(Paths.get(path + "201806290001968259-20190713_out_error.txt"))) {
            stream.forEachOrdered(it -> {
                try {
                    //
                    //writer.write(genrateSuccSql(it) + "\n");
                    writer.write(genrateFailSql(it) + "\n");
                } catch (Exception e) {
                    System.out.println(it);
                    e.printStackTrace();
                }
            });
        }finally {
            writer.flush();
            writer.close();
        }
        System.out.println("文件生成成功");

    }

    public static String genrateSuccSql(String line){
        if(StringUtils.isEmpty(line)){
            return null;
        }

        String[] message = line.split(",");
        String uuid = message[1];
        String cardno = message[2];
        String idno = message[3];
        String name = message[4];
        String phone = message[5];
        String contrno = message[6];

        String template = "INSERT INTO succ_lianpay190805 (AGREE_ID, PAY_ORG, CHANNEL, CONTRACT_NO, UUID" +
                ", ID_NO, ID_TYPE, BANK_CODE, CARD_NO, CARD_NAME" +
                ", CARD_PHONE, CARD_TYPE, CVV2, VAILD_DATE, CER_DATE" +
                ", CER_TIME, DAY_TRANS_AG_AMT_SUM, DAY_TRANS_AG_COUNT, DAY_INSUFFICIENT_BAL_AG_COUNT, DAY_FAIL_AG_COUNT" +
                ", MONTH_TRANS_AG_AMT_SUM, MONTH_TRANS_AG_COUNT, MAX_TRANS_AG_AMT_SUM, MAX_TRANS_AG_COUNT, CREATE_TIME" +
                ", LST_UPD_TIME, JPA_VERSION, PLATFORM)" +
                "VALUES (tp.tp_agreement_seq.nextval, ''RFXD'', ''LIANPAY'', ''{0}'', ''{1}''" +
                ", ''{2}'', ''I'', null, ''{3}'', ''{4}''" +
                ", ''{5}'', ''D'', NULL, NULL, to_date(''2019-08-05'', ''YYYY-MM-DD'')" +
                ", to_date(''2019-08-05'', ''YYYY-MM-DD''), 0, 0, 0, 0" +
                ", 0, 0, 0, 0, to_date(''2019-08-05'', ''YYYY-MM-DD'')" +
                ", to_date(''2019-08-05'', ''YYYY-MM-DD''), 0, ''RUIFUCREDIT'')";

        //0 协议号
        //1 UUID
        //2 身份证
        //3.卡号
        //4.姓名
        //5.手机号

        String sql = MessageFormat.format(template, contrno, uuid, idno, cardno, name, phone);
        return sql;

    }

    public static String genrateFailSql(String line){
        if(StringUtils.isEmpty(line)){
            return null;
        }

        String[] message = line.split(",");
        String uuid = message[1];
        String cardno = message[2];
        String idno = message[3];
        String name = message[4];
        String phone = message[5];
        String contrno = message[6];

        String sql = "INSERT INTO fail_lianpay190805 (card_no,id_no) values('"+cardno+"','"+idno+"')";

        //0 协议号
        //1 UUID
        //2 身份证
        //3.卡号
        //4.姓名
        //5.手机号
        return sql;

    }



}
