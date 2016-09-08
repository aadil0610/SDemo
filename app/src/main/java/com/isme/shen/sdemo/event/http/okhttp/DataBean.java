package com.isme.shen.sdemo.event.http.okhttp;

/**
 * Created by shen on 2016/8/31.
 */
public class DataBean {

    /*"error_code": 0,
	"reason": "Success",
	"result": {
		"data":{
			 "avoid" : "嫁娶.出行.入宅.开市.安门." ,
			 "animalsYear" : "猴" ,
			 "weekday" : "星期三" ,
			 "suit" : "祭祀.沐浴.理发.整手足甲.冠笄.解除.入殓.移柩.破土.启攒.安葬." ,
			 "lunarYear" : "丙申年" ,
			 "lunar" : "七月廿九" ,
			 "year-month" : "2016-8" ,
			 "date" : "2016-8-31"
		}
	}*/
    public String error_code;
    public String reason;
    public Result result;

    public class Result{

        private Data data;
        public class Data{
            public String avoid;
            public String animalsYear;
            public String weekday;
            public String suit;
            public String lunarYear;
            public String lunar;
            public String year_month;
            public String date;

            public Data(String avoid, String animalsYear, String weekday, String suit, String lunarYear, String lunar, String year_month, String date) {
                this.avoid = avoid;
                this.animalsYear = animalsYear;
                this.weekday = weekday;
                this.suit = suit;
                this.lunarYear = lunarYear;
                this.lunar = lunar;
                this.year_month = year_month;
                this.date = date;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "avoid='" + avoid + '\'' +
                        ", animalsYear='" + animalsYear + '\'' +
                        ", weekday='" + weekday + '\'' +
                        ", suit='" + suit + '\'' +
                        ", lunarYear='" + lunarYear + '\'' +
                        ", lunar='" + lunar + '\'' +
                        ", year_month='" + year_month + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }
        }

        public Result(Data data) {
            this.data = data;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public DataBean(String error_code, String reason, Result result) {
        this.error_code = error_code;
        this.reason = reason;
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
