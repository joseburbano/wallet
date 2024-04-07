# Usa una imagen base que contenga Java 17 y sea compatible con Spring Boot
FROM openjdk

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR construido desde el directorio target de tu proyecto a la imagen
COPY target/*.jar app.jar

# Expone el puerto en el que se ejecuta tu aplicación Spring Boot
EXPOSE 8080

# Define el comando para ejecutar tu aplicación Spring Boot cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]

