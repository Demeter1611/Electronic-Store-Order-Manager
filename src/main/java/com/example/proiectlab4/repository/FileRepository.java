package com.example.proiectlab4.repository;



import com.example.proiectlab4.domain.Entity;
import com.example.proiectlab4.domain.IEntityFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileRepository<T extends Entity> extends Repository<T> {
    private String fileName;
    private IEntityFactory<T> entityFactory;

    public FileRepository(String fileName, IEntityFactory<T> entityFactory) {
        this.fileName = fileName;
        this.entityFactory = entityFactory;
        load();
    }

    private void save(){
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            for(T entity: entities){
                fileWriter.write(entity.toFile() + "\n");
            }
            fileWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void load(){
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    T entity = entityFactory.createEntity(line);
                    super.add(entity);
                }catch(Exception e){
                    break;
                }
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void commit(){
        save();
    }
}

