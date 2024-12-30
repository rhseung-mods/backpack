package com.rhseung.backpack.util

import java.lang.reflect.Field
import java.lang.reflect.Method

object Utils {
    fun Boolean.toInt() = if (this) 1 else 0;

    @Throws(NoSuchFieldException::class)
    private fun getField(clazz: Class<*>, fieldName: String): Field {
        return try {
            clazz.getDeclaredField(fieldName);
        } catch (e: NoSuchFieldException) {
            val superClass = clazz.superclass;
            if (superClass == null) {
                throw e;
            } else {
                getField(superClass, fieldName);
            }
        }
    }

    fun <T> get(receiver: Any, propertyName: String): T {
        val clazz: Class<*> = receiver.javaClass

        var field: Field? = null
        try {
            field = getField(clazz, propertyName)
        } catch (e: NoSuchFieldException) {
            throw e
        }

        field.isAccessible = true

        return try {
            @Suppress("UNCHECKED_CAST")
            field[receiver] as T
        } catch (e: IllegalAccessException) {
            throw e
        } catch (e: ClassCastException) {
            throw e
        }
    }

    fun <T> Any.getProperty(propertyName: String): T {
        return get(this, propertyName);
    }

    @Throws(NoSuchMethodException::class)
    private fun getMethod(clazz: Class<*>, methodName: String, vararg parameterTypes: Class<*>): Method {
        val methods =
            clazz.declaredMethods.filter { it.name == methodName && it.parameterCount == parameterTypes.size };

        if (methods.isEmpty()) {
            val superClass = clazz.superclass;
            if (superClass == null)
                throw NoSuchMethodException();
            else
                return getMethod(superClass, methodName, *parameterTypes);
        }

        if (methods.size == 1)
            return methods[0];

        for (method in methods) {
            if (method.parameterTypes.contentEquals(parameterTypes))
                return method;
        }

        throw NoSuchMethodException();
    }

    fun <T> invoke(receiver: Any, methodName: String, vararg args: Any?): T {
        val clazz: Class<*> = receiver.javaClass

        // Determine the parameter types of the method
        val parameterTypes = args.map { it?.javaClass ?: Any::class.java }.toTypedArray()

        val method: Method
        try {
            method = getMethod(clazz, methodName, *parameterTypes)
        } catch (e: NoSuchMethodException) {
            throw e
        }

        method.isAccessible = true

        return try {
            @Suppress("UNCHECKED_CAST")
            method.invoke(receiver, *args) as T
        } catch (e: IllegalAccessException) {
            throw e
        } catch (e: ClassCastException) {
            throw e
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }

    fun <T> Any.invokeMethod(methodName: String, vararg args: Any?): T {
        return invoke(this, methodName, *args)
    }
}