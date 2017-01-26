FROM openjdk:8
VOLUME /tmp
ADD build/libs/poly-gateway-0.1.0-SNAPSHOT.jar unidev-poly-gateway.jar
RUN sh -c 'touch /unidev-poly-gateway.jar'
ENV JAVA_OPTS="-server -XX:+UseG1GC -Xmx128M -Dspring.profiles.active=production"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /unidev-poly-gateway.jar" ]