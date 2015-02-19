package com.epam.eighty.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test utility class that makes easy work of testing default behavior of
 * getters and setters.
 */
public final class PropertyAsserter {

    /**
     * Attribute constant LOG of PropertyAsserter.
     */
    private static final Log LOG = LogFactory.getLog(PropertyAsserter.class);
    /**
     * Attribute constant TYPE_ARGUMENTS of PropertyAsserter.
     */
    @SuppressWarnings("rawtypes")
    private static final Map<Class, Object> TYPE_ARGUMENTS = new HashMap<Class, Object>();
    /**
     * Attribute constant DEFAULT_TYPE_ARGUMENTS of PropertyAsserter.
     */
    @SuppressWarnings("rawtypes")
    private static final Map<Class, Object> DEFAULT_TYPE_ARGUMENTS = Collections
            .unmodifiableMap(new HashMap<Class, Object>(TYPE_ARGUMENTS));
    /**
     * Attribute constant TEST_ARRAY_SIZE of PropertyAsserter.
     */
    private static final int TEST_ARRAY_SIZE = 10;

    static {
        TYPE_ARGUMENTS.put(Collection.class, new ArrayList<Object>(32));
        TYPE_ARGUMENTS.put(List.class, new ArrayList<Object>(32));
        TYPE_ARGUMENTS.put(Set.class, new HashSet<Object>(32));
        TYPE_ARGUMENTS.put(SortedSet.class, new TreeSet<Object>());
        TYPE_ARGUMENTS.put(Map.class, new HashMap<Object, Object>(32));
        TYPE_ARGUMENTS.put(SortedMap.class, new TreeMap<Object, Object>());
        TYPE_ARGUMENTS.put(Boolean.class, true);
        TYPE_ARGUMENTS.put(Boolean.TYPE, true);
        TYPE_ARGUMENTS.put(Character.class, 'Z');
        TYPE_ARGUMENTS.put(Character.TYPE, 'Z');
        TYPE_ARGUMENTS.put(Byte.class, (byte) 10);
        TYPE_ARGUMENTS.put(Byte.TYPE, (byte) 10);
        TYPE_ARGUMENTS.put(Short.class, (short) 10);
        TYPE_ARGUMENTS.put(Short.TYPE, (short) 10);
        TYPE_ARGUMENTS.put(Integer.class, 10);
        TYPE_ARGUMENTS.put(Integer.TYPE, 10);
        TYPE_ARGUMENTS.put(Long.class, 10L);
        TYPE_ARGUMENTS.put(Long.TYPE, 10L);
        TYPE_ARGUMENTS.put(Float.class, 3.14159F);
        TYPE_ARGUMENTS.put(Float.TYPE, 3.14159F);
        TYPE_ARGUMENTS.put(Double.class, 3.14159);
        TYPE_ARGUMENTS.put(Double.TYPE, 3.14159);
        TYPE_ARGUMENTS.put(java.sql.Date.class,
                new java.sql.Date(new Date().getTime()));
        TYPE_ARGUMENTS
                .put(Timestamp.class, new Timestamp(new Date().getTime()));
        TYPE_ARGUMENTS.put(Calendar.class, Calendar.getInstance());
    }

    /**
     * Default constructor.
     */
    private PropertyAsserter() {

    }

    /**
     * Helper method.
     *
     * @param type
     *            Class
     * @param defaultArgument
     *            Object
     */
    public static void registerTypeAndDefaultArgument(Class<?> type,
            Object defaultArgument) {
        TYPE_ARGUMENTS.put(type, defaultArgument);
    }

    /**
     * Helper method.
     *
     * @param type
     *            Class
     */
    public static void deregisterType(Class<?> type) {
        TYPE_ARGUMENTS.remove(type);
    }

    /**
     * Helper method.
     */
    public static void resetToDefaultTypes() {
        TYPE_ARGUMENTS.clear();
        TYPE_ARGUMENTS.putAll(DEFAULT_TYPE_ARGUMENTS);
    }

