package com.egc.bot.commands;

import com.egc.bot.commands.interfaces.ICommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * Basic Ping command
 */
public class closures implements ICommand {

    public void run(SlashCommandInteraction ctx) throws IOException {
        ctx.deferReply().queue();
        Calendar cal = Calendar.getInstance();
        String date = new SimpleDateFormat("MMMM dd, yyyy").format(cal.getTime());
        JSONArray jsonArray  = new JSONArray(IOUtils.toString(new URL("https://starbase.nerdpg.live/api/json/roadClosures"), Charset.forName("UTF-8")));
        StringBuilder builder = new StringBuilder();
        builder.append("# Starbase Road Closures").append('\n');
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(date.equals(jsonObject.getString("date"))){
                if(Objects.equals(jsonObject.getString("time"), "12:00 am to 2:00 pm")){
                    builder.append("**").append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time")+"     LAUNCH CLOSURE**\n");
                }else if(Objects.equals(jsonObject.getString("time"), "5:00 am to 5:00 pm")){
                    builder.append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time") + "     WDR CLOSURE\n");
                }else{
                    builder.append("**").append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time")+"**\n");

                }
            }else {
                if (Objects.equals(jsonObject.getString("time"), "12:00 am to 2:00 pm")) {
                    builder.append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time") + "     LAUNCH CLOSURE\n");

                }else if(Objects.equals(jsonObject.getString("time"), "5:00 am to 5:00 pm")){
                    builder.append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time") + "     WDR CLOSURE\n");

                }else{
                    builder.append(jsonObject.getString("type")).append(":").append(jsonObject.getString("date")).append(":").append(jsonObject.getString("time")+"\n");
                }
            }

        }
        System.out.println(builder);
        ctx.getHook().sendMessage(builder.toString()).queue();
    }
}
