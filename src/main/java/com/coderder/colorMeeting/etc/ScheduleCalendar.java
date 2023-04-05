package com.coderder.colorMeeting.etc;

import com.coderder.colorMeeting.dto.response.AvailableScheduleDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.PersonalSchedule;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.*;

@AllArgsConstructor
public class ScheduleCalendar {
    private List<List<Set<Member>>> calendar;
    private List<Member> teamMembers;

    public static ScheduleCalendar createCalendar(List<PersonalSchedule> teamSchedules, List<Member> members, int hourDividedBy) {
        List<List<Set<Member>>> calendar = new ArrayList<>();
        for(int i=0; i<7; i++){
            List<Set<Member>> list = new ArrayList<>();
            for(int j=0; j<24*hourDividedBy; j++){
                list.add(new HashSet<>());
            }
            calendar.add(list);
        }

        for(PersonalSchedule schedule : teamSchedules){
            List<Set<Member>> list = calendar.get(convertWeekday(schedule.getWeekday()));
            int startIndex = convertTime(schedule.getStartTime(), hourDividedBy);
            int endIndex = convertTime(schedule.getFinishTime(), hourDividedBy);
            for(int i=startIndex; i<=endIndex; i++){
                list.get(i).add(schedule.getMember());
            }
        }
        //members can change!!!
        return new ScheduleCalendar(calendar, members);
    }
    private static int convertWeekday(String weekday){
        if(weekday.equalsIgnoreCase("mon") || weekday.equalsIgnoreCase("monday")) return 0;
        else if(weekday.equalsIgnoreCase("tue") || weekday.equalsIgnoreCase("tuesday")) return 1;
        else if(weekday.equalsIgnoreCase("wed") || weekday.equalsIgnoreCase("wednesday")) return 2;
        else if(weekday.equalsIgnoreCase("thu") || weekday.equalsIgnoreCase("thursday")) return 3;
        else if(weekday.equalsIgnoreCase("fri") || weekday.equalsIgnoreCase("friday")) return 4;
        else if(weekday.equalsIgnoreCase("sat") || weekday.equalsIgnoreCase("saturday")) return 5;
        else if(weekday.equalsIgnoreCase("sun") || weekday.equalsIgnoreCase("sunday")) return 6;
        else return -1;
    }
    private static int convertTime(LocalTime time, int timeInterval){
        int min = time.getMinute();
        int hour = time.getHour();
        int blockIndex = hour*timeInterval + (min / (60/timeInterval));
        return blockIndex;
    }

    private static int convertTime(String time, int timeInterval){
        int min = Integer.parseInt(time.split(":")[1]);
        int hour = Integer.parseInt(time.split(":")[0]);
        int blockIndex = hour*timeInterval + (min / (60/timeInterval));
        return blockIndex;
    }

    public List<AvailableScheduleDto> getMostAvailableList(Integer spanTime, Integer hourDividedBy) {
        Integer maxNum = 0;
        Integer requiredBlockNum = spanTime / (60/hourDividedBy);
        //then, start,end time would be start only by "hourDividedBy"

        Map<Long, Integer> memberCount = new HashMap<>();
        for(Member member : teamMembers){
            memberCount.put()
        }

        for(List<Set<Member>> day : calendar){

        }
    }
}
