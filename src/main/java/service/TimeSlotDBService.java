package service;

import dao.TimeSlotDao;

import java.util.UUID;

import static dao.DaoCache.getCache;

public class TimeSlotDBService {

    private static TimeSlotDao timeSlotDB =
            (TimeSlotDao) getCache().getDao(TimeSlotDao.class.getName());

    public void deleteSlotById(UUID id){
        timeSlotDB.deleteById(id);
    }
}
