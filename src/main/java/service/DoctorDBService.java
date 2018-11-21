package service;

import dao.DoctorDao;
import entity.Doctor;

import java.util.HashSet;
import java.util.UUID;

import static dao.DaoCache.getCache;

public class DoctorDBService {

    private static DoctorDao doctorDB =
            (DoctorDao) getCache().getDao(DoctorDao.class.getName());

    public HashSet<Doctor> getAll(){
        return doctorDB.getAll();
    }

    public Doctor getById(UUID id){
        return doctorDB.getById(id);
    }
}
