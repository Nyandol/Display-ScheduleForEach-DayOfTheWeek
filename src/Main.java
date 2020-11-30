import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Calendar;

class Main extends ListenerAdapter {

    public static JDA jda;

    public static String message_id = null;

    public static final String TOKEN = "Bot TOKEN";



    public static void main(String args[]) throws LoginException {

        jda = JDABuilder.createDefault(TOKEN)
                .addEventListeners(new Main())
                .build();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String s = e.getMessage().getContentRaw();
        //コマンド
        if (!e.getAuthor().equals(jda.getSelfUser())) {
            if(s.startsWith("/")){
                s = s.substring(1);
                if(s.contains("cal")) {
                    int pos = s.indexOf(" ");
                    if (pos == -1 && (s.equals("cal") || s.equals("calender"))) {   //コマンドが/calもしくは/calenderの場合
                        String str = getDayOfTheWeek();
                        e.getTextChannel().sendMessage(str).queue();
                    } else{
                        if(s.substring(pos + 1).equals("tomorrow")){                //コマンドの後ろにtomorrowがついていた場合
                            String str = getDayOfTheWeekTom();
                            e.getTextChannel().sendMessage(str).queue();
                        }else if(s.substring(pos + 1).equals("today")){             //コマンドの後ろにtodayがついていた場合
                            String str = getDayOfTheWeek();
                            e.getTextChannel().sendMessage(str).queue();
                        }else{                                                      //コマンドが間違っていた場合
                            String str = "-コマンドcal(calender)の使い方が変わりました\n" +
                                    "本日の予定をご覧になりたい場合\n" +
                                    ">/cal today\n" +
                                    ">/calender today\n" +
                                    "明日の予定をご覧になりたい場合\n" +
                                    ">/cal tomorrow\n" +
                                    ">/calender tomorrow";
                            e.getTextChannel().sendMessage(str).queue();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {            //起動時処理
        System.out.println("bot is now ready.");
    }

    static String su = "日曜日";
    static String mo = "月曜日";
    static String tu = "火曜日";
    static String we = "水曜日";
    static String th = "木曜日";
    static String fr = "金曜日";
    static String sa = "土曜日";

    public static String getDayOfTheWeek(){                         //本日の予定を読み込む

        Calendar cal = Calendar.getInstance();
        switch ( cal.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY: return su;
            case Calendar.MONDAY: return mo;
            case Calendar.TUESDAY: return tu;
            case Calendar.WEDNESDAY: return we;
            case Calendar.THURSDAY: return th;
            case Calendar.FRIDAY: return fr;
            case Calendar.SATURDAY: return sa;
        }
        return null;
    }

    public static String getDayOfTheWeekTom(){                          //明日の予定を読み込む

        Calendar cal = Calendar.getInstance();
        switch ( cal.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY: return mo;
            case Calendar.MONDAY: return tu;
            case Calendar.TUESDAY: return we;
            case Calendar.WEDNESDAY: return th;
            case Calendar.THURSDAY: return fr;
            case Calendar.FRIDAY: return sa;
            case Calendar.SATURDAY: return su;
        }
        return null;
    }
}
