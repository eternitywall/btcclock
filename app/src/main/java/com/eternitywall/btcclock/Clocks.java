package com.eternitywall.btcclock;

import android.content.Context;

import com.eternitywall.btcclock.clocks.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class Clocks {

    private static Clock[] clocks = new Clock[]{
            new BitcoinClock(),
            new BitcoinGithubClock(),
            new StandardClock(),
            new NasaClock(),
            new TimestampClock(),
            new SpaceXClock()
    };

    public static Clock[] get(){
        return clocks;
    }

    // parse from package
    public static String[] getClassesOfPackage(Context context, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            String packageCodePath = context.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String className = iter.nextElement();
                if (className.contains(packageName) && !className.contains("$")) {
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes.toArray(new String[classes.size()]);
    }

    public static Clock get(int id){
        if (clocks == null){
            clocks = get();
        }
        for(Clock clock : clocks){
            if(clock.id == id){
                return clock;
            }
        }
        return null;
    }
}
