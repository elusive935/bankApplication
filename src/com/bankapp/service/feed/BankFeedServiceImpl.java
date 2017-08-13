package com.bankapp.service.feed;

import com.bankapp.commandInterface.BankCommander;
import com.bankapp.model.Account;
import com.bankapp.model.Client;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BankFeedServiceImpl implements BankFeedService {
    @Override
    public void loadFeed(String folder) {
        File f = new File(folder);
        File[] files = f.listFiles(p -> p.getName().endsWith(".feed"));
        if (files != null) {
            for (File file : files) {
                loadFeed(file);
            }
        }
    }

    @Override
    public void loadFeed(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                Map<String, String> map = parseLine(reader.readLine());
                BankCommander.currentBank.parseFeed(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseLine(String line) {
        Map<String, String> result = new HashMap<>();
        String[] properties = line.split(";");
        for (String property : properties) {
            String[] values = property.split("=");
            result.put(values[0], values[1]);
        }
        return result;
    }

    @Override
    public void saveFeed(String file) throws IllegalAccessException, InvocationTargetException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feeds/" + file))) {
            for (Client client : BankCommander.currentBank.getClients()) {
                String clientInfo = collectFeedInfo(client, client.getClass()).delete(0, 1).toString();

                for (Account account : client.getAccounts()) {
                    StringBuilder accountInfo = new StringBuilder(clientInfo);
                    accountInfo.append(collectFeedInfo(account, account.getClass()));
                    writer.write(accountInfo.append("\n").toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder collectFeedInfo(Object object, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        StringBuilder builder = new StringBuilder();

        appendAllFieldsValues(builder, object, clazz);
        appendAllMethodsValues(builder, object, clazz);
        appendAllFromSuperclass(builder, object, clazz);
        return builder;
    }

    private StringBuilder appendAllFieldsValues(StringBuilder builder, Object object, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Feed.class) && !"accounts".equals(field.getName())) {
                field.setAccessible(true);

                //Iterate recursively through collection field
                if (Collection.class.isAssignableFrom(field.getType())) {
                    for (Object obj : (Collection) field.get(object)) {
                        builder.append(collectFeedInfo(obj, obj.getClass()));
                    }
                    //collection should not been processed twice in Object field case
                    continue;
                }

                //Object field, not String
                if (!field.getType().isPrimitive() && !String.class.equals(field.getType())) {
                    builder.append(collectFeedInfo(field.get(object), field.getType()));
                }

                //Primitive field or String
                Object value = field.get(object);
                String name = field.getName();
                appendFieldOrMethodValue(builder, field, name, value);
            }
        }
        return builder;
    }

    private StringBuilder appendAllMethodsValues(StringBuilder builder, Object object, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Feed.class)) {
                Object value = method.invoke(object);
                String name = method.getName().substring(3).toLowerCase();
                appendFieldOrMethodValue(builder, method, name, value);
            }
        }
        return builder;
    }

    private StringBuilder appendAllFromSuperclass(StringBuilder builder, Object object, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        if (clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass())) {
            builder.append(collectFeedInfo(object, clazz.getSuperclass()));
        }
        return builder;
    }

    /*
    * @param builder        object to collect data
    * @param fieldOrMethod  field or method object
    * @param name           name of the field or the method
    * @param value          value of the field or returned value from the method
    *
    * if value != null appends a string like ";name=value"
    * if annotation Feed on the field/method has parameter "name" then annotated name will be preferred
    * */
    private void appendFieldOrMethodValue(StringBuilder builder, Object fieldOrMethod, String name, Object value){
        if (value == null) {
            return;
        }

        Feed annotation;
        if (fieldOrMethod instanceof Field){
            annotation = ((Field) fieldOrMethod).getAnnotation(Feed.class);
        } else if (fieldOrMethod instanceof Method){
            annotation = ((Method) fieldOrMethod).getAnnotation(Feed.class);
        } else {
            return;
        }

        String annotatedName = annotation.value();
        if (annotatedName.isEmpty()) {
            annotatedName = name;
        }

        builder.append(";")
                .append(annotatedName)
                .append("=")
                .append(value);
    }

}
