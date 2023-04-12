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
            int endIndex = convertTime(schedule.getFinishTime().minusMinutes(1), hourDividedBy);
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

    private String convertToWeekday(int weekday){
        if(weekday == 0) return "mon";
        else if(weekday == 1) return "tue";
        else if(weekday == 2) return "wed";
        else if(weekday == 3) return "thu";
        else if(weekday == 4) return "fri";
        else if(weekday == 5) return "sat";
        else if(weekday == 6) return "sun";
        else return "";
    }
    private LocalTime convertToTime(int timeBlock, int timeInterval){
        return LocalTime.of(timeBlock/timeInterval
                , (timeBlock%timeInterval)*(60/timeInterval));
    }


    public List<AvailableScheduleDto> getMostAvailableList(Integer spanTime, Integer hourDividedBy) {
        Integer maxNum = 0;
        Integer requiredBlockNum = spanTime / (60/hourDividedBy);
        System.out.println(requiredBlockNum);
        //then, start,end time would be start only by "hourDividedBy"

        //count continuous blocks(value) per member(key)
        Map<Member, Integer> memberCounter = new HashMap<>();

        initializeCounter(memberCounter, teamMembers);
        List<AvailableScheduleDto> availableScheduleDtoList = new ArrayList<>();
        for(int i=0; i< calendar.size(); i++){
            List<Set<Member>> day = calendar.get(i);
            for(int blockIndex=0; blockIndex<day.size(); blockIndex++){
                Set<Member> oneBlock = day.get(blockIndex);
                setCounter(memberCounter, oneBlock, requiredBlockNum);

                Set<Member> availableMems = new HashSet<>();
                for(Member member : memberCounter.keySet()){
                    if(memberCounter.get(member) == requiredBlockNum) availableMems.add(member);
                }

                if(maxNum < availableMems.size()){
                    maxNum = availableMems.size();
                    availableScheduleDtoList.clear();
                    String start = convertBlockToTime(blockIndex-requiredBlockNum+1, hourDividedBy);
                    String end = convertBlockToTime(blockIndex, hourDividedBy);
                    insertAvailableSchedule(availableScheduleDtoList, availableMems, start, end, convertToWeekday(i));

                }else if(maxNum == availableMems.size()){
                    String start = convertBlockToTime(blockIndex-requiredBlockNum+1, hourDividedBy);
                    String end = convertBlockToTime(blockIndex+1, hourDividedBy);
                    insertAvailableSchedule(availableScheduleDtoList, availableMems, start, end, convertToWeekday(i));
                }
            }
        }
        return availableScheduleDtoList;
    }

    private String convertBlockToTime(Integer blockIndex, Integer hourDividedBy) {
        int hour = blockIndex / hourDividedBy;
        int minute = (blockIndex % hourDividedBy) * (60/hourDividedBy);

        String time = String.format("%02d",hour)+":"+String.format("%02d",minute);
        return time;
    }

    private void setCounter(Map<Member, Integer> memberCounter, Set<Member> oneBlock, Integer requiredBlockNum) {
        for(Member mem : memberCounter.keySet()){
            if(oneBlock.contains(mem)) memberCounter.put(mem, 0);
            else {
                Integer continuousCount = memberCounter.get(mem) == requiredBlockNum ?
                        memberCounter.get(mem) : memberCounter.get(mem)+1;
                memberCounter.put(mem, continuousCount);
            }
        }
    }

    private void insertAvailableSchedule(List<AvailableScheduleDto> availableScheduleDtoList,
                                         Set<Member> availableMems,
                                         String start,
                                         String end,
                                         String weekday) {
        List<Member> templist = new ArrayList<>();
        for(Member mem : availableMems){
            templist.add(mem);
        }
        AvailableScheduleDto dto = AvailableScheduleDto.builder()
                .availableMember(templist)
                .start(weekday+"+"+start)
                .end(weekday+"+"+end)
                .build();
        availableScheduleDtoList.add(dto);
    }

    private void initializeCounter(Map<Member, Integer> memberCounter, List<Member> teamMembers) {
        for(Member member : teamMembers){
            memberCounter.put(member, 0);
        }
    }
}
