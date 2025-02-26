package com.Wether.Temperature.Service;

import com.Wether.Temperature.Repository.TempRepo;
import com.Wether.Temperature.model.Temp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempService {

    @Autowired
    private TempRepo tempRepo;

    public List<Temp> SaveAll(List<Temp> temp){
        return tempRepo.saveAll(temp);
    }

    public Temp Save(Temp temp){
        try{
            return tempRepo.save(temp);
        }catch (Exception e){
            System.out.println("Not able to Save"+ e.getMessage());
            return null;
        }
        //return tempRepo.save(temp);
    }

    public List<Temp> getTempByTime(int time){
        return tempRepo.findByTime(time);
    }

    public void deleteAll(){
        tempRepo.deleteAll();
    }

    public void deleteById(String id){
        tempRepo.deleteById(id);
    }

}


