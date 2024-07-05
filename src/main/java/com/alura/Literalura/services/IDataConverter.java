package com.alura.Literalura.services;

public interface IDataConverter {
    <T> T obtenerDatos(String json, Class<T> clase);
}
