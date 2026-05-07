FROM eclipse-temurin:25-jdk-alpine-3.23 AS jre-builder

RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.compiler,java.desktop,java.instrument,java.logging,java.management,java.naming,java.net.http,java.prefs,java.rmi,java.scripting,java.security.jgss,java.security.sasl,java.sql,java.transaction.xa,java.xml,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /opt/java-minimal

FROM alpine:3.23.3

RUN apk add --no-cache libstdc++

ENV JAVA_HOME=/opt/java-minimal
ENV PATH=/opt/java-minimal/bin

WORKDIR /app
COPY --from=jre-builder /opt/java-minimal /opt/java-minimal

COPY build/libs/*.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
