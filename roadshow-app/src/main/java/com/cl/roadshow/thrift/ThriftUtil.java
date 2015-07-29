package com.cl.roadshow.thrift;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Thrift前缀的对象和不含Thrift前缀的对象相互转换.
 * 参考：
 * http://blog.csdn.net/it___ladeng/article/details/7026524
 * http://www.cnblogs.com/jqyp/archive/2012/03/29/2423112.html
 * http://www.cnblogs.com/bingoidea/archive/2009/06/21/1507889.html
 * http://java.ccidnet.com/art/3539/20070924/1222147_1.html
 * http://blog.csdn.net/justinavril/article/details/2873664
 */
public class ThriftUtil {
    public static final Integer THRIFT_PORT = 9177;

    /**
     * Thrift生成的类的实例和项目原来的类的实例相关转换并赋值
     * 1.类的属性的名字必须完全相同
     * 2.当前支持的类型仅包括：byte,short,int,long,double,String,Date,List
     * 3.如果有Specified列，则此列为true才赋值，否则，不为NULL就赋值
     * @param sourceObject
     * @param targetClass
     * @param toThrift:true代表把JavaObject转换成ThriftObject，false代表把ThriftObject转换成JavaObject，ThriftObject中含有Specified列
     * @return
     */
    public static Object convert(Object sourceObject,Class<?> targetClass,Boolean toThrift)
    {
        if(sourceObject==null)
        {
            return null;
        }       
        //对于简单类型，不进行转换，直接返回
        if(sourceObject.getClass().getName().startsWith("java.lang"))
        {
            return sourceObject;
        }
        Class<?> sourceClass = sourceObject.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Object targetObject = null;
        try {
            targetObject = targetClass.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        };
        if(targetObject==null)
        {
            return null;
        }
        for(Field sourceField:sourceFields)
        {
            try {
                //转换时过滤掉Thrift框架自动生成的对象
                if(sourceField.getType().getName().startsWith("org.apache.thrift")
                        ||sourceField.getName().substring(0,2).equals("__")
                        ||("schemes,metaDataMap,serialVersionUID".indexOf(sourceField.getName())!=-1)
                        ||(sourceField.getName().indexOf("_Fields")!=-1)
                        ||(sourceField.getName().indexOf("Specified")!=-1)
                        )
                {
                    continue;
                }
                
                //处理以DotNet敏感字符命名的属性，比如operator
                String sourceFieldName = sourceField.getName();
                if(sourceFieldName.equals("operator"))
                {
                    sourceFieldName = "_operator";
                } else {
                    if(sourceFieldName.equals("_operator"))
                    {
                        sourceFieldName = "operator";
                    }
                }
                //找出目标对象中同名的属性
                Field targetField = targetClass.getDeclaredField(sourceFieldName);
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                String sourceFieldSimpleName = sourceField.getType().getSimpleName().toLowerCase().replace("integer", "int");
                String targetFieldSimpleName = targetField.getType().getSimpleName().toLowerCase().replace("integer", "int");
                //如果两个对象同名的属性的类型完全一致:Boolean,String,以及5种数字类型：byte,short,int,long,double，以及List
                if(targetFieldSimpleName.equals(sourceFieldSimpleName))
                {
                    //对于简单类型，直接赋值
                    if("boolean,string,byte,short,int,long,double".indexOf(sourceFieldSimpleName)!=-1)
                    {
                        Object o = sourceField.get(sourceObject);
                        if(o != null)
                        {
                            targetField.set(targetObject, o);
                            //处理Specified列，或者根据Specified列对数值对象赋NULL值
                            try
                            {
                                if(toThrift)
                                {
                                    Field targetSpecifiedField = targetClass.getDeclaredField(sourceFieldName+"Specified");
                                    if(targetSpecifiedField != null)
                                    {
                                        targetSpecifiedField.setAccessible(true);
                                        targetSpecifiedField.set(targetObject, true);
                                    }
                                } else {
                                    Field sourceSpecifiedField = sourceClass.getDeclaredField(sourceFieldName+"Specified");
                                    if(sourceSpecifiedField != null 
                                            && "B,S,B,I,L,D".indexOf(targetField.getType().getSimpleName().substring(0,1))!=-1
                                            )
                                    {
                                        sourceSpecifiedField.setAccessible(true);
                                        if(sourceSpecifiedField.getBoolean(sourceObject)==false)
                                        {
                                            targetField.set(targetObject, null);
                                        }
                                    }
                                }
                            } catch (NoSuchFieldException e) {
                                //吃掉NoSuchFieldException，达到效果：如果Specified列不存在，则所有的列都赋值
                            }
                        }
                        continue;
                    }
                    //对于List
                    if(sourceFieldSimpleName.equals("list"))
                    {
                        @SuppressWarnings("unchecked")
                        List<Object> sourceSubObjs = (ArrayList<Object>)sourceField.get(sourceObject);
                        @SuppressWarnings("unchecked")
                        List<Object> targetSubObjs = (ArrayList<Object>)targetField.get(targetObject);
                        //关键的地方，如果是List类型，得到其Generic的类型 
                        Type targetType = targetField.getGenericType();
                        //如果是泛型参数的类型 
                        if(targetType instanceof ParameterizedType) 
                        {
                            ParameterizedType pt = (ParameterizedType) targetType;
                            //得到泛型里的class类型对象。  
                            Class<?> c = (Class<?>)pt.getActualTypeArguments()[0]; 
                            if(sourceSubObjs!=null)
                            {
                                if(targetSubObjs==null)
                                {
                                    targetSubObjs = new ArrayList<Object>();
                                }
                                for(Object obj:sourceSubObjs)
                                {
                                    targetSubObjs.add(convert(obj,c,toThrift));
                                }
                                targetField.set(targetObject, targetSubObjs);
                            }                           
                        }
                        continue;                       
                    }                   
                }
                //转换成Thrift自动生成的类：Thrift没有日期类型，我们统一要求日期格式化成yyyy-MM-dd HH:mm:ss形式
                if(toThrift)
                {
                    if(sourceFieldSimpleName.equals("date")&&targetFieldSimpleName.equals("string"))
                    {
                        Date d = (Date)sourceField.get(sourceObject);
                        if(d!=null)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            targetField.set(targetObject,sdf.format(d));                            
                        }
                        continue;
                    }
                } else {
                    if(sourceFieldSimpleName.equals("string")&&targetFieldSimpleName.equals("date"))
                    {
                        String s = (String)sourceField.get(sourceObject);
                        if(s!=null)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            targetField.set(targetObject,sdf.parse(s));
                        }
                        continue;
                    }
                }
                //对于其他自定义对象             
                targetField.set(targetObject, convert(sourceField.get(sourceObject),targetField.getType(),toThrift));
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return targetObject;
    }
}