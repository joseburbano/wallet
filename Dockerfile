# Usar una imagen base que contenga Java y sea compatible con Spring Boot
FROM adoptopenjdk/openjdk17:alpine-jre

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR generado por Maven a la imagen Docker
COPY target/wallet-2.0.1-rc-1.jar app.jar

# Exponer el puerto en el que la aplicación se ejecuta dentro del contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]
