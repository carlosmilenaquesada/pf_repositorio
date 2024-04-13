package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;


import android.app.Application;

import com.example.tfg_carlosmilenaquesada.views.activities.MainActivity;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.UsersHttpGetter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskController {
    Application application;
    public FutureTaskController(Application application){
        this.application = application;
    }

    public boolean tableUsersFiller() {
        boolean done = false;
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Boolean call() throws Exception {
                Boolean done = false;
                MainActivity.getDbHelper().wipeTable(DbHelper.TABLE_USERS);
                new UsersHttpGetter(application).getUsersFromServer();
                done = true;
                return done;
            }

        });

        Executors.newSingleThreadExecutor().submit(futureTask);
        try {
            done = (Boolean) futureTask.get(5000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        return done;

    }
}
