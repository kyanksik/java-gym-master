package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }


    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfSession = trainingSession.getDayOfWeek();
        TimeOfDay timeOfSession = trainingSession.getTimeOfDay();


        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mapOfSession = timetable.get(dayOfSession);
        if (mapOfSession == null) {
            mapOfSession = new TreeMap<>();
            timetable.put(dayOfSession, mapOfSession);
        }

        ArrayList<TrainingSession> sessionInThisDay = mapOfSession.get(timeOfSession);

        if (sessionInThisDay == null) {
            sessionInThisDay = new ArrayList<TrainingSession>();
            mapOfSession.put(timeOfSession, sessionInThisDay);
        }

        sessionInThisDay.add(trainingSession);
        System.out.println("Тренировочная сессия успешно добавлена");
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.get(dayOfWeek) == null) {
            System.out.println("В этот день нет тренировок");
            return null;
        }
        return timetable.get(dayOfWeek);

    }

    public void printTrainingSessionForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> result = timetable.get(dayOfWeek);
        if (result == null) {
            System.out.println("В этот день нет тренировок");
            return;
        }
        for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public ArrayList<TrainingSession>  /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.get(dayOfWeek).get(timeOfDay) != null) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        }

        System.out.println("Тренировки в этот день в это время нет.");
        return null;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> noSorted = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> mapByDay = timetable.get(day);
            if (mapByDay == null) {
                continue;
            }

            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : mapByDay.entrySet()) {
                for (TrainingSession session : entry.getValue()) {
                    if (!noSorted.containsKey(session.getCoach())) {
                        noSorted.put(session.getCoach(), 0);
                    }
                    noSorted.put(session.getCoach(), noSorted.get(session.getCoach()) + 1);
                }

            }
        }
        List<CounterOfTrainings> counters = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : noSorted.entrySet()) {
            counters.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(counters);
        return counters;
    }

}
