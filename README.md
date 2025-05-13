# Backend del portal web SID.


En este archivo encontraran todo lo relacionado a como se maneja lo básico
del backend realizado en java 21 SpringBoot.

## ¿Cómo instalar? 

Debemos instalar java 21 o superior en nuestro computador,
 recomendado usar java 21. Podemos verificarlo con: 

```bash
  java --version
```
Luego de eso, y teniendo git instalado y configurado en
nuestro computador clonamos el repositorio: 

``` bash
  git clone https://github.com/GrupoSemanadeingenioSID/SID_backend.git
```

Despues encontraremos una carpeta donde esta toda la organización
del proyecto, abrimos con nuestro IDE o editor de texto, instalamos
las dependencias de maven.

---

Este proyecto usa PostgreSQL que será configurado en el apartado de
variables de entorno de su IDE de prefrencia, recuerde NO usar directamente en 
las porpiedades, toca dejarlas como se enceutran ya que son parte de
las configuraciones del proyecto. 

En el apartado de testing es importante usar las siguientes credenciales: 

**url**:  jdbc:postgresql://localhost:5432/sid_portalweb_test
\
**user**: user
\
**password**: 1234

---
Una vez tenemos configurado el PostgreSQL y Java podemos optar
por descargar Maven desde : [maven](https://maven-apache-org.translate.goog/download.cgi?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=tc)
.
\
Aquí para correr el programa podemos usar el comando: 
```bash
 mvn spring-boot:run -DskipTests \
  -Dspring-boot.run.jvmArguments="
    -Dspring.datasource.url=jdbc:postgresql://localhost:5432/sid_portalweb
    -Dspring.datasource.username=_tu_usuario_
    -Dspring.datasource.password=_tu_contraseña_
  "
```
O sencillamente usamos el que nos da el IDE o el editor de texto.

Una vez alli podemos ir al navegador y ver el puerto 8080 y usar: 

http://localhost:8080/actuator/health

--- 
## Apartado de commits
El apartado de commits del proyecto podran ser tanto en ingles como en español.
Preferiblemente en español para un mayor entendimiento del equipo.
Su estructura será: 

```bash
 *que_tocaste*: Mensaje de lo que hiciste | Tu nombre
```
Por ejemplo: 

```bash
 Entidad Usuario: Arregle el problema n+1 que probocaba la query  | Henry Ricaurte Mora
```

El mensaje siempre empezará con un verbo.

