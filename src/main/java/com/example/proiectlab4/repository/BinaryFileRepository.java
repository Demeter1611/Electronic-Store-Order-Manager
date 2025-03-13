package com.example.proiectlab4.repository;



import com.example.proiectlab4.domain.Entity;

import java.io.*;

public class BinaryFileRepository<T extends Entity> extends Repository<T> {
    private String fileName;
    public BinaryFileRepository(String fileName) {
        this.fileName = fileName;
        load();
    }

    private void load(){
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(true) {
                try {
                    T entity = (T) ois.readObject();
                    super.add(entity);
                }
                catch(EOFException e){
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void save(){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(T entity: entities){
                oos.writeObject(entity);
            }
            oos.close();
            fos.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void commit(){
        save();
    }

}
