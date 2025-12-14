package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());


        //Проверить, что за вторник не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));


    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        timetable.printTrainingSessionForDay(DayOfWeek.MONDAY);
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        //Проверить, что за  четверг вернулись 2 занятия
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        System.out.println(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());


        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));


    }


    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();
        Group group1 = new Group("Гимнастика", Age.CHILD, 60);
        Group group2 = new Group("ММА", Age.ADULT, 90);
        Group group3 = new Group("Фитнес", Age.ADULT, 45);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Бобров", "Василий", "Иванович");
        Coach coach3 = new Coach("Афанасьев", "Егор", "Павлович");
        TrainingSession gimnastikaSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mmaSession = new TrainingSession(group2, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession fitnessSession = new TrainingSession(group3, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession fitnessSession2 = new TrainingSession(group3, coach3,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession fitnessSession3 = new TrainingSession(group3, coach3,
                DayOfWeek.SATURDAY, new TimeOfDay(18, 0));
        TrainingSession mmaSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(13, 0));

        // 1. Проверяем пустой результат до добавления занятий
        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        assertEquals(0, counts.size());

        // Добавляем 3 занятия (1 у coach1, 2 у coach2)
        timetable.addNewTrainingSession(gimnastikaSession);
        timetable.addNewTrainingSession(mmaSession);
        timetable.addNewTrainingSession(mmaSession2);

        counts = timetable.getCountByCoaches();
        assertEquals(2, counts.size());  // 2 тренера с занятиями


        // Добавляем 3 занятия для coach3
        timetable.addNewTrainingSession(fitnessSession);
        timetable.addNewTrainingSession(fitnessSession2);
        timetable.addNewTrainingSession(fitnessSession3);

        System.out.println(timetable.getCountByCoaches());

        counts = timetable.getCountByCoaches();
        assertEquals(3, counts.size());  // Теперь 3 тренера

        // Проверяем сортировку по убыванию (coach3: 3, coach2: 2, coach1: 1)
        assertEquals(coach3, counts.get(0).getCoach());
        assertEquals(3, counts.get(0).getCountOfTrainings());

        assertEquals(coach2, counts.get(1).getCoach());
        assertEquals(2, counts.get(1).getCountOfTrainings());

        assertEquals(coach1, counts.get(2).getCoach());
        assertEquals(1, counts.get(2).getCountOfTrainings());
    }

}