    /**
     * Helper method.
     *
     * @param type
     *            Class
     * @return Object
     */
    public static Object defaultArgumentForType(Class<?> type) {
        return TYPE_ARGUMENTS.get(type);
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     * @param property
     *            String
     */
    public static void assertBasicGetterSetterBehavior(Object target,
            String property) {
        assertBasicGetterSetterBehavior(target, property, null);
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     * @param property
     *            String
     * @param argument
     *            Object
     */
    public static void assertBasicGetterSetterBehavior(Object target,
            String property, Object argument) {
        try {
            final PropertyDescriptor descriptor = new PropertyDescriptor(
                    property, target.getClass());
            Object arg = argument;
            final Class<?> type = descriptor.getPropertyType();
            if (null == arg) {
                if (type.isArray()) {
                    arg = Array.newInstance(type.getComponentType(),
                            new int[] { TEST_ARRAY_SIZE });
                } else if (type.isEnum()) {
                    arg = type.getEnumConstants()[0];
                } else if (TYPE_ARGUMENTS.containsKey(type)) {
                    arg = TYPE_ARGUMENTS.get(type);
                } else {
                    arg = invokeDefaultConstructorEvenIfPrivate(type);
                }
            }

            final Method writeMethod = descriptor.getWriteMethod();
            final Method readMethod = descriptor.getReadMethod();

            writeMethod.invoke(target, arg);
            final Object propertyValue = readMethod.invoke(target);
            if (type.isPrimitive()) {
                assertEquals(property + " getter/setter failed test", arg,
                        propertyValue);
            } else {
                assertSame(property + " getter/setter failed test", arg,
                        propertyValue);
            }
        } catch (IntrospectionException e) {
            final String msg = "Error creating PropertyDescriptor for property ["
                    + property + "]. Do you have a getter and a setter?";
            LOG.error(msg, e);
            fail(msg);
        } catch (IllegalAccessException e) {
            final String msg = "Error accessing property. Are the getter and setter both accessible?";
            LOG.error(msg, e);
            fail(msg);
        } catch (InvocationTargetException e) {
            final String msg = "Error invoking method on target";
            LOG.error(msg, e);
            fail(msg);
        }
    }

    /**
     * Reflection method.
     *
     * @param type
     *            Class
     * @return Object
     */
    private static Object invokeDefaultConstructorEvenIfPrivate(Class<?> type) {
        try {
            final Constructor<?> ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not invoke default constructor on type " + type, ex);
        }
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     * @param properties
     *            Map<String, Object>
     */
    public static void assertBasicGetterSetterBehavior(Object target,
            Map<String, Object> properties) {
        final Set<Map.Entry<String, Object>> entries = properties.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            assertBasicGetterSetterBehavior(target, entry.getKey(),
                    entry.getValue());
        }
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     * @param propertyNames
     *            String...
     */
    public static void assertBasicGetterSetterBehavior(Object target,
            String... propertyNames) {
        final Map<String, Object> properties = new LinkedHashMap<String, Object>();
        for (String propertyName : propertyNames) {
            properties.put(propertyName, null);
        }
        assertBasicGetterSetterBehavior(target, properties);
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     */
    public static void assertBasicGetterSetterBehavior(Object target) {
        assertBasicGetterSetterBehaviorWithBlacklist(target);
    }

    /**
     * Assert method.
     *
     * @param target
     *            Object
     * @param propertyNames
     *            String...
     */
    public static void assertBasicGetterSetterBehaviorWithBlacklist(
            Object target, String... propertyNames) {
        final List<String> blacklist = Arrays.asList(propertyNames);
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(target
                    .getClass());
            final PropertyDescriptor[] descriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                if (null == descriptor.getWriteMethod()) {
                    continue;
                }
                if (!blacklist.contains(descriptor.getDisplayName())) {
                    assertBasicGetterSetterBehavior(target,
                            descriptor.getDisplayName());
                }
            }
        } catch (IntrospectionException e) {
            LOG.error(e);
            fail("Failed while introspecting target " + target.getClass());
        }
    }
}
