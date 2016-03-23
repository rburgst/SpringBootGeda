# Example application for spring boot and GEDA

This project shows an example where mapping DTOs with GEDA caused CastExceptions (see Update below):

    java.lang.ClassCastException: com.example.UserEntity cannot be cast to com.example.UserEntity
        at com.example.UserEntityDataReaderMgetIdIDjavassist.read(UserEntityDataReaderMgetIdIDjavassist.java) ~[geda.core-3.1.0.jar:na]
        at com.inspiresoftware.lib.dto.geda.assembler.DataPipe.writeFromEntityToDto(DataPipe.java:144) ~[geda.core-3.1.0.jar:na]
        at com.inspiresoftware.lib.dto.geda.assembler.DTOtoEntityAssemblerImpl.assembleDto(DTOtoEntityAssemblerImpl.java:240) ~[geda.core-3.1.0.jar:na]
        at com.inspiresoftware.lib.dto.geda.assembler.DTOtoEntityAssemblerImpl.assembleDtos(DTOtoEntityAssemblerImpl.java:262) ~[geda.core-3.1.0.jar:na]
        at com.example.UserController.getAllUsers(DemoApplication.java:60) ~[main/:na]
        
## Update

The issue was fixed by specifying a specific classloader when creating the assembler in the constructor of 
`UserController`.
 
## How to run

    ./gradlew bootRun

Then open your web browser and point it to [http://localhost:8080/user](http://localhost:8080/user).
