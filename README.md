# Examen Mercadolibre


## Problema
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men.
Te ha contratado a ti para que desarrolles un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.
Para eso te ha pedido crear un programa en donde recibirás como parámetro un array de Strings que representan cada fila de una
tabla de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representan cada base nitrogenada del ADN.

Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras
iguales , de forma oblicua, horizontal o vertical.

## Solución

Sabemos que un humano es mutante, si encontramos más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical.
Por lo tanto he decidido transformar el tipo de entrada String[] a una matriz NxN, para de esta forma recorrer item por item en busca de secuencias oblicuas, horizontales o verticales.

La Matriz NxN se definió como una matriz estados, la cual ayudará a determinar si un elemento ya hace parte de una secuencia de ADN mutante.

La evaluación de secuencias mutantes siempre se hace hacia adelante y en los siguientes sentidos:

- Horizontal
- Vertical
- Diagonal derecha
- Diagonal izquierda

<img src="/images/matrix.PNG"/>

Para evitar que por cada ítem recorrido se evalúen las tres posiciones siguientes con el fin determinar si la secuencia es mutante, se implementó un algoritmo con base en indicios o supuestos el cual funciona de la siguiente manera:

1. Tomar una posición (x,y) en la matrix
2. Evaluar el ultimo item de la secuencia dada la posición (x, y)


   <img src="/images/checking-sequence.PNG"/>


3. Si el valor en la posición inicial es igual al valor en la posición final de la secuencia, entonces procedemos con la evaluación de los ítems que se encuentren entre ambas posiciones, de lo contrario se descarta la secuencia y se continúa con la siguiente.

También se tuvo en cuenta que no es necesario recorrer toda la matriz dado que a partir de cierto punto los ítems dejan de ser aptos para pertenecer a una secuencia de DNA.
Esto se da cuando la posición del elemento es mayor o igual a la longitud de la matriz menos el tamaño de la secuencia.

   <img src="/images/matrix-hole.PNG"/>

## Desplegando la aplicación en ambiente local

Antes de desplegar la aplicación en nuestro ambiente local, debemos contar con los siguientes prerrequisitos:

- Instalar Docker

  https://docs.docker.com/engine/install/ubuntu/

  https://docs.docker.com/docker-for-windows/install/


- Instalar AWS CLI

  https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html


Cuando cumplamos los requisitos anteriormente mencionados podemos proceder con la configuración de nuestra aplicación en ambiente local, para esto debemos ejecutar los siguientes pasos:

#### 1. Crear imagen de docker de la aplicación

Para crear la imagen de docker de la aplicación es necesario  situarnos en la raíz del proyecto en donde se encuentra el archivo docker-compose.yml y ejecutar los siguientes comandos:

Primero construimos el artefacto con:

`mvn clean install`

Luego ejecutamos el archivo docker-compose.yml

`docker-compose pull dynamo`

<img src="/images/docker-compose-pull-dynamo.PNG"/>

`docker-compose build mutant`

<img src="/images/docker-compose-build.PNG"/>

Al final de este proceso deben existir tres imágenes en su ambiente local:
`mutant:latest`
`tomcat:9.0`
`amazon/dynamodb-local:latest`


<img src="/images/docker-images.PNG"/>

#### 2. Crear contenedor para la imagen de docker

Una vez tengamos creadas las imágenes de docker ejecutamos el siguiente comando para crear los contenedores:

`docker-compose up`

<img src="/images/docker-compose-up.PNG"/>

#### 3. Configurar Base de datos

Antes ejecutar nuestra aplicación debemos configurar la base de datos `DynamoDB`, para ello necesitamos crear un usuario de AWS con el siguiente comando:

`aws configure`

Ingresamos los siguientes datos, los cuales sirven para conectarnos localmente a la instancia de DynamoDB:

AWS Access Key ID  = fakeMyKeyId

AWS Secret Access Key  = fakeSecretAccessKey

<img src="/images/aws-configure.PNG"/>

Luego creamos una tabla en la base de datos:

`aws dynamodb create-table --table-name DNACatalog --attribute-definitions AttributeName=DNA,AttributeType=S --key-schema AttributeName=DNA,KeyType=HASH --endpoint-url http://localhost:8000 --billing-mode PAY_PER_REQUEST`

#### 4. Consumir el API de la aplicación

Si seguimos el paso a paso de la configuración del ambiente local, deberíamos estar listos para ejecutar nuestra aplicación.

Para validar si efectivamente tenemos corriendo la aplicación solo es necesario consumir el API como se muestra a continuación

`curl --location --request GET 'http://localhost:8080/mercadolibre/ping'`

<img src="/images/curl-ping.PNG"/>

## Desplegando la aplicación en AWS
Para desplegar la aplicación en la nube de AWS hacemos uso de los siguientes servicios de AWS:

- ECR
- ECS
- DynamoDB
- VPC
- CloudWatch
- IAM
- EC2

#### 1. Configurar repositorio de imágenes en ECR

Lo primero que debemos hacer es publicar nuestra imagen de docker en AWS, para ello utilizamos el servicio de ECR.

<img src="/images/push-ecr.PNG"/>

<img src="/images/ecr.PNG"/>

#### 2. Configurar el servicio de DynamoDB

Para configurar nuestra base de datos nos dirigimos al servicio de AWS DynamoDB y solo tenemos que crear la tabla en donde almacenaremos las secuencias de DNA.

<img src="/images/dynamo.PNG"/>

#### 3. Configurar Task Definition del servicio

Con la imagen de docker en ECR y la tabla en DynamoDB tenemos todo listo para crear la plantilla del servicio con la ayuda de ECS.

<img src="/images/task-definition.PNG"/>

#### 4. Configurar el cluster y el servicio

Procedemos con la creación del cluster en el servicio de ECS con las especificaciones que deseamos, para este ejercicio he decidido usar una máquina t2.micro.

Cuando se crea el cluster automáticamente se aprovisiona el Launch Configuration, el AutoScaling group y las instancias de EC2.

<img src="/images/cluster.PNG"/>

Con el cluster ya creado continuamos con la creación del servicio con base en la task definition que creamos anteriormente.

<img src="/images/service.PNG"/>

#### 5. Configurar balanceador de aplicación
Por último creamos un balanceador de aplicación que este expuesto a internet y lo asociamos a nuestro servicio mediante un Target Group.

<img src="/images/alb.PNG"/>


## Swagger

Ambiente local
http://localhost:8080/mercadolibre/swagger-ui/

Nube AWS
http://ALB-1821320394.us-east-1.elb.amazonaws.com/mercadolibre/swagger-ui/


## Cobertura de pruebas unitarias
<img src="/images/jacoco.PNG"/>

## Pruebas de carga
A continuación se muestra los resultados de las pruebas de carga a cada servicio del API:

Servicio `/mutant`
<img src="/images/mutant-load-test.PNG"/>

Servicio `/stats`
<img src="/images/stats-load-test.PNG"/>

Para acceder a la configuración de las pruebas de carga ingrese [aquí](./mutant.jmx).


